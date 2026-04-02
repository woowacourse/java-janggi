package domain.moveStrategy;

import static domain.common.Constant.MAX_COLUMN;
import static domain.common.Constant.MAX_ROW;
import static domain.common.Constant.MIN_COLUMN;
import static domain.common.Constant.MIN_ROW;

import domain.board.Board;
import domain.place.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class StubBoard {
    private final Map<Position, Piece> board;

    public StubBoard(){
        this.board = new HashMap<>();
    }

    public StubBoard put(Position position,Piece piece){
        board.put(position,piece);

        return this;
    }

    public Board create(){ return new Board(board); }
}
