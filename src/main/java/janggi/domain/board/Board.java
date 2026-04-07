package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public Piece move(Position from, Position to) {
        Piece piece = board.get(from);
        Piece capturedPiece = board.get(to);
        if (!piece.canMove(from, to, this)) {
            throw new IllegalArgumentException("해당 기물은 이동할 수 없습니다.");
        }

        movePiece(from, to);
        return capturedPiece;
    }

    public Piece findPiece(Position position) {
        return board.get(position);
    }

    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    public Map<Position, Piece> findPiecesOn(List<Position> positions) {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        for (Position position : positions) {
            if (board.containsKey(position)) {
                positionPieces.put(position, board.get(position));
            }
        }
        return positionPieces;
    }

    public double calculateScore(Team team) {
        double score = board.values().stream()
                .filter(piece -> piece.getTeam() == team)
                .mapToDouble(Piece::score)
                .sum();

        if (team == Team.HAN) {
            return score + 1.5;
        }

        return score;
    }

    private void movePiece(Position from, Position to) {
        board.put(to, board.get(from));
        board.remove(from);
    }
}
