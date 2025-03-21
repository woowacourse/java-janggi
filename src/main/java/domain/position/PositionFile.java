package domain.position;

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

    private final int amount;

    PositionFile(final int amount) {
        this.amount = amount;
    }

    public PositionFile add(final int i) {
        return findByAmount(amount + i);
    }

    private PositionFile findByAmount(final int i) {
        return Arrays.stream(PositionFile.values())
                .filter(file -> file.amount == i)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 파일을 찾을 수 없습니다."));
    }

    public boolean validateAdd(final int fileAmount) {
        return Arrays.stream(PositionFile.values())
                .anyMatch(p -> p.amount == this.amount + fileAmount);
    }

    public List<PositionFile> getBetweenFiles(final PositionFile file) {
        List<PositionFile> betweenFiles = new ArrayList<>();
        for (int newValue = Math.min(amount, file.amount) + 1; newValue < Math.max(amount, file.amount); newValue++) {
            betweenFiles.add(findByAmount(newValue));
        }
        return betweenFiles;
    }

    public int distance(final PositionFile file) {
        return Math.abs(amount - file.amount);
    }
}
