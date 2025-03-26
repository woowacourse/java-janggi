package domain.piece;

import domain.score.Score;

public enum PieceType {

    WANG("왕", new Score(0)),
    SA("사", new Score(3)),
    CHA("차", new Score(13)),
    SANG("상", new Score(3)),
    MA("마", new Score(5)),
    PO("포", new Score(7)),
    BYEONG("병", new Score(2)),
    ;

    private final String title;
    private final Score score;

    PieceType(final String title, final Score score) {
        this.title = title;
        this.score = score;
    }

    public String title() {
        return title;
    }

    public Score score() {
        return score;
    }
}
