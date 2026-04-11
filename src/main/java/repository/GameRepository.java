package repository;

import domain.Board;
import domain.Game;
import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class GameRepository {

    // 초기 게임판과 게임 정보 저장
    public void save(Game game, Connection connection) {
        Long gameId = insertGame(game, connection);
        game.assignId(gameId);
    }

    public Game findByGameId(Long gameId, Connection connection, Map<Position, Piece> pieces) {
        String sql = "SELECT turn, is_finished FROM game WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                throw new RuntimeException("게임 없음");
            }

            Team team = Team.valueOf(rs.getString("turn"));
            boolean isFinished = rs.getBoolean("is_finished");

            Board board = new Board(pieces, isFinished);
            return new Game(gameId, team, board);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Game findLatest(Connection connection, Map<Position, Piece> pieces) {
        String sql = "SELECT id FROM game ORDER BY id DESC LIMIT 1";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                throw new RuntimeException("저장된 게임 없음");
            }

            Long gameId = rs.getLong("id");
            return findByGameId(gameId, connection, pieces);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGame(Game game, Connection connection) {
        String sql = "UPDATE game SET turn = ?, is_finished = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, String.valueOf(game.turn()));
            pstmt.setBoolean(2, !game.board().canNextTurn());
            pstmt.setLong(3, game.id());

            pstmt.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Long insertGame(Game game, Connection connection) {
        String sql = "INSERT INTO game (turn, is_finished) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            Board board = game.board();
            pstmt.setString(1, String.valueOf(game.turn()));
            pstmt.setBoolean(2, !board.canNextTurn());
            pstmt.execute();

            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new RuntimeException("ID 생성 실패");
        } catch (Exception e) {
            System.out.println("테이블을 생성하지 못했습니다" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
