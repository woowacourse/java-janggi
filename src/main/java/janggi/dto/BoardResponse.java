package janggi.dto;

import janggi.domain.board.Board;
import janggi.domain.common.Position;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardResponse {

    private final Map<PositionResponse, PieceResponse> boardInfos;

    private BoardResponse(Map<PositionResponse, PieceResponse> boardInfos) {
        this.boardInfos = boardInfos;
    }

    public static BoardResponse from(Board board) {
        return of(board, List.of());
    }

    public static BoardResponse of(Board board, List<Position> movablePositions) {
        Map<PositionResponse, PieceResponse> boardInfos = new HashMap<>();

        for (int y = 1; y <= 10; y++) {
            fillRowInfos(board, movablePositions, y, boardInfos);
        }
        return new BoardResponse(boardInfos);
    }

    private static void fillRowInfos(Board board, List<Position> movablePositions, int y,
                                     Map<PositionResponse, PieceResponse> boardInfos) {
        for (int x = 1; x <= 9; x++) {
            Position currentPos = new Position(x, y);

            boolean isMovable = movablePositions.contains(currentPos);
            Piece piece = board.pieceAt(currentPos);

            boardInfos.put(PositionResponse.from(currentPos), PieceResponse.from(piece, isMovable));
        }
    }

    public Map<PositionResponse, PieceResponse> getBoardInfos() {
        return boardInfos;
    }
}
