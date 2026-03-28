package dto;

import domain.piece.Position;
import java.util.Map;

public record PieceInfosDto(Map<Position, PieceInfoDto> pieceInfos) {

    public static PieceInfosDto of(final Map<Position, PieceInfoDto> pieceInfos) {
        return new PieceInfosDto(pieceInfos);
    }
}
