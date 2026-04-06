package janggi.dto;

public record BoardPiece(int gameRoomId,
                         int rowPos,
                         int colPos,
                         String pieceType,
                         String side) {
}
