package domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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

class HorseTest {

    static Stream<Arguments> validateMoveEmptyPosition() {
        return Stream.of(
                Arguments.of(Position.of(1, 0)),
                Arguments.of(Position.of(0, 1)),
                Arguments.of(Position.of(0, 3)),
                Arguments.of(Position.of(1, 4)),
                Arguments.of(Position.of(3, 4)),
                Arguments.of(Position.of(4, 3)),
                Arguments.of(Position.of(4, 1)),
                Arguments.of(Position.of(3, 0))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동하려는 경로가 비었을 때 정상적으로 이동할 수 있다")
    void validateMoveEmptyPosition(Position movePosition) {
        // given
        Position startPosition = Position.of(2, 2);
        Piece horse = new Horse(TeamType.CHO);

        // when & then
        assertThatCode(() -> horse.validateMove(startPosition, movePosition, new Board(Map.of())))
                .doesNotThrowAnyException();
    }

    static Stream<Arguments> validateMoveBlocked() {
        return Stream.of(
                Arguments.of(Position.of(3, 2), Position.of(4, 3)),
                Arguments.of(Position.of(2, 3), Position.of(3, 4)),
                Arguments.of(Position.of(1, 2), Position.of(0, 3)),
                Arguments.of(Position.of(2, 1), Position.of(1, 0))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동 경로에 다른 기물이 있으면 예외가 발생한다")
    void validateMoveBlocked(Position otherPosition, Position endPosition) {
        // given
        Position startPosition = Position.of(2, 2);
        Piece horse = new Horse(TeamType.HAN);

        Piece other = new Soldier(TeamType.HAN);
        Board board = new Board(Map.of(otherPosition, other));

        // when & then
        assertThatThrownBy(() -> horse.validateMove(startPosition, endPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 있어 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 예외가 발생한다")
    void validateMoveTeam() {
        // given
        Position startPosition = Position.of(2, 2);
        Position movePosition = Position.of(3, 4);
        Piece horse = new Horse(TeamType.HAN);

        Position teamPosition = Position.of(3, 4);
        Piece team = new Soldier(TeamType.HAN);
        Board board = new Board(Map.of(teamPosition, team));

        // when & then
        assertThatThrownBy(() -> horse.validateMove(startPosition, movePosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 예외가 발생하지 않는다")
    void validateMoveEnemy() {
        // given
        Position startPosition = Position.of(2, 2);
        Position movePosition = Position.of(3, 4);
        Piece horse = new Horse(TeamType.CHO);

        Position teamPosition = Position.of(3, 4);
        Piece team = new Soldier(TeamType.HAN);
        Board board = new Board(Map.of(teamPosition, team));

        // when & then
        assertThatCode(() -> horse.validateMove(startPosition, movePosition, board))
                .doesNotThrowAnyException();
    }
}
