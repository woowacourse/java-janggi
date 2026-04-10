package janggi.dto;

public record BoardPiece(long gameRoomId,
                         int rowPos,
                         int colPos,
                         String pieceType,
                         String side) {
}
