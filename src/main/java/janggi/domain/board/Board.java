package janggi.domain.board;

import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> initBoard) {
        this.board = new HashMap<>(initBoard);
    }

    public List<Position> findAvailablePositions(Position position) {
        if (!board.containsKey(position)) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 좌표입니다.");
        }
        Piece currentPiece = board.get(position);
        List<Position> availablePositions = currentPiece.findAvailableDestinations(position, board);
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

    private static void validateCantMovePiece(List<Position> availablePositions) {
        if (availablePositions.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없는 좌표입니다.");
        }
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }

    public Piece getPiece(Position position) {
        return board.get(position);
    }

    public boolean isKingDead(Team team) {
        return board.values().stream()
                .noneMatch(piece -> piece.isSameTeam(team) && piece.isKing());
    }

    public double calculateScore(Team team) {
        return board.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(Piece::getPieceScore)
                .sum();
    }
}
