package piece;

import direction.Point;

public class Cannon extends Piece {
    private static final String CANNON_EXPRESSION = "n";

    public Cannon(String name, Point point) {
        super(name, point);
    }

    @Override
    public void validateDestination(Point to) {
        validateStraightDestination(currentPosition, to);
        validateNotSamePosition(currentPosition, to);
    }

    public void checkPaths(Pieces allPieces, Point to) {
        if (calculateCannonPieceCountInPaths(allPieces, currentPosition, to) >= 1) {
            throw new IllegalArgumentException("[ERROR] 포가 존재하여 움직일 수 없습니다.");
        }
        if (calculateNotCannonPieceCountInPaths(allPieces, currentPosition, to) != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 제외한 하나의 기물을 넘어야 합니다.");
        }
    }

    private int calculateNotCannonPieceCountInPaths(Pieces pieces, Point from, Point to) {
        return (int) findStraightPaths(from, to).stream()
                .filter(pieces::isContainPiece)
                .map(pieces::getByPoint)
                .filter(piece -> !piece.isSameType(CANNON_EXPRESSION))
                .count();
    }

    private int calculateCannonPieceCountInPaths(Pieces pieces, Point from, Point to) {
        return (int) findStraightPaths(from, to).stream()
                .filter(pieces::isContainPiece)
                .map(pieces::getByPoint)
                .filter(piece -> piece.isSameType(CANNON_EXPRESSION))
                .count();
    }
}
