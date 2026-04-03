package participant;

import pieces.PieceScore;
import pieces.PieceType;

public record Score(long value) {

    public Score addScoreOf(PieceType pieceType) {
        Score other = PieceScore.from(pieceType);
        return new Score(value + other.value);
    }
}
