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

    public Board(final Map<Piece, Position> positions, final Map<Position, Piece> pieces) {
        this.positions = new HashMap<>(positions);
        this.pieces = new HashMap<>(pieces);
    }

    public void move(final List<Integer> positions, final Team currentTeam) {
        final Position currentPosition = Position.from(positions.getFirst());
        final Position arrivalPosition = Position.from(positions.getLast());
        validateSamePosition(currentPosition, arrivalPosition);

        final Piece piece = findOwnPiece(currentTeam, currentPosition);
        final Path path = piece.makePath(currentPosition, arrivalPosition, pieces);
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

    private void validateSamePosition(final Position currentPosition, final Position arrivalPosition) {
        if (currentPosition.equals(arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로는 이동할 수 없습니다.");
        }
    }

    private Piece findOwnPiece(final Team currentTeam, final Position currentPosition) {
        final Piece piece = findPieceByPosition(currentPosition);
        validateOwnPiece(currentTeam, piece);
        return piece;
    }

    private Piece findPieceByPosition(final Position position) {
        if (pieces.containsKey(position)) {
            return pieces.get(position);
        }
        throw new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
    }

    private void validateOwnPiece(final Team currentTeam, final Piece piece) {
        if (!piece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물만 움직일 수 있습니다.");
        }
    }

    private void movePiece(final Piece piece, final Position currentPosition, final Position arrivalPosition, final Path path) {
        if (hasPiece(arrivalPosition)) {
            catchPiece(currentPosition, arrivalPosition, piece);
        }
        updatePosition(currentPosition, arrivalPosition, piece);
    }

    private boolean hasPiece(final Position position) {
        return pieces.containsKey(position);
    }

    private void catchPiece(final Position currentPosition, final Position arrivalPosition, final Piece piece) {
        final Piece existPiece = findPieceByPosition(arrivalPosition);
        if (existPiece.isSameTeam(piece.getTeam())) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물은 잡을 수 없습니다.");
        }
        updatePosition(currentPosition, arrivalPosition, piece);
        this.positions.remove(existPiece);
    }

    private void updatePosition(final Position currentPosition, final Position arrivalPosition, final Piece piece) {
        this.positions.put(piece, arrivalPosition);
        this.pieces.remove(currentPosition);
        this.pieces.put(arrivalPosition, piece);
    }

    private int calculateExistKing() {
        return (int) positions.keySet().stream()
                .filter(piece -> piece.matchPieceType(PieceType.KING))
                .count();
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }
}
