package domain.piece;

import domain.Score;

public enum PieceType {
    CANNON("포", new Score(7)),
    CHARIOT("차", new Score(13)),
    ELEPHANT("상", new Score(3)),
    GENERAL("왕", new Score(0)),
    GUARD("사", new Score(3)),
    HORSE("마", new Score(5)),
    ZZU("쭈", new Score(2));

    private final String title;
    private final Score score;

    PieceType(final String title, final Score score) {
        this.title = title;
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public Score getScore() {
        return score;
    }
}
