package domain.board;

import domain.vo.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Palace chuPalace;
    private final Palace hanPalace;
    private final Map<Position, Piece> board;

    private Board(final Map<Position, Piece> board) {
        this.chuPalace = Palace.of(Team.CHU);
        this.hanPalace = Palace.of(Team.HAN);
        this.board = board;
    }

    public static Board of(final Map<Position, Piece> board) {
        return new Board(board);
    }

    public Optional<Piece> tryToMove(final Position from, final Position to) {
        Piece fromPiece = findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));

        if (!fromPiece.canMovePiece(from, to, Board.of(new HashMap<>(this.board)))) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 움직일 수 없습니다.");
        }

        return movePiece(from, to, fromPiece);
    }

    public boolean isExistPosition(final Position tempPosition) {
        return board.containsKey(tempPosition);
    }

    public Optional<Piece> findPieceByPosition(final Position position) {
        return Optional.ofNullable(board.get(position));
    }

    public boolean canOccupy(Position from, Position to) {
        Piece currentPiece = findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 움직일 기물이 존재하지 않습니다."));

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

    public Palace getPalace(Team team) {
        if (team == Team.CHU) return chuPalace;
        return hanPalace;
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }

    private Optional<Piece> movePiece(Position from, Position to, Piece fromPiece) {
        Optional<Piece> capturedPiece = findPieceByPosition(to);

        board.remove(from);
        board.put(to, fromPiece);
        return capturedPiece;
    }
}
