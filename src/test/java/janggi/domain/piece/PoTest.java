package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PoTest {

    @Test
    @DisplayName("포는 상하좌우로 직선 이동할 수 있다.")
    void isValidMovePatternStraight() {
        // given
        Po po = new Po(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(po.isValidMovePattern(4, 4, 4, 8)).isTrue(),
            () -> assertThat(po.isValidMovePattern(4, 4, 4, 1)).isTrue(),
            () -> assertThat(po.isValidMovePattern(4, 4, 7, 4)).isTrue(),
            () -> assertThat(po.isValidMovePattern(4, 4, 1, 4)).isTrue()
        );
    }

    @Test
    @DisplayName("포는 대각선이나 제자리로 이동할 수 없다.")
    void cannotMoveInvalidPattern() {
        // given
        Po po = new Po(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(po.isValidMovePattern(4, 4, 5, 5)).isFalse(),
            () -> assertThat(po.isValidMovePattern(4, 4, 4, 4)).isFalse()
        );
    }

    @Test
    @DisplayName("포는 사이에 기물이 하나만 있으면 이동할 수 있다.")
    void isObstaclesNotExistWhenExactlyOneBridgeExists() {
        // given
        Po po = new Po(TeamType.CHU);
        Board board = Board.createInitialBoard();
        Board movedBoard = board.move(new Position(1, 4), new Position(2, 4), TeamType.CHU);

        // when
        boolean result = po.isObstaclesNotExist(new Position(2, 3), new Position(2, 6), movedBoard);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포는 사이에 기물이 없으면 이동할 수 없다.")
    void cannotMoveWithoutBridge() {
        // given
        Po po = new Po(TeamType.CHU);
        Board board = Board.createInitialBoard();

        // when
        boolean result = po.isObstaclesNotExist(new Position(2, 3), new Position(2, 6), board);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포는 포를 다리로 사용할 수 없고 포를 잡을 수도 없다.")
    void cannotUsePoAsBridgeOrTarget() {
        // given
        Po po = new Po(TeamType.CHU);
        Board board = Board.createInitialBoard();
        Board movedBoard = board.move(new Position(1, 4), new Position(2, 4), TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(po.isObstaclesNotExist(new Position(2, 3), new Position(2, 10), board)).isFalse(),
            () -> assertThat(po.isObstaclesNotExist(new Position(2, 3), new Position(2, 8), movedBoard)).isFalse()
        );
    }
}
