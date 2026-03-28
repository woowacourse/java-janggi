package domain.piece;

import static domain.piece.Team.CHO;
import static domain.piece.Team.HAN;

import java.util.List;
import java.util.Map;

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

    public Team getTeam() {
        return team;
    }

    public boolean isCannon() {
        return pieceType == PieceType.CANNON;
    }

    public boolean isGeneral() {
        return pieceType == PieceType.GENERAL;
    }

    public boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    public String getPieceTypeNameBy(Team team) {
        return pieceType.getNameOf(team);
    }

    public List<Position> calculateMovablePositions(Position current, Map<Position, Piece> pieces) {
        return pieceType.calculateMovablePositions(current, pieces);
    }
}
