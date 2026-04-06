package janggi.dto;

import janggi.domain.piece.Piece;

public record PieceResponse(String displayPiece) {

    private static final String HAN_COLOR = "\u001B[31m";
    private static final String CHO_COLOR = "\u001B[34m";
    private static final String AVAILABLE_COLOR = "\u001B[32m";
    private static final String RESET = "\u001B[0m";
    private static final String EMPTY_CELL = "． ";
    private static final String AVAILABLE_EMPTY_CELL = "Ｏ";

    public static PieceResponse from(Piece piece, boolean isMovable) {
        if (isMovable) {
            return checkEmptyCell(piece);
        }
        if (piece == null) {
            return new PieceResponse(EMPTY_CELL);
        }
        return new PieceResponse(selectColor(piece) + piece.getPieceTypeName() + RESET + " ");
    }

    private static PieceResponse checkEmptyCell(Piece piece) {
        if (piece == null) {
            return new PieceResponse(AVAILABLE_COLOR + AVAILABLE_EMPTY_CELL + RESET + " ");
        }
        return new PieceResponse(AVAILABLE_COLOR + piece.getPieceTypeName() + RESET + " ");
    }

    private static String selectColor(Piece piece) {
        if (piece.isCHO()) {
            return CHO_COLOR;
        }
        return HAN_COLOR;
    }
}
