package dto;

import domain.game.JanggiGame;
import java.util.Map;
import java.util.stream.Collectors;

public record PiecesDto(Map<PositionDto, PieceInfoDto> pieces) {

    public static PiecesDto of(final Map<PositionDto, PieceInfoDto> pieces) {
        return new PiecesDto(pieces);
    }

    public static PiecesDto from(final JanggiGame janggiGame) {
        Map<PositionDto, PieceInfoDto> pieces = janggiGame.getPieces().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> PositionDto.of(entry.getKey()),
                        entry -> PieceInfoDto.of(entry.getValue().getPieceType(), entry.getValue().getTeam())
                ));
        return PiecesDto.of(pieces);
    }
}
