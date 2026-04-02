package domain.piece;

public enum Side {

    CHO("초", "\\u001B[34m") {
        @Override
        public Side next() {
            return HAN;
        }
    },
    HAN("한", "\\u001B[31m") {
        @Override
        public Side next() {
            return CHO;
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

    public abstract Side next();
}
