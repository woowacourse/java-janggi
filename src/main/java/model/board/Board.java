package model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.move.Move;
import model.pieces.Piece;
import model.position.Position;

public class Board {
    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 10;
    private static final int MIN_COL = 1;
    private static final int MAX_COL = 9;
    private final Map<Position, Piece> board;

    public Board() {
        this.board = new HashMap<>();
    }

    public boolean isInside(Position position) {
        return position.row().value() >= MIN_ROW &&
                position.row().value() <= MAX_ROW &&
                position.column().value() >= MIN_COL &&
                position.column().value() <= MAX_COL;
    }

    public void place(Position position, Piece piece) {
        board.put(position, piece);
    }

    public void remove(Position position) {
        board.remove(position);
    }

    public Piece move(Move move) {
        Piece piece = findPiece(move.from());
        validatePieceExists(piece);

        if (!piece.canMove(move, this)) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다.");
        }

        Piece capturedPiece = findPiece(move.to());
        executeMove(move, piece);
        return capturedPiece;
    }

    public boolean isPieceAt(Position position, Piece piece) {
        return piece.equals(board.get(position));
    }

    public Piece findPiece(Position position) {
        return board.get(position);
    }

    public boolean isPathEmpty(Position position) {
        return findPiece(position) == null;
    }

    public double calculateScore(Country country) {
        double totalScore = country.bonusScore();
        for (Piece piece : board.values()) {
            if (piece.country() != country) {
                continue;
            }
            totalScore += piece.score();
        }
        return totalScore;
    }

    public List<Piece> findBetweenPieces(Move move) {
        List<Piece> pieces = new ArrayList<>();
        Position from = move.from();
        Position to = move.to();

        Position current = from.move(move.direction());

        while (!current.isSamePosition(to)) {
            current = getPosition(move, current, pieces);
        }
        return List.copyOf(pieces);
    }

    public List<PlacedPiece> placedPieces() {
        List<PlacedPiece> placedPieces = new ArrayList<>();

        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            placedPieces.add(new PlacedPiece(entry.getKey(), entry.getValue()));
        }

        return List.copyOf(placedPieces);
    }

    private static void validatePieceExists(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("[ERROR] 기물이 없습니다.");
        }
    }

    private void executeMove(Move move, Piece piece) {
        place(move.to(), piece);
        remove(move.from());
    }

    private Position getPosition(Move move, Position current, List<Piece> pieces) {
        Piece piece = findPiece(current);
        if (piece != null) {
            pieces.add(piece);
        }
        current = current.move(move.direction());
        return current;
    }
}
