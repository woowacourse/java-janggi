package domain.board;

import java.util.ArrayList;
import java.util.List;

public record Offset(
    int x,
    int y
) {

    public static final Offset UP = new Offset(0, 1);
    public static final Offset DOWN = new Offset(0, -1);
    public static final Offset LEFT = new Offset(-1, 0);
    public static final Offset RIGHT = new Offset(1, 0);
    public static final Offset LEFT_UP = new Offset(-1, 1);
    public static final Offset RIGHT_UP = new Offset(1, 1);
    public static final Offset LEFT_DOWN = new Offset(-1, -1);
    public static final Offset RIGHT_DOWN = new Offset(1, -1);

    private static final int MAX_X_RANGE = 8;
    private static final int MAX_Y_RANGE = 9;

    public Offset {  // TODO. 캐싱 적용
        validateRange(x, y);
    }

    private void validateRange(
        final int x,
        final int y
    ) {
        if (Math.abs(x) > MAX_X_RANGE || Math.abs(y) > MAX_Y_RANGE) {
            throw new IllegalArgumentException("오프셋의 범위를 벗어났습니다.");
        }
    }

    public List<Offset> calculateUnitSteps() {
        if (!isLinear() && !isDiagonal()) {
            throw new IllegalStateException("해당 오프셋은 직선또는 대각선이 아닙니다.");
        }

        final Offset unitStep = calculateUnitStep();
        final int stepCount = calculateStepCount();
        final List<Offset> unitSteps = new ArrayList<>();

        for (int i = 0; i < stepCount; i++) {
            unitSteps.add(unitStep);
        }

        return unitSteps;
    }

    public boolean isLinear() {
        return isHorizontal() || isVertical();
    }

    private boolean isHorizontal() {
        return y == 0 && x != 0;
    }

    private boolean isVertical() {
        return x == 0 && y != 0;
    }

    public boolean isDiagonal() {
        if (x == 0 && y == 0) {
            return false;
        }

        return Math.abs(x) == Math.abs(y);
    }

    private Offset calculateUnitStep() {
        return new Offset(Integer.compare(this.x(), 0), Integer.compare(this.y(), 0));
    }

    public boolean isSingleStep() {
        return calculateStepCount() == 1;
    }

    private int calculateStepCount() {
        return Math.max(Math.abs(this.x()), Math.abs(this.y()));
    }
}
