package repository;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Rook;
import janggi.domain.piece.Side;
import janggi.domain.position.Position;
import janggi.repository.JanggiDao;
import janggi.repository.JanggiTableCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ReplaceUnderBar
public class JanggiDaoTest {

    private Connection connection;
    private final JanggiDao janggiDao = new JanggiDao();

    @BeforeEach
    void 장기_테이블_생성() throws SQLException {
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:13307/chess_test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            "root","root");
        JanggiTableCreator.createTurnTable(connection);
        JanggiTableCreator.createPieceTable(connection);
    }

    @AfterEach
    void 데이터_초기화() throws SQLException {
        connection.createStatement().executeUpdate("DELETE FROM piece");
        connection.createStatement().executeUpdate("DELETE FROM turn");
    }

    @Test
    void 기물_저장_테스트() {
        King king = new King(Side.CHO, 4, 1);

        janggiDao.insertPiece(king, connection);

        List<Piece> findPieces = janggiDao.loadPieces(connection);
        assertThat(findPieces.getFirst()).isEqualTo(king);
    }

    @Test
    void 턴_저장_테스트() {
        Side cho = Side.CHO;
        janggiDao.insertTurn(cho, connection);

        Side turn = janggiDao.loadTurn(connection);
        assertThat(turn).isEqualTo(cho);
    }

    @Test
    void 저장된_기물이_있을때_저장된_게임_여부_테스트() {
        janggiDao.insertPiece(new King(Side.HAN, 4, 8), connection);

        boolean hasPiece = janggiDao.hasGamePiece(connection);
        assertThat(hasPiece).isTrue();
    }

    @Test
    void 저장된_기물이_없을때_저장된_게임_여부_테스트() {
        boolean hasPiece = janggiDao.hasGamePiece(connection);
        assertThat(hasPiece).isFalse();
    }

    @Test
    void 기물_조회_테스트() {
        King king = new King(Side.CHO, 4, 1);
        Rook rook = new Rook(Side.HAN, 3, 2);
        janggiDao.insertPiece(king, connection);
        janggiDao.insertPiece(rook, connection);

        List<Piece> pieces = janggiDao.loadPieces(connection);
        assertThat(pieces.size()).isEqualTo(2);
        assertThat(pieces).isEqualTo(List.of(king, rook));
    }

    @Test
    void 턴_조회_테스트() {
        Side han = Side.HAN;
        janggiDao.insertTurn(han, connection);

        Side findTurn = janggiDao.loadTurn(connection);
        assertThat(findTurn).isEqualTo(han);
    }

    @Test
    void 목적지_기물_제거_테스트() {
        Rook rook = new Rook(Side.CHO, 3, 8);
        janggiDao.insertPiece(rook, connection);

        janggiDao.removeDestinationPiece(new Position(3, 8), connection);

        List<Piece> pieces = janggiDao.loadPieces(connection);
        assertThat(pieces.isEmpty()).isTrue();
    }

    @Test
    void 기물_위치_수정_테스트() {
        Rook rook = new Rook(Side.CHO, 3, 8);
        janggiDao.insertPiece(rook, connection);

        janggiDao.updateMovingPiece(new Position(3,8), new Position(3, 4), connection);

        List<Piece> pieces = janggiDao.loadPieces(connection);
        assertThat(pieces.getFirst()).isEqualTo(new Rook(Side.CHO, 3, 4));
    }

    @Test
    void 턴_변경_테스트() {
        Side cho = Side.CHO;
        Side han = Side.HAN;
        janggiDao.insertTurn(cho, connection);

        janggiDao.updateTurn(cho, connection);

        Side turn = janggiDao.loadTurn(connection);
        assertThat(turn).isEqualTo(han);
    }

    @Test
    void 턴_제거_테스트() {
        Side cho = Side.CHO;
        janggiDao.insertTurn(cho, connection);

        janggiDao.removeTurn(connection);

        Side turn = janggiDao.loadTurn(connection);
        assertThat(turn).isEqualTo(null);
    }

    @Test
    void 기물_제거_테스트() {
        King king = new King(Side.CHO, 4, 1);
        Rook rook = new Rook(Side.HAN, 3, 2);
        janggiDao.insertPiece(king, connection);
        janggiDao.insertPiece(rook, connection);

        janggiDao.removePieces(connection);

        List<Piece> pieces = janggiDao.loadPieces(connection);
        assertThat(pieces.isEmpty()).isTrue();
    }
}
