package dto;

import domain.board.Board;
import java.util.Map;
import java.util.stream.Collectors;

public record PieceInfosDto(Map<PositionDto, PieceName> pieceInfos) {

    public static PieceInfosDto from(final Board board) {
        Map<PositionDto, PieceName> pieceInfos = board.getPieces().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> PositionDto.from(entry.getKey()),
                        entry -> PieceName.from(entry.getValue())
                ));
        return new PieceInfosDto(pieceInfos);
    }
}
