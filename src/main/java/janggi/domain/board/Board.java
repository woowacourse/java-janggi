package janggi.domain.board;

import janggi.domain.Piece;
import janggi.domain.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> initBoard) {
        this.board = new HashMap<>(initBoard);
    }

    private static void validateCantMovePiece(List<Position> availablePositions) {
        if (availablePositions.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없는 좌표입니다.");
        }
    }

    public List<Position> findAvailablePositions(Position position) {
        if (!board.containsKey(position)) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 좌표입니다.");
        }
        Piece piece = board.get(position);
        List<Position> availablePositions = piece.findAvailableDestinations(position, board);
        // TODO: Position에 같은 팀 진영에 있는거 필터하기
        validateCantMovePiece(availablePositions);
        return availablePositions;
    }

    public void movePiece(Position movePiecePosition, Position destination) {
        Piece piece = board.get(movePiecePosition);
        board.remove(movePiecePosition);
        board.put(destination, piece);
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

        if (!hasPosition) {
            throw new IllegalArgumentException("[ERROR] 이동 가능한 좌표 중에서 선택하세요.");
        }
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }

    public Piece getPiece(Position position) {
        return board.get(position);
    }
}
