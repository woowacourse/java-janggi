package model.janggiboard;

import model.Point;

public class JangSaGoongsungRule {
    private final Point myGoongsungCenterPoint;

    public JangSaGoongsungRule(Point myGoongsungCenterPoint) {
        this.myGoongsungCenterPoint = myGoongsungCenterPoint;
    }

    public void validateOutOfGoongsung(Point targetPoint) {
        if (Math.abs(targetPoint.x() - myGoongsungCenterPoint.x()) > 1
                || Math.abs(targetPoint.y() - myGoongsungCenterPoint.y()) > 1) {
            throw new IllegalArgumentException("[ERROR] 궁성을 벗어날 수 없습니다.");
        }
    }

    public boolean containsCenterPoint(Point beforePoint, Point targetPoint) {
        return beforePoint.equals(myGoongsungCenterPoint) || targetPoint.equals(myGoongsungCenterPoint);
    }

}
