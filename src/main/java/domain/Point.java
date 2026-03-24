package domain;

public class Point {
    private final int y;
    private final int x;

    public Point(int y, int x) {
        validate(y, x);
        this.y = y;
        this.x = x;
    }

    private void validate(int y, int x){
        if(0 <= y && y <= 9 && 0 <= x && x <= 8){
            return;
        }
        throw new IllegalArgumentException();
    }
}
