package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.None;
import janggi.domain.piece.Position;
import janggi.domain.piece.PositionSide;
import janggi.domain.piece.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("현재 기물 위치와 이동 시킬 위치를 받아 기물을 이동시킨다.")
    @Test
    void movePiece() {
        Board board = BoardFactory.getInitializedBoard(PositionSide.LEFT, PositionSide.LEFT,
                PositionSide.LEFT, PositionSide.LEFT);

        Position beforePosition = new Position(7, 1);
        Position afterPosition = new Position(6, 1);
        board.movePiece(beforePosition, afterPosition);

        assertThat(board.getPieceByPosition(beforePosition)).isInstanceOf(None.class);
        assertThat(board.getPieceByPosition(afterPosition)).isInstanceOf(Soldier.class);
    }
}