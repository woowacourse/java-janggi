package domain.board;

import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.policy.MovePolicy;
import domain.policy.MovePolicyRegistry;

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

    public boolean isEmpty(Position position) {
        return getPieceBy(position).isNeutral();
    }

    public boolean isOpponentSide(Position position, Side turn) {
        return !getPieceBy(position).isSameSide(turn);
    }

    public Piece getPieceBy(Position position) {
        return board.get(position);
    }

    public void movePiece(Position start, Position dest) {
        Position.of(start.col(), start.row());
        Position.of(dest.col(), dest.row());
        board.put(dest, board.get(start));
        board.put(start, EmptyPiece.getInstance());
    }

    public List<Position> calculatePossibleMoves(Position start) {
        Piece piece = getPieceBy(start);
        List<Position> totalPossibleMoves = new ArrayList<>();

        for (List<Direction> path : piece.getPotentialPaths(start)) {
            totalPossibleMoves.addAll(getLegalMovesInPath(start, piece.getType(), path));
        }

        return totalPossibleMoves;
    }

    private List<Position> getLegalMovesInPath(Position start, PieceType type, List<Direction> path) {
        List<MovePolicy> policies = movePolicyRegistry.findBy(type);
        return policies.stream()
                .map(policy -> policy.apply(this, start, path))
                .reduce(this::calculateIntersection)
                .orElse(List.of());
    }

    private List<Position> calculateIntersection(List<Position> a, List<Position> b) {
        List<Position> intersection = new ArrayList<>(a);
        intersection.retainAll(b);
        return intersection;
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
