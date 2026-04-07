package domain.board;

import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;

public class IntersectionState {
    private final Point point;
    private final PieceType pieceType;
    private final Team team;

    public IntersectionState(Point point, PieceType pieceType, Team team) {
        this.point = point;
        this.pieceType = pieceType;
        this.team = team;
    }

    public Point getPoint() {
        return point;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }
}
