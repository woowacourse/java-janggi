package janggi.board;

import janggi.Team.Team;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Path;
import janggi.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void move(final List<Position> positions, final Team currentTeam) {
        Position currentPosition = positions.getFirst();
        Position arrivalPosition = positions.getLast();
        validateSamePosition(currentPosition, arrivalPosition);

        Piece piece = findOwnPiece(currentTeam, currentPosition);
        Path path = piece.makePath(currentPosition, arrivalPosition);

        piece.validateExistPieceInPath(getPiecesByPath(path), hasPiece(arrivalPosition));
        movePiece(piece, currentPosition, arrivalPosition, path);
    }

    public boolean hasEachKing() {
        return calculateExistKing() == 2;
    }

    public Team findWinningTeam() {
        if (calculateExistKing() != 1) {
            throw new IllegalStateException("[ERROR] 궁이 하나가 아니라면 접근할 수 없습니다.");
        }

        return pieces.values().stream()
                .filter(piece -> piece.matchPieceType(PieceType.GUNG))
                .map(Piece::getTeam)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 궁이 존재하지 않을 수 없습니다."));
    }

    private void validateSamePosition(Position currentPosition, Position arrivalPosition) {
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
        if (currentTeam != piece.getTeam()) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물만 움직일 수 있습니다.");
        }
    }

    private List<Piece> getPiecesByPath(final Path path) {
        return path.getPositions().stream()
                .filter(pieces::containsKey)
                .map(pieces::get)
                .toList();
    }

    private boolean hasPiece(final Position position) {
        return pieces.containsKey(position);
    }

    private void movePiece(Piece piece, Position currentPosition, Position arrivalPosition, Path path) {
        if (hasPiece(arrivalPosition)) {
            catchPiece(currentPosition, arrivalPosition, piece);
            return;
        }
        updatePosition(currentPosition, arrivalPosition, piece);
    }

    private void updatePosition(Position currentPosition, Position arrivalPosition, Piece piece) {
        this.pieces.remove(currentPosition);
        this.pieces.put(arrivalPosition, piece);
    }

    private void catchPiece(Position currentPosition, Position arrivalPosition, Piece piece) {
        Piece existPiece = findPieceByPosition(arrivalPosition);
        if (existPiece.getTeam() == piece.getTeam()) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물은 잡을 수 없습니다.");
        }
        updatePosition(currentPosition, arrivalPosition, piece);
    }

    private int calculateExistKing() {
        return (int) pieces.values().stream()
                .filter(piece -> piece.matchPieceType(PieceType.GUNG))
                .count();
    }

    public Map<Position, Piece> getPieces() {
        return new HashMap<>(pieces);
    }
}
