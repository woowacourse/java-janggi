package domain.piece;

import domain.Moves;
import domain.Position;
import domain.Team;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final Team team;
    private Position position;

    public Piece(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    public abstract List<Moves> getMoveOptions(Position startPosition, Position targetPosition);

    public List<Position> calculatePath(Position src, Position dest) {
        Moves possibleMoves = getMoveOptions(src, dest).stream()
                .filter(moves -> moves.isPossibleToArrive(src, dest))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이 위치로 이동할 수 없습니다."));

        return possibleMoves.convertToPath(src);
    }

    public abstract int getScore();

    public boolean isSamePosition(Position position) {
        return this.position.equals(position);
    }

    public void moveTo(Position position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isTeam(Piece otherPiece) {
        return isTeam(otherPiece.team);
    }

    public boolean isTeam(Team team) {
        return this.team == team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return team == piece.team && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
