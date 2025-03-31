package domain;

public enum Team {
    BLUE,
    RED;

    public static Team fromString(String color) {
        return switch (color.toUpperCase()) {
            case "BLUE" -> BLUE;
            case "RED" -> RED;
            default -> throw new IllegalArgumentException("유효하지 않은 팀 색상: " + color);
        };
    }
}
