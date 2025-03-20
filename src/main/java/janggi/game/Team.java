package janggi.game;

public enum Team {
    HAN("한나라", "\u001B[31m"),
    CHO("초나라", "\u001B[32m");

    private final String text;
    private final String colorCode;

    Team(String text, String colorCode) {
        this.text = text;
        this.colorCode = colorCode;
    }

    public Team reverse() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public String getText() {
        return text;
    }

    public String getColorCode() {
        return colorCode;
    }
}
