package model.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.move.Move;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;

public class Board {
    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 10;
    private static final int MIN_COL = 1;
    private static final int MAX_COL = 9;
    private static final int GENERAL_COUNT = 2;
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

    public void checkTurn(Position from, Country country) {
        findPiece(from).ifPresent(fromPiece -> {
            validatePieceExists(fromPiece);
            if (!country.myTurn(fromPiece.country())) {
                throw new IllegalArgumentException("[ERROR] 아군 기물이 아닙니다.");
            }
        });
    }

    public void move(Move move) {
        findPiece(move.from()).ifPresent(piece -> {
            if (!piece.canMove(move, this)) {
                throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다.");
            }

            executeMove(move, piece);
        });
    }

    public boolean isPieceAt(Position position, Piece piece) {
        return piece.equals(board.get(position));
    }

    public Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(board.get(position));
    }

    public int countPiecesOnPath(List<Position> path) {
        return (int) path.stream()
                .map(this::findPiece)
                .flatMap(Optional::stream)
                .count();
    }

    public boolean hasPieceTypeOnPath(List<Position> path, PieceType type) {
        return path.stream()
                .map(this::findPiece)
                .flatMap(Optional::stream)
                .anyMatch(piece -> piece.pieceType() == type);
    }

    public boolean endCondition() {
        return countGeneral();
    }

    private boolean countGeneral() {
        int generalCount = (int) board.values().stream()
                .filter(piece -> piece.pieceType() == PieceType.GENERAL).count();
        return generalCount == GENERAL_COUNT;
    }

    public Optional<Country> winnerCountry() {
        return board.values().stream()
                .filter(piece -> piece.pieceType() == PieceType.GENERAL)
                .findFirst()
                .map(Piece::country);
    }

    private void executeMove(Move move, Piece piece) {
        place(move.to(), piece);
        remove(move.from());
    }

    private static void validatePieceExists(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("[ERROR] 기물이 없습니다.");
        }
    }
}
