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

    private final int amount;

    PositionFile(final int amount) {
        this.amount = amount;
    }

    public static List<PositionFile> getAllFiles() {
        return Arrays.stream(values()).toList();
    }

    public PositionFile add(final int fileAmount) {
        return findByAmount(amount + fileAmount);
    }

    public static PositionFile findByAmount(final int fileAmount) {
        return Arrays.stream(PositionFile.values())
                .filter(file -> file.amount == fileAmount)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 파일을 찾을 수 없습니다."));
    }

    public boolean isValidToAdd(final int fileAmount) {
        return Arrays.stream(PositionFile.values())
                .anyMatch(p -> p.amount == this.amount + fileAmount);
    }

    /**
     * 두 파일 사이의 파일들을 반환하는 기능입니다.
     * 첫 파일와 끝 파일를 포함하지 않습니다.
     *
     * @param file 끝 파일
     * @return 두 파일 사이의 파일들
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

    public int amount() {
        return amount;
    }

    public static int maxFileAmount() {
        return FILE_9.amount;
    }
}
