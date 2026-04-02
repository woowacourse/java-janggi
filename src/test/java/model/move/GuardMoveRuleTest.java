package model.move;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import model.board.Board;
import model.board.Country;
import model.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GuardMoveRuleTest {

    Board board = new Board();

    @ParameterizedTest
    @MethodSource("provideGuardMoves")
    void 사는_궁성_안에서만_이동할_수_있다(Position from, Position to, boolean expected) {
        Move move = new Move(from, to);
        GuardMoveRule rule = new GuardMoveRule();

        boolean actual = rule.patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, board, Country.HAN));

        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideGuardMoves() {
        return Stream.of(
                // 1. 직선 이동 (상하좌우)
                arguments(Position.of(2, 5), Position.of(1, 5), true),
                arguments(Position.of(1, 4), Position.of(1, 5), true),

                // 2. 대각선 이동 (유효한 경우)
                arguments(Position.of(1, 4), Position.of(2, 5), true),
                arguments(Position.of(2, 5), Position.of(3, 6), true),

                // 3. 대각선 이동 (불가능한 경우)
                arguments(Position.of(1, 5), Position.of(2, 4), false),
                arguments(Position.of(2, 6), Position.of(1, 5), false),

                // 4. 궁성 밖으로 나가는 경우 (실패)
                arguments(Position.of(1, 4), Position.of(1, 3), false),
                arguments(Position.of(3, 5), Position.of(4, 5), false),

                // 5. 한의 궁성에서 초의 궁성으로 순간이동 (실패)
                arguments(Position.of(2, 5), Position.of(9, 5), false)
        );
    }
}