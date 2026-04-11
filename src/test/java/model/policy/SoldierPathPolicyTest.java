package model.policy;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import model.board.Board;
import model.board.Country;
import model.move.Move;
import model.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierPathPolicyTest {

    Board board = new Board();

    @ParameterizedTest
    @MethodSource("provideValidMoves")
    void 병사는_유효한_방향으로_이동한다(Position from, Position to, Country country) {
        Move move = new Move(from, to);
        SoldierPathPolicy policy = new SoldierPathPolicy();

        assertThatCode(() -> policy.validateDestination(move, board, country))
                .doesNotThrowAnyException();
    }


    @ParameterizedTest
    @MethodSource("provideInvalidMoves")
    void 병사가_뒤로_가면_예외가_발생한다(Position from, Position to, Country country) {
        Move move = new Move(from, to);
        SoldierPathPolicy policy = new SoldierPathPolicy();

        assertThatThrownBy(() -> policy.validateDestination(move, board, country))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 병사는 뒤로 갈 수 없습니다.");
    }

    private static Stream<Arguments> provideValidMoves() {
        return Stream.of(
                // 1. 한나라 기물 상대 진영으로 이동
                Arguments.arguments(Position.of(8, 4), Position.of(9, 5), Country.HAN),
                Arguments.arguments(Position.of(8, 4), Position.of(9, 4), Country.HAN),
                Arguments.arguments(Position.of(9, 5), Position.of(10, 6), Country.HAN),
                // 2. 초나라 기물 상대 진영으로 이동
                Arguments.arguments(Position.of(3, 4), Position.of(2, 5), Country.CHO),
                Arguments.arguments(Position.of(2, 5), Position.of(1, 6), Country.CHO),
                Arguments.arguments(Position.of(3, 6), Position.of(2, 6), Country.CHO)
        );
    }

    private static Stream<Arguments> provideInvalidMoves() {
        return Stream.of(
                // 1. 한나라 기물 아군 진영으로 이동
                Arguments.arguments(Position.of(10, 6), Position.of(9, 5), Country.HAN),
                Arguments.arguments(Position.of(9, 5), Position.of(8, 4), Country.HAN),
                Arguments.arguments(Position.of(10, 4), Position.of(9, 4), Country.HAN),
                // 2. 초나라 기물 아군 진영으로 이동 불가
                Arguments.arguments(Position.of(1, 4), Position.of(2, 5), Country.CHO),
                Arguments.arguments(Position.of(1, 4), Position.of(2, 4), Country.CHO),
                Arguments.arguments(Position.of(2, 5), Position.of(3, 6), Country.CHO)
        );
    }
}