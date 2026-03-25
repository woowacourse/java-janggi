package domain.player;

import domain.board.Board;
import domain.board.Placement;
import domain.piece.Side;

public class Player {
    private final Side side;
    private final Board board;

    public Player(Side side, Board board) {
        this.side = side;
        this.board = board;
    }

    public Side getSide() {
        return side;
    }

    public void initBoard(int placementCode) {
        board.placePieces(side, Placement.from(placementCode));
    }
}
