package domain.piece;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import domain.player.Team;

public class Piece {

    private final Team team;
    private final PieceType pieceType;

    public static Piece choPieceOf(final PieceType pieceType) {
        return new Piece(CHO, pieceType);
    }

    public static Piece hanPieceOf(final PieceType pieceType) {
        return new Piece(HAN, pieceType);
    }

    private Piece(final Team team, final PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }
}
