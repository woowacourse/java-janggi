package dto;

import domain.board.Board;
import domain.game.JanggiGame;
import java.util.Map;
import java.util.stream.Collectors;

public record PieceInfosDto(Map<PositionDto, PieceNameDto> pieceInfos) {

    public static PieceInfosDto from(final JanggiGame janggiGame) {
        Board board = janggiGame.getBoard();
        Map<PositionDto, PieceNameDto> pieceInfos = board.getPieces().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> PositionDto.of(entry.getKey()),
                        entry -> PieceNameDto.from(entry.getValue())
                ));
        return new PieceInfosDto(pieceInfos);
    }
}
