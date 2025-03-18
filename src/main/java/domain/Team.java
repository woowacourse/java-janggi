package domain;

import java.util.function.Function;

public enum Team {
    HAN(i -> i * -1),
    CHO(i -> i),
    ;

    private Function<Integer, Integer> direction;

    Team(Function<Integer, Integer> direction) {
        this.direction = direction;
    }

    int applyDirection(int distance) {
        return direction.apply(distance);
    }
}