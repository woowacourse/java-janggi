import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Cannon extends Piece {
    private static final String NAME = "포";

    public Cannon(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        List<Dot> route = new ArrayList<>();

        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        if (dx != 0 && dy != 0) {
            throw new UnsupportedOperationException("[ERROR] 포가 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }

        if (dx == 0) {
            if (dy > 0) {
                for (int i = 1; i < dy; i++) {
                    route.add(Dot.of(origin.getX(), origin.getY() + i));
                }
            }

            for (int i = -1; i > dy; i--) {
                route.add(Dot.of(origin.getX(), origin.getY() + i));
            }
        }

        if (dy == 0) {
            if (dx > 0) {
                for (int i = 1; i < dx; i++) {
                    route.add(Dot.of(origin.getX() + i, origin.getY()));
                }
            }

            for (int i = -1; i > dx; i--) {
                route.add(Dot.of(origin.getX() + i, origin.getY()));
            }
        }

        return route;
    }

    @Override
    public boolean canMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        if (isSameDynasty(destinationPiece)) {
            throw new UnsupportedOperationException("[ERROR] 같은 나라의 말은 공격할 수 없습니다.");
        }

        if (destinationPiece != null && destinationPiece.getName().equals(NAME)) {
            throw new UnsupportedOperationException("[ERROR] 포는 포를 공격할 수 없습니다.");
        }

        List<Piece> pieces = routesWithPiece.values()
                .stream()
                .filter(Objects::nonNull)
                .toList();

        if (pieces.size() != 1) {
            throw new UnsupportedOperationException("[ERROR] 포는 경로에 단 한개의 기물만 존재해야 합니다.");
        }

        if (pieces.getFirst().getName().equals(NAME)) {
            throw new UnsupportedOperationException("[ERROR] 포끼리 뛰어 넘을 수 없습니다.");
        }
        return true;
    }


    @Override
    public String getName() {
        return NAME;
    }
}
