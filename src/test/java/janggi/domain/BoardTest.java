package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.HorseSide;
import janggi.domain.piece.None;
import janggi.domain.piece.Position;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("현재 기물 위치와 이동 시킬 위치를 받아 기물을 이동시킨다.")
    @Test
    void movePiece() {
        Board board = BoardFactory.getInitializedBoard(HorseSide.LEFT, HorseSide.LEFT,
                HorseSide.LEFT, HorseSide.LEFT);
        Position beforePosition = new Position(7, 1);
        Position afterPosition = new Position(6, 1);

        board.movePiece(Team.BLUE, beforePosition, afterPosition);

        assertThat(board.getBoard().get(beforePosition)).isInstanceOf(None.class);
        assertThat(board.getBoard().get(afterPosition)).isInstanceOf(Soldier.class);
    }

    @DisplayName("기물 별 이동 조건을 만족하지 않는 경우 예외를 발생시킨다.")
    @Test
    void movePieceException() {
        Board board = BoardFactory.getInitializedBoard(HorseSide.LEFT, HorseSide.LEFT,
                HorseSide.LEFT, HorseSide.LEFT);
        Position beforePosition = new Position(7, 1);
        Position afterPosition = new Position(5, 1);

        assertThatThrownBy(() -> board.movePiece(Team.RED, beforePosition, afterPosition));
    }
}
