package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaTest {

    @Test
    @DisplayName("차는 상하좌우로 직선 이동할 수 있다.")
    void isValidMovePatternStraight() {
        // given
        Cha cha = new Cha(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(cha.isValidMovePattern(createPosition(4, 4), createPosition(7, 4))).isTrue(),
            () -> assertThat(cha.isValidMovePattern(createPosition(4, 4), createPosition(1, 4))).isTrue(),
            () -> assertThat(cha.isValidMovePattern(createPosition(4, 4), createPosition(4, 8))).isTrue(),
            () -> assertThat(cha.isValidMovePattern(createPosition(4, 4), createPosition(4, 1))).isTrue()
        );
    }

    @Test
    @DisplayName("차는 대각선으로 이동할 수 없다.")
    void cannotMoveDiagonal() {
        // given
        Cha cha = new Cha(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(cha.isValidMovePattern(createPosition(4, 4), createPosition(5, 5))).isFalse(),
            () -> assertThat(cha.isValidMovePattern(createPosition(4, 4), createPosition(2, 2))).isFalse()
        );
    }

    @Test
    @DisplayName("차는 제자리로 이동할 수 없다.")
    void cannotMoveSamePosition() {
        // given
        Cha cha = new Cha(TeamType.CHU);

        // when & then
        assertThat(cha.isValidMovePattern(createPosition(4, 4), createPosition(4, 4))).isFalse();
    }

    @Test
    @DisplayName("이동 경로가 비어 있으면 차는 이동할 수 있다.")
    void isValidPathWhenRouteIsEmpty() {
        // given
        Cha cha = new Cha(TeamType.CHU);
        Board board = Board.createInitialBoard();

        // when
        boolean result = cha.isObstaclesNotExist(new Position(1, 1), new Position(1, 3), board);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이동 경로가 막혀 있으면 차는 이동할 수 없다.")
    void cannotMoveWhenRouteIsBlocked() {
        // given
        Cha cha = new Cha(TeamType.CHU);
        Board board = Board.createInitialBoard();

        // when
        boolean result = cha.isObstaclesNotExist(new Position(1, 1), new Position(1, 5), board);

        // then
        assertThat(result).isFalse();
    }

    private Position createPosition(int x, int y) {
        return new Position(x, y);
    }
}
