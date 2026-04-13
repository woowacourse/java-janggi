package janggi.dto;

import janggi.domain.GameContext;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.LinkedHashMap;
import java.util.Map;

public record BoardDto(Map<Position, PieceDto> pieceMap) {

    public static BoardDto from(final GameContext gameContext) {
        final Map<Position, Piece> positionPieceMap = gameContext.getPositionPieceMap();
        final Map<Position, PieceDto> pieceDtoMap = new LinkedHashMap<>();
        positionPieceMap.forEach((position, piece) ->
                pieceDtoMap.put(position, new PieceDto(piece.pieceType(), piece.teamType())));
        return new BoardDto(Map.copyOf(pieceDtoMap));
    }
}
