package piece;

import direction.Point;

public class Cannon extends Piece {

    public Cannon(PieceType pieceType, Point point) {
        super(pieceType, point);
    }

    @Override
    public void validateDestination(Point from, Point to) {
        validateStraightDestination(from, to);
        validateNotSamePosition(from, to);
    }

    @Override
    public void checkPaths(Pieces allPieces, Point from, Point to) {
        if(calculateCannonPieceCountInPaths(allPieces, from, to) >= 1) {
            throw new IllegalArgumentException("[ERROR] 포가 존재하여 움직일 수 없습니다.");
        }
        if (calculateNotCannonPieceCountInPaths(allPieces, from, to) != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 제외한 하나의 기물을 넘어야 합니다.");
        }
    }

    private int calculateNotCannonPieceCountInPaths(Pieces pieces, Point from, Point to) {
        return (int) findStraightPaths(from, to).stream()
                .filter(pieces::isContainPiece)
                .map(pieces::getByPoint)
                .filter(piece -> piece.getPieceType().isNotCannon())
                .count();
    }

    private int calculateCannonPieceCountInPaths(Pieces pieces, Point from, Point to) {
        return (int) findStraightPaths(from, to).stream()
                .filter(pieces::isContainPiece)
                .map(pieces::getByPoint)
                .filter(piece -> piece.getPieceType().isCannon())
                .count();
    }
}
