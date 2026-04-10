package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.List;
import java.util.Map;

public abstract class Piece {
    private final PieceInfo pieceInfo;
    private final MoveStrategy moveStrategy;

    Piece(PieceInfo pieceInfo, MoveStrategy moveStrategy) {
        this.pieceInfo = pieceInfo;
        this.moveStrategy = moveStrategy;
    }

    public List<Path> findMovablePaths(Position current) {
        return moveStrategy.findMovablePaths(current);
    }

    public String displayName() {
        return pieceDisplayName(pieceInfo.getCamp());
    }

    public boolean isSameCamp(Piece piece) {
        return this.pieceInfo.isSameCamp(piece.pieceInfo);
    }

    public boolean isSameCamp(Camp camp) {
        return pieceInfo.isSameCamp(camp);
    }

    public Camp getCamp() {
        return pieceInfo.getCamp();
    }

    public int getScoreIfCampMatches(Camp camp) {
        return pieceInfo.getScoreIfCampMatches(camp);
    }

    abstract public boolean canPassRoute(Map<Position, Piece> piecesInPath);

    abstract public boolean canCatch(Piece piece);

    abstract public boolean canBeJumpedOver();

    abstract public boolean canBeCapturedByCannon();

    abstract protected String pieceDisplayName(Camp camp);
}
