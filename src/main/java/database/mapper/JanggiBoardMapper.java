package database.mapper;

import domain.intersection.Intersection;
import domain.intersection.IntersectionType;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;

public class JanggiBoardMapper {

    public static Intersection toIntersection(int y, int x, String pieceType, String team, String intersectionType) {
        Point point = new Point(y, x);
        Piece piece = new Piece(Team.valueOf(team), PieceType.valueOf(pieceType));
        IntersectionType type = IntersectionType.valueOf(intersectionType);
        return type.create(point, piece);
    }

}
