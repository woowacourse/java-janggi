package janggi.piece.pawn;

import janggi.Team;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Route;
import java.util.Arrays;
import java.util.List;

import janggi.position.Position;

public abstract class Pawn extends Piece {

    public Pawn(Position position, Team team) {
        super(position, team);
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }

//    private enum PositionSide {
//        UP(Team.CHO, new Position(0, -1)),
//        DOWN(Team.HAN, new Position(0, 1)),
//        ;
//
//        private final Team team;
//        private final Position side;
//
//        PositionSide(Team team, Position side) {
//            this.team = team;
//            this.side = side;
//        }
//
//        public static Route getRouteFor(Team team, Position position) {
//            PositionSide matched = Arrays.stream(values())
//                .filter(positionSide -> positionSide.team == team)
//                .findAny()
//                .orElseThrow(() -> new IllegalStateException("[ERROR] 잘못된 팀 정보입니다."));
//            return new Route(List.of(position.multiply(matched.side)));
//        }
//    }
}
