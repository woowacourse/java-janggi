package janggi.domain.position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PositionFile {
    FILE_1(1),
    FILE_2(2),
    FILE_3(3),
    FILE_4(4),
    FILE_5(5),
    FILE_6(6),
    FILE_7(7),
    FILE_8(8),
    FILE_9(9),
    ;

    public final int amount;

    PositionFile(final int amount) {
        this.amount = amount;
    }

    public PositionFile add(final int i) {
        return findByAmount(amount + i);
    }

    public static PositionFile findByAmount(final int i) {
        return Arrays.stream(PositionFile.values())
                .filter(file -> file.amount == i)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 파일을 찾을 수 없습니다."));
    }

    public boolean validateAdd(final int fileAmount) {
        return Arrays.stream(PositionFile.values())
                .anyMatch(p -> p.amount == this.amount + fileAmount);
    }
    /*
    public List<PositionRank> getBetweenRanks(final PositionRank rank) {
        if (this.ordinal() > rank.ordinal()) {
            return rank.getBetweenRanks(this).reversed();
        }

        List<PositionRank> betweenRanks = new ArrayList<>();
        for (int newAmount = amount + 1; newAmount < rank.amount; newAmount++) {
            betweenRanks.add(findByAmount(newAmount));
        }
        return betweenRanks;
    }
     */

    public List<PositionFile> getBetweenFiles(final PositionFile file) {
        if (this.ordinal() > file.ordinal()) {
            return file.getBetweenFiles(this).reversed();
        }

        List<PositionFile> betweenFiles = new ArrayList<>();
        for (int newValue = amount + 1; newValue < file.amount; newValue++) {
            betweenFiles.add(findByAmount(newValue));
        }
        return betweenFiles;
    }

    public boolean isBetween(final PositionFile minFile, final PositionFile maxFile) {
        return minFile.ordinal() <= ordinal() && ordinal() <= maxFile.ordinal();
    }
}
