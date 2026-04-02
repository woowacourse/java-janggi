package ui.dto;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.position.Position;

public class PieceDto {
    private final int row;
    private final int column;
    private final String description;

    private PieceDto(int row, int column, String description) {
        this.row = row;
        this.column = column;
        this.description = description;
    }

    public static PieceDto of(Piece piece, Position position) {
        int row = position.getRow().getValue();
        int column = position.getColumn().getValue();

        return new PieceDto(row, column, selectDescription(piece));
    }

    private static String selectDescription(Piece piece) {
        if (piece.isSameTeam(Team.CHO)) {
            return selectChoDescription(piece.getPieceType());
        }
        return selectHanDescription(piece.getPieceType());
    }

    private static String selectChoDescription(PieceType pieceType) {
        if (pieceType == PieceType.CHA) {
            return "차";
        }
        if (pieceType == PieceType.SANG) {
            return "상";
        }
        if (pieceType == PieceType.MA) {
            return "마";
        }
        if (pieceType == PieceType.SA) {
            return "사";
        }
        if (pieceType == PieceType.JANG) {
            return "장";
        }
        if (pieceType == PieceType.PO) {
            return "포";
        }
        return "졸";
    }

    private static String selectHanDescription(PieceType pieceType) {
        if (pieceType == PieceType.CHA) {
            return "車";
        }
        if (pieceType == PieceType.SANG) {
            return "象";
        }
        if (pieceType == PieceType.MA) {
            return "馬";
        }
        if (pieceType == PieceType.SA) {
            return "士";
        }
        if (pieceType == PieceType.JANG) {
            return "漢";
        }
        if (pieceType == PieceType.PO) {
            return "包";
        }
        return "兵";
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getDescription() {
        return description;
    }
}
