package domain.piece;

import domain.piece.policy.GungseongDiagonalMovementPolicy;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.strategy.ByeongMoveStrategy;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceTest {
    @ParameterizedTest
    @MethodSource("allyCanNotBeCaptured")
    void 아군_기물을_잡으면_예외가_발생한다(Piece target) {
        Byeong testPiece = new Byeong(new ByeongMoveStrategy(),
                List.of(new NormalMovementPolicy(), new GungseongDiagonalMovementPolicy()), Team.CHO);
        Assertions.assertThatThrownBy(() -> testPiece.capture(target)).isInstanceOf(IllegalArgumentException.class);

    }

    private static Stream<Arguments> allyCanNotBeCaptured() {
        return Stream.of(
                Arguments.of(PieceFactory.create(PieceType.CHA, Team.CHO)),
                Arguments.of(PieceFactory.create(PieceType.PO, Team.CHO)),
                Arguments.of(PieceFactory.create(PieceType.SANG, Team.CHO)),
                Arguments.of(PieceFactory.create(PieceType.JOL, Team.CHO)),
                Arguments.of(PieceFactory.create(PieceType.MA, Team.CHO)),
                Arguments.of(PieceFactory.create(PieceType.SA, Team.CHO)),
                Arguments.of(PieceFactory.create(PieceType.JANG, Team.CHO)));
    }

    @ParameterizedTest
    @MethodSource("enemyCanBeCaptured")
    void 상대_기물은_잡을_수_있다(Piece target) {
        Byeong testPiece = new Byeong(new ByeongMoveStrategy(),
                List.of(new NormalMovementPolicy(), new GungseongDiagonalMovementPolicy()), Team.CHO);
        Assertions.assertThatNoException().isThrownBy((() -> testPiece.capture(target)));

    }

    private static Stream<Arguments> enemyCanBeCaptured() {
        return Stream.of(
                Arguments.of(PieceFactory.create(PieceType.CHA, Team.HAN)),
                Arguments.of(PieceFactory.create(PieceType.PO, Team.HAN)),
                Arguments.of(PieceFactory.create(PieceType.SANG, Team.HAN)),
                Arguments.of(PieceFactory.create(PieceType.JOL, Team.HAN)),
                Arguments.of(PieceFactory.create(PieceType.MA, Team.HAN)),
                Arguments.of(PieceFactory.create(PieceType.SA, Team.HAN)),
                Arguments.of(PieceFactory.create(PieceType.JANG, Team.HAN)));
    }
}