package domain.board;

import domain.game.Side;
import java.util.List;

public final class Palace {

    private static final int MIN_FILE = 4;
    private static final int CENTER_FILE = 5;
    private static final int MAX_FILE = 6;

    private static final int HAN_MIN_ROW = 1;
    private static final int HAN_CENTER_ROW = 2;
    private static final int HAN_MAX_ROW = 3;

    private static final int CHO_MIN_ROW = 8;
    private static final int CHO_CENTER_ROW = 9;
    private static final int CHO_MAX_ROW = 10;

    private Palace() {
    }

    public static boolean contains(Intersection pos, Side side) {
        final int row = pos.row();
        final int file = pos.file();

        boolean inPalaceFile = file >= MIN_FILE && file <= MAX_FILE;

        if (side == Side.HAN) { // 만약 Side enum의 이름이 다르다면 맞게 수정해주세요
            return inPalaceFile && row >= HAN_MIN_ROW && row <= HAN_MAX_ROW;
        }
        return inPalaceFile && row >= CHO_MIN_ROW && row <= CHO_MAX_ROW;
    }

    public static boolean isCenter(Intersection pos) {
        final int row = pos.row();
        final int file = pos.file();

        return file == CENTER_FILE &&
                (row == HAN_CENTER_ROW || row == CHO_CENTER_ROW);
    }

    public static boolean isCorner(Intersection pos) {
        final int row = pos.row();
        final int file = pos.file();

        boolean isHanCorner = Math.abs(row - HAN_CENTER_ROW) == 1;
        boolean isChoCorner = Math.abs(row - CHO_CENTER_ROW) == 1;
        boolean isFileCorner = Math.abs(file - CENTER_FILE) == 1;

        return (isHanCorner || isChoCorner) && isFileCorner;
    }

    public static Intersection getCenterOf(Intersection cornerPos) {
        if (cornerPos.row() <= HAN_MAX_ROW) {
            return new Intersection(HAN_CENTER_ROW, CENTER_FILE);
        }
        return new Intersection(CHO_CENTER_ROW, CENTER_FILE);
    }

    public static List<Intersection> getCornersOf(Intersection center) {
        if (!isCenter(center)) {
            throw new IllegalStateException("중앙 좌표가 아닙니다.");
        }
        final int r = center.row();
        final int f = center.file();
        return List.of(
                new Intersection(r - 1, f - 1), new Intersection(r - 1, f + 1),
                new Intersection(r + 1, f - 1), new Intersection(r + 1, f + 1)
        );
    }

    public static Intersection getOppositeCornerOf(Intersection corner) {
        if (!isCorner(corner)) {
            throw new IllegalArgumentException("코너 좌표가 아닙니다.");
        }

        Intersection center = getCenterOf(corner);
        int deltaRow = center.row() - corner.row();
        int deltaFile = center.file() - corner.file();

        return new Intersection(center.row() + deltaRow, center.file() + deltaFile);
    }
}
