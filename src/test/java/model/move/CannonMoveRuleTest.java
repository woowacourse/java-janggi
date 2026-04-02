package model.move;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;
import model.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonMoveRuleTest {

    @ParameterizedTest()
    @MethodSource("provideCannonMove")
    void 포는_직선_방향으로만_패턴을_생성한다(Position from, Position to, boolean expected) {
        Move move = new Move(from, to);
        CannonMoveRule rule = new CannonMoveRule();

        List<MovePattern> patterns = rule.patterns(move);

        assertThat(!patterns.isEmpty()).isEqualTo(expected);
    }

    static Stream<Arguments> provideCannonMove() {
        return Stream.of(
                // 1. 가로
                arguments(Position.of(1, 2), Position.of(1, 5), true),
                // 2. 세로
                arguments(Position.of(1, 2), Position.of(4, 2), true),
                // 3. 대각선
                arguments(Position.of(1, 2), Position.of(2, 3), false),
                arguments(Position.of(1, 4), Position.of(3, 6), true)
        );
    }
}