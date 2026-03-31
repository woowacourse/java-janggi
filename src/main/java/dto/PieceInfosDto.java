package dto;

import domain.board.Board;
import java.util.Map;
import java.util.stream.Collectors;

public record PieceInfosDto(Map<PositionDto, PieceNameDto> pieceInfos) {

    public static PieceInfosDto from(final Board board) {
        Map<PositionDto, PieceNameDto> pieceInfos = board.getPieces().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> PositionDto.from(entry.getKey()),
                        entry -> PieceNameDto.from(entry.getValue())
                ));
        return new PieceInfosDto(pieceInfos);
    }
}
