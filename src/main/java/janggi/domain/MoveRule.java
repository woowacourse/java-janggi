package janggi.domain;

import java.util.List;

public class MoveRule {

    public List<Route> findRoute(Piece piece) {

        if(piece.getPieceType() == PieceType.ZOL) {
            if(piece.getTeamName().equals(Team.CHO.getName())) {
                Route route1 = new Route(List.of(Direction.UP));
                Route route2 = new Route(List.of(Direction.LEFT));
                Route route3 = new Route(List.of(Direction.RIGHT));
                return List.of(route1, route2, route3);
            }
            Route route1 = new Route(List.of(Direction.DOWN));
            Route route2 = new Route(List.of(Direction.LEFT));
            Route route3 = new Route(List.of(Direction.RIGHT));
            return List.of(route1, route2, route3);
        }

        // 마일때 경로를 반환
        if(piece.getPieceType() == PieceType.MA){
            Route route1 = new Route(List.of(Direction.UP, Direction.UP_LEFT));
            Route route2 = new Route( List.of(Direction.UP, Direction.UP_RIGHT));
            Route route3 = new Route(List.of(Direction.RIGHT, Direction.UP_RIGHT));
            Route route4 = new Route(List.of(Direction.RIGHT, Direction.DOWN_RIGHT));
            Route route5 = new Route(List.of(Direction.DOWN, Direction.DOWN_RIGHT));
            Route route6 = new Route( List.of(Direction.DOWN, Direction.DOWN_LEFT));
            Route route7 = new Route( List.of(Direction.LEFT, Direction.DOWN_LEFT));
            Route route8 = new Route( List.of(Direction.LEFT, Direction.UP_LEFT));
            return List.of(route1, route2, route3, route4, route5, route6, route7, route8);
        }

        if(piece.getPieceType() == PieceType.SANG){
            Route route1 = new Route(List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT));
            Route route2 = new Route( List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT));
            Route route3 = new Route(List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT));
            Route route4 = new Route(List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));
            Route route5 = new Route(List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));
            Route route6 = new Route( List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT));
            Route route7 = new Route( List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT));
            Route route8 = new Route( List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT));
            return List.of(route1, route2, route3, route4, route5, route6, route7, route8);
        }
        if(piece.getPieceType()==PieceType.KING){
            Route route1 = new Route(List.of(Direction.UP));
            Route route2 = new Route(List.of(Direction.RIGHT));
            Route route3 = new Route(List.of(Direction.DOWN));
            Route route4 = new Route(List.of(Direction.LEFT));
            Route route5 = new Route(List.of(Direction.UP_LEFT));
            Route route6 = new Route(List.of(Direction.UP_RIGHT));
            Route route7 = new Route(List.of(Direction.DOWN_LEFT));
            Route route8 = new Route(List.of(Direction.DOWN_RIGHT));
            return List.of(route1, route2, route3, route4, route5, route6, route7, route8);
        }

        if(piece.getPieceType()==PieceType.SA){
            Route route1 = new Route(List.of(Direction.UP));
            Route route2 = new Route(List.of(Direction.RIGHT));
            Route route3 = new Route(List.of(Direction.DOWN));
            Route route4 = new Route(List.of(Direction.LEFT));
            Route route5 = new Route(List.of(Direction.UP_LEFT));
            Route route6 = new Route(List.of(Direction.UP_RIGHT));
            Route route7 = new Route(List.of(Direction.DOWN_LEFT));
            Route route8 = new Route(List.of(Direction.DOWN_RIGHT));
            return List.of(route1, route2, route3, route4, route5, route6, route7, route8);
        }

        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        return List.of(route1, route2, route3, route4);
    }


}
