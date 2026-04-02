package domain.piece;

import domain.player.Team;
import java.util.function.Function;

public enum PieceType {

    CHA(Cha::new, 13),
    MA(Ma::new, 5),
    SA(Sa::new, 3),
    SANG(Sang::new, 3),
    JANG(Jang::new, 0),
    PO(Po::new, 7),
    JOL(Jol::new, 2);

    private final Function<Team, Piece> pieceCreator;
    private final int score;

    PieceType(Function<Team, Piece> pieceCreator, int score) {
        this.pieceCreator = pieceCreator;
        this.score = score;
    }

    public Piece createPiece(Team team) {
        return this.pieceCreator.apply(team);
    }

    public int score() {
        return score;
    }
}
