package participant;

import pieces.PieceType;
import pieces.Side;

public record Player(Side side, Score score) {

    public Player attack(PieceType pieceType) {
        return new Player(side, score.addScoreOf(pieceType));
    }
}
