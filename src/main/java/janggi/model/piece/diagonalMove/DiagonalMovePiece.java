package janggi.model.piece.diagonalMove;

import janggi.model.Team;
import janggi.model.piece.Piece;
import java.util.List;

public abstract class DiagonalMovePiece extends Piece {

    }

    @Override
    public boolean canPassThrough(
            List<Piece> piecesOnPath,
            Piece pieceAtTo
    ) {
        return piecesOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }
}
