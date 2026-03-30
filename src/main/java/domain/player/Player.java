package domain.player;

import domain.board.Board;
import domain.board.Placement;
import domain.piece.Piece;
import domain.piece.Side;
import domain.position.Position;

import java.util.Map;

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

    public Map<Position, Piece> findBoardState() {
        return board.findState();
    }

    public void move(Position from, Position to) {
        board.move(from, to, side);
    }
}
