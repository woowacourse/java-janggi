package janggi.view;

import java.util.Arrays;

public enum XPointFormat {
    가("가",0),
    나("나",1),
    다("다",2),
    라("라",3),
    마("마",4),
    바("바",5),
    사("사",6),
    아("아",7),
    자("자",8),
    차("차",9)
    ;

    private final String format;
    private final int pointX;

    XPointFormat(String format, int pointX) {
        this.format = format;
        this.pointX = pointX;
    }

    public int getPointX() {
        return pointX;
    }

    public static int convertToInt(String format){
        return Arrays.stream(values())
                .filter(XPointFormat ->  XPointFormat.equalFormat(format))
                .findAny()
                .map(XPointFormat::getPointX)
                .orElseThrow(()-> new IllegalStateException("PointFormat 에 없는 값입니다."));
    }

    public boolean equalFormat(String format){
        return this.format.equals(format);
    }
}
