package janggi.coordinate;


import janggi.piece.Country;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record JanggiPosition(int x, int y) {

    public static final int POSITION_RANGE_X_MIN = 1;
    public static final int POSITION_RANGE_X_MAX = 10;
    public static final int POSITION_RANGE_Y_MIN = 1;
    public static final int POSITION_RANGE_Y_MAX = 9;
    public static final JanggiPosition PALACE_CENTER_HAN = new JanggiPosition(2, 5);
    public static final JanggiPosition PALACE_CENTER_CHO = new JanggiPosition(9, 5);

    private static final double MAX_DISTANCE_OF_SAME_PALACE = Math.sqrt(8);
    private static final JanggiPosition PALACE_TOP_LEFT_HAN = new JanggiPosition(1, 4);
    private static final JanggiPosition PALACE_BOTTOM_RIGHT_HAN = new JanggiPosition(3, 6);
    private static final JanggiPosition PALACE_TOP_LEFT_CHO = new JanggiPosition(8, 4);
    private static final JanggiPosition PALACE_BOTTOM_RIGHT_CHO = new JanggiPosition(10, 6);

    public JanggiPosition {
        validatePositionRange(x, y);
    }

    private void validatePositionRange(final int x, final int y) {
        if (x < POSITION_RANGE_X_MIN || POSITION_RANGE_X_MAX < x) {
            throw new IllegalArgumentException("좌표 범위가 벗어났습니다.");
        }
        if (y < POSITION_RANGE_Y_MIN || POSITION_RANGE_Y_MAX < y) {
            throw new IllegalArgumentException("좌표 범위가 벗어났습니다.");
        }
    }

    public boolean isSamePalace(final JanggiPosition janggiPosition){
        if(!(this.isInsidePalace() && janggiPosition.isInsidePalace())){
            return false;
        }
        return this.calculateDistance(janggiPosition) <= MAX_DISTANCE_OF_SAME_PALACE;
    }

    public double calculateDistance(final JanggiPosition descJanggiPosition) {
        return Math.sqrt(
                Math.pow(Math.abs(this.x - descJanggiPosition.x), 2) + Math.pow(Math.abs(this.y - descJanggiPosition.y), 2));
    }

    public boolean isSameLine(final JanggiPosition descJanggiPosition) {
        return x == descJanggiPosition.x || y == descJanggiPosition.y;
    }

    public boolean isXGreaterThan(final JanggiPosition descJanggiPosition) {
        return x >= descJanggiPosition.x;
    }

    public boolean isXLessThan(final JanggiPosition descJanggiPosition) {
        return x <= descJanggiPosition.x;
    }

    public JanggiPosition plusPosition(final int x, final int y) {
        return new JanggiPosition(this.x + x, this.y + y);
    }

    public List<JanggiPosition> calculateBetweenPositions(final JanggiPosition destination) {
        if (!isSameLine(destination)) {
            return Collections.emptyList();
        }

        final List<JanggiPosition> betweenJanggiPositions = makePositionsToDestination(destination);
        removeSourceAndDestination(betweenJanggiPositions);

        return betweenJanggiPositions;
    }

    private List<JanggiPosition> makePositionsToDestination(final JanggiPosition destination) {
        final List<JanggiPosition> betweenJanggiPositions = new ArrayList<>();
        final int minX = Math.min(x, destination.x);
        final int minY = Math.min(y, destination.y);
        final int maxX = Math.max(x, destination.x);
        final int maxY = Math.max(y, destination.y);

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                betweenJanggiPositions.add(new JanggiPosition(i, j));
            }
        }
        return betweenJanggiPositions;
    }

    private void removeSourceAndDestination(final List<JanggiPosition> betweenJanggiPositions) {
        betweenJanggiPositions.removeFirst();
        betweenJanggiPositions.removeLast();
    }

    public JanggiPosition calculatePalaceCenterPosition(){
        if(isInsidePalace(Country.HAN)) {
            return PALACE_CENTER_HAN;
        }
        return PALACE_CENTER_CHO;
    }

    public boolean isInsidePalace(final Country country) {
        if (country == Country.HAN) {
            return isYInsidePalace() && isXInsideHanPalace();
        }
        return isYInsidePalace() && isXInsideChoPalace();
    }

    public boolean isInsidePalace(){
        return isYInsidePalace() && (isXInsideHanPalace() || isXInsideChoPalace());
    }

    private boolean isYInsidePalace() {
        return this.isYGreaterThan(PALACE_TOP_LEFT_HAN) && this.isYLessThan(PALACE_BOTTOM_RIGHT_HAN);
    }

    private boolean isXInsideChoPalace() {
        return this.isXGreaterThan(PALACE_TOP_LEFT_CHO) && this.isXLessThan(PALACE_BOTTOM_RIGHT_CHO);
    }

    private boolean isXInsideHanPalace() {
        return this.isXGreaterThan(PALACE_TOP_LEFT_HAN) && this.isXLessThan(PALACE_BOTTOM_RIGHT_HAN);
    }

    private boolean isYGreaterThan(final JanggiPosition janggiPosition) {
        return y >= janggiPosition.y;
    }

    private boolean isYLessThan(final JanggiPosition janggiPosition) {
        return y <= janggiPosition.y;
    }

    public boolean isCenterInPalace(){
        return this.equals(PALACE_CENTER_HAN) || this.equals(PALACE_CENTER_CHO);
    }

    public boolean isCornerInPalace(final Country country) {
        if (country == Country.HAN) {
            return (y == PALACE_TOP_LEFT_CHO.y || y == PALACE_BOTTOM_RIGHT_CHO.y)
                    && (x == PALACE_TOP_LEFT_HAN.x || x == PALACE_BOTTOM_RIGHT_HAN.x);
        }
        return (y == PALACE_TOP_LEFT_CHO.y || y == PALACE_BOTTOM_RIGHT_CHO.y)
                && (x == PALACE_TOP_LEFT_CHO.x || x == PALACE_BOTTOM_RIGHT_CHO.x);
    }

    public boolean isCornerInPalace(){
        return isCornerInPalace(Country.HAN) || isCornerInPalace(Country.CHO);
    }

}
