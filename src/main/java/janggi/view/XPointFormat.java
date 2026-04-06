package janggi.view;

import java.util.Arrays;

public enum XPointFormat {
    GA("가", 0),
    NA("나", 1),
    DA("다", 2),
    RA("라", 3),
    MA("마", 4),
    BA("바", 5),
    SA("사", 6),
    A("아", 7),
    JA("자", 8),
    CHA("차", 9);

    private final String format;
    private final int pointX;

    XPointFormat(String format, int pointX) {
        this.format = format;
        this.pointX = pointX;
    }

    public String getFormat() {
        return format;
    }

    public int getPointX() {
        return pointX;
    }

    public static int convertToInt(String format) {
        return Arrays.stream(values())
                .filter(XPointFormat -> XPointFormat.equalFormat(format))
                .findAny()
                .map(XPointFormat::getPointX)
                .orElseThrow(() -> new IllegalStateException("PointFormat 에 없는 값입니다."));
    }

    public boolean equalFormat(String format) {
        return this.format.equals(format);
    }
}
