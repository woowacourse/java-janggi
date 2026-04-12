package janggi.domain.piece;

public enum PieceType {

    EMPTY("ㆍ"),
    GUNG("궁"),
    SA("사"),
    MA("마"),
    SANG("상"),
    CHA("차"),
    PO("포"),
    JOL("졸"),
    BYEONG("병"),
    ;

    private final String koreanNameFormat;

    PieceType(String koreanNameFormat) {
        this.koreanNameFormat = koreanNameFormat;
    }

    public String getNameFormat() {
        return koreanNameFormat;
    }
}
