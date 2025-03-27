package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.piece.Cannon;
import janggi.model.piece.Chariot;
import janggi.model.piece.Elephant;
import janggi.model.piece.Guard;
import janggi.model.piece.Horse;
import janggi.model.piece.King;
import janggi.model.piece.Soldier;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    void 장기_게임에서_움직이는_위치에_잡을_수있는_기물이_있는지_확인한다() {
        Board board = new Board();
        board.putPiece(new Position(1, 1), new Cannon(Color.BLUE));
        JanggiGame janggiGame = new JanggiGame(board, new Turn(Color.BLUE));

        boolean isCatchable = janggiGame.existCatchablePiece(Color.RED, new Position(1, 1));

        assertThat(isCatchable).isTrue();
    }

    @Test
    void 특정_위치에_있는_기물을_움직일_수_있다() {
        Board board = new Board();
        Chariot chariot = new Chariot(Color.BLUE);
        Position position = new Position(4, 4);
        board.putPiece(position, chariot);
        board.putPiece(new Position(5, 4), new Chariot(Color.BLUE));
        JanggiGame janggiGame = new JanggiGame(board, new Turn(Color.BLUE));

        janggiGame.move(position, new Position(4, 5));

        OccupiedPositions occupiedPositions = board.generateOccupiedPositions();

        assertThat(occupiedPositions.existPosition(position)).isFalse();
        assertThat(occupiedPositions.existPosition(new Position(4, 5))).isTrue();
    }

    @Test
    void 기물이_존재하지_않는_위치를_움직이려_하면_예외가_발생한다() {
        Board board = new Board();
        JanggiGame janggiGame = new JanggiGame(board, new Turn(Color.BLUE));

        assertThatThrownBy(() -> janggiGame.move(
                new Position(1, 1),
                new Position(1, 4)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동할_수_없는_위치로이동하려_하면_에외가_발생한다() {
        Board board = new Board();
        JanggiGame janggiGame = new JanggiGame(board, new Turn(Color.BLUE));

        Chariot chariot = new Chariot(Color.BLUE);
        Position position = new Position(4, 4);

        board.putPiece(position, chariot);
        board.putPiece(new Position(5, 4), new Chariot(Color.BLUE));

        assertThatThrownBy(() -> janggiGame.move(
                position,
                new Position(5, 4)
        ));
    }
}
