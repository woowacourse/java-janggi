package janggi.domain;

import janggi.domain.team.TeamType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = Board.createInitialBoard();
    }

    @Test
    @DisplayName("출발지와 목적지가 동일할 경우 예외 발생")
    void checkSamePosition() {
        // given
        Position start = new Position(4, 4);
        Position end = new Position(4, 4);

        // when & then
        assertThatThrownBy(() -> board.validateCanMove(start, end, TeamType.CHU))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출발지와 목적지가 동일합니다.");
    }

    @Test
    @DisplayName("입력한 좌표에 기물이 없을 경우 false 반환")
    void cannotMoveWhenStartPositionHasNoCurrentTeamPiece() {
        // given
        Position notExistPosition = new Position(2, 2);

        // when & then
        boolean pieceExists = board.isPieceExists(notExistPosition, TeamType.CHU);
        Assertions.assertThat(pieceExists).isFalse();
    }

    @Test
    @DisplayName("목적 좌표에 아군 기물이 있을 경우 예외 발생")
    void cannotMoveToSameTeamPiecePosition() {
        // given
        Position currentTeamPosition = new Position(2, 1);

        // when & then
        assertThatThrownBy(() -> board.validateCanMove(new Position(1, 1), currentTeamPosition, TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("아군이 존재하는 좌표로는 이동할 수 없습니다.");
    }
}
