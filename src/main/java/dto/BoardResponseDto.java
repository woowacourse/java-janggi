package dto;

import domain.board.Board;
import domain.position.Position;

import java.util.Map;
import java.util.stream.Collectors;

public record BoardResponseDto(
        Map<Position, PieceDto> state
) {

    public static BoardResponseDto from(Board board) {
        return new BoardResponseDto(board.getState().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> PieceDto.from(entry.getValue()))
                ));
    }
}
