package janggi.domain.piece;

public enum PieceType {
    차(13),
    포(7),
    마(5),
    상(3),
    사(3),
    졸(2),
    병(2),
    장(0),
    ;

    public final int score;

    PieceType(final int score) {
        this.score = score;
    }
}