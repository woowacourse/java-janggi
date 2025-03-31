package repository.converter;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.jumpingPiece.Cannon;
import janggi.piece.normalPiece.Blank;
import janggi.piece.normalPiece.Elephant;
import janggi.piece.normalPiece.Horse;
import janggi.piece.palacePiece.King;
import janggi.piece.palacePiece.Soldier;
import janggi.piece.pawnPiece.ChoPawn;
import janggi.piece.pawnPiece.HanPawn;
import janggi.piece.straightPiece.Chariot;
import janggi.position.Position;

public record PieceConverter(
        String rowIndex,
        String columnIndex,
        String pieceTypeName,
        String teamName
) {

    public static PieceConverter toEntity(final Piece piece) {
        return new PieceConverter(
                piece.row().ordinal() + "",
                piece.column().ordinal() + "",
                piece.type().toString(),
                piece.team().toString()
        );
    }

    public static Piece from(String rowIndex, String columnIndex, String pieceTypeName,
                             String teamName) {
        return switch (pieceTypeName) {
            case "KING" -> new King(Team.convert(teamName), new Position(columnIndex, rowIndex));
            case "CHARIOT" -> new Chariot(Team.convert(teamName), new Position(columnIndex, rowIndex));
            case "CANNON" -> new Cannon(Team.convert(teamName), new Position(columnIndex, rowIndex));
            case "HORSE" -> new Horse(Team.convert(teamName), new Position(columnIndex, rowIndex));
            case "ELEPHANT" -> new Elephant(Team.convert(teamName), new Position(columnIndex, rowIndex));
            case "SOLDIER" -> new Soldier(Team.convert(teamName), new Position(columnIndex, rowIndex));
            case "HANPAWN" -> new HanPawn(new Position(columnIndex, rowIndex));
            case "CHOPAWN" -> new ChoPawn(new Position(columnIndex, rowIndex));
            default -> new Blank(new Position(columnIndex, rowIndex));
        };
    }

}
