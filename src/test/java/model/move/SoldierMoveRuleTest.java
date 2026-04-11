package model.move;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import model.board.Board;
import model.board.Country;
import model.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierMoveRuleTest {

    Board board = new Board();

    @ParameterizedTest
    @MethodSource("provideHanSoldierMoves")
    void 한나라_병사는_한_칸_이동_가능하다(Position from, Position to, boolean expected) {
        Move move = new Move(from, to);
        SoldierMoveRule rule = new SoldierMoveRule();

        boolean actual = rule.patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, board, Country.HAN));

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideChoSoldierMoves")
    void 초나라_병사는_한_칸_이동_가능하다(Position from, Position to, boolean expected) {
        Move move = new Move(from, to);
        SoldierMoveRule rule = new SoldierMoveRule();

        boolean actual = rule.patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, board, Country.CHO));

        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideHanSoldierMoves() {
        return Stream.of(
                Arguments.arguments(Position.of(5, 5), Position.of(6, 5), true),
                Arguments.arguments(Position.of(5, 5), Position.of(5, 4), true),
                Arguments.arguments(Position.of(5, 5), Position.of(5, 6), true)
        );
    }

    private static Stream<Arguments> provideChoSoldierMoves() {
        return Stream.of(
                Arguments.arguments(Position.of(5, 5), Position.of(4, 5), true),
                Arguments.arguments(Position.of(5, 5), Position.of(5, 4), true),
                Arguments.arguments(Position.of(5, 5), Position.of(5, 6), true)
        );
    }
}