package domain;

import domain.strategy.NonMoveableStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Board {

    private static final double SECOND_PLAYER_BONUS_SCORE = 1.5;

    private final Map<Position, Piece> board;

    private Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public static Board of(Map<Position, Piece> board) {
        return new Board(board);
    }

    public void movePiece(Position currentPosition, Position destination) {
        Piece piece = pieceAt(currentPosition);
        board.put(destination, piece);
        board.put(currentPosition, emptyPieceAt());
    }

    public boolean canMove(Position currentPosition, Position destination) {
        Piece piece = pieceAt(currentPosition);

        if (hasSameTeamPieceAt(piece, destination)) {
            return false;
        }

        if (!piece.canMoveTo(currentPosition, destination)) {
            return false;
        }

        if (piece.isCannon()) {
            return (canCannonMove(piece, currentPosition, destination));
        }

        return piece.hasValidPathTo(currentPosition, destination, occupiedPositions());
    }

    private Piece pieceAt(Position currentPosition) {
        return board.get(currentPosition);
    }

    private static Piece emptyPieceAt() {
        return Piece.of(new PieceProperty(PieceType.EMPTY_VALUE, Team.NONE), new NonMoveableStrategy());
    }

    private boolean hasSameTeamPieceAt(Piece piece, Position destination) {
        if (piece.isRedTeam()) {
            return pieceAt(destination).isRedTeam();
        }
        if (piece.isGreenTeam()) {
            return pieceAt(destination).isGreenTeam();
        }
        return false;
    }

    private boolean canCannonMove(Piece piece, Position currentPosition, Position destination) {
        if (pieceAt(destination).isCannon()) {
            return false;
        }
        if (piece.hasValidPathTo(currentPosition, destination, cannonPositions())) {
            return false;
        }
        return piece.hasValidPathTo(currentPosition, destination, occupiedPositions());
    }

    private List<Position> cannonPositions() {
        return board.keySet().stream()
                .filter(position -> board.get(position).isCannon())
                .toList();
    }

    private List<Position> occupiedPositions() {
        return board.entrySet().stream()
                .filter(entry -> !entry.getValue().isNoneTeam())
                .map(Map.Entry::getKey)
                .toList();
    }

    public Map<Position, Piece> greenPieces() {
        return piecesByTeam(Piece::isGreenTeam);
    }

    public Map<Position, Piece> redPieces() {
        return piecesByTeam(Piece::isRedTeam);
    }

    public Map<Position, Piece> nonePieces() {
        return piecesByTeam(Piece::isNoneTeam);
    }

    private Map<Position, Piece> piecesByTeam(Predicate<Piece> predicate) {
        return board.entrySet().stream()
                .filter(entry -> predicate.test(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public boolean hasGreenTeamGeneral() {
        return hasGeneral(greenPieces());
    }

    public boolean hasRedTeamGeneral() {
        return hasGeneral(redPieces());
    }

    private boolean hasGeneral(Map<Position, Piece> pieces) {
        return pieces.values().stream()
                .anyMatch(Piece::isGeneral);
    }

    public double redPiecesScore() {
        return calculateRemainingPieceScore(redPieces()) + SECOND_PLAYER_BONUS_SCORE;
    }

    public double greenPiecesScore() {
        return calculateRemainingPieceScore(greenPieces());
    }

    private double calculateRemainingPieceScore(Map<Position, Piece> pieces) {
        return pieces.values()
                .stream()
                .mapToInt(Piece::score)
                .sum();
    }

    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }

}
