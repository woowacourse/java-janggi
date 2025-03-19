package domain.position;

import java.util.Arrays;

public enum PositionFile {

    가(1),
    나(2),
    다(3),
    라(4),
    마(5),
    바(6),
    사(7),
    아(8),
    자(9),
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

}
