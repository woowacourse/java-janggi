package persistence.repository;

import domain.GameStatus;
import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import persistence.entity.GameState;
import persistence.entity.PieceState;

public final class JdbcGameStateRepository implements GameStateRepository {

    private final DataSource dataSource;

    public JdbcGameStateRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(GameState gameState) {
        try (Connection connection = dataSource.getConnection()) {
            save(gameState, connection);
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    private void save(GameState gameState, Connection connection) throws SQLException {
        try {
            connection.setAutoCommit(false);
            deleteSavedState(connection);
            insertGameState(connection, gameState);
            insertPieceStates(connection, gameState.pieceStates());
            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();
            throw exception;
        }
    }

    private void deleteSavedState(Connection connection) throws SQLException {
        deletePieceStates(connection);
        deleteGame(connection);
    }

    private static void deletePieceStates(Connection connection) throws SQLException {
        String sql = "delete from piece_state";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    private static void deleteGame(Connection connection) throws SQLException {
        String sql = "delete from game";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    private void insertGameState(Connection connection, GameState gameState) throws SQLException {
        String sql = "insert into game (id, game_status) values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, 1L);
            preparedStatement.setString(2, gameState.gameStatus().name());
            preparedStatement.executeUpdate();
        }
    }

    private void insertPieceStates(Connection connection, List<PieceState> pieceStates) throws SQLException {
        for (PieceState pieceState : pieceStates) {
            insertPieceState(connection, pieceState);
        }
    }

    private void insertPieceState(Connection connection, PieceState pieceState) throws SQLException {
        String sql = """
                insert into piece_state (game_id, row_number, col_number, piece_type, team) 
                values (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, 1L);
            preparedStatement.setInt(2, pieceState.position().row());
            preparedStatement.setInt(3, pieceState.position().col());
            preparedStatement.setString(4, pieceState.pieceProperty().pieceType().name());
            preparedStatement.setString(5, pieceState.pieceProperty().team().name());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Optional<GameState> load() {
        try (Connection connection = dataSource.getConnection()) {
            Optional<GameStatus> gameStatus = loadGameStatus(connection);
            if (gameStatus.isEmpty()) {
                return Optional.empty();
            }

            List<PieceState> pieceStates = loadPieceStates(connection);
            GameState gameState = new GameState(pieceStates, gameStatus.get());
            return Optional.of(gameState);
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }

    private Optional<GameStatus> loadGameStatus(Connection connection) throws SQLException {
        String sql = "select game_status from game";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(GameStatus.valueOf(resultSet.getString("game_status")));
        }
    }

    private List<PieceState> loadPieceStates(Connection connection) throws SQLException {
        String sql = """
                select row_number, col_number, piece_type, team
                from piece_state
                """;
        List<PieceState> pieceStates = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                pieceStates.add(toPieceState(resultSet));
            }
        }
        return pieceStates;
    }

    private PieceState toPieceState(ResultSet resultSet) throws SQLException {
        PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
        Team team = Team.valueOf(resultSet.getString("team"));
        PieceProperty pieceProperty = new PieceProperty(pieceType, team);
        Position position = new Position(
                resultSet.getInt("row_number"),
                resultSet.getInt("col_number"));
        return new PieceState(pieceProperty, position);
    }

    @Override
    public boolean exist() {
        String sql = "select 1 from game limit 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next();
        } catch (SQLException exception) {
            throw new IllegalStateException("저장된 게임 상태 존재 여부를 조회할 수 없습니다.", exception);
        }
    }
}
