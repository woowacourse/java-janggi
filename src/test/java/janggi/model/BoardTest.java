package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Board;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.Position;
import janggi.model.piece.Cannon;
import janggi.model.piece.Chariot;
import janggi.model.piece.Elephant;
import janggi.model.piece.Guard;
import janggi.model.piece.Horse;
import janggi.model.piece.King;
import janggi.model.piece.Soldier;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 보드에_특정_위치에_있는_기물을_움직일_수_있다() {
        Board board = new Board();

        Chariot chariot = new Chariot(Color.RED);
        Position position = new Position(4, 4);

        board.putPiece(position, chariot);
        board.move(position, new Position(4, 5));

        OccupiedPositions occupiedPositions = board.generateOccupiedPositions();

        assertThat(occupiedPositions.existPosition(new Position(4, 5))).isTrue();
    }

    @Test
    void 홍팀의_점수를_계산한다() {
        Board board = new Board();

        board.putPiece(new Position(4, 4), new Horse(Color.RED));
        board.putPiece(new Position(5, 4), new Cannon(Color.RED));
        board.putPiece(new Position(6, 4), new Chariot(Color.RED));
        board.putPiece(new Position(7, 4), new Soldier(Color.RED));
        board.putPiece(new Position(8, 4), new King(Color.RED));
        board.putPiece(new Position(9, 4), new Guard(Color.RED));
        board.putPiece(new Position(10, 4), new Elephant(Color.RED));

        assertThat(board.calculateScore(Color.RED)).isEqualTo(34.5);
    }

    @Test
    void 청팀의_점수를_계산한다() {
        Board board = new Board();

        board.putPiece(new Position(4, 4), new Horse(Color.BLUE));
        board.putPiece(new Position(5, 4), new Cannon(Color.BLUE));
        board.putPiece(new Position(6, 4), new Chariot(Color.BLUE));
        board.putPiece(new Position(7, 4), new Soldier(Color.BLUE));
        board.putPiece(new Position(8, 4), new King(Color.BLUE));
        board.putPiece(new Position(9, 4), new Guard(Color.BLUE));
        board.putPiece(new Position(10, 4), new Elephant(Color.BLUE));

        assertThat(board.calculateScore(Color.BLUE)).isEqualTo(33);
    }
}
