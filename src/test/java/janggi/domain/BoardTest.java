package janggi.domain;

import janggi.domain.side.TeamType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    @DisplayName("도착 좌표가 장기판 범위 밖일 경우 예외 발생")
    void cannotMoveWhenEndPositionIsOutOfRange() {
        // given
        Board board = Board.createInitialBoard();
        Position outOfBound = new Position(1, 11);

        // when & then
        assertThatThrownBy(() -> board.validateCanMove(new Position(1, 4), outOfBound, TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("입력한 좌표가 장기판 범위 밖입니다.");
    }

    @Test
    @DisplayName("입력한 좌표에 기물이 없을 경우 false 반환")
    void cannotMoveWhenStartPositionHasNoCurrentTeamPiece() {
        // given
        Board board = Board.createInitialBoard();
        Position notExistPosition = new Position(2, 2);

        // when & then
        boolean pieceExists = board.isPieceExists(notExistPosition, TeamType.CHU);
        Assertions.assertThat(pieceExists).isFalse();
    }

    @Test
    @DisplayName("목적 좌표에 아군 기물이 있을 경우 예외 발생")
    void cannotMoveToSameTeamPiecePosition() {
        // given
        Board board = Board.createInitialBoard();
        Position currentTeamPosition = new Position(2, 1);

        // when & then
        assertThatThrownBy(() -> board.validateCanMove(new Position(1, 1), currentTeamPosition, TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("아군이 존재하는 좌표로는 이동할 수 없습니다.");
    }
}
