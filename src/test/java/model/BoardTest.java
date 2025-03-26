package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Board;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.Position;
import janggi.model.piece.Chariot;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 보드에_특정_위치에_있는_기물을_움직일_수_있다() {
        Board board = new Board();

        Chariot chariot = new Chariot(Color.RED);
        Position position = new Position(4, 4);

        board.putPiece(position, chariot);
        board.putPiece(new Position(5, 4), new Chariot(Color.RED));
        board.move(position, new Position(4, 5), turn.getCurrentTurn());

        OccupiedPositions occupiedPositions = board.generateOccupiedPositions();

        assertThat(occupiedPositions.existPosition(position)).isFalse();
        assertThat(occupiedPositions.existPosition(new Position(4, 5))).isTrue();
    }

    @Test
    void 기물이_존재하지_않는_위치를_움직이려_하면_예외가_발생한다() {
        Board board = new Board();

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(1, 4), turn.getCurrentTurn()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동할_수_없는_위치로이동하려_하면_에외가_발생한다() {
        Board board = new Board();

        Chariot chariot = new Chariot(Color.RED);
        Position position = new Position(4, 4);

        board.putPiece(position, chariot);
        board.putPiece(new Position(5, 4), new Chariot(Color.RED));

        assertThatThrownBy(() -> board.move(position, new Position(5, 4), turn.getCurrentTurn()));
    }
}
