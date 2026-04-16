package entity;

import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class PieceEntity {
    private final String team;
    private final String pieceType;
    private final int y;
    private final int x;

    private PieceEntity(String team, String pieceType, int y, int x) {
        this.team = team;
        this.pieceType = pieceType;
        this.y = y;
        this.x = x;
    }

    public static PieceEntity fromRow(ResultSet rs) throws SQLException {
        return new PieceEntity(
                rs.getString("team"),
                rs.getString("piece_type"),
                rs.getInt("y"),
                rs.getInt("x")
        );
    }

    public Intersection toIntersection() {
        return new Intersection(
                new Point(y, x),
                new Piece(Team.valueOf(team), PieceType.valueOf(pieceType))
        );
    }

    public String team() {
        return team;
    }

    public String pieceType() {
        return pieceType;
    }

    public int y() {
        return y;
    }

    public int x() {
        return x;
    }
}
