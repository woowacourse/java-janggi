package model.pieces.rule;

import java.util.ArrayList;
import java.util.List;

public class MovePattern {
    private List<Step> steps;

    public MovePattern(List<Step> step) {
        this.steps = step;
    }

    public List<Step> steps() {
        return steps;
    }
}
