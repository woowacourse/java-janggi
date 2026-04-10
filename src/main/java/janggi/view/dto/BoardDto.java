package janggi.view.dto;

import janggi.domain.piece.Piece;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.view.mapper.DynastyColorMapper;
import janggi.view.mapper.PieceMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record BoardDto(
        List<List<PieceDto>> board
) {
    private static final String BACKGROUND_GREEN = "\u001B[42m";

    public static BoardDto from(Map<Position, Piece> board) {
        List<List<PieceDto>> allPieces = new ArrayList<>();
        for (int row = Row.MIN_ROW; row <= Row.MAX_ROW; row++) {
            List<PieceDto> piecesByRow = new ArrayList<>();
            addRow(board, row, piecesByRow, List.of());
            allPieces.add(piecesByRow);
        }
        return new BoardDto(allPieces);
    }


    public static BoardDto canMovePositionsFrom(Map<Position, Piece> board, List<Position> canMovePositions) {
        List<List<PieceDto>> allPieces = new ArrayList<>();
        for (int row = Row.MIN_ROW; row <= Row.MAX_ROW; row++) {
            List<PieceDto> piecesByRow = new ArrayList<>();
            addRow(board, row, piecesByRow, canMovePositions);
            allPieces.add(piecesByRow);
        }
        return new BoardDto(allPieces);
    }

    private static void addRow(Map<Position, Piece> board, int row, List<PieceDto> piecesByRow, List<Position> canMovePositions) {
        for (int column = Column.MIN_COLUMN; column <= Column.MAX_COLUMN; column++) {
            Position position = Position.from(row, column);
            boolean canMove = canMovePositions.contains(position);
            Piece piece = board.get(position);

            if (piece != null) {
                String color = DynastyColorMapper.from(piece.dynasty());
                if (canMove) {
                    color = BACKGROUND_GREEN + color;
                }
                piecesByRow.add(new PieceDto(PieceMapper.from(piece), color));
                continue;
            }

            if (canMove) {
                piecesByRow.add(new PieceDto("＊", BACKGROUND_GREEN));
                continue;
            }
            piecesByRow.add(new PieceDto("＊", ""));
        }
    }

}
