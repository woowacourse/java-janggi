package domain.position;

public record Distance(int x, int y) {

    public boolean isNotHorizontal() {
        return x == 0;
    }

    public boolean isNotVertical() {
        return y == 0;
    }

    public boolean isRight() {
        return x > 0;
    }

    public boolean isLeft() {
        return x < 0;
    }

    public boolean isUp() {
        return y > 0;
    }

    public boolean isDown() {
        return y < 0;
    }

    public double calculateDistance() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
