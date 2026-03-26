package janggi.dto;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record BoardDto(List<PositionPieceDto> positionPieces) {
    public static BoardDto from(Board board) {
        Map<Position, Piece> copiedBoard = board.getBoard();
        List<PositionPieceDto> positionPieces = new ArrayList<>();
        for (Position position : copiedBoard.keySet()) {
            positionPieces.add(PositionPieceDto.from(position, copiedBoard.get(position)));
        }
        return new BoardDto(positionPieces);
    }
}
