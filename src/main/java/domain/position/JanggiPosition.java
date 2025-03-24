package domain.position;

import domain.MovingPattern;
import java.util.List;

public record JanggiPosition(Rank rank, File file) {

    private static final List<JanggiPosition> DIAGONAL_MOVABLE_PALACE_POSITION = List.of(
            new JanggiPosition(1, 4),
            new JanggiPosition(1, 6),
            new JanggiPosition(2, 5),
            new JanggiPosition(3, 4),
            new JanggiPosition(3, 6),
            new JanggiPosition(8, 4),
            new JanggiPosition(8, 6),
            new JanggiPosition(9, 5),
            new JanggiPosition(0, 4),
            new JanggiPosition(0, 6)
    );
    private static final List<JanggiPosition> ORTHOGONAL_MOVABLE_PALACE_POSITION = List.of(
            new JanggiPosition(1, 5),
            new JanggiPosition(2, 4),
            new JanggiPosition(2, 6),
            new JanggiPosition(3, 5),
            new JanggiPosition(8, 5),
            new JanggiPosition(9, 4),
            new JanggiPosition(9, 6),
            new JanggiPosition(0, 5)
    );

    public JanggiPosition(int rank, int file) {
        this(Rank.findByNumber(rank), File.findByNumber(file));
    }

    public JanggiPosition move(final List<MovingPattern> patterns) {
        JanggiPosition newPosition = this;
        for (MovingPattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
        }
        return newPosition;
    }

    public boolean canMove(final List<MovingPattern> patterns) {
        JanggiPosition newPosition = this;
        for (MovingPattern pattern : patterns) {
            if (!newPosition.canMoveOnePosition(pattern)) {
                return false;
            }
           newPosition = newPosition.moveOnePosition(pattern);
        }
        return true;
    }

    public boolean isPalace() {
        return DIAGONAL_MOVABLE_PALACE_POSITION.contains(this)
                || ORTHOGONAL_MOVABLE_PALACE_POSITION.contains(this);
    }

    public boolean isDiagonalMovablePalace() {
        return DIAGONAL_MOVABLE_PALACE_POSITION.contains(this);
    }

    public JanggiPosition moveOnePosition(final MovingPattern pattern) {
        Rank newRank = rank.moveRank(pattern);
        File newFile = file.moveFile(pattern);

        return new JanggiPosition(newRank, newFile);
    }

    public boolean canMoveOnePosition(final MovingPattern pattern) {
        return rank.canMoveRank(pattern) && file.canMoveFile(pattern);
    }

    public boolean isBiggerRankThan(final JanggiPosition beforePosition) {
        return this.rank.isBiggerThan(beforePosition.rank);
    }

    public boolean isBiggerFileThan(final JanggiPosition beforePosition) {
        return this.file.isBiggerThan(beforePosition.file);
    }

    public int getRankGap(final JanggiPosition beforePosition) {
        return rank.getGapBetween(beforePosition.rank);
    }

    public int getFileGap(final JanggiPosition beforePosition) {
        return file.getGapBetween(beforePosition.file);
    }
}
