package database.mapper;

import database.dto.IntersectionDto;
import domain.intersection.Intersection;
import domain.intersection.IntersectionType;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;

import java.util.List;

public class JanggiBoardMapper {

    public static Intersection toIntersection(int y, int x, String pieceType, String team, String intersectionType) {
        Point point = new Point(y, x);
        Piece piece = new Piece(Team.valueOf(team), PieceType.valueOf(pieceType));
        IntersectionType type = IntersectionType.valueOf(intersectionType);
        return type.create(point, piece);
    }

    public IntersectionDto toIntersectionDto(Intersection intersection) {
        Point point = intersection.getPoint();
        Piece piece = intersection.readPiece();
        String pieceType = piece.pieceType().name();
        String teamName = piece.team().name();
        String intersectionType = intersection.readIntersectionType().name();
        return new IntersectionDto(point.y(), point.x(), pieceType, teamName, intersectionType);
    }

    public List<IntersectionDto> toIntersectionDtoList(List<Intersection> intersections) {
        return intersections.stream()
                .map(this::toIntersectionDto)
                .toList();
    }

}
