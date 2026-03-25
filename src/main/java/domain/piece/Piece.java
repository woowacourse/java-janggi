package domain.piece;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;

public abstract class Piece {

    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    //경로 계산.
    public abstract Path calculatePath(Position src, Position dest);

    public String getPieceString() {
        return pieceType.getSymbol();
    }
}
