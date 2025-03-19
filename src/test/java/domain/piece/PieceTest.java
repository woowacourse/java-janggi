package domain.piece;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @Test
    void 같은_기물임을_확인() {
        Piece soldier = new Soldier(PieceColor.BLUE);
        Piece otherSoldier = new Soldier(PieceColor.RED);

        boolean samePiece = soldier.isSamePiece(otherSoldier);

        assertThat(samePiece).isTrue();
    }

    @Test
    void 다른기물일때_같은기물이아님을_확인() {
        Piece soldier = new Soldier(PieceColor.BLUE);
        Piece otherSoldier = new Elephant(PieceColor.BLUE);

        boolean samePiece = soldier.isSamePiece(otherSoldier);

        assertThat(samePiece).isFalse();
    }

    @Test
    void 이동경로에_몇개의_기물이_있는지_확인() {
        ArrayList<Piece> piecesInRoute = new ArrayList<>();
        piecesInRoute.add(new Soldier(PieceColor.BLUE));
        piecesInRoute.add(new Soldier(PieceColor.BLUE));
        piecesInRoute.add(new EmptyPiece());

        Piece piece = new Soldier(PieceColor.BLUE);
        int pieceCountInRoute = piece.countPieceInRoute(piecesInRoute);

        assertThat(pieceCountInRoute).isEqualTo(2);
    }

}