package domain.position;

import java.util.Objects;

public abstract class Axis<T extends Axis<T>> {

    protected final int value;

    protected Axis(int value) {
        validateBound(value);
        this.value = value;
    }

    protected abstract int getMin();

    protected abstract int getMax();

    protected abstract T create(int value);

    public void validateBound(int value) {
        if (getMin() > value || value > getMax()) {
            throw new IllegalArgumentException("좌표가 장기판의 범위를 벗어났습니다.");
        }
    }

    public boolean canMove(int delta) {
        int moveValue = value + delta;
        return moveValue <= getMax() && moveValue >= getMin();
    }

    public T move(int delta) {
        return create(value + delta);
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Axis axis = (Axis) o;
        return value == axis.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
