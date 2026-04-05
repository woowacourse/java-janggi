package janggi.domain.board;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardSnapshot {

    private final Map<Position, Piece> board;

    private BoardSnapshot(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public static BoardSnapshot of(Map<Position, Piece> board) {
        return new BoardSnapshot(board);
    }

    // 모든 위치 리스트에서 처음으로 기물을 만나기까지의 위치만을 선택해서 반환하는 메서드
    public List<Position> selectUntilNearestPiecePosition(List<Position> allPositions) {
        List<Position> positions = new ArrayList<>();
        for (Position position : allPositions) {
            positions.add(position);
            if (!isEmpty(position)) {
                break;
            }
        }
        return positions;
    }

    public boolean isPlaceable(Position position, Dynasty dynasty) {
        return isEmpty(position) || !isSameDynasty(position, dynasty);
    }

    public boolean isSameDynasty(Position position, Dynasty dynasty) {
        if (isEmpty(position)) {
            return false;
        }
        return board.get(position).isSame(dynasty);
    }

    public boolean isSamePieceType(Position position, PieceType pieceType) {
        if (isEmpty(position)) {
            return false;
        }
        return board.get(position).isSame(pieceType);
    }

    public boolean isEmpty(Position position) {
        return !board.containsKey(position);
    }

}
