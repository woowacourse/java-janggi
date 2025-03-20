package janggi.board;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void move(final List<Integer> positions, final Team currentTeam) {
        Position currentPosition = Position.from(positions.getFirst());
        Position arrivalPosition = Position.from(positions.getLast());
        validateSamePosition(currentPosition, arrivalPosition);

        Piece piece = findOwnPiece(currentTeam, currentPosition);
        Path path = piece.makePath(currentPosition, arrivalPosition);
        movePiece(piece, currentPosition, arrivalPosition, path);
    }

    public boolean canContinue() {
        return calculateExistKing() == 2;
    }

    public Team findWinningTeam() {
        if (calculateExistKing() != 1) {
            throw new IllegalStateException("[ERROR] 왕이 하나가 아니라면 접근할 수 없습니다.");
        }

        return pieces.values().stream()
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
        if (currentTeam != piece.getTeam()) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물만 움직일 수 있습니다.");
        }
    }

    private void movePiece(Piece piece, Position currentPosition, Position arrivalPosition, Path path) {
        if (piece.matchPieceType(PieceType.CANNON)) {
            moveCannon(currentPosition, arrivalPosition, piece, path);
            return;
        }
        moveOther(currentPosition, arrivalPosition, piece, path);
    }

    private void moveCannon(Position currentPosition, Position arrivalPosition, Piece piece, Path path) {
        if (computeCountExistPieceExceptLast(path) != 1) {
            throw new IllegalArgumentException("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
        }
        if (hasCannon(path)) {
            throw new IllegalArgumentException("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
        }
        if (hasPiece(arrivalPosition)) {
            catchPiece(currentPosition, arrivalPosition, piece);
        }
        updatePosition(currentPosition, arrivalPosition, piece);
    }

    private void moveOther(Position currentPosition, Position arrivalPosition, Piece piece, Path path) {
        if (doNotContain(path)) {
            updatePosition(currentPosition, arrivalPosition, piece);
            return;
        }
        if (!hasPiece(arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
        catchPiece(currentPosition, arrivalPosition, piece);
    }

    private int computeCountExistPieceExceptLast(Path path) {
        List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();

        return (int) positions.stream()
                .filter(pieces::containsKey)
                .count();
    }

    private boolean hasCannon(final Path path) {
        return path.getPositions().stream()
                .filter(pieces::containsKey)
                .map(pieces::get)
                .anyMatch(piece -> piece.matchPieceType(PieceType.CANNON));
    }

    private boolean hasPiece(final Position position) {
        return pieces.containsKey(position);
    }

    private void catchPiece(Position currentPosition, Position arrivalPosition, Piece piece) {
        Piece existPiece = findPieceByPosition(arrivalPosition);
        if (existPiece.getTeam() == piece.getTeam()) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물은 잡을 수 없습니다.");
        }
        updatePosition(currentPosition, arrivalPosition, piece);
    }

    private void updatePosition(Position currentPosition, Position arrivalPosition, Piece piece) {
        this.pieces.remove(currentPosition);
        this.pieces.put(arrivalPosition, piece);
    }

    private boolean doNotContain(Path path) {
        return path.getPositions().stream()
                .noneMatch(pieces::containsKey);
    }

    private int calculateExistKing() {
        return (int) pieces.values().stream()
                .filter(piece -> piece.matchPieceType(PieceType.KING))
                .count();
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }
}
