package janggi.dao.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import janggi.domain.piece.position.Position;
import janggi.domain.players.Team;
import janggi.dto.PieceDto;
import janggi.dto.PieceMove;
import janggi.fixture.TestFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PieceDaoTest {

    private final PieceDao pieceDao = TestPieceDaoImpl.getPieceDao();

    @BeforeEach
    void setUp() {
        pieceDao.deleteAll();

        final PieceDto hanKingPiece = TestFixture.makeHanKingPiece();
        pieceDao.insert(hanKingPiece);
        final PieceDto choKingPiece = TestFixture.makeChoKingPiece();
        pieceDao.insert(choKingPiece);
    }

    @Test
    void 조회_기능_테스트() {
        // Given

        // When
        final List<PieceDto> choPieces = pieceDao.select(Team.HAN);

        // Then
        assertThat(choPieces).containsOnly(TestFixture.makeHanKingPiece());
    }

    @Test
    void 전체_조회_기능_테스트() {
        // Given

        // When
        final List<PieceDto> pieceDtos = pieceDao.selectAll();

        // Then
        assertThat(pieceDtos).containsAll(List.of(TestFixture.makeChoKingPiece(), TestFixture.makeHanKingPiece()));
    }

    @Test
    void 추가_테스트() {
        // Given
        final PieceDto hanCannon = new PieceDto(Team.HAN, Piece.CANNON, 3, 2);

        // When
        pieceDao.insert(hanCannon);

        // Then
        assertThat(pieceDao.select(Team.HAN)).contains(hanCannon);
    }

    @Test
    void 갱신_테스트() {
        // Given
        final PieceMove pieceMove = new PieceMove(Team.HAN, Piece.KING, new Position(2, 5), new Position(3, 5), null
        );

        // When
        pieceDao.update(pieceMove);

        // Then
        assertThat(pieceDao.select(Team.HAN)).contains(new PieceDto(Team.HAN, Piece.KING, 3, 5));
    }

    @Test
    void 삭제_테스트() {
        // Given
        pieceDao.insert(new PieceDto(Team.CHO, Piece.CANNON, 4, 5));
        final PieceMove pieceMove = PieceMove.capture(Team.CHO, Piece.CANNON, new Position(4, 5), new Position(2, 5),
                Piece.KING);

        // When
        pieceDao.delete(pieceMove);

        // Then
        assertThat(pieceDao.select(Team.CHO)).doesNotContain(new PieceDto(Team.CHO, Piece.KING, 2, 5));
    }

    @Test
    void 전체_삭제_테스트() {
        // Given

        // When
        pieceDao.deleteAll();

        // Then
        assertThat(pieceDao.selectAll()).isEmpty();
    }
}
