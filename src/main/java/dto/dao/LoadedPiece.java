package dto.dao;

import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.sql.ResultSet;
import java.sql.SQLException;

public record LoadedPiece(String team, String pieceType, int y, int x) {
    public static LoadedPiece fromRow(ResultSet rs) throws SQLException {
        return new LoadedPiece(
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
}
