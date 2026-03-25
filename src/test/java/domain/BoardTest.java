package domain;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.piece.General;
import domain.piece.Team;
import domain.point.Point;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void 장기판_기물_배치를_테스트한다() {
        Point point = new Point(1, 4);
        General general = new General(Team.HAN);
        Intersection actualIntersection = new Intersection(point, general);
        TestIntersectionGenerator testIntersectionGenerator = new TestIntersectionGenerator(
                Arrays.asList(actualIntersection));

        JanggiBoard janggiBoard = new JanggiBoard(testIntersectionGenerator);
        Intersection expectedIntersection = janggiBoard.getIntersection(point);

        Assertions.assertThat(actualIntersection.isSamePiece(expectedIntersection))
                .isTrue();
    }
}
