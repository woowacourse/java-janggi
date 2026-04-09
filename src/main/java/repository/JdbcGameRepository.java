package repository;

import config.DatabaseConfig;
import model.board.Country;
import model.pieces.PieceType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private static final String DELETE_PIECE = "DELETE FROM piece";
    private static final String DELETE_GAME = "DELETE FROM game";

    private static final String INSERT_GAME = """
            INSERT INTO game(turn,finished,winner)
            VALUES (?,?,?)
            """;

    private static final String INSERT_PIECE = """
            INSERT INTO piece(game_id, row_number, column_number,country,piece_type)
            VALUES(?,?,?,?,?)
            """;

    private static final String SELECT_GAME = """
            SELECT id, turn, finished, winner
            FROM game
            LIMIT 1
            """;

    private static final String SELECT_PIECES = """
            SELECT row_number, column_number,country,piece_type
            FROM piece
            WHERE game_id = ?
            """;

    @Override
    public void save(SavedGame savedGame) {
        Connection connection = null;
        try {
            connection = DatabaseConfig.getConnection();
            connection.setAutoCommit(false);

            deleteAll(connection);
            long gameId = insertGame(connection, savedGame);
            insertPieces(connection, gameId, savedGame);

            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            throw new IllegalStateException("[ERROR] 게임 저장에 실패했습니다.", exception);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<SavedGame> find() {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement gameStatement = connection.prepareStatement(SELECT_GAME);
             ResultSet gameResultSet = gameStatement.executeQuery();

        ) {
            if (!gameResultSet.next()) {
                return Optional.empty();
            }

            return findSavedGame(gameResultSet, connection);

        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 저장된 게임 조회에 실패했습니다.", exception);
        }
    }

    @Override
    public void clear() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            deleteAll(connection);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 저장 데이터 삭제에 실패했습니다.", exception);
        }
    }

    private void deleteAll(Connection connection) throws SQLException {
        try (PreparedStatement pieceStatement = connection.prepareStatement(DELETE_PIECE);
             PreparedStatement gameStatement = connection.prepareStatement(DELETE_GAME);
        ) {
            pieceStatement.executeUpdate();
            gameStatement.executeUpdate();
        }
    }

    private long insertGame(Connection connection, SavedGame savedGame) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, savedGame.turn().name());
            statement.setBoolean(2, savedGame.finished());
            statement.setString(3, winnerName(savedGame));
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new IllegalStateException("[ERROR] game id를 생성할 수 없습니다.");
            }
        }
    }

    private void insertPieces(Connection connection, long gameId, SavedGame savedGame) throws SQLException {
        for (SavedPiece savedPiece : savedGame.pieces()) {
            insertPiece(connection, gameId, savedPiece);
        }
    }

    private static void insertPiece(Connection connection, long gameId, SavedPiece savedPiece) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PIECE);) {
            statement.setLong(1, gameId);
            statement.setInt(2, savedPiece.row());
            statement.setInt(3, savedPiece.col());
            statement.setString(4, savedPiece.country().name());
            statement.setString(5, savedPiece.pieceType().name());
            statement.executeUpdate();
        }
    }

    private String winnerName(SavedGame savedGame) {
        if (savedGame.winner() == null) {
            return null;
        }
        return savedGame.winner().name();
    }

    private void rollback(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.rollback();
        } catch (SQLException ignored) {
        }
    }

    private void close(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }

    private static Optional<SavedGame> findSavedGame(ResultSet gameResultSet, Connection connection) throws SQLException {
        long gameId = gameResultSet.getLong("id");
        Country turn = Country.valueOf(gameResultSet.getString("turn"));
        boolean finished = gameResultSet.getBoolean("finished");
        String winnerString = gameResultSet.getString("winner");

        Country winner = null;
        if (winnerString != null) {
            winner = Country.valueOf(winnerString);
        }

        List<SavedPiece> savedPieces = findSavedPieces(connection, gameId);
        SavedGame savedGame = new SavedGame(turn, finished, winner, List.copyOf(savedPieces));

        return Optional.of(savedGame);
    }

    private static List<SavedPiece> findSavedPieces(Connection connection, long gameId) throws SQLException {
        List<SavedPiece> savedPieces = new ArrayList<>();
        try (PreparedStatement pieceStatement = connection.prepareStatement(SELECT_PIECES)) {
            pieceStatement.setLong(1, gameId);
            try (ResultSet pieceResultSet = pieceStatement.executeQuery()) {
                while (pieceResultSet.next()) {
                    int row = pieceResultSet.getInt("row_number");
                    int col = pieceResultSet.getInt("column_number");
                    Country country = Country.valueOf(pieceResultSet.getString("country"));
                    PieceType pieceType = PieceType.valueOf(pieceResultSet.getString("piece_type"));
                    savedPieces.add(new SavedPiece(row, col, country, pieceType));
                }
            }
        }
        return savedPieces;
    }
}
