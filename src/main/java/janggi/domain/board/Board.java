package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Path;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void move(final List<Position> positions, final TeamType currentTeamType) {
        Position currentPosition = positions.getFirst();
        Position arrivalPosition = positions.getLast();
        validateSamePosition(currentPosition, arrivalPosition);

        Piece piece = findOwnPiece(currentTeamType, currentPosition);
        Path path = piece.makePath(currentPosition, arrivalPosition);

        piece.validateExistPieceInPath(getPiecesByPath(path), hasPiece(arrivalPosition));
        movePiece(piece, currentPosition, arrivalPosition, path);
    }

    public boolean hasEachKing() {
        return calculateExistKing() == 2;
    }

    public TeamType findWinningTeam() {
        if (calculateExistKing() != 1) {
            throw new IllegalStateException("[ERROR] 궁이 하나가 아니라면 접근할 수 없습니다.");
        }

        return pieces.values().stream()
                .filter(piece -> piece.matchPieceType(PieceType.GUNG))
                .map(Piece::getTeamType)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 궁이 존재하지 않을 수 없습니다."));
    }

    public int calculateCurrentScoreByTeam(TeamType teamType) {
        return pieces.values()
                .stream()
                .filter(piece -> piece.getTeamType().equals(teamType))
                .mapToInt(Piece::getScore)
                .sum();
    }

    private void validateSamePosition(Position currentPosition, Position arrivalPosition) {
        if (currentPosition.equals(arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로는 이동할 수 없습니다.");
        }
    }

    private Piece findOwnPiece(final TeamType currentTeamType, final Position currentPosition) {
        final Piece piece = findPieceByPosition(currentPosition);
        validateOwnPiece(currentTeamType, piece);
        return piece;
    }

    private Piece findPieceByPosition(final Position position) {
        if (pieces.containsKey(position)) {
            return pieces.get(position);
        }
        throw new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
    }

    private void validateOwnPiece(final TeamType currentTeamType, final Piece piece) {
        if (currentTeamType != piece.getTeamType()) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물만 움직일 수 있습니다.");
        }
    }

    private List<Piece> getPiecesByPath(final Path path) {
        return pieces.entrySet().stream()
                .filter(entry -> path.hasPosition(entry.getKey()))
                .map(Entry::getValue)
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
        if (existPiece.getTeamType() == piece.getTeamType()) {
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
