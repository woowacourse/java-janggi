package domain;

import domain.vo.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<Position, Piece> board;

    private Board(final Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board of(final Map<Position, Piece> board) {
        return new Board(board);
    }

    public void move(final Position from, final Position to, final Team currentTeam) {
        Piece fromPiece = findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));

        if (fromPiece.getTeam() != currentTeam) {
            throw new IllegalArgumentException("[ERROR] 상대편의 기물은 움직일 수 없습니다.");
        }

        List<Position> path = fromPiece.getPathPositions(from, to);
        Map<Position, Piece> piecesOnPath = findPiecesAt(path);

        if (!fromPiece.canMovePiece(from, to, piecesOnPath)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 움직일 수 없습니다.");
        }

        board.remove(from);
        board.put(to, fromPiece);
    }

    public boolean isExistPosition(final Position tempPosition) {
        return board.containsKey(tempPosition);
    }

    public Optional<Piece> findPieceByPosition(final Position position) {
        return Optional.ofNullable(board.get(position));
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }

    public boolean isGeneralAlive(Team team) {
        return board.values().stream()
                .anyMatch(piece -> piece.getTeam() == team && piece.getType() == Type.GENERAL);
    }

    public int calculateScore(Team team) {
        return board.values()
                .stream()
                .filter(piece -> piece.getTeam() == team)
                .map(piece -> piece.getType().getScore())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Map<Position, Piece> findPiecesAt(final List<Position> positions) {
        Map<Position, Piece> result = new HashMap<>();
        for (Position pos : positions) {
            findPieceByPosition(pos).ifPresent(piece -> result.put(pos, piece));
        }
        return result;
    }
}