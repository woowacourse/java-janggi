import java.util.ArrayList;
import java.util.List;

public class Chariot {
    private final Dynasty dynasty;

    public Chariot(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    //   - [ ] 차는 칸수 제한 없이 직선으로만 이동한다.

    public List<Dot> getRoute(Dot origin, Dot destination) {
        List<Dot> route = new ArrayList<>();

        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);

        if (dx == 0 && dy == 0) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동할 수 없습니다.");
        }

        if (dx == 0) {
            if(dy > 0) {
                for (int i = 1; i <= dy; i++) {
                    route.add(Dot.of(origin.getX(), origin.getY() + i));
                }
            }

            for (int i = -1; i >= dy; i--) {
                route.add(Dot.of(origin.getX(), origin.getY() + i));
            }
        }

        if (dy == 0) {
            if(dx > 0) {
                for (int i = 1; i <= dx; i++) {
                    route.add(Dot.of(origin.getX() + i, origin.getY()));
                }
            }

            for (int i = -1; i >= dx; i--) {
                route.add(Dot.of(origin.getX() + i, origin.getY()));
            }
        }
        return route;
    }


}
