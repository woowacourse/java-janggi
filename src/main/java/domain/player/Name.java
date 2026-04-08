package domain.player;

public record Name(String value) {
    public boolean isSame(String otherValue) {
        return value.equals(otherValue);
    }
}
