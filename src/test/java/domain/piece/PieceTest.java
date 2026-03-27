package domain.piece;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class PieceTest {
    @Test
    void 같은_팀의_기물인지_확인한다() {
        Piece piece = new Piece(PieceType.HORSE, Team.CHO, new HorseStrategy());
        Piece anotherPiece = new Piece(PieceType.HORSE, Team.CHO, new HorseStrategy());

        Assertions.assertThat(piece.isSameTeam(anotherPiece)).isTrue();
    }

    @Test
    void 다른_팀의_기물인지_확인한다() {
        Piece piece = new Piece(PieceType.HORSE, Team.CHO, new HorseStrategy());
        Piece anotherPiece = new Piece(PieceType.HORSE, Team.HAN, new HorseStrategy());

        Assertions.assertThat(piece.isSameTeam(anotherPiece)).isFalse();
    }
}
