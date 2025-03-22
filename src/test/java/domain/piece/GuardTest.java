package domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import domain.Board;
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
                Arguments.of(Position.of(1, 0)),
                Arguments.of(Position.of(2, 1)),
                Arguments.of(Position.of(1, 2)),
                Arguments.of(Position.of(0, 1))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveGuard(Position movePosition) {
        // given
        Position currentPosition = Position.of(1, 1);
        Piece guard = new Guard(TeamType.CHO);
        Board board = new Board(Map.of());

        // when & then
        assertThatCode(() -> guard.validateMove(currentPosition, movePosition, board))
                .doesNotThrowAnyException();
    }

    static Stream<Arguments> canMoveGuardException() {
        return Stream.of(
                Arguments.of(Position.of(2, 2)),
                Arguments.of(Position.of(0, 2)),
                Arguments.of(Position.of(0, 0)),
                Arguments.of(Position.of(2, 0))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동 위치가 올바르지 않으면 예외가 발생한다")
    void canMoveGuardException(Position movePosition) {
        // given
        Position currentPosition = Position.of(1, 1);
        Piece guard = new Guard(TeamType.CHO);
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
        Position movePosition = Position.of(2, 1);
        Position position = Position.of(1, 1);
        Piece guard = new Guard(TeamType.CHO);

        Map<Position, Piece> pieces = new HashMap<>();
        Position teamPosition = Position.of(2, 1);
        Piece team = new Soldier(TeamType.CHO);
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
        Position movePosition = Position.of(3, 1);
        Position position = Position.of(2, 1);
        Piece guard = new Guard(TeamType.CHO);

        Map<Position, Piece> pieces = new HashMap<>();
        Position otherPosition = Position.of(3, 1);
        Piece other = new Soldier(TeamType.HAN);
        pieces.put(otherPosition, other);
        Board board = new Board(pieces);

        // when & then
        assertThatCode(() -> guard.validateMove(position, movePosition, board))
                .doesNotThrowAnyException();
    }
}
