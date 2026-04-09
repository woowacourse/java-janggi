package domain.piece;

public class Piece {

    private final Team team;
    private final PieceType pieceType;

    public static Piece choPieceOf(final PieceType pieceType) {
        return new Piece(Team.CHO, pieceType);
    }

    public static Piece hanPieceOf(final PieceType pieceType) {
        return new Piece(Team.HAN, pieceType);
    }

    public Piece(final Team team, final PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public boolean isCannon() {
        return pieceType == PieceType.CANNON;
    }

    public boolean isGeneral() {
        return pieceType == PieceType.GENERAL;
    }

    public boolean isOpposite(final Piece other) {
        return this.team != other.team;
    }

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }

    public double getScore() {
        return pieceType.getScore();
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
