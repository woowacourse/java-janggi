package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.direction.MoveAmount;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.factory.CannonFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("기물의 초기 배치 테스트")
class InitialPositionTest {

    @DisplayName("진영에 따른 기물 초기 배치")
    @ParameterizedTest(name = "잔영이 {0}일 때")
    @MethodSource("sideAndExpected")
    void 진영에_따른_기물_초기_배치(Side side, Map<Intersection, Piece> expected) {
        InitialPosition initialPosition = new InitialPosition(side, new MoveAmount(2), 2, 8);
        Map<Intersection, Piece> placed = initialPosition.placePiece(new CannonFactory());

        assertThat(placed)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("배치된 기물은 서로 동일하지 않아야 된다")
    @Test
    void 배치된_기물은_서로_동일하지_않아야_된다() {
        Side side = Side.CHO;
        InitialPosition initialPosition = new InitialPosition(side, new MoveAmount(2), 2, 8);

        Map<Intersection, Piece> placed = initialPosition.placePiece(new CannonFactory());
        List<Piece> pieces = List.copyOf(placed.values());

        assertThat(pieces)
                .extracting(System::identityHashCode)
                .doesNotHaveDuplicates();
    }

    private static Stream<Arguments> sideAndExpected() {
        final Side cho = Side.CHO;
        final Side han = Side.HAN;

        return Stream.of(
                Arguments.of(
                        cho,
                        Map.of(
                                new Intersection(8, 2), new Piece(PieceType.CANNON, cho),
                                new Intersection(8, 8), new Piece(PieceType.CANNON, cho)
                        )
                ),
                Arguments.of(
                        han,
                        Map.of(
                                new Intersection(3, 2), new Piece(PieceType.CANNON, han),
                                new Intersection(3, 8), new Piece(PieceType.CANNON, han)
                        )
                )
        );
    }
}
