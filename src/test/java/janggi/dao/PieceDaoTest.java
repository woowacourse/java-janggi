package janggi.dao;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.direction.Position;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private final PieceDao pieceDao = new PieceDao();

    @BeforeAll
    static void setUpClass() {
        DatabaseConnection.setTestMode(true);
    }

    @BeforeEach
    void setUp() {
        pieceDao.deleteAllPieces();
    }

    @AfterAll
    static void tearDownClass() {
        DatabaseConnection.setTestMode(false);
    }

    @DisplayName("데이터베이스에 기물을 저장한다.")
    @Test
    void addPieceTest() {

        // given
        final Piece piece = new Soldier(new Position(1, 1), RED);

        // when & then
        assertThatCode(() -> {
            pieceDao.addPiece(piece);
        }).doesNotThrowAnyException();
    }

    @DisplayName("기물의 id를 찾는다.")
    @Test
    void findPieceTest() {

        // given
        final Piece solider = new Soldier(new Position(1, 1), RED);
        pieceDao.addPiece(solider);

        // when
        final int findPieceId = pieceDao.findIdByPiece(solider);
        final Piece findPiece = pieceDao.findPieceById(findPieceId);

        // then
        assertThat(findPiece).isEqualTo(solider);
    }

    @DisplayName("데이터베이스에 기물 모음을 저장한다.")
    @Test
    void addPiecesTest() {

        // given
        final Piece cannon = new Cannon(new Position(1, 1), RED);
        final Piece chariot = new Chariot(new Position(2, 2), RED);
        final List<Piece> pieces = List.of(cannon, chariot);

        // when & then
        assertThatCode(() -> {
            pieceDao.addPieces(pieces);
        }).doesNotThrowAnyException();
    }

    @DisplayName("데이터베이스의 모든 기물을 삭제한다.")
    @Test
    void deleteAllPiecesTest() {

        // given
        final Piece cannon = new Cannon(new Position(1, 1), RED);
        final Piece chariot = new Chariot(new Position(2, 2), RED);
        final List<Piece> pieces = List.of(cannon, chariot);
        pieceDao.addPieces(pieces);

        // when
        pieceDao.deleteAllPieces();

        // then
        assertThat(pieceDao.findAllPieces().size()).isEqualTo(0);
    }
}
