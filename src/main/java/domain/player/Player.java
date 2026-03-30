package domain.player;

import domain.board.Board;
import domain.board.Placement;
import domain.piece.Side;
import domain.position.Move;

public class Player {
    private final Side side;

    public Player(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public void initBoard(Board board, int placementCode) {
        board.placePieces(side, Placement.from(placementCode));
    }

    public void play(Board board, Move move) {
        board.move(move.startPosition(), move.endPosition(), side);
    }
}
