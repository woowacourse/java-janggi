package domain;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceGenerator {

    public static Map<Position, Piece> generatePieces(Camp camp, ElephantFormation elephantFormation) {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(generatePiece(camp));
        board.putAll(generatePiece(camp, elephantFormation));

        return board;
    }

    private static Map<Position, Piece> generatePiece(Camp camp) {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(generatePieces(camp, InitialPieceLocation.GENERAL, PieceType.GENERAL));
        board.putAll(generatePieces(camp, InitialPieceLocation.SOLDIER, PieceType.SOLDIER));
        board.putAll(generatePieces(camp, InitialPieceLocation.GUARD, PieceType.GUARD));
        board.putAll(generatePieces(camp, InitialPieceLocation.CANNON, PieceType.CANNON));
        board.putAll(generatePieces(camp, InitialPieceLocation.CHARIOT, PieceType.CHARIOT));

        return board;
    }

    private static Map<Position, Piece> generatePiece(Camp camp, ElephantFormation elephantFormation) {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(generatePieces(camp, InitialPieceLocation.HORSE, elephantFormation, PieceType.HORSE));
        board.putAll(generatePieces(camp, InitialPieceLocation.ELEPHANT, elephantFormation, PieceType.ELEPHANT));

        return board;
    }

    private static Map<Position, Piece> generatePieces(Camp camp,
                                                       InitialPieceLocation location,
                                                       PieceType pieceType) {
        Map<Position, Piece> pieces = new HashMap<>();
        List<Position> positions = location.getPositions(camp);
        for (Position position : positions) {
            pieces.put(position, createPiece(camp, pieceType));
        }
        return pieces;
    }

    private static Map<Position, Piece> generatePieces(Camp camp,
                                                       InitialPieceLocation location,
                                                       ElephantFormation elephantFormation,
                                                       PieceType pieceType) {
        Map<Position, Piece> pieces = new HashMap<>();
        List<Position> positions = location.getPositions(camp, elephantFormation);
        for (Position position : positions) {
            pieces.put(position, createPiece(camp, pieceType));
        }
        return pieces;
    }

    public static Piece createPiece(Camp camp, PieceType pieceType) {
        return switch (pieceType) {
            case GENERAL -> new General(camp);
            case GUARD -> new Guard(camp);
            case HORSE -> new Horse(camp);
            case ELEPHANT -> new Elephant(camp);
            case CANNON -> new Cannon(camp);
            case CHARIOT -> new Chariot(camp);
            case SOLDIER -> new Soldier(camp);
            default -> throw new IllegalArgumentException("[ERROR] 알 수 없는 기물 타입: " + pieceType);
        };
    }
}
