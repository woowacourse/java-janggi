package domain.position;

import domain.MovingPattern;
import janggiexception.OutOfBoardException;
import java.util.Arrays;

public enum File {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    private final int file;

    File(int file) {
        this.file = file;
    }

    public static File findByNumber(int number) {
        return Arrays.stream(File.values())
                .filter(file -> file.file == number)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("올바르지 않은 열입니다."));
    }

    public File moveFile(MovingPattern pattern) {
        if (pattern.getY() == 1) {
            return moveRight();
        }
        if (pattern.getY() == -1) {
            return moveLeft();
        }
        return this;
    }

    public boolean canMoveFile(MovingPattern pattern) {
        return canMoveRight(pattern) && canMoveLeft(pattern);
    }

    private boolean canMoveRight(MovingPattern pattern) {
        return !(pattern.getY() == 1 && this == rightMost());
    }

    private boolean canMoveLeft(MovingPattern pattern) {
        return !(pattern.getY() == -1 && this == leftMost());
    }

    public boolean isBiggerThan(File file) {
        return this.file > file.file;
    }

    public int getGapBetween(File file) {
        return Math.abs(this.file - file.file);
    }

    private File leftMost() {
        return ONE;
    }

    private File rightMost() {
        return NINE;
    }

    private File moveRight() {
        if (this == NINE) {
            throw new OutOfBoardException();
        }
        return findByNumber(file + 1);
    }

    private File moveLeft() {
        if (this == ONE) {
            throw new OutOfBoardException();
        }
        return findByNumber(file - 1);
    }
}
