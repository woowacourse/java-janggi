package janggi.domain.board;

import janggi.domain.common.Position;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board = new HashMap<>();

    public void place(Position position, Piece piece) {
        board.put(position, piece);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public void movePiece(Position movePiecePosition, Position destination) {
        Piece piece = board.get(movePiecePosition);
        board.remove(movePiecePosition);
        board.put(destination, piece);
    }

    public Piece pieceAt(Position position) {
        return board.get(position);
    }

    public boolean hasPiece(Position position) {
        return board.containsKey(position);
    }

    public List<Position> findAvailablePositions(Position position) {
        Piece piece = pieceAt(position);

        return piece.findMovablePositions(this, position);
    }

    public void validateMovePiecePosition(Position movePiecePosition) {
        if (!hasPiece(movePiecePosition)) {
            throw new IllegalArgumentException("[ERROR] 빈 칸을 선택하셨습니다.");
        }
    }

    public void validateDestination(Position movePiecePosition, Position destination) {
        List<Position> availablePositions = findAvailablePositions(movePiecePosition);
        boolean hasPosition = false;
        for (Position position : availablePositions) {
            if (position.equals(destination)) {
                hasPosition = true;
                break;
            }
        }
        if (hasPosition == false) {
            throw new IllegalArgumentException("[ERROR] 이동 가능한 좌표 중에서 선택하세요.");
        }
    }
}
