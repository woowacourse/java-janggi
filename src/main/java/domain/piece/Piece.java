package domain.piece;

import domain.path.PathInfo;
import domain.board.Position;
import domain.piece.strategy.MoveStrategy;

import java.util.List;

public record Piece(Camp camp, PieceType pieceType, MoveStrategy moveStrategy) {
    public static Piece of(Camp camp, PieceType pieceType) {
        return new Piece(camp, pieceType, pieceType.moveStrategy());
    }

    public boolean isSameCampe(Piece otherPiece) {
        return otherPiece.camp.equals(camp);
    }

    public List<Position> getPath(Position departure, Position destination) {
        return moveStrategy.getPath(departure, destination);
    }

    public void validateBlockingPiece(List<PathInfo> pathInfos, Position departure, Position destination) {
        moveStrategy.validateBlockingPiece(pathInfos, departure, destination);
    }
}
