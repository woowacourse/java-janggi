package domain.game;

import domain.strategy.Direction;

public enum Side {
    CHO("초") {
        @Override
        public Direction soldierForward() {
            return Direction.N;
        }
    },
    HAN("한") {
        @Override
        public Direction soldierForward() {
            return Direction.S;
        }
    };

    private final String name;

    Side(String name) {
        this.name = name;
    }

    public abstract Direction soldierForward();

    public boolean isAlly(Side other) {
        return this.equals(other);
    }

    public String getName() {
        return name;
    }
}
