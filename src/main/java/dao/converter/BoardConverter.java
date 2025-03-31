package dao.converter;

import dao.PieceDto;
import domain.board.Board;
import domain.board.PathFinder;
import domain.board.PathFinderFactory;
import domain.piece.Cha;
import domain.piece.ChoByeong;
import domain.piece.HanByeong;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.Wang;
import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Point;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardConverter {

    public static List<PieceDto> convertToPieceEntities(Map<Point, Piece> pieceByPoint, String gameRoomName) {
        return pieceByPoint.entrySet().stream()
                .map(entry -> convertToPieceEntity(entry, gameRoomName))
                .toList();
    }

    private static PieceDto convertToPieceEntity(Map.Entry<Point, Piece> pieceByPoint, String gameRoomName) {
        Point point = pieceByPoint.getKey();
        Piece piece = pieceByPoint.getValue();
        return new PieceDto(null, point.row(), point.column(), piece.type(), piece.team(), gameRoomName);
    }

    public static Board convertToBoard(List<PieceDto> pieceEntities) {
        PathFinderFactory pathFinderFactory = new PathFinderFactory();
        PathFinder pathFinder = pathFinderFactory.createDefaultPathFinder();

        Map<Point, Piece> boardPieces = convertToPieceByPoint(pieceEntities);
        return new Board(boardPieces, pathFinder);
    }

    private static Map<Point, Piece> convertToPieceByPoint(List<PieceDto> pointPieces) {
        return pointPieces.stream()
                .collect(Collectors.toMap(
                        pointPiece -> Point.of(pointPiece.rowIndex(), pointPiece.columnIndex()),
                        pointPiece -> BoardConverter.convertToPiece(pointPiece.pieceType(), pointPiece.team())
                ));
    }

    private static Piece convertToPiece(PieceType pieceType, Team team) {
        return switch (pieceType) {
            case MA -> new Ma(team);
            case BYEONG -> switch (team) {
                case CHO -> new ChoByeong();
                case HAN -> new HanByeong();
            };
            case PO -> new Po(team);
            case SA -> new Sa(team);
            case CHA -> new Cha(team);
            case SANG -> new Sang(team);
            case WANG -> new Wang(team);
        };
    }
}
