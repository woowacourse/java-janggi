package fixture;

import domain.intersection.Intersection;
import domain.intersection.palace.NormalIntersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;

public class IntersectionFixture {

    public static Intersection generate(int row, int file, Team team, PieceType type) {
        return generate(new Point(row, file), team, type);
    }

    public static Intersection generate(Point point, Team team, PieceType type) {
        return new NormalIntersection(point, new Piece(team, type));
    }

}
