package janggi.persistence;

import janggi.application.GameRepository;
import janggi.application.dto.GameSnapshot;
import janggi.application.dto.GameSummary;
import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {

    private JdbcConnectionManager connectionManager;

    public JdbcGameRepository(JdbcConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<GameSummary> findAll() {
        try (
                Connection connection = connectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, current_turn, finished FROM games ORDER BY id"
                );
                ResultSet resultSet = statement.executeQuery()
        ) {
            return toGameSummaries(resultSet);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<GameSnapshot> findById(Long gameId) {
        try (
                Connection connection = connectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, current_turn, finished, winner FROM games WHERE id = ?"
                )
        ) {
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(toGameSnapshot(connection, resultSet));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void save(GameSnapshot gameSnapshot) {
        try (Connection connection = connectionManager.getConnection()) {
            Long gameId = insertGame(connection, gameSnapshot);
            insertPieces(connection, gameId, gameSnapshot.positions());
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void update(GameSnapshot gameSnapshot) {

    }

    private Long insertGame(Connection connection, GameSnapshot gameSnapshot) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO games(current_turn, finished, winner) VALUES (?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, gameSnapshot.currentTurn().name());
            statement.setBoolean(2, gameSnapshot.finished());
            statement.setString(3, winnerName(gameSnapshot.winner()));
            statement.executeUpdate();
            return generatedId(statement);
        }
    }

    private Long generatedId(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getLong(1);
    }

    private void insertPieces(
            Connection connection,
            Long gameId,
            List<PositionInfo> positions
    ) throws SQLException {
        for (PositionInfo positionInfo : positions) {
            insertPiece(connection, gameId, positionInfo);
        }
    }

    private void insertPiece(
            Connection connection,
            Long gameId,
            PositionInfo position
    ) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO game_pieces(game_id, team, piece_type, x_value, y_value) " +
                                "VALUES (?, ?, ?, ?, ?)"
                )
        ) {
            statement.setLong(1, gameId);
            statement.setString(2, teamName(position));
            statement.setString(3, position.piece().getType().name());
            statement.setInt(4, position.point().getX());
            statement.setInt(5, position.point().getY());
            statement.executeUpdate();
        }
    }

    private String teamName(PositionInfo position) {
        if (position.piece().isSameTeam(Team.HAN)) {
            return Team.HAN.name();
        }
        return Team.CHO.name();
    }

    private String winnerName(Team winner) {
        if (winner == null) {
            return null;
        }
        return winner.name();
    }

    private Team winner(String winner) {
        if (winner == null) {
            return null;
        }
        return Team.valueOf(winner);
    }

    private List<GameSummary> toGameSummaries(ResultSet resultSet) throws SQLException {
        List<GameSummary> gameSummaries = new ArrayList<>();
        while (resultSet.next()) {
            gameSummaries.add(new GameSummary(
                    resultSet.getLong("id"),
                    resultSet.getBoolean("finished")
            ));
        }
        return gameSummaries;
    }

    private List<PositionInfo> findPositions(
            Connection connection,
            Long gameId
    ) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT team, piece_type, x_value, y_value FROM game_pieces WHERE game_id = ? ORDER BY id"
                )
        ) {
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            return toPositions(resultSet);
        }
    }

    private List<PositionInfo> toPositions(ResultSet resultSet) throws SQLException {
        List<PositionInfo> positions = new ArrayList<>();
        while (resultSet.next()) {
            positions.add(PositionInfo.from(
                    Team.valueOf(resultSet.getString("team")),
                    resultSet.getString("piece_type"),
                    resultSet.getInt("x_value"),
                    resultSet.getInt("y_value")
            ));
        }
        return positions;
    }

    private GameSnapshot toGameSnapshot(
            Connection connection,
            ResultSet resultSet
    ) throws SQLException {
        Long gameId = resultSet.getLong("id");
        return new GameSnapshot(
                gameId,
                Team.valueOf(resultSet.getString("current_turn")),
                resultSet.getBoolean("finished"),
                winner(resultSet.getString("winner")),
                findPositions(connection, gameId)
        );
    }
}
