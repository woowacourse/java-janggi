package domain.position;

import domain.MovingPattern;
import java.util.List;

public record JanggiPosition(Rank rank, File file) {

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
