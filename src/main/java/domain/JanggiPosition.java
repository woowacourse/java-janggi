package domain;

import domain.pattern.Pattern;
import domain.piece.Side;
import java.util.List;

public record JanggiPosition(int rank, int file) {

    private static final int MAX_RANK_BOUND = 9;
    private static final int MIN_RANK_BOUND = 1;
    private static final int MAX_FILE_BOUND = 9;
    private static final int MIN_FILE_BOUND = 0;

    public JanggiPosition move(List<Pattern> patterns) {
        JanggiPosition newPosition = this;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
        }
        return newPosition;
    }

    public JanggiPosition moveOnePosition(Pattern pattern) {
        int newRank = rank + pattern.getRank();
        int newFile = transformedNewFile(pattern, file);

        return new JanggiPosition(newRank, newFile);
    }

    private int transformedNewFile(Pattern pattern, int file) {
        int newFile = file + pattern.getFile();
        if ((pattern.equals(Pattern.UP) && file == MIN_FILE_BOUND) || (pattern.equals(Pattern.DIAGONAL_UP_RIGHT)
                && file == MIN_FILE_BOUND) || (
                pattern.equals(Pattern.DIAGONAL_UP_LEFT) && file == MIN_FILE_BOUND)) {
            newFile = 9;
        }
        if ((pattern.equals(Pattern.DOWN) && file == MAX_FILE_BOUND) || (pattern.equals(Pattern.DIAGONAL_DOWN_LEFT)
                && file == MAX_FILE_BOUND) || (
                pattern.equals(Pattern.DIAGONAL_DOWN_RIGHT) && file == MAX_FILE_BOUND)) {
            newFile = 0;
        }
        return newFile;
    }

    public boolean isBiggerRankThan(JanggiPosition beforePosition) {
        return this.rank > beforePosition.rank;

    }

    public boolean isBiggerFileThan(JanggiPosition beforePosition) {
        if (this.file == 0) {
            return true;
        }
        if (beforePosition.file == 0) {
            return false;
        }
        return this.file > beforePosition.file;
    }

    public int getRankGap(JanggiPosition beforePosition) {
        return Math.abs(this.rank - beforePosition.rank);
    }

    public int getFileGap(JanggiPosition beforePosition) {
        if (this.file == 0) {
            return 10 - beforePosition.file();
        }

        if (beforePosition.file == 0) {
            return 10 - this.file;
        }

        return Math.abs(this.file - beforePosition.file);
    }

    public void validateBound() {
        if (rank < MIN_RANK_BOUND || rank > MAX_RANK_BOUND || file < MIN_FILE_BOUND || file > MAX_FILE_BOUND) {
            throw new IllegalArgumentException("보드판을 넘어서 이동할 수 없습니다.");
        }
    }

    public boolean isSameRankWith(JanggiPosition beforePosition) {
        return this.rank == beforePosition.rank;
    }

    public boolean isSameFileWith(JanggiPosition beforePosition) {
        return this.file == beforePosition.file;
    }

    public boolean isDiagonalTo(JanggiPosition afterPosition) {
        return this.rank != afterPosition.rank && this.file != afterPosition.file;
    }

    public boolean isPassThroughCenter(Side side, JanggiPosition afterPosition) {
        int centerPalaceRank = 5;
        int centerPalaceFile = (side == Side.CHO) ? 9 : 2;

        JanggiPosition centerPosition = new JanggiPosition(centerPalaceRank, centerPalaceFile);

        return this.equals(centerPosition) || afterPosition.equals(centerPosition);
    }
}
