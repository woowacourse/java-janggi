package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.side.TeamType;

public abstract class OneStepPiece extends Piece{

    public OneStepPiece(TeamType teamType, PieceType pieceType) {
        super(teamType, pieceType);
    }

    @Override
    public void validateCanMove(Position start, Position end, Board board) {

    }
}
