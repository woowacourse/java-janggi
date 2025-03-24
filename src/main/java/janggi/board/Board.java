package janggi.board;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Path;
import janggi.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int TOTAL_KING_COUNT = 2;

    private final Map<Piece, Position> positions;
    private final Map<Position, Piece> pieces;

    public Board(Map<Piece, Position> positions, Map<Position, Piece> pieces) {
        this.positions = new HashMap<>(positions);
        this.pieces = new HashMap<>(pieces);
    }

    public void move(final List<Integer> positions, final Team currentTeam) {
        Position currentPosition = Position.from(positions.getFirst());
        Position arrivalPosition = Position.from(positions.getLast());
        validateSamePosition(currentPosition, arrivalPosition);

        Piece piece = findOwnPiece(currentTeam, currentPosition);
        Path path = piece.makePath(currentPosition, arrivalPosition, pieces);
        movePiece(piece, currentPosition, arrivalPosition, path);
    }

    public boolean canContinue() {
        return calculateExistKing() == TOTAL_KING_COUNT;
    }

    public Team findWinningTeam() {
        if (calculateExistKing() != 1) {
            throw new IllegalStateException("[ERROR] 왕이 하나가 아니라면 접근할 수 없습니다.");
        }

        return positions.keySet().stream()
                .filter(piece -> piece.matchPieceType(PieceType.KING))
                .map(Piece::getTeam)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 왕이 존재하지 않을 수 없습니다."));
    }

    private void validateSamePosition(Position currentPosition, Position arrivalPosition) {
        if (currentPosition.equals(arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로는 이동할 수 없습니다.");
        }
    }

    private Piece findOwnPiece(Team currentTeam, Position currentPosition) {
        Piece piece = findPieceByPosition(currentPosition);
        validateOwnPiece(currentTeam, piece);
        return piece;
    }

    private Piece findPieceByPosition(final Position position) {
        if (pieces.containsKey(position)) {
            return pieces.get(position);
        }
        throw new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
    }

    private void validateOwnPiece(Team currentTeam, Piece piece) {
        if (!piece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물만 움직일 수 있습니다.");
        }
    }

    private void movePiece(Piece piece, Position currentPosition, Position arrivalPosition, Path path) {
        if (hasPiece(arrivalPosition)) {
            catchPiece(currentPosition, arrivalPosition, piece);
        }
        updatePosition(currentPosition, arrivalPosition, piece);
    }

    private boolean hasPiece(final Position position) {
        return pieces.containsKey(position);
    }

    private void catchPiece(Position currentPosition, Position arrivalPosition, Piece piece) {
        Piece existPiece = findPieceByPosition(arrivalPosition);
        if (existPiece.isSameTeam(piece.getTeam())) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물은 잡을 수 없습니다.");
        }
        updatePosition(currentPosition, arrivalPosition, piece);
        this.positions.remove(existPiece);
    }

    private void updatePosition(Position currentPosition, Position arrivalPosition, Piece piece) {
        this.positions.put(piece, arrivalPosition);
        this.pieces.remove(currentPosition);
        this.pieces.put(arrivalPosition, piece);
    }

    private int calculateExistKing() {
        return (int) positions.keySet().stream()
                .filter(piece -> piece.matchPieceType(PieceType.KING))
                .count();
    }

    public Map<Piece, Position> getPositions() {
        return positions;
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }
}
