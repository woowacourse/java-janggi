package repository;

import domain.board.Board;
import domain.board.Position;
import domain.game.JanggiGame;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.state.ChoPlayingState;
import domain.state.FinishedState;
import domain.state.GameState;
import domain.state.HanPlayingState;
import dto.JanggiGameDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import javax.sql.DataSource;

public class JdbcJanggiGameRepository implements JanggiGameRepository {

    private final DataSource dataSource;

    public JdbcJanggiGameRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long save(JanggiGame janggiGame) {
        String sql = "INSERT INTO game (current_team, winner) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            return saveCurrentGame(connection, preparedStatement, janggiGame);
        } catch (SQLException e) {
            throw new IllegalStateException("게임을 저장하는 중 오류가 발생했습니다.", e);
        }
    }


    private Long saveCurrentGame(Connection connection, PreparedStatement preparedStatement, JanggiGame janggiGame)
            throws SQLException {
        preparedStatement.setString(1, janggiGame.getCurrentTeam().name());
        preparedStatement.setString(2, getWinner(janggiGame));
        preparedStatement.executeUpdate();

        Long gameId = generatedId(preparedStatement);
        savePieces(connection, gameId, janggiGame);
        return gameId;
    }

    private String getWinner(JanggiGame janggiGame) {
        if (janggiGame.isFinished()) {
            return janggiGame.getWinnerTeam().name();
        }
        return null;
    }

    private Long generatedId(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new SQLException("ID 생성에 실패했습니다.");
        }
    }

    private void savePieces(Connection connection, Long gameId, JanggiGame janggiGame) throws SQLException {
        String sql = "INSERT INTO piece (game_id, piece_type, team, position_column, position_row) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            executeBatchInsert(preparedStatement, gameId, janggiGame);
        }
    }

    private void executeBatchInsert(PreparedStatement preparedStatement, Long gameId, JanggiGame janggiGame)
            throws SQLException {
        for (Entry<Position, Piece> entry : janggiGame.getPieces().entrySet()) {
            setPieceStatement(preparedStatement, gameId, entry.getKey(), entry.getValue());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }

    private void setPieceStatement(PreparedStatement preparedStatement, Long gameId, Position position, Piece piece)
            throws SQLException {
        preparedStatement.setLong(1, gameId);
        preparedStatement.setString(2, piece.getPieceType().toString());
        preparedStatement.setString(3, piece.getTeam().toString());
        preparedStatement.setInt(4, position.column());
        preparedStatement.setInt(5, position.row());
    }

    @Override
    public List<JanggiGameDto> findAll() {
        String sql = "SELECT id, current_team, winner FROM game ORDER BY id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mapToDtos(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException("게임 목록을 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    private List<JanggiGameDto> mapToDtos(ResultSet resultSet) throws SQLException {
        List<JanggiGameDto> games = new ArrayList<>();
        while (resultSet.next()) {
            games.add(new JanggiGameDto(
                    resultSet.getLong("id"),
                    resultSet.getString("current_team"),
                    resultSet.getString("winner")
            ));
        }
        return games;
    }

    @Override
    public Optional<JanggiGame> findById(Long gameId) {
        try (Connection connection = dataSource.getConnection()) {
            return findGameById(connection, gameId);
        } catch (SQLException e) {
            throw new IllegalStateException("게임을 불러오는 중 오류가 발생했습니다. gameId=" + gameId, e);
        }
    }

    private Optional<JanggiGame> findGameById(Connection connection, Long gameId) throws SQLException {
        Optional<GameState> gameState = findGameState(connection, gameId);
        if (gameState.isEmpty()) {
            return Optional.empty();
        }

        Map<Position, Piece> pieces = findPieces(connection, gameId);
        return Optional.of(JanggiGame.of(Board.init(pieces), gameState.get()));
    }

    @Override
    public void update(Long gameId, JanggiGame janggiGame) {
        try (Connection connection = dataSource.getConnection()) {
            updateInTransaction(connection, gameId, janggiGame);
        } catch (SQLException e) {
            throw new IllegalStateException("게임을 업데이트하는 중 오류가 발생했습니다. gameId=" + gameId, e);
        }
    }


    private void updateInTransaction(Connection connection, Long gameId, JanggiGame janggiGame) throws SQLException {
        connection.setAutoCommit(false);
        try {
            updateGameInfo(connection, gameId, janggiGame);
            deletePieces(connection, gameId);
            savePieces(connection, gameId, janggiGame);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private void updateGameInfo(Connection connection, Long gameId, JanggiGame janggiGame) throws SQLException {
        String sql = "UPDATE game SET current_team = ?, winner = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, janggiGame.getCurrentTeam().name());
            preparedStatement.setString(2, getWinner(janggiGame));
            preparedStatement.setLong(3, gameId);
            preparedStatement.executeUpdate();
        }
    }

    private void deletePieces(Connection connection, Long gameId) throws SQLException {
        String sql = "DELETE FROM piece WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.executeUpdate();
        }
    }

    private Optional<GameState> findGameState(Connection connection, Long gameId) throws SQLException {
        String sql = "SELECT current_team, winner FROM game WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            return queryForGameState(preparedStatement);
        }
    }

    private Optional<GameState> queryForGameState(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return Optional.of(mapToGameState(resultSet));
            }
            return Optional.empty();
        }
    }

    private GameState mapToGameState(ResultSet resultSet) throws SQLException {
        String winner = resultSet.getString("winner");
        if (winner != null) {
            return new FinishedState(Team.valueOf(winner));
        }

        String currentTeam = resultSet.getString("current_team");
        if ("CHO".equals(currentTeam)) {
            return new ChoPlayingState();
        }
        return new HanPlayingState();
    }

    private Map<Position, Piece> findPieces(Connection connection, Long gameId) throws SQLException {
        String sql = "SELECT piece_type, team, position_column, position_row FROM piece WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return mapToPieces(resultSet);
            }
        }
    }

    private Map<Position, Piece> mapToPieces(ResultSet resultSet) throws SQLException {
        Map<Position, Piece> pieces = new HashMap<>();
        while (resultSet.next()) {
            int positionColumn = resultSet.getInt("position_column");
            int positionRow = resultSet.getInt("position_row");
            Position position = Position.of(positionColumn, positionRow);

            Team team = Team.valueOf(resultSet.getString("team"));
            PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
            Piece piece = new Piece(team, pieceType);

            pieces.put(position, piece);
        }
        return pieces;
    }
}
