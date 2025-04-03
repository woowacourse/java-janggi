package piece;

import direction.Point;
import team.Team;

public abstract class Piece {

    protected final PieceType type;
    protected final Team team;
    protected Point current;

    public Piece(PieceType type, Team team, Point current) {
        this.type = type;
        this.team = team;
        this.current = current;
    }

    public abstract void move(final Pieces allPieces, final Point destination);

    public boolean isSamePoint(final Point point) {
        return current.equals(point);
    }

    public boolean isSameType(final Piece piece) {
        return type.equals(piece.type);
    }

    public boolean isSameType(final PieceType pieceType) {
        return type.equals(pieceType);
    }

    public abstract int score();

    public PieceType type() {
        return type;
    }

    public int row() {
        return current.row();
    }

    public int column() {
        return current.column();
    }

    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    public Team team() {
        return team;
    }
}
