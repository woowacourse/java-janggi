package janggi.dto;

public record BoardDto(
        int position_row,
        int position_col,
        String piece_type,
        String piece_color
){
}
