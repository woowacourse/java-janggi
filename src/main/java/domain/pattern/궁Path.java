package domain.pattern;

import static domain.pattern.Direction.DOWN;
import static domain.pattern.Direction.LEFT;
import static domain.pattern.Direction.RIGHT;
import static domain.pattern.Direction.UP;

import java.util.List;
import java.util.Map;

public class 궁Path extends Path {

    public 궁Path() {
        super(List.of(RIGHT, DOWN, LEFT, UP), Map.of(
                RIGHT, List.of(Pattern.RIGHT),
                DOWN, List.of(Pattern.DOWN),
                LEFT, List.of(Pattern.LEFT),
                UP, List.of(Pattern.UP)
        ));
    }
}
