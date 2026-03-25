package domain.board;

import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public abstract class Wings {

    protected final LeftWing leftWing;
    protected final RightWing rightWing;

    public Wings(List<Piece> leftWingPieces, List<Piece> rightWingPieces) {
        this.leftWing = new LeftWing(leftWingPieces);
        this.rightWing = new RightWing(rightWingPieces);
    }

    public abstract Map<Intersection, Piece> setUpPieces();
}
