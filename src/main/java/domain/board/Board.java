package domain.board;

import domain.vo.Position;

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

    public boolean isExistPosition(final Position tempPosition) {
        return board.containsKey(tempPosition);
    }

    public Optional<Piece> findPieceByPosition(final Position position) {
        return Optional.ofNullable(board.get(position));
    }

    public void validateMove(Position from, Position to) {
        Piece piece = findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));

        if (!piece.canMovePiece(from, to, this)) {
            throw new IllegalArgumentException("해당 위치로 움직일 수 없습니다.");
        }
    }

    public void movePiece(Position from, Position to) {
        Piece fromPiece = findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("움직일 기물이 존재하지 않습니다."));

        board.remove(from);
        board.put(to, fromPiece);
    }

    public boolean canOccupy(Position from, Position to) {
        Piece currentPiece = findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("움직일 기물이 존재하지 않습니다."));

        Optional<Piece> target = findPieceByPosition(to);
        if (target.isEmpty()) {
            return true;
        }

        Piece targetPiece = target.get();
        return currentPiece.isAnotherTeam(targetPiece);
    }

    public int calculateScore(Team team) {
        int totalScore = 0;
        for (Position position : board.keySet()) {
            Piece piece = findPieceByPosition(position)
                    .orElse(null);

            if (piece != null && piece.getTeam() == team) {
                totalScore += piece.getType().getScore();
            }
        }
        return totalScore;
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
