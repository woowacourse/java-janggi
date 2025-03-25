import java.awt.Point;
import java.util.Map;
import java.util.Set;
import model.Chariot;
import model.Color;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    void 차가_움직일_수_있는_위치들을_반환한다() {
        Chariot chariot = new Chariot(Color.RED);
        Point point = new Point(4, 4);

        Map<Point, Color> existBoardPieces = Map.of(
                new Point(4, 5), Color.RED
        );
        Set<Point> points = chariot.calculateMovePath(point, existBoardPieces);

        Assertions.assertThat(points).hasSize(11);
    }
}
