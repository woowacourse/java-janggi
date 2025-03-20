package domain.pattern;

import static domain.pattern.Direction.DOWN;
import static domain.pattern.Direction.LEFT;
import static domain.pattern.Direction.RIGHT;

import java.util.List;
import java.util.Map;

public class 병Path extends Path {

    public 병Path() {
        super(List.of(RIGHT, DOWN, LEFT), Map.of(
                RIGHT, List.of(Pattern.RIGHT),
                DOWN, List.of(Pattern.DOWN),
                LEFT, List.of(Pattern.LEFT)
        ));
    }
}
