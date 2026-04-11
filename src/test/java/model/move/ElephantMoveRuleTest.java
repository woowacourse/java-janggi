package model.move;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import model.board.Board;
import model.board.Country;
import model.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantMoveRuleTest {

    ElephantMoveRule rule = new ElephantMoveRule();
    Board board = new Board();

    @ParameterizedTest()
    @MethodSource("provideValidElephantMoves")
    void 상은_유효한_8개_방향으로_이동할_수_있다(Position from, Position to) {
        Move move = new Move(from, to);

        boolean actual = rule.patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, board, Country.CHO));

        assertThat(actual).isTrue();
    }

    @ParameterizedTest()
    @MethodSource("provideInvalidElephantMoves")
    void 상은_허용되지_않은_방향으로는_이동할_수_없다(Position from, Position to) {
        Move move = new Move(from, to);

        boolean actual = rule.patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, board, Country.CHO));

        assertThat(actual).isFalse();
    }

    static Stream<Arguments> provideValidElephantMoves() {
        Position center = Position.of(5, 5);
        return Stream.of(
                arguments(center, Position.of(2, 3)),
                arguments(center, Position.of(2, 7)),
                arguments(center, Position.of(3, 8)),
                arguments(center, Position.of(7, 8)),
                arguments(center, Position.of(8, 7)),
                arguments(center, Position.of(8, 3)),
                arguments(center, Position.of(7, 2)),
                arguments(center, Position.of(3, 2))
        );
    }

    static Stream<Arguments> provideInvalidElephantMoves() {
        Position center = Position.of(5, 5);
        return Stream.of(
                arguments(center, Position.of(5, 6)),
                arguments(center, Position.of(3, 4)),
                arguments(center, Position.of(8, 8)),
                arguments(center, Position.of(4, 4))
        );
    }

}