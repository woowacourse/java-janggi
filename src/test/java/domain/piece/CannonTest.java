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

class CannonTest {

    static Stream<Arguments> validateMoveCannon() {
        return Stream.of(
                Arguments.of(Position.of(3, 5), Position.of(3, 4), true),
                Arguments.of(Position.of(3, 6), Position.of(3, 5), true),
                Arguments.of(Position.of(3, 7), Position.of(3, 6), true),
                Arguments.of(Position.of(3, 8), Position.of(3, 5), true),
                Arguments.of(Position.of(5, 3), Position.of(4, 3), true),
                Arguments.of(Position.of(6, 3), Position.of(5, 3), true),
                Arguments.of(Position.of(7, 3), Position.of(6, 3), true),
                Arguments.of(Position.of(8, 3), Position.of(7, 3), true),
                Arguments.of(Position.of(1, 3), Position.of(2, 3), true),
                Arguments.of(Position.of(0, 3), Position.of(1, 3), true),
                Arguments.of(Position.of(3, 1), Position.of(3, 2), true),
                Arguments.of(Position.of(3, 0), Position.of(3, 2), true)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("포가 이동할 수 있다")
    void validateMoveCannon(Position movePosition, Position otherPosition) {
        // given
        Position startPosition = Position.of(3, 3);
        Piece cannon = new Cannon(TeamType.CHO);
        Piece other = new King(TeamType.HAN);
        Board board = new Board(Map.of(otherPosition, other));

        // when & then
        assertThatCode(() -> cannon.validateMove(startPosition, movePosition, board))
                .doesNotThrowAnyException();
    }

    static Stream<Arguments> validateMoveCannonException() {
        return Stream.of(
                Arguments.of(Position.of(4, 3), Position.of(5, 3)),
                Arguments.of(Position.of(2, 3), Position.of(4, 3)),
                Arguments.of(Position.of(3, 2), Position.of(2, 2)),
                Arguments.of(Position.of(3, 4), Position.of(5, 3)),
                Arguments.of(Position.of(4, 4), Position.of(3, 6)),
                Arguments.of(Position.of(5, 5), Position.of(3, 7))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("포가 이동할 수 없으면 예외가 발생한다")
    void validateMoveCannonException(Position movePosition, Position otherPosition) {
        // given
        Position startPosition = Position.of(3, 3);
        Piece cannon = new Cannon(TeamType.CHO);
        Piece other = new King(TeamType.HAN);
        Board board = new Board(Map.of(otherPosition, other));

        // when & then
        assertThatThrownBy(() -> cannon.validateMove(startPosition, movePosition, board))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 포가 있으면 예외가 발생한다")
    void validateMoveCannonOnPathException() {
        // given
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece cannon = new Cannon(TeamType.HAN);

        Position otherPosition = Position.of(3, 2);
        Piece otherCannon = new Cannon(TeamType.HAN);
        Board board = new Board(Map.of(otherPosition, otherCannon));

        // when & then
        assertThatThrownBy(() -> cannon.validateMove(startPosition, expectedPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 뛰어넘을 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 예외가 발생한다")
    void validateMoveTeam() {
        // given
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece cannon = new Cannon(TeamType.HAN);

        Position jumpPosition = Position.of(3, 2);
        Piece jump = new Soldier(TeamType.CHO);

        Position otherPosition = Position.of(4, 2);
        Piece other = new Soldier(TeamType.HAN);

        Board board = new Board(Map.of(jumpPosition, jump, otherPosition, other));

        // when & then
        assertThatThrownBy(() -> cannon.validateMove(startPosition, expectedPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 예외가 발생하지 않는다")
    void validateMoveEnemy() {
        // given
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece cannon = new Cannon(TeamType.HAN);

        Position jumpPosition = Position.of(3, 2);
        Piece jump = new Soldier(TeamType.CHO);

        Position enemyPosition = Position.of(4, 2);
        Piece enemy = new Soldier(TeamType.CHO);

        Board board = new Board(Map.of(jumpPosition, jump, enemyPosition, enemy));

        // when & then
        assertThatCode(() -> cannon.validateMove(startPosition, expectedPosition, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("도착 지점에 포가 있으면 false를 반환한다")
    void validateMoveDestinationException() {
        // given
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece cannon = new Cannon(TeamType.HAN);

        Position jumpPosition = Position.of(3, 2);
        Piece jump = new Soldier(TeamType.CHO);

        Position otherPosition = Position.of(4, 2);
        Piece otherCannon = new Cannon(TeamType.CHO);
        Board board = new Board(Map.of(jumpPosition, jump, otherPosition, otherCannon));

        // when & then
        assertThatThrownBy(() -> cannon.validateMove(startPosition, expectedPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 잡을 수 없습니다.");

    }

    @Test
    @DisplayName("도착 지점까지 기물이 2개 이상이면 false를 반환한다")
    void validateMoveCannonJumpException() {
        // given
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(6, 2);
        Piece cannon = new Cannon(TeamType.HAN);

        Position otherPosition1 = Position.of(4, 2);
        Piece other1 = new Horse(TeamType.CHO);

        Position otherPosition2 = Position.of(5, 2);
        Piece other2 = new Horse(TeamType.CHO);

        Board board = new Board(Map.of(otherPosition1, other1, otherPosition2, other2));

        assertThatThrownBy(() -> cannon.validateMove(startPosition, expectedPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 하나의 기물을 뛰어넘어야 합니다.");
    }
}
