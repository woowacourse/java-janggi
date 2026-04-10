package domain.board;

import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.policy.MovePolicyRegistry;
import domain.state.Side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;
    private final MovePolicyRegistry movePolicyRegistry;

    public Board(Map<Position, Piece> initializeBoard) {
        this.board = new HashMap<>(initializeBoard);
        this.movePolicyRegistry = new MovePolicyRegistry();
    }

    public Piece getPieceBy(Position position) {
        return board.get(position);
    }

    public boolean isEmpty(Position position) {
        return getPieceBy(position).isNeutral();
    }

    public boolean isOpponentSide(Position position, Side turn) {
        return !getPieceBy(position).isSameSide(turn);
    }

    public boolean hasKing(Side side) {
        for (Map.Entry<Position, Piece> positionPieceEntry : board.entrySet()) {
            Piece piece = positionPieceEntry.getValue();
            if (piece.isSameSide(side) && piece.getType() == PieceType.KING) {
                return true;
            }
        }

        return false;
    }

    public void movePiece(Position start, Position dest) {
        Position.of(start.col(), start.row());
        Position.of(dest.col(), dest.row());
        board.put(dest, board.get(start));
        board.put(start, EmptyPiece.getInstance());
    }

    public List<Position> calculateLegalMoves(Position start) {
        List<Position> possibleMoves = calculatePossibleMoves(start);
        List<Position> legalMoves = new ArrayList<>();
        Piece piece = getPieceBy(start);

        for (Position dest : possibleMoves) {
            Board simulated = simulateMove(start, dest);

            if (simulated.isSafe(piece.getSide())) {
                legalMoves.add(dest);
            }
        }

        return legalMoves;
    }

    public List<Position> calculatePossibleMoves(Position start) {
        Piece piece = getPieceBy(start);
        List<Position> totalPossibleMoves = new ArrayList<>();

        for (List<Direction> path : piece.getPotentialPaths(start)) {
            totalPossibleMoves.addAll(getLegalMovesInPath(start, piece.getType(), path));
        }

        return totalPossibleMoves;
    }

    public boolean isSafe(Side side) {
        Position kingPos = findKingPosition(side);

        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            Piece piece = entry.getValue();

            if (piece.isSameSide(side.opposite())) {
                List<Position> moves = calculatePossibleMoves(entry.getKey());
                if (moves.contains(kingPos)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isCheckmate(Side side) {
        if (isSafe(side)) {
            return false;
        }

        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            Piece piece = entry.getValue();

            if (!piece.isSameSide(side)) continue;

            Position from = entry.getKey();
            List<Position> moves = calculatePossibleMoves(from);

            for (Position to : moves) {
                Board simulated = simulateMove(from, to);
                if (simulated.isSafe(side)) {
                    return false;
                }
            }
        }

        return true;
    }

    public double calculateScore(Side side) {
        int totalScore = 0;

        for (Map.Entry<Position, Piece> positionPieceEntry : board.entrySet()) {
            Piece piece = positionPieceEntry.getValue();
            if (piece.isSameSide(side)) {
                totalScore += piece.getScore();
            }
        }

        return totalScore;
    }

    private List<Position> getLegalMovesInPath(Position start, PieceType type, List<Direction> path) {
        return movePolicyRegistry.findBy(type).stream()
                .map(policy -> policy.apply(this, start, path))
                .reduce(this::calculateIntersection)
                .orElse(List.of());
    }

    private List<Position> calculateIntersection(List<Position> a, List<Position> b) {
        List<Position> intersection = new ArrayList<>(a);
        intersection.retainAll(b);
        return intersection;
    }

    private Position findKingPosition(Side side) {
        return board.entrySet().stream()
                .filter(entry -> entry.getValue().isSameSide(side)
                        && entry.getValue().getType() == PieceType.KING)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
    }

    private Board simulateMove(Position from, Position to) {
        Map<Position, Piece> copied = new HashMap<>(board);

        copied.put(to, copied.get(from));
        copied.put(from, EmptyPiece.getInstance());
        return new Board(copied);
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
