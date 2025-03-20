package domain.pattern;

import static domain.pattern.Direction.LEFT;
import static domain.pattern.Direction.RIGHT;
import static domain.pattern.Direction.UP;

import java.util.List;
import java.util.Map;

public class 졸Path extends Path {

    public 졸Path() {
        super(List.of(RIGHT, LEFT, UP), Map.of(
                RIGHT, List.of(Pattern.RIGHT),
                LEFT, List.of(Pattern.LEFT),
                UP, List.of(Pattern.UP)
        ));
    }
}
