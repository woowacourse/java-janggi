package domain.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Board;
import domain.TeamType;
import domain.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotTest {

    static Stream<Arguments> validateMoveChariotEmpty() {
        return Stream.of(
                Arguments.of(Position.of(4, 3)),
                Arguments.of(Position.of(5, 3)),
                Arguments.of(Position.of(6, 3)),
                Arguments.of(Position.of(7, 3)),
                Arguments.of(Position.of(8, 3)),
                Arguments.of(Position.of(2, 3)),
                Arguments.of(Position.of(1, 3)),
                Arguments.of(Position.of(0, 3)),
                Arguments.of(Position.of(3, 2)),
                Arguments.of(Position.of(3, 1)),
                Arguments.of(Position.of(3, 0)),
                Arguments.of(Position.of(3, 4)),
                Arguments.of(Position.of(3, 5)),
                Arguments.of(Position.of(3, 6)),
                Arguments.of(Position.of(3, 7)),
                Arguments.of(Position.of(3, 8))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동하려는 경로가 비었을 때 정상적으로 이동할 수 있다")
    void validateMoveChariotEmpty(Position movePosition) {
        // given
        Position startPosition = Position.of(3, 3);
        Piece chariot = new Chariot(TeamType.CHO);
        Board board = new Board(Map.of());

        // when & then
        assertThatCode(() -> chariot.validateMove(startPosition, movePosition, board))
                .doesNotThrowAnyException();
    }

    static Stream<Arguments> validateMoveChariotException() {
        return Stream.of(
                Arguments.of(Position.of(4, 4)),
                Arguments.of(Position.of(5, 5))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동할 수 없는 경로면 예외가 발생한다")
    void validateMoveChariotException(Position movePosition) {
        // given
        Position startPosition = Position.of(3, 3);
        Piece chariot = new Chariot(TeamType.CHO);
        Board board = new Board(Map.of());

        // when & then
        assertThatThrownBy(() -> chariot.validateMove(startPosition, movePosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 포지션으로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("이동 경로에 다른 기물이 있으면 예외가 발생한다")
    void validateMoveChariotBlocked() {
        // given
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece chariot = new Chariot(TeamType.HAN);

        Position otherPosition = Position.of(3, 2);
        Piece other = new Soldier(TeamType.HAN);

        Board board = new Board(Map.of(otherPosition, other));

        // when & then
        assertThatThrownBy(() -> chariot.validateMove(startPosition, expectedPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 있어 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 예외가 발생한다")
    void validateMoveChariotTeam() {
        // given
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece chariot = new Chariot(TeamType.HAN);

        Position otherPosition = Position.of(4, 2);
        Piece other = new Soldier(TeamType.HAN);
        Board board = new Board(Map.of(otherPosition, other));

        // when & then
        assertThatThrownBy(() -> chariot.validateMove(startPosition, expectedPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 예외가 발생하지 않는다")
    void validateMoveChariotEnemy() {
        // given
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece chariot = new Chariot(TeamType.HAN);

        Position otherPosition = Position.of(4, 2);
        Piece other = new Soldier(TeamType.CHO);

        Board board = new Board(Map.of(otherPosition, other));

        // when & then
        assertThatCode(() -> chariot.validateMove(startPosition, expectedPosition, board))
                .doesNotThrowAnyException();
    }
}
