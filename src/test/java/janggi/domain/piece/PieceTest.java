package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.move.MovePattern;
import janggi.domain.move.MovementStrategy;
import janggi.domain.move.OneStepStrategy;
import janggi.domain.space.Destinations;
import janggi.domain.space.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceTest {

    @ParameterizedTest
    @MethodSource("provideSideCase")
    void 주어진_진영과_자신의_진영이_같은지_올바르게_판별한다(Side side, Side other, boolean expected) {
        Piece piece = PieceFactory.createSoldier(side);

        boolean actual = piece.isAlly(other);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> provideSideCase() {
        return Stream.of(
                arguments(Side.CHO, Side.CHO, true),
                arguments(Side.HAN, Side.HAN, true),
                arguments(Side.CHO, Side.HAN, false),
                arguments(Side.HAN, Side.CHO, false)
        );
    }

    @DisplayName("궁과 사를 제외한 기물은 이동 경로 탐색 시 궁성 밖의 좌표도 정상적으로 목적지에 포함한다.")
    @Test
    void findDestinations_unrestrictedPiece_includesOutsidePalace() {
        // given
        Position current = Position.of(3, 1);
        MovementStrategy strategy = new OneStepStrategy(MovePattern.LINEAR);
        Piece normalPiece = new Piece(Side.CHO, strategy) {
            @Override
            public PieceType getType() {
                return null;
            }
        };
        Map<Position, Piece> pieces = Map.of(current, normalPiece);
        Board board = new Board(pieces);

        // when
        Destinations actual = normalPiece.findDestinations(current, board);

        // then
        assertThat(actual.getPositions()).contains(Position.of(2, 1));
    }
}
