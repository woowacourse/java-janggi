package dto;

import java.util.Map;

public record PiecesDto(Map<PositionDto, PieceInfoDto> pieces) {

    public static PiecesDto of(final Map<PositionDto, PieceInfoDto> pieces) {
        return new PiecesDto(pieces);
    }
}
