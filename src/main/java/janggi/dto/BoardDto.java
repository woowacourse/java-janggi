package janggi.dto;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;

import java.util.LinkedHashMap;
import java.util.Map;

public record BoardDto(Map<String, PositionPieceDto> positionPieces) {
    public static BoardDto from(Board board) {
        Map<Position, Piece> copiedBoard = board.getBoard();
        Map<String, PositionPieceDto> positionPieceDtos = new LinkedHashMap<>();
        for (Position position : copiedBoard.keySet()) {
            PositionPieceDto dto = PositionPieceDto.from(position, copiedBoard.get(position));
            positionPieceDtos.put(position.getX() + "," + position.getY(), dto);
        }
        return new BoardDto(positionPieceDtos);
    }

    public PositionPieceDto findPiece(int x, int y) {
        return positionPieces.get(x + "," + y);
    }
}
