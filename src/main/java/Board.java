import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Space> spaces;

    public Board() {
        this.spaces = initialize();
    }

    private List<Space> initialize() {
        List<Space> spaces = new ArrayList<>();
        for (int x = LocationX.LOCATION_X_MIN_VALUE; x < LocationX.LOCATION_X_MAX_VALUE; x++) {
            for (int y = LocationY.LOCATION_Y_MIN_VALUE; y < LocationY.LOCATION_Y_MAX_VALUE; y++) {
                spaces.add(new Space(LocationX.getInstance(x), LocationY.getInstance(y)));
            }
        }
        return spaces;
    }

/*    - [ ] 차는 (0, 0), (8, 0)에 위치한다
      - [ ] 상은 (1, 0), (7, 0)에 위치한다
      - [ ] 마는 (2, 0), (6, 0)에 위치한다
      - [ ] 사는 (3, 0)과 (5, 0)에 위치한다
      - [ ] 장은 (1, 4)에 위치한다
      - [ ] 포는 (2, 1), (2, 7)에 위치한다
      - [ ] 병은 (3, 0), (3, 2), (3, 4), (3, 6), (3, 8)에 위치한다*/
}
