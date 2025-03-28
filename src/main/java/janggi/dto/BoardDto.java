package janggi.dto;

public record BoardDto(
        int positionRow,
        int positionCol,
        String pieceType,
        String pieceColor
){
}
