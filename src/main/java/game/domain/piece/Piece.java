package game.domain.piece;

import game.domain.board.BoardLocation;
import game.domain.board.PieceExtractor;
import game.domain.board.PieceFinder;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public void validateMovable(BoardLocation current, BoardLocation destination, PieceExtractor pieceExtractor, PieceFinder pieceFinder) {
        validateArrival(current, destination);
        List<BoardLocation> allPath = createAllPath(current, destination);
        List<Piece> pathPiece = pieceExtractor.extract(allPath);
        validateMovePath(pathPiece);
        pieceFinder.findByLocation(destination)
                .ifPresent(this::validateKillable);
    }

    public abstract void validateArrival(BoardLocation current, BoardLocation target);

    public abstract List<BoardLocation> createAllPath(BoardLocation current, BoardLocation target);

    public abstract void validateMovePath(List<Piece> pathPiece);

    protected abstract void validateKillable(Piece destinationPiece);

    public abstract PieceType getType();

    public final void validateEqualTeam(Team team){
        if (this.isEqualTeam(team)) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 자신의 팀 기물만 움직일 수 있습니다");
    };

    public final void validateOccupiable(Piece destinationPiece) {
        if (destinationPiece.isNull()) {
            return;
        }
        validateKillable(destinationPiece);
    }

    public final boolean isSameType(Piece piece) {
        return Objects.equals(this.getType(), piece.getType());
    }

    public final boolean isEqualTeam(Team team) {
        return this.team == team;
    }

    public final boolean isEqualTeam(Piece piece) {
        return this.team == piece.team;
    }

    public final Team getTeam() {
        return this.team;
    }

    public boolean isNull() {
        return false;
    }
}
