package dao;

import domain.board.Board;
import domain.board.Position;
import domain.game.Game;
import domain.game.GameState;
import domain.game.InProgress;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GameDAO {

    private static final String URL = "jdbc:h2:./janggi;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void initTables() {
        String createGameTableSql = """
                CREATE TABLE IF NOT EXISTS game(
                    game_id INT AUTO_INCREMENT PRIMARY KEY,
                    current_turn VARCHAR(10) NOT NULL,
                    game_state VARCHAR(20) NOT NULL
                    )
                """;

        String createPieceTableSql = """
                CREATE TABLE IF NOT EXISTS piece(
                    game_id INT NOT NULL,
                    x_coordinate INT NOT NULL,
                    y_coordinate INT NOT NULL,
                    camp VARCHAR(20) NOT NULL,
                    piece_type VARCHAR(20) NOT NULL,
                    PRIMARY KEY (game_id, x_coordinate, y_coordinate),
                    FOREIGN KEY (game_id) REFERENCES game(game_id)
                )
                """;

        try (Connection connection = getConnection();
             PreparedStatement gameStatement = connection.prepareStatement(createGameTableSql);
             PreparedStatement pieceStatement = connection.prepareStatement(createPieceTableSql)) {

            gameStatement.execute();
            pieceStatement.execute();

            System.out.println("장기 DB 테이블 생성 완료");
        } catch (SQLException e) {
            System.err.println("테이블 생성 실패: " + e.getMessage());
        }
    }

    public int save(Game game) {
        String insertGameSql = "INSERT INTO game (current_turn, game_state) VALUES (?, ?)";
        String insertPieceSql = "INSERT INTO piece (game_id, x_coordinate, y_coordinate, camp, piece_type) VALUES (?, ?, ?, ?, ?)";

        int generatedGameId = -1;

        try (Connection connection = getConnection();
             PreparedStatement gameStatement = connection.prepareStatement(insertGameSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pieceStatement = connection.prepareStatement(insertPieceSql)) {

            gameStatement.setString(1, game.currentTurn().name());
            gameStatement.setString(2, game.isFinished() ? "FINISHED" : "IN_PROGRESS");
            gameStatement.executeUpdate();

            try (java.sql.ResultSet resultSet = gameStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedGameId = resultSet.getInt(1);
                }
            }
            System.out.println("게임 저장 완료. 발급된 게임 번호: " + generatedGameId);

            for (Map.Entry<Position, Piece> entry : game.board().getPieces().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                pieceStatement.setInt(1, generatedGameId);
                pieceStatement.setInt(2, position.x());
                pieceStatement.setInt(3, position.y());
                pieceStatement.setString(4, piece.camp().name());
                pieceStatement.setString(5, piece.type().name());
                pieceStatement.addBatch();
            }

            pieceStatement.executeBatch();

        } catch (SQLException e) {
            System.err.println("게임 저장 실패: " + e.getMessage());
        }

        return generatedGameId;
    }

    public Game findBy(int gameId) {
        String selectGameSql = "SELECT * FROM game WHERE game_id = ?";
        String selectPieceSql = "SELECT * FROM piece WHERE game_id=?";

        try (Connection connection = getConnection();
             PreparedStatement gameStatement = connection.prepareStatement(selectGameSql)) {

            gameStatement.setInt(1, gameId);

            try (ResultSet gameResultSet = gameStatement.executeQuery()) {
                if (!gameResultSet.next()) {
                    throw new IllegalArgumentException("[ERROR] 해당 번호의 게임을 찾을 수 없습니다");
                }

                String gameStateStr = gameResultSet.getString("game_state");

                if ("FINISHED".equals(gameStateStr)) {
                    throw new IllegalArgumentException("이미 종료된 게임입니다.");
                }

                String currentTurnStr = gameResultSet.getString("current_turn");
                Camp currentTurn = Camp.valueOf(currentTurnStr);

                GameState gameState = new InProgress();

                Map<Position, Piece> pieces = new HashMap<>();

                try (PreparedStatement pieceStatement = connection.prepareStatement(selectPieceSql)) {
                    pieceStatement.setInt(1, gameId);

                    try (ResultSet pieceResultSet = pieceStatement.executeQuery()) {
                        while (pieceResultSet.next()) {
                            int x = pieceResultSet.getInt("x_coordinate");
                            int y = pieceResultSet.getInt("y_coordinate");
                            String campStr = pieceResultSet.getString("camp");
                            String pieceTypeStr = pieceResultSet.getString("piece_type");

                            Position position = new Position(x, y);
                            Camp camp = Camp.valueOf(campStr);
                            Piece piece = createPiece(camp, pieceTypeStr);

                            pieces.put(position, piece);
                        }
                    }
                }
                Board board = new Board(pieces);
                return new Game(board, currentTurn, gameState);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] 게임을 불러오는 중 DB 오류가 발생했습니다.", e);
        }
    }

    private Piece createPiece(Camp camp, String pieceTypeStr) {
        PieceType pieceType = PieceType.valueOf(pieceTypeStr);

        return new Piece(camp, pieceType);
    }

    public void updateMove(int gameId, Game game, Position from, Position to) {
        String updateGameSql = "UPDATE game SET current_turn=?, game_state=? WHERE game_id=?";
        String deletePieceSql = "DELETE FROM piece WHERE game_id=? AND x_coordinate=? AND y_coordinate=?";
        String updatePieceSql = "UPDATE piece SET x_coordinate=?, y_coordinate=? WHERE game_id=? AND x_coordinate=? AND y_coordinate=?";

        try (Connection connection = getConnection()) {

            connection.setAutoCommit(false);

            try {
                try (PreparedStatement gameStatement = connection.prepareStatement(updateGameSql)) {
                    gameStatement.setString(1, game.currentTurn().name());
                    gameStatement.setString(2, game.isFinished() ? "FINISHED" : "IN_PROGRESS");
                    gameStatement.setInt(3, gameId);
                    gameStatement.executeUpdate();
                }

                try (PreparedStatement deleteStatement = connection.prepareStatement(deletePieceSql)) {
                    deleteStatement.setInt(1, gameId);
                    deleteStatement.setInt(2, to.x());
                    deleteStatement.setInt(3, to.y());
                    deleteStatement.executeUpdate();
                }

                try (PreparedStatement updateStatement = connection.prepareStatement(updatePieceSql)) {
                    updateStatement.setInt(1, to.x());
                    updateStatement.setInt(2, to.y());
                    updateStatement.setInt(3, gameId);
                    updateStatement.setInt(4, from.x());
                    updateStatement.setInt(5, from.y());
                    updateStatement.executeUpdate();
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new IllegalArgumentException("기물 이동 중 DB 오류가 발생하여 롤백되었습니다.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("기물 이동 업데이트 실패: " + e.getMessage());
        }
    }

    public void updateGameStatus(int gameId, Game game) {
        String updateGameSql = "UPDATE game SET current_turn=?, game_state=? WHERE game_id=?";
        try (Connection connection = getConnection();
             PreparedStatement gameStatement = connection.prepareStatement(updateGameSql)) {
            gameStatement.setString(1, game.currentTurn().name());
            gameStatement.setString(2, game.isFinished() ? "FINISHED" : "IN_PROGRESS");
            gameStatement.setInt(3, gameId);
            gameStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("게임 상태 업데이트 실패: " + e.getMessage());
        }
    }
}
