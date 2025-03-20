package domain;

import domain.pattern.Pattern;
import java.util.List;

public record JanggiPosition(int rank, int file) {

    private static final int MAX_RANK_BOUND = 9;
    private static final int MIN_RANK_BOUND = 0;
    private static final int MAX_FILE_BOUND = 9;
    private static final int MIN_FILE_BOUND = 1;
    public static final int RANK_THRESHOLD = 10;

    public JanggiPosition move(final List<Pattern> patterns) {
        JanggiPosition newPosition = this;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
        }
        return newPosition;
    }

    public JanggiPosition moveOnePosition(final Pattern pattern) {
        int newRank = getTransformedRank(pattern, rank);
        int newFile = file + pattern.getY();

        return new JanggiPosition(newRank, newFile);
    }

    private int getTransformedRank(final Pattern pattern, int rank) {
        int newRank = rank + pattern.getX();
        if ((pattern.equals(Pattern.MOVE_UP) && rank == MIN_RANK_BOUND) || (pattern.equals(Pattern.MOVE_DIAGONAL_UP_RIGHT)
                && rank == MIN_RANK_BOUND) || (
                pattern.equals(Pattern.MOVE_DIAGONAL_UP_LEFT) && rank == MIN_RANK_BOUND)) {
            newRank = 9;
        }
        if ((pattern.equals(Pattern.MOVE_DOWN) && rank == MAX_RANK_BOUND) || (pattern.equals(Pattern.MOVE_DIAGONAL_DOWN_LEFT)
                && rank == MAX_RANK_BOUND) || (
                pattern.equals(Pattern.MOVE_DIAGONAL_DOWN_RIGHT) && rank == MAX_RANK_BOUND)) {
            newRank = 0;
        }
        return newRank;
    }

    public boolean isBiggerRankThan(final JanggiPosition beforePosition) {
        if (this.rank == 0) {
            return true;
        }
        if (beforePosition.rank == 0) {
            return false;
        }
        return this.rank > beforePosition.rank;
    }

    public boolean isBiggerFileThan(final JanggiPosition beforePosition) {
        return this.file > beforePosition.file;
    }

    public int getRankGap(final JanggiPosition beforePosition) {
        if (this.rank == 0) {
            return RANK_THRESHOLD - beforePosition.rank();
        }

        if (beforePosition.rank == 0) {
            return RANK_THRESHOLD - this.rank;
        }

        return Math.abs(this.rank - beforePosition.rank);
    }

    public int getFileGap(final JanggiPosition beforePosition) {
        return Math.abs(this.file - beforePosition.file);
    }

    public void validateBound() {
        if (rank < MIN_RANK_BOUND || rank > MAX_RANK_BOUND || file < MIN_FILE_BOUND || file > MAX_FILE_BOUND) {
            throw new IllegalArgumentException("보드판을 넘어서 이동할 수 없습니다.");
        }
    }
}
