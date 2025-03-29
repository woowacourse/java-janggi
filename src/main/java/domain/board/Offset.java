package domain.board;

import java.util.ArrayList;
import java.util.List;

public record Offset(
    int x,
    int y
) {

    private static final int MAX_X_RANGE = 8;
    private static final int MAX_Y_RANGE = 9;

    public Offset {
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

    public boolean isLinear() {
        return isHorizontal() || isVertical();
    }

    private boolean isHorizontal() {
        return y == 0 && x != 0;
    }

    private boolean isVertical() {
        return x == 0 && y != 0;
    }

    public List<Offset> calculateLinearOffsets() {
        if (!isLinear()) {
            throw new IllegalStateException("해당 오프셋은 직선이 아닙니다.");
        }

        final Offset unitStep = calculateUnitStep();
        final int stepCount = calculateStepCount();
        final List<Offset> linearOffsets = new ArrayList<>();

        for (int i = 0; i < stepCount; i++) {
            linearOffsets.add(unitStep);
        }

        return linearOffsets;
    }

    private Offset calculateUnitStep() {
        return new Offset(Integer.compare(this.x(), 0), Integer.compare(this.y(), 0));
    }

    private int calculateStepCount() {
        return Math.max(Math.abs(this.x()), Math.abs(this.y()));
    }
}
