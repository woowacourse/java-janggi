package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;

public class Soldier extends StaticPositionedPiece {

    private static final int FAR_FROM_BASE_ROW = 3;
    private static final List<Integer> INITAL_FILES = List.of(1, 3, 5, 7, 9);

    public Soldier(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> initAt() {
        int baseRow = side.getBaseRow();
        int initialRow = side.getForwardedRow(baseRow, FAR_FROM_BASE_ROW);

        return INITAL_FILES.stream()
                .map(file -> new Intersection(initialRow, file))
                .toList();
    }

    public boolean canMove(
            Intersection from,
            Intersection to,
            AlivePieces alivePieces
    ) {
        return movableIntersections(from, alivePieces)
                .contains(to);
    }

    public List<Intersection> movableIntersections(
            Intersection from,
            AlivePieces alivePieces
    ) {
        List<Intersection> movableIntersections = new ArrayList<>();

        int forwardRow = side.getForwardedRow(from.row());
        Intersection forwardIntersection = new Intersection(forwardRow, from.file());
        addIfMovable(forwardIntersection, alivePieces, movableIntersections);

        int leftFile = side.getLeftFile(from.file());
        Intersection leftIntersection = new Intersection(from.row(), leftFile);
        addIfMovable(leftIntersection, alivePieces, movableIntersections);

        int rightFile = side.getRightFile(from.file());
        Intersection rightIntersection = new Intersection(from.row(), rightFile);
        addIfMovable(rightIntersection, alivePieces, movableIntersections);

        return List.copyOf(movableIntersections);
    }

    private void addIfMovable(
            Intersection destination,
            AlivePieces alivePieces,
            List<Intersection> movableIntersections
    ) {
        Piece rightPiece = alivePieces.placedAt(destination);

        if (alivePieces.isEmpty(destination) || rightPiece.hasDifferentSide(side)) {
            movableIntersections.add(destination);
        }
    }
}
