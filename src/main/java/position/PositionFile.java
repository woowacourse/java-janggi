package position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PositionFile {
    FILE_1("가", 1),
    FILE_2("나", 2),
    FILE_3("다", 3),
    FILE_4("라", 4),
    FILE_5("마", 5),
    FILE_6("바", 6),
    FILE_7("사", 7),
    FILE_8("아", 8),
    FILE_9("자", 9);

    private final String displayName;
    private final int amount;

    PositionFile(final String displayName, final int amount) {
        this.displayName = displayName;
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

    public static PositionFile fromString(String fileStr) {
        return Arrays.stream(values())
                .filter(p -> p.displayName.equals(fileStr))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파일입니다: " + fileStr));
    }

    @Override
    public String toString() {
        return displayName;
    }
}
