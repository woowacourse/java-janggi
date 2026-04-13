package domain.piece;

import domain.game.Team;
import domain.rule.MoveRule;
import domain.position.Position;
import java.util.List;
import java.util.function.Function;

public abstract class Piece {
    private final Team team;
    private final List<MoveRule> moveRules;

    protected Piece(Team team, List<MoveRule> moveRules) {
        this.team = team;
        this.moveRules = moveRules;
    }

    public void validateMove(Position source, Position target, Function<Position, Piece> pieceAt) {
        List<Position> route = findRoute(source, target);
        List<Piece> piecesOnRoute = route.stream().map(pieceAt).toList();
        validateRoute(piecesOnRoute);
        validateDestination(pieceAt.apply(target));
    }

    private List<Position> findRoute(Position source, Position target) {
        return moveRules.stream()
                .filter(rule -> rule.canMove(source, target))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 위치입니다."))
                .calculateRoute(source, target);
    }

    protected void validateRoute(List<Piece> piecesOnRoute) {
        if (piecesOnRoute.stream().anyMatch(Piece::isNotEmpty)) {
            throw new IllegalArgumentException("이동 경로에 기물이 있습니다.");
        }
    }

    protected void validateDestination(Piece destinationPiece) {
        if (destinationPiece.isAlly(this)) {
            throw new IllegalArgumentException("아군 기물이 있는 위치로 이동할 수 없습니다.");
        }
    }

    public boolean isNotEmpty() {
        return true;
    }

    public boolean isAlly(Piece other) {
        return this.team.isAllyWith(other.team);
    }

    public boolean belongsTo(Team team) {
        return this.team == team;
    }

    public boolean canBeTargetedByCannon() {
        return true;
    }

    public boolean canBeJumpedByCannon() {
        return true;
    }

    public boolean isEssentialForVictory() {
        return false;
    }

    public abstract double score();

    public Team getTeam() {
        return team;
    }
}
