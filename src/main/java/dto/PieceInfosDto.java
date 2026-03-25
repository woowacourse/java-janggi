package dto;

import java.util.List;

public record PieceInfosDto(List<PieceInfoDto> pieceInfos) {

    public static PieceInfosDto of(final List<PieceInfoDto> pieceInfos) {
        return new PieceInfosDto(pieceInfos);
    }
}
