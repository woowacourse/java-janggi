package janggi.piece;

import janggi.position.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pieces {

    private static final int TOTAL_KING_COUNT = 2;

    private final Set<Piece> pieces;

    public Pieces(final Set<Piece> pieces) {
        this.pieces = new HashSet<>(pieces);
    }

    public void move(final List<Integer> positions, final Team currentTeam) {
        final Position currentPosition = Position.from(positions.getFirst());
        final Position arrivalPosition = Position.from(positions.getLast());

        final Piece piece = findPieceByPosition(currentPosition);
        piece.checkMovement(arrivalPosition, currentTeam, this);
        movePiece(piece, arrivalPosition);
    }

    public boolean canContinue() {
        return calculateExistKing() == TOTAL_KING_COUNT;
    }

    public Team findWinningTeam() {
        if (calculateExistKing() != 1) {
            throw new IllegalStateException("[ERROR] 왕이 하나가 아니라면 접근할 수 없습니다.");
        }

        return pieces.stream()
                .filter(piece -> piece.matchPieceType(PieceType.KING))
                .map(Piece::getTeam)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 왕이 존재하지 않을 수 없습니다."));
    }

    private void movePiece(final Piece piece, final Position arrivalPosition) {
        if (hasPiece(arrivalPosition)) {
            catchPiece(arrivalPosition, piece);
        }
        piece.updatePosition(arrivalPosition);
    }

    public boolean hasPiece(final Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(position));
    }

    public Piece findPieceByPosition(final Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 존재하지 않습니다."));
    }

    public void addAll(final Pieces givenPieces) {
        pieces.addAll(givenPieces.pieces);
    }

    private void catchPiece(final Position arrivalPosition, final Piece piece) {
        final Piece existPiece = findPieceByPosition(arrivalPosition);
        if (existPiece.isSameTeam(piece.getTeam())) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물은 잡을 수 없습니다.");
        }
        piece.updatePosition(arrivalPosition);
    }

    private int calculateExistKing() {
        return (int) pieces.stream()
                .filter(piece -> piece.matchPieceType(PieceType.KING))
                .count();
    }

    public Set<Piece> getPieces() {
        return pieces;
    }
}
