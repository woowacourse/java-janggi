package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Piece;
import domain.piece.Soldier;
import domain.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {

    static Stream<Arguments> validateMove() {
        return Stream.of(
                Arguments.of(Position.of(1, 0)),
                Arguments.of(Position.of(1, 2)),
                Arguments.of(Position.of(0, 1))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void validateMove(Position movePosition) {
        // given
        Position position = Position.of(1, 1);
        Piece solider = new Soldier(TeamType.HAN);

        // when & then
        assertThatCode(() -> solider.validateMove(position, movePosition, new Board(Map.of())))
                .doesNotThrowAnyException();
    }

    static Stream<Arguments> validateMoveException() {
        return Stream.of(
                Arguments.of(Position.of(2, 1)),
                Arguments.of(Position.of(2, 2)),
                Arguments.of(Position.of(0, 2)),
                Arguments.of(Position.of(0, 0)),
                Arguments.of(Position.of(2, 0))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동 위치가 올바르지 않으면 예외가 발생한다")
    void validateMoveException(Position movePosition) {
        // given
        Position position = Position.of(1, 1);
        Piece solider = new Soldier(TeamType.HAN);

        // when & then
        assertThatThrownBy(() -> solider.validateMove(position, movePosition, new Board(Map.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 포지션으로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 아군이 있으면 이동할 수 없다.")
    void validateMoveTeamException() {
        // given
        Position movePosition = Position.of(2, 1);
        Position position = Position.of(1, 1);
        Piece solider = new Soldier(TeamType.CHO);

        Position teamPosition = Position.of(2, 1);
        Piece team = new Soldier(TeamType.CHO);
        Board board = new Board(Map.of(teamPosition, team));

        // when & then
        assertThatThrownBy(() -> solider.validateMove(position, movePosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
    }

    @Test
    @DisplayName("도착 칸에 적이 있으면 이동할 수 있다.")
    void canMoveSoldier3() {
        // given
        Position movePosition = Position.of(3, 1);
        Position position = Position.of(2, 1);
        Piece soldier = new Soldier(TeamType.CHO);

        Position enemyPosition = Position.of(3, 1);
        Piece enemy = new Soldier(TeamType.HAN);
        Board board = new Board(Map.of(enemyPosition, enemy));

        // when & then
        assertThatCode(() -> soldier.validateMove(position, movePosition, board))
                .doesNotThrowAnyException();
    }
}
