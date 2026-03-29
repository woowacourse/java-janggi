package controller.dto;

public record MovedPieceRequest(int currentRow,
                                int currentColumn,
                                int nextRow,
                                int nextColumn,
                                String pieceType) {
}
