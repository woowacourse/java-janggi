package model.piece.movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import model.Position;
import model.Team;
import model.board.BoardSearcher;
import model.piece.Piece;

public class PalaceMovement implements Movement {

    private static final Position CENTER_POSITION = new Position(4, 1);

    private final boolean canMoveOutOfPalace;
    private final boolean canMoveStraight;

    public PalaceMovement(boolean canMoveOutOfPalace, boolean canMoveStraight) {
        this.canMoveOutOfPalace = canMoveOutOfPalace;
        this.canMoveStraight = canMoveStraight;
    }

    public static Position initialPositionForTeam(Team team) {
        return new Position(team.onBaseX(CENTER_POSITION.x()), team.onBaseY(CENTER_POSITION.y()));
    }

    @Override
    public void validateNormalRoute(Team team, Position target) {
        if (!canMoveOutOfPalace && !isInPalace(team, target)) {
            throw new IllegalArgumentException("[ERROR] 궁 밖으로 나갈 수 없는 기물입니다.");
        }
    }

    @Nullable
    public static Team getTeamOfPalace(Position position) {
        return Arrays.stream(Team.values())
            .filter(team -> isInPalace(team, position))
            .findAny()
            .orElse(null);
    }

    public static boolean isInPalace(Team team, Position position) {
        Position center = initialPositionForTeam(team);
        return position.x() >= center.x() - 1
            && position.x() <= center.x() + 1
            && position.y() >= center.y() - 1
            && position.y() <= center.y() + 1;
    }

    public boolean isOnVertexOfPalace(Team team, Position position) {
        Position center = initialPositionForTeam(team);
        return position.equals(center.move(new Position(-1, -1)))
            || position.equals(center.move(new Position(-1, 1)))
            || position.equals(center.move(new Position(1, 1)))
            || position.equals(center.move(new Position(1, -1)));
    }

    @Override
    public Piece.Route findMovableRoute(BoardSearcher boardSearcher, List<Piece.Route> routes, Team team,
        Position position, Position difference) {
        Position target = position.move(difference);
        if (getDiagonalLines().contains(new Line(position, target))) {
            return new Piece.Route(List.of(target.difference(position).toDirection()));
        }
        if (getDiagonalLines().contains(new Line(target, position))) {
            return new Piece.Route(List.of(target.difference(position).toDirection()));
        }
        return null;
    }

    private List<Line> getDiagonalLines() {
        return Arrays.stream(Team.values())
            .map(team -> {
                Position center = initialPositionForTeam(team);
                List<Line> lines = new ArrayList<>();
                if (canMoveStraight) {
                    lines.add(new Line(center.move(new Position(-1, -1)), center.move(new Position(1, 1))));
                    lines.add(new Line(center.move(new Position(-1, 1)), center.move(new Position(1, -1))));
                }
                lines.add(new Line(center, center.move(new Position(-1, -1))));
                lines.add(new Line(center, center.move(new Position(-1, 1))));
                lines.add(new Line(center, center.move(new Position(1, 1))));
                lines.add(new Line(center, center.move(new Position(1, -1))));
                return lines;
            })
            .flatMap(List::stream)
            .toList();
    }

    private record Line(
        Position position1,
        Position position2
    ) {

    }
}
