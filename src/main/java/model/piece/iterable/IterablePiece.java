package model.piece.iterable;

import model.Position;
import model.Team;
import model.board.Board;
import model.piece.Piece;
import model.piece.PieceType;

public abstract class IterablePiece extends Piece {

    protected IterablePiece(int x, int y, Team team) {
        super(x, y, team);
    }

    protected abstract Route findMovableRoute(Board board, int dx, int dy);

    protected abstract void validateRoute(Board board, Route route, Position target);

    public abstract PieceType type();
}
