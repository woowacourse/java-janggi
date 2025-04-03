package domain.piece;

import domain.Moves;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Piece {

    private final Team team;
    private final PieceType type;
    private Position position;

    public Piece(Team team, PieceType type, Position position) {
        this.team = team;
        this.type = type;
        this.position = position;
    }

    public List<Position> calculatePath(Position src, Position dest) {
        Moves possibleMoves = type.findPossibleMoves(src, dest, team).stream()
                .filter(moves -> moves.isPossibleToArrive(src, dest))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이 위치로 이동할 수 없습니다."));

        return possibleMoves.convertToPath(src);
    }

    public void applyRule(List<Position> path, List<Piece> piecesInPath, Optional<Piece> targetPiece) {
        type.applyRule(path, piecesInPath, this, targetPiece);
    }

    public boolean isTeam(Piece otherPiece) {
        return isTeam(otherPiece.team);
    }

    public boolean isTeam(Team team) {
        return this.team == team;
    }

    public boolean isSamePosition(Position position) {
        return this.position.equals(position);
    }

    public boolean isType(PieceType type) {
        return this.type == type;
    }

    public boolean isSameType(Piece piece) {
        return this.type == piece.type;
    }

    public void changePosition(Position position) {
        this.position = position;
    }

    public int getScore() {
        return type.getScore();
    }

    public Team getTeam() {
        return team;
    }

    public Position getPosition() {
        return position;
    }

    public PieceType getType() {
        return type;
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
        return team == piece.team && type == piece.type && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, type, position);
    }
}
