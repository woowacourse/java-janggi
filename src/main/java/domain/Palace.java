package domain;

import java.util.List;

public class Palace {

    private static final Position palaceRedCenter = new Position(1, 4);
    private static final Position palaceGreenCenter = new Position(8, 4);

    public List<Position> reachablePositionsInPalace(Position currentPosition) {
        if (isPalaceGreenArea(currentPosition)) {
            return diagonalPositionsInPalace(palaceGreenCenter, currentPosition);
        }
        if (isPalaceRedArea(currentPosition)) {
            return diagonalPositionsInPalace(palaceRedCenter, currentPosition);
        }

        return List.of();
    }

    private List<Position> diagonalPositionsInPalace(Position palaceCenter, Position currentPosition) {
        if (currentPosition.equals(palaceCenter)) {
            return List.of(currentPosition.upCrossRight(), currentPosition.upCrossLeft(),
                    currentPosition.downCrossLeft(), currentPosition.downCrossRight());
        }
        if (Math.abs(palaceCenter.row() - currentPosition.row()) == 1 && Math.abs(palaceCenter.col() - currentPosition.col()) == 1) {
            return List.of(palaceCenter);
        }
        return List.of();
    }

    public boolean isPalaceRedArea(Position currentPosition) {
        return isPalaceArea(currentPosition, palaceRedCenter);
    }

    public boolean isPalaceGreenArea(Position currentPosition) {
        return isPalaceArea(currentPosition, palaceGreenCenter);
    }

    private boolean isPalaceArea(Position currentPosition, Position palaceCenter) {
        return palacePositions(palaceCenter).contains(currentPosition);
    }

    private List<Position> palacePositions(Position palaceCenter) {
        return List.of(
                palaceCenter.upCrossLeft(),
                palaceCenter.up(),
                palaceCenter.upCrossRight(),
                palaceCenter.left(),
                palaceCenter,
                palaceCenter.right(),
                palaceCenter.downCrossLeft(),
                palaceCenter.down(),
                palaceCenter.downCrossRight()
        );
    }
}
