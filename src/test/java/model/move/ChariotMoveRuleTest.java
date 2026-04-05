package model.move;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import model.board.Board;
import model.board.Country;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotMoveRuleTest {

    Board board = new Board();

    @ParameterizedTest
    @MethodSource("provideStraight")
    void 차는_직선으로_움직여야_한다(Position from, Position to, boolean expected) {
        Move move = Move.of(from, to);
        ChariotMoveRule rule = new ChariotMoveRule();

        boolean actual = rule.patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, board, Country.HAN));

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("providePalaceDiagonal")
    void 차는_궁성에서_대각선으로_이동_가능하다(Position from, Position to, boolean expected) {
        Move move = Move.of(from, to);
        ChariotMoveRule rule = new ChariotMoveRule();

        boolean actual = rule.patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, board, Country.HAN));

        assertEquals(expected, actual);
    }

    @Test
    void 가운데에_기물이_있을_경우_대각선으로_이동하지_못한다() {
        Move move = Move.of(Position.of(1, 4), Position.of(3, 6));
        board.place(Position.of(2, 5), new Piece(Country.HAN, PieceType.GUARD));
        ChariotMoveRule rule = new ChariotMoveRule();

        boolean expected = false;
        boolean actual = rule.patterns(move).stream()
                .anyMatch(pattern -> pattern.matches(move, board, Country.HAN));

        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideStraight() {
        return Stream.of(
                Arguments.arguments(Position.of(4, 5), Position.of(6, 5), true),
                Arguments.arguments(Position.of(7, 5), Position.of(7, 3), true),
                Arguments.arguments(Position.of(5, 5), Position.of(4, 4), false)
        );
    }

    private static Stream<Arguments> providePalaceDiagonal() {
        return Stream.of(
                Arguments.arguments(Position.of(1, 4), Position.of(2, 5), true),
                Arguments.arguments(Position.of(1, 4), Position.of(3, 6), true),
                Arguments.arguments(Position.of(1, 5), Position.of(2, 4), false),
                Arguments.arguments(Position.of(3, 4), Position.of(1, 4), true),
                Arguments.arguments(Position.of(1, 5), Position.of(3, 5), true)
        );
    }

}