package janggi.domain.piece;

public enum PieceType {

    EMPTY("ㆍ", 0),
    GUNG("궁", 0),
    SA("사", 3),
    MA("마", 5),
    SANG("상", 3),
    CHA("차", 13),
    PO("포", 7),
    JOL("졸", 2),
    BYEONG("병", 2),
    ;

    private final String koreanNameFormat;
    private final int score;

    PieceType(String koreanNameFormat, int score) {
        this.koreanNameFormat = koreanNameFormat;
        this.score = score;
    }

    public String getNameFormat() {
        return koreanNameFormat;
    }

    public int getScore() {
        return score;
    }
}
