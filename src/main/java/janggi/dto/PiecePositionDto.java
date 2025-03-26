package janggi.dto;

public record PiecePositionDto(
        int position_row,
        int position_col,
        String piece_type,
        String piece_color
){
}
