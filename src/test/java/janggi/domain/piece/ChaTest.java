package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.team.TeamType;
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
            () -> assertThat(cha.isValidMovePattern(4, 4, 7, 4)).isTrue(),
            () -> assertThat(cha.isValidMovePattern(4, 4, 1, 4)).isTrue(),
            () -> assertThat(cha.isValidMovePattern(4, 4, 4, 8)).isTrue(),
            () -> assertThat(cha.isValidMovePattern(4, 4, 4, 1)).isTrue()
        );
    }

    @Test
    @DisplayName("차는 궁성 안에서 대각선으로 이동할 수 있다.")
    void canMoveDiagonalInsidePalace() {
        // given
        Cha cha = new Cha(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(cha.isValidMovePattern(4, 1, 5, 2)).isTrue(),
            () -> assertThat(cha.isValidMovePattern(4, 1, 6, 3)).isTrue()
        );
    }

    @Test
    @DisplayName("차는 궁성 밖 대각선이나 연결되지 않은 궁성 대각선으로 이동할 수 없다.")
    void cannotMoveDiagonal() {
        // given
        Cha cha = new Cha(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(cha.isValidMovePattern(4, 4, 5, 5)).isFalse(),
            () -> assertThat(cha.isValidMovePattern(4, 4, 2, 2)).isFalse(),
            () -> assertThat(cha.isValidMovePattern(4, 2, 5, 1)).isFalse()
        );
    }

    @Test
    @DisplayName("차는 제자리로 이동할 수 없다.")
    void cannotMoveSamePosition() {
        // given
        Cha cha = new Cha(TeamType.CHU);

        // when & then
        assertThat(cha.isValidMovePattern(4, 4, 4, 4)).isFalse();
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

    @Test
    @DisplayName("궁성 대각선 경로가 비어 있으면 차는 이동할 수 있다.")
    void isValidDiagonalPathInsidePalaceWhenRouteIsEmpty() {
        // given
        Cha cha = new Cha(TeamType.CHU);
        Board board = Board.createInitialBoard();
        Board movedBoard = board.move(new Position(5, 2), new Position(5, 3), TeamType.CHU);

        // when
        boolean result = cha.isObstaclesNotExist(new Position(4, 1), new Position(6, 3), movedBoard);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁성 대각선 경로가 막혀 있으면 차는 이동할 수 없다.")
    void cannotMoveDiagonalWhenPalaceRouteIsBlocked() {
        // given
        Cha cha = new Cha(TeamType.CHU);
        Board board = Board.createInitialBoard();

        // when
        boolean result = cha.isObstaclesNotExist(new Position(4, 1), new Position(6, 3), board);

        // then
        assertThat(result).isFalse();
    }
}
