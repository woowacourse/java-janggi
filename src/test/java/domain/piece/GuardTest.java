package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import domain.TeamType;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GuardTest {

    static Stream<Arguments> canMoveGuard() {
        return Stream.of(
                Arguments.of(Position.of(2, 4)),
                Arguments.of(Position.of(0, 4)),
                Arguments.of(Position.of(1, 3)),
                Arguments.of(Position.of(1, 5)),
                Arguments.of(Position.of(2, 3)),
                Arguments.of(Position.of(2, 5)),
                Arguments.of(Position.of(0, 3)),
                Arguments.of(Position.of(0, 5))
        );
    }

    static Stream<Arguments> canMoveGuardException() {
        return Stream.of(
                Arguments.of(Position.of(7, 5)),
                Arguments.of(Position.of(9, 5)),
                Arguments.of(Position.of(7, 4)),
                Arguments.of(Position.of(8, 5))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveGuard(Position movePosition) {
        // given
        Position currentPosition = Position.of(1, 4);
        Piece guard = new Guard(TeamType.CHO);
        Board board = new Board(Map.of());

        // when & then
        assertThatCode(() -> guard.validateMove(currentPosition, movePosition, board))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동 위치가 올바르지 않으면 예외가 발생한다")
    void canMoveGuardException(Position movePosition) {
        // given
        Position currentPosition = Position.of(9, 3);
        Piece guard = new Guard(TeamType.HAN);
        Board board = new Board(Map.of());

        // when & then
        assertThatThrownBy(() -> guard.validateMove(currentPosition, movePosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 포지션으로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 아군이 있으면 이동할 수 없다.")
    void canMoveGuardTeamException() {
        // given
        Position movePosition = Position.of(9, 3);
        Position position = Position.of(8, 4);
        Piece guard = new Guard(TeamType.HAN);

        Map<Position, Piece> pieces = new HashMap<>();
        Position teamPosition = Position.of(9, 3);
        Piece team = new Soldier(TeamType.HAN);
        pieces.put(teamPosition, team);
        Board board = new Board(pieces);

        // when & then
        assertThatThrownBy(() -> guard.validateMove(position, movePosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
    }

    @Test
    @DisplayName("도착 칸에 적이 있으면 이동할 수 있다.")
    void validateMoveOtherTeam() {
        // given
        Position movePosition = Position.of(9, 3);
        Position position = Position.of(8, 4);
        Piece guard = new Guard(TeamType.HAN);

        Map<Position, Piece> pieces = new HashMap<>();
        Position otherPosition = Position.of(9, 3);
        Piece other = new Soldier(TeamType.CHO);
        pieces.put(otherPosition, other);
        Board board = new Board(pieces);

        // when & then
        assertThatCode(() -> guard.validateMove(position, movePosition, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("해당 점수를 반환한다")
    void getScore() {
        // given
        Guard guard = new Guard(TeamType.HAN);

        // when & then
        double actual = guard.getScore();
        double expected = 3.0;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("자신의 궁의 위치를 벗어나면 예외가 발생한다")
    void canMoveAreaException() {
        // given
        Guard guard = new Guard(TeamType.HAN);
        Position from = Position.of(8, 3);
        Position to = Position.of(8, 2);
        Board board = new Board(Map.of());

        // when & then
        assertThatThrownBy(() -> guard.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정된 지역으로 이동할 수 없는 기물입니다.");
    }
}
