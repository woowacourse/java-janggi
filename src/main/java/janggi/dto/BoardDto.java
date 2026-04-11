package janggi.dto;

import janggi.domain.Game;
import java.util.HashMap;
import java.util.Map;

public record BoardDto(Map<PositionDto, PieceDto> board) {

    public static BoardDto from(Game game) {
        Map<PositionDto, PieceDto> board = new HashMap<>();

        game.forEachPiece((position, piece) ->
                board.put(PositionDto.from(position), PieceDto.from(piece))
        );

        return new BoardDto(Map.copyOf(board));
    }
}
