package domain.piece;

import domain.Path;
import domain.position.Position;
import domain.TeamType;
import java.util.List;
import java.util.Optional;

public abstract class Piece {
    protected Position position;
    protected final TeamType teamType;

    protected Piece(Position position, TeamType teamType) {
        this.position = position;
        this.teamType = teamType;
    }

    protected Piece(Piece piece) {
        this.position = piece.position;
        this.teamType = piece.teamType;
    }

    public void moveTo(Position position) {
        this.position = position;
    }

    public boolean hasSamePosition(Position position) {
        return this.position.equals(position);
    }

    public boolean isSameTeam(Piece piece) {
        return this.teamType.equals(piece.teamType);
    }

    public boolean isSameTeam(TeamType teamType) {
        return this.teamType.equals(teamType);
    }

    public boolean isSameType(PieceType pieceType) {
        return this.getType().equals(pieceType);
    }

    public boolean validateMove(Position expectedPosition, List<Piece> alivePieces) {
        Path path = findReachablePath(expectedPosition)
                .orElseThrow(() -> new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다."));

        List<Position> pathPositions = path.findPathPositionsFrom(position);
        validateMovePath(pathPositions, alivePieces);

        validateDestination(expectedPosition, alivePieces);

        return true;
    }

    private Optional<Path> findReachablePath(Position expectedPosition) {
        return this.getPaths().stream()
                .filter(path -> path.canReachFromTo(position, expectedPosition))
                .findFirst();
    }

    protected void validateMovePath(List<Position> pathPositions, List<Piece> alivePieces) {
        if (hasPieceOnPath(pathPositions, alivePieces)) {
            throw new IllegalArgumentException("다른 기물이 막고 있어 이동할 수 없습니다.");
        }
    }

    protected void validateDestination(Position expectedPosition, List<Piece> alivePieces) {
        boolean hasTeamAtPosition = alivePieces.stream()
                .anyMatch(piece -> piece.hasSamePosition(expectedPosition) && piece.isSameTeam(this));
        if (hasTeamAtPosition) {
            throw new IllegalArgumentException("도착 지점에 같은 팀의 기물이 있어 이동할 수 없습니다.");
        }
    }

    private boolean hasPieceOnPath(List<Position> positions, List<Piece> alivePieces) {
        return positions.stream()
                .anyMatch(pos -> alivePieces.stream()
                        .anyMatch(piece -> piece.hasSamePosition(pos)));
    }

    protected abstract List<Path> getPaths();

    public Position getPosition() {
        return position;
    }

    public abstract PieceType getType();

    public abstract Piece newInstance();
}
