package janggi.piece;

import janggi.Team;
import java.util.Arrays;
import java.util.List;

import janggi.board.Position;

public class Pawn extends Piece {

    public Pawn(int x, int y, Team team) {
        super(x, y, team);
        routes.add(new Route(List.of(new Position(-1, 0))));
        routes.add(new Route(List.of(new Position(1, 0))));
        routes.add(PositionSide.getRouteFor(team, new Position(0, 1)));
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }

    private enum PositionSide {
        UP(Team.CHO, new Position(0, -1)),
        DOWN(Team.HAN, new Position(0, 1)),
        ;

        private final Team team;
        private final Position side;

        PositionSide(Team team, Position side) {
            this.team = team;
            this.side = side;
        }

        public static Route getRouteFor(Team team, Position position) {
            PositionSide matched = Arrays.stream(values())
                .filter(positionSide -> positionSide.team == team)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 잘못된 팀 정보입니다."));
            return new Route(List.of(position.multiply(matched.side)));
        }
    }
}
