package domain.board;

import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class InitialPiecesTest {

    private static final int INITIAL_PIECES_AMOUNT = 32;

    private final List<Piece> hanWing = List.of(new Horse(Side.HAN), new Elephant(Side.HAN));
    private final List<Piece> choWing = List.of(new Horse(Side.CHO), new Elephant(Side.CHO));
    private final HanWings hanWings = new HanWings(hanWing, hanWing);
    private final ChoWings choWings = new ChoWings(choWing, choWing);

    @Test
    void 초기화_해야_할_모든_기물을_반환한다() {
        // given
        InitialPieces initialPieces = new InitialPieces(hanWings, choWings);

        // when
        AlivePieces initializedPieces = initialPieces.get();

        // then
        Assertions.assertThat(initializedPieces.get()).hasSize(INITIAL_PIECES_AMOUNT);
    }
}
