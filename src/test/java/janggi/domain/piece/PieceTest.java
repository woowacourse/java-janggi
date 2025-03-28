package janggi.domain.piece;

import static janggi.domain.TestFixture.BLUE_ELEPHANT;
import static janggi.domain.TestFixture.BLUE_HORSE;
import static janggi.domain.TestFixture.BLUE_SOLDIER;
import static janggi.domain.TestFixture.RED_ELEPHANT;
import static janggi.domain.TestFixture.RED_SOLDIER;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void 같은_기물임을_확인() {
        Piece soldier = RED_SOLDIER;
        Piece otherSoldier = BLUE_SOLDIER;

        boolean samePiece = soldier.isSamePieceType(otherSoldier);

        assertThat(samePiece).isTrue();
    }

    @Test
    void 다른기물일때_같은기물이아님을_확인() {
        Piece soldier = BLUE_SOLDIER;
        Piece otherSoldier = BLUE_ELEPHANT;

        boolean samePiece = soldier.isSamePieceType(otherSoldier);

        assertThat(samePiece).isFalse();
    }

    @Test
    void 이동경로에_몇개의_기물이_있는지_확인() {
        ArrayList<Piece> piecesInRoute = new ArrayList<>();
        piecesInRoute.add(BLUE_HORSE);
        piecesInRoute.add(RED_ELEPHANT);
        piecesInRoute.add(EmptyPiece.INSTANCE);

        Piece piece = RED_SOLDIER;
        long pieceCountInRoute = piece.countPieceInRoute(piecesInRoute);

        assertThat(pieceCountInRoute).isEqualTo(2);
    }

}
