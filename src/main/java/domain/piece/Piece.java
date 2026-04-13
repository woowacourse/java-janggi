package domain.piece;

import domain.path.PathInfo;
import domain.board.Position;
import domain.path.PathInfos;
import domain.piece.strategy.MoveStrategy;

import java.util.List;
import java.util.Objects;

public class Piece {
    private final Camp camp;
    private final PieceType pieceType;
    private final MoveStrategy moveStrategy;

    public Piece(Camp camp, PieceType pieceType, MoveStrategy moveStrategy) {
        this.camp = camp;
        this.pieceType = pieceType;
        this.moveStrategy = moveStrategy;
    }

    public static Piece of(Camp camp, PieceType pieceType) {
        return new Piece(camp, pieceType, pieceType.moveStrategy(camp));
    }

    public boolean isSameCamp(Piece otherPiece) {
        return otherPiece.camp.equals(camp);
    }

    public boolean isSameType(PieceType otherPieceType) {
        return pieceType.equals(otherPieceType);
    }

    public Camp camp() {
        return camp;
    }

    public PieceType pieceType() {
        return pieceType;
    }

    public List<Position> getPath(Position departure, Position destination) {
        return moveStrategy.getPath(departure, destination);
    }

    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        moveStrategy.validateBlockingPiece(new PathInfos(pathInfos), destination);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) return false;
        return camp == piece.camp && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(camp, pieceType);
    }
}
