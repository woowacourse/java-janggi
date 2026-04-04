package repository;

import domain.Board;
import domain.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GameRepository {

    private final Connection connection;
    private final PieceRepository pieceRepository;

    public GameRepository(Connection connection) {
        this.connection = connection;
        this.pieceRepository = new PieceRepository(connection);
    }

    // 초기 게임판과 게임 정보 저장
    public void save(Game game, Board board) {
        Long gameId = insertGame(game, board);
        game.assignId(gameId);
        pieceRepository.save(gameId, board);
    }

    public void update(Game game, Board board) {
        updateGame(game, board);
        pieceRepository.updatePieces(game.id(), board);
    }


    private void updateGame(Game game, Board board) {
        String sql = "UPDATE game SET turn = ?, is_finished = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, String.valueOf(game.turn()));
            pstmt.setBoolean(2, !board.canNextTurn());
            pstmt.setLong(3, game.id());

            pstmt.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Long insertGame(Game game, Board board) {
        String sql = "INSERT INTO game (turn, is_finished) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, String.valueOf(game.turn()));
            pstmt.setBoolean(2, !board.canNextTurn());
            pstmt.execute();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();

            return rs.getLong(1);

        } catch (Exception e) {
            System.out.println("테이블을 생성하지 못했습니다" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
