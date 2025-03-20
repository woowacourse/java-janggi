package piece;

import game.Board;
import game.Dot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Chariot extends Piece {
    private static final String NAME = "차";

    public Chariot(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        List<Dot> route = new ArrayList<>();

        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        if (dx != 0 && dy != 0) {
            throw new UnsupportedOperationException("[ERROR] 차가 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }

        if (dx == 0) {
            if (dy > 0) {
                for (int i = 1; i < dy; i++) {
                    route.add(Board.findBy(origin.getX(), origin.getY() + i));
                }
            }

            for (int i = -1; i > dy; i--) {
                route.add(Board.findBy(origin.getX(), origin.getY() + i));
            }
        }

        if (dy == 0) {
            if (dx > 0) {
                for (int i = 1; i < dx; i++) {
                    route.add(Board.findBy(origin.getX() + i, origin.getY()));
                }
            }

            for (int i = -1; i > dx; i--) {
                route.add(Board.findBy(origin.getX() + i, origin.getY()));
            }
        }

        return route;
    }

    @Override
    public boolean canMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        if (isSameDynasty(destinationPiece)) {
            throw new UnsupportedOperationException("[ERROR] 같은 나라의 말은 공격할 수 없습니다.");
        }

        return routesWithPiece.values()
                .stream()
                .noneMatch(Objects::nonNull);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
