package domain.piece;

import domain.player.Team;
import java.util.function.Function;

public enum PieceType {

    CHA(Cha::new),
    MA(Ma::new),
    SA(Sa::new),
    SANG(Sang::new),
    JANG(Jang::new),
    PO(Po::new),
    JOL(Jol::new),
    NONE(team -> new None());

    private final Function<Team, Piece> pieceCreator;

    PieceType(Function<Team, Piece> pieceCreator) {
        this.pieceCreator = pieceCreator;
    }

    public Piece createPiece(Team team) {
        return this.pieceCreator.apply(team);
    }
}