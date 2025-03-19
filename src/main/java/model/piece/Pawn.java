package model.piece;

import java.util.List;

import model.Position;
import model.Team;

public class Pawn extends Piece {

    public Pawn(int x, int y, Team team) {
        super(x, y, team);
        routes.add(new Route(List.of(new Position(-1, 0))));
        routes.add(new Route(List.of(new Position(1, 0))));
        int initY = 1;
        if (team == Team.CHO) {
            initY *= -1;
        }
        routes.add(new Route(List.of(new Position(0, initY))));
        // TODO: 지우기
        // 초나라 -> 아래로 (윗진영)
        // 한나라 -> 위로 (아랫진영)
    }


    // TODO: direction 적용
    // private final Position direction;

    // TODO: 적용하기
/*
    private enum PositionSide {
        LEFT(new Position(-1, 0)),
        RIGHT(new Position(0, 1)),
        UP(new Position(0, 1)),
        DOWN(new Position(0, -1)),
        ;

        private final Position side;

        PositionSide(Position side) {
            this.side = side;
        }
    }
*/
}
