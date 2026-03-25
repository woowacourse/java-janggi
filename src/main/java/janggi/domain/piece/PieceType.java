package janggi.domain.piece;

public enum PieceType {
    CHA("차", 13),
    GUNG("궁", 0),
    JOL("졸", 2),
    MA("마", 5),
    PO("포", 7),
    SA("사", 3),
    SANG("상", 3),
    ;

    PieceType(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    private final String nickname;
    private final int score;

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }
}
