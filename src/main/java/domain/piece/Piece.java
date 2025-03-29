package domain.piece;

import domain.board.BoardLocation;
import domain.board.PieceExtractor;
import domain.board.PieceFinder;
import java.util.List;

public abstract class Piece {

    protected final Team team;
    protected final Score score;

    public Piece(Team team, Score score) {
        this.team = team;
        this.score = score;
    }

    public void validateMovable(
            BoardLocation current,
            BoardLocation destination,
            PieceExtractor pieceExtractor,
            PieceFinder pieceFinder
    ) {
        validateArrival(current, destination);
        List<BoardLocation> allPath = createAllPath(current, destination);
        List<Piece> pathPiece = pieceExtractor.extract(allPath);
        validateMovePath(pathPiece);
        pieceFinder.findByLocation(destination)
                .ifPresent(this::validateKillable);
    }

    protected abstract void validateArrival(BoardLocation current, BoardLocation target);

    protected abstract List<BoardLocation> createAllPath(BoardLocation current, BoardLocation target);

    protected abstract void validateMovePath(List<Piece> pathPiece);

    public abstract PieceType getType();

    protected void validateKillable(Piece destinationPiece) {
        if (isEqualTeam(destinationPiece)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목적지로 이동할 수 없습니다.");
        }
    }

    public final void validateEqualTeam(Team team) {
        if (isNotEqualTeam(team)) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물만 움직일 수 있습니다");
        }
    }

    public final Team getTeam() {
        return this.team;
    }

    public Score getScore() {
        return score;
    }

    private boolean isNotEqualTeam(Team team) {
        return this.team != team;
    }

    private boolean isEqualTeam(Piece piece) {
        return this.team == piece.team;
    }

    public boolean isStoppedGameIfDie() {
        return false;
    }
}
