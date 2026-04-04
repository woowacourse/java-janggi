package janggi.dto;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import java.util.LinkedHashMap;
import java.util.Map;

public record BoardDto(Map<Position, PieceDto> pieceMap) {

    public static BoardDto from(final Board board) {
        final Map<Position, Piece> positionPieceMap = board.getPositionPieceMapForDTO();
        final Map<Position, PieceDto> pieceDtoMap = new LinkedHashMap<>();
        positionPieceMap.forEach((position, piece) ->
                pieceDtoMap.put(position, new PieceDto(piece.getPieceTypeForDTO(), piece.getTeamTypeForDTO())));
        return new BoardDto(Map.copyOf(pieceDtoMap));
    }
}
