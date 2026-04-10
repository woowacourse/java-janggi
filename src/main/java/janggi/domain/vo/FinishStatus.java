package janggi.domain.vo;

import java.util.Objects;

public class FinishStatus {
    private final boolean isFinished;

    public FinishStatus(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFinished);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        FinishStatus finishStatus = (FinishStatus) obj;
        return isFinished == finishStatus.isFinished;
    }
}
