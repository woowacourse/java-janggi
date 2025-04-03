package janggi.database;

import janggi.piece.Cannon;
import janggi.piece.Color;
import janggi.piece.Elephant;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.PieceDto;
import janggi.piece.Pieces;
import janggi.piece.Soldier;
import janggi.piece.Tank;
import janggi.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceConverter {

    public static Piece convertToPiece(final PieceDto pieceDto) {
        final Color pieceColor = getColor(pieceDto.getColor());

        switch (pieceDto.getPieceType()) {
            case "TANK":
                return new Tank(pieceColor);
            case "CANNON":
                return new Cannon(pieceColor);
            case "HORSE":
                return new Horse(pieceColor);
            case "ELEPHANT":
                return new Elephant(pieceColor);
            case "GUARD":
                return new Guard(pieceColor);
            case "SOLDIER":
                return new Soldier(pieceColor);
            case "KING":
                return new King(pieceColor);
            default:
                throw new IllegalArgumentException("알 수 없는 타입입니다.");
        }
    }

    public static Pieces convertToPieces(final List<PieceDto> pieceDtos) {
        final Map<Position, Piece> pieces = new HashMap<>();
        for (PieceDto pieceDto : pieceDtos) {
            Position position = new Position(pieceDto.getX(), pieceDto.getY());
            Piece piece = convertToPiece(pieceDto);
            pieces.put(position, piece);
        }
        return new Pieces(pieces);
    }

    private static Color getColor(final String color) {
        if (color.equals("RED")) {
            return Color.RED;
        }
        return Color.BLUE;
    }
}
