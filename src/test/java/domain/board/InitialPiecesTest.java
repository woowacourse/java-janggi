package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.wing.Wings;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class InitialPiecesTest {

    private static final int INITIAL_PIECES_AMOUNT = 32;

    private final List<Piece> hanWing = List.of(
            new Piece(PieceType.HORSE, Side.HAN),
            new Piece(PieceType.ELEPHANT, Side.HAN)
    );
    private final List<Piece> choWing = List.of(
            new Piece(PieceType.HORSE, Side.CHO),
            new Piece(PieceType.ELEPHANT, Side.CHO)
    );
    private final Wings hanWings = new Wings(Side.HAN, hanWing, hanWing);
    private final Wings choWings = new Wings(Side.CHO, choWing, choWing);

    @Test
    void 초기화_해야_할_모든_기물을_반환한다() {
        // given
        InitialPieces initialPieces = new InitialPieces(hanWings, choWings);

        // when
        Map<Intersection, Piece> initializedPieces = initialPieces.toMap();

        // then
        assertThat(initializedPieces).hasSize(INITIAL_PIECES_AMOUNT);
    }
}
