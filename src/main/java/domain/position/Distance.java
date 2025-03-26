package domain.position;

public record Distance(int x, int y) {

    public double calculateDistance() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
