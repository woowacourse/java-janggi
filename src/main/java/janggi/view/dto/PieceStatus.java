package janggi.view.dto;

import janggi.domain.Camp;
import janggi.domain.Position;

public record PieceStatus(
        PositionInfo positionInfo,
        PieceInfo pieceInfo
) {
    public static PieceStatus from(Position position, Camp camp, String displayName) {
        int row = position.getRow();
        int col = position.getColumn();
        return new PieceStatus(new PositionInfo(row, col), new PieceInfo(camp.isCho(), displayName));
    }

    private record PositionInfo(int row, int col) {
    }

    private record PieceInfo(boolean isCho, String displayName) {
    }

    public int getRow() {
        return positionInfo.row();
    }

    public int getColumn() {
        return positionInfo.col();
    }

    public boolean isCho() {
        return pieceInfo.isCho();
    }

    public String getDisplayName() {
        return pieceInfo.displayName();
    }
}
