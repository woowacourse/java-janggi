package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import domain.Board;
import domain.JanggiGame;
import domain.Position;
import domain.enums.Country;
import domain.enums.PieceType;
import domain.pieces.Piece;
import domain.pieces.PieceFactory;
import domain.state.State;

public class JanggiGameRepository {
    private final Connection conn;

    public JanggiGameRepository(Connection conn) {
        this.conn = conn;
    }

    public int saveGame(JanggiGame janggiGame) {
        String sql = "Insert into game (state) VALUES (?);";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, janggiGame.getStateValue());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("게임 ID 생성 실패");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBoard(Board board, int gameId) {
        String sql = """
                Insert into piece_position (game_id, piece_id, x, y)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()) {

                Position position = entry.getKey();
                Piece piece = entry.getValue();

                int pieceId = findByPieceId(piece);

                ps.setInt(1, gameId);
                ps.setInt(2, pieceId);
                ps.setInt(3, position.getX());
                ps.setInt(4, position.getY());
                ps.addBatch(); // ⭐ batch 처리
            }

            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMoveReuslt(JanggiGame game, int gameId) {
        updateStateByGameId(gameId, game);
        updateBoardByGameId(gameId, game);
    }

    private void updateBoardByGameId(int gameId, JanggiGame janggiGame) {
        String sql = "DELETE FROM piece_position WHERE game_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gameId);
            ps.executeUpdate();
            saveBoard(janggiGame.getBoard(),gameId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateStateByGameId(int gameId, JanggiGame janggiGame) {
        String sql = "UPDATE game SET state=? where id = ?";
        String stateStr = janggiGame.getStateValue();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, stateStr);
            ps.setInt(2, gameId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int findByPieceId(Piece piece) {
        String sql = "Select id from piece where piece_type = ? AND Country = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, piece.getPieceType().getName());
            ps.setString(2, piece.getCountry().name());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("piece_id 없음");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public JanggiGame findByGameId(int gameId) {
        State state = findStateByGameId(gameId);
        Board board = findBoardByGameId(gameId);
        return new JanggiGame(board, state);
    }

    public Board findBoardByGameId(int gameId) {
        String sql = """
                SELECT
                    pp.x,
                    pp.y,
                    p.piece_type,
                    p.country
                    FROM piece_position pp
                JOIN piece p ON pp.piece_id = p.id
                WHERE pp.game_id=?
                """;
        Map<Position, Piece> board = new HashMap<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gameId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                String pieceTypeStr = rs.getString("piece_type");
                String countryStr = rs.getString("country");

                PieceType pieceType = PieceType.of(pieceTypeStr);
                Country country = Country.valueOf(countryStr);
                Piece piece = PieceFactory.createPiece(pieceType, country);
                Position position = Position.create(x, y);
                board.put(position, piece);
            }

            validateDataExist(!board.isEmpty(), "piece_postion에 기물 정보가 없습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Board(board);
    }

    public State findStateByGameId(int gameId) {
        String sql = "SELECT state FROM game where id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gameId);

            ResultSet rs = ps.executeQuery();

            validateDataExist(rs.next(), "저장된 게임 상태가 없습니다.");
            String stateStr = rs.getString("state");
            validateGameIsContinue(stateStr);
            return State.from(stateStr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void validateDataExist(boolean isDataExist, String message) throws SQLException {
        if (!isDataExist) {
            throw new IllegalStateException("DB 정보 없음 - " + message);
        }
    }

    private void validateGameIsContinue(String stateStr) {
        if ("Exit".equals(stateStr)) {
            throw new IllegalStateException("종료된 게임");
        }
    }

    public void validateGame(int gameId) {
        String sql = "Select id from game where id = ? and state != ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gameId);
            ps.setString(2, "Exit"); // enum이면 DB에 저장된 값 기준

            ResultSet rs = ps.executeQuery();

            validateDataExist(rs.next(), "진행중인 game이 없습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
