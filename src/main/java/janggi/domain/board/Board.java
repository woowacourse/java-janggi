package janggi.domain.board;

import janggi.domain.PieceInfo;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.List;
import java.util.Map;

public class Board implements BoardInterface {
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    @Override
    public boolean isEmpty(Position position) {
        return board.get(position).isEqualPieceType(PieceType.NONE);
    }

    @Override
    public boolean isPo(Position position) {
        return board.get(position).isEqualPieceType(PieceType.PO);
    }

    @Override
    public boolean isEnemy(Side side, Position position) {
        return !board.get(position).isEqualSide(side);
    }

    @Override
    public boolean isAlly(Side side, Position position) {
        return board.get(position).isEqualSide(side);
    }

    @Override
    public PieceInfo[][] getCurrentBoard(){
        PieceInfo[][] currentBoard = new PieceInfo[10][9];
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            currentBoard[position.x() - 1][position.y() - 1] = piece.getPieceInfo();
        }
        return currentBoard;
    }

    public boolean move(Position start, Position end) {
        Piece piece = board.get(start);
        List<Position> route = piece.findRoute(start, end);

        piece.validateRoute(route, this);
        board.put(end, piece);
        board.put(start,new None());
        return true;
    }
}
