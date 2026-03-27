package view;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.position.Position;

public class PieceDto {
    private final int row;
    private final int column;
    private final String description;

    public PieceDto(int row, int column, String description) {
        this.row = row;
        this.column = column;
        this.description = description;
    }

    public static PieceDto toDto(Position position, Piece piece) {
        int row = position.getRow().getValue();
        int col = position.getColumn().getValue();
        PieceType type = piece.getPieceType();
        Team team = piece.getTeam();

        if (team == Team.CHO) {
            if (type == PieceType.CHA) {
                return new PieceDto(row, col, "차");
            }
            if (type == PieceType.SANG) {
                return new PieceDto(row, col, "상");
            }
            if (type == PieceType.MA) {
                return new PieceDto(row, col, "마");
            }
            if (type == PieceType.SA) {
                return new PieceDto(row, col, "사");
            }
            if (type == PieceType.JANG) {
                return new PieceDto(row, col, "궁");
            }
            if (type == PieceType.PO) {
                return new PieceDto(row, col, "포");
            }
            if (type == PieceType.JOL) {
                return new PieceDto(row, col, "졸");
            }
        }

        if (team == Team.HAN) {
            if (type == PieceType.CHA) {
                return new PieceDto(row, col, "車");
            }
            if (type == PieceType.SANG) {
                return new PieceDto(row, col, "象");
            }
            if (type == PieceType.MA) {
                return new PieceDto(row, col, "馬");
            }
            if (type == PieceType.SA) {
                return new PieceDto(row, col, "士");
            }
            if (type == PieceType.JANG) {
                return new PieceDto(row, col, "漢");
            }
            if (type == PieceType.PO) {
                return new PieceDto(row, col, "包");
            }
            if (type == PieceType.BYEONG) {
                return new PieceDto(row, col, "兵");
            }
        }

        throw new IllegalArgumentException("일치하는 기물 정보가 없습니다.");
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
