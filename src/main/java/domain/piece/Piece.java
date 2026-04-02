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

    private Piece(final Team team, final PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isCannon() {
        return pieceType == PieceType.CANNON;
    }

    public boolean isGeneral() {
        return pieceType == PieceType.GENERAL;
    }

    public boolean isOpposite(Piece other) {
        return this.team != other.team;
    }

    public boolean isChoPiece() {
        return team == Team.CHO;
    }

    public String getNameForCho() {
        return pieceType.getNameForCho();
    }

    public String getNameForHan() {
        return pieceType.getNameForHan();
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }
}
