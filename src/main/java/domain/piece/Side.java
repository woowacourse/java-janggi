package domain.piece;

public enum Side {

    HAN("한", "\\u001B[31m") {
        @Override
        public Side opposite() {
            return CHO;
        }
    },
    CHO("초", "\\u001B[34m") {
        @Override
        public Side opposite() {
            return HAN;
        }
    };

    private final String name;
    private final String color;

    Side(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public boolean isHan() {
        return this == HAN;
    }

    public boolean isCho() {
        return this == CHO;
    }

    public abstract Side opposite();
}
