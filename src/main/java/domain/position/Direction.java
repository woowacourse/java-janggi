package domain.position;

public record Direction(int x, int y) {

    public boolean isNotHorizontal() {
        return x == 0;
    }

    public boolean isNotVertical() {
        return y == 0;
    }

    public boolean isUp() {
        return y > 0;
    }

    public boolean isDown() {
        return y < 0;
    }

    public boolean isRight() {
        return x > 0;
    }

    public boolean isLeft() {
        return x < 0;
    }

    public boolean isFirstQuadrant() {
        return isRight() && isUp();
    }

    public boolean isSecondQuadrant() {
        return isLeft() && isUp();
    }

    public boolean isThirdQuadrant() {
        return isLeft() && isDown();
    }

    public boolean isFourthQuadrant() {
        return isRight() && isDown();
    }

    public int horizontalDistance() {
        return Math.abs(x);
    }

    public int verticalDistance() {
        return Math.abs(y);
    }

    public double calculateDistance() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
