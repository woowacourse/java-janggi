package game;

import java.util.HashMap;
import java.util.Map;
import piece.Piece;
import position.Position;

public class Board {

    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = board;
    }

    public Board(BoardSetting boardSetting) {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.putAll(boardSetting.setting());
        this.board = pieces;
    }

    public void movePiece(Position fromPosition, Position toPosition) {
        Piece piece = board.get(fromPosition);
        piece.canMove(fromPosition, toPosition, this);

    }


}
