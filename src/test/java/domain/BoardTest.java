package domain;

import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiGenerator;
import domain.fixture.TestIntersectionGenerator;
import domain.intersection.Intersection;
import domain.piece.General;
import domain.piece.Team;
import domain.point.Point;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    public static final int DEFAULT_ELEPHANT_AND_HORSE_ROW = 0;

    @Test
    void 장기판_기물_배치를_테스트한다() {
        Point point = new Point(1, 4);
        General general = new General(Team.HAN);
        Intersection actualIntersection = new Intersection(point, general);
        TestIntersectionGenerator testIntersectionGenerator = new TestIntersectionGenerator(
                Arrays.asList(actualIntersection));

        JanggiBoard janggiBoard = new JanggiBoard(testIntersectionGenerator);
        Intersection expectedIntersection = janggiBoard.findIntersection(point);

        Assertions.assertThat(actualIntersection.isSamePiece(expectedIntersection))
                .isTrue();
    }

    @Test
    void 차림이_선택되었을_때_상과_마를_정확한_위치에_배치해야_한다() {
        Formation elephantHorseHorseElephant = Formation.ELEPHANT_HORSE_HORSE_ELEPHANT;
        JanggiGenerator janggiGenerator = new JanggiGenerator(
                elephantHorseHorseElephant, elephantHorseHorseElephant
        );
        JanggiBoard janggiBoard = new JanggiBoard(janggiGenerator);

        List<Point> elephantAndHorsePoints = Stream.concat(
                elephantHorseHorseElephant.elephantFormations().stream()
                        .map(x -> new Point(DEFAULT_ELEPHANT_AND_HORSE_ROW, x)),
                elephantHorseHorseElephant.horseFormations().stream()
                        .map(x -> new Point(DEFAULT_ELEPHANT_AND_HORSE_ROW, x))
        ).toList();

        List<Intersection> actual = elephantAndHorsePoints.stream()
                .map(janggiBoard::findIntersection)
                .toList();

        List<Intersection> expected =
                janggiGenerator.createElephantAndHorseByFormation(Team.HAN, Formation.ELEPHANT_HORSE_HORSE_ELEPHANT);

        for (int i = 0; i < actual.size(); i++) {
            Intersection actualIntersection = actual.get(i);
            Intersection expectedIntersection = expected.get(i);

            Assertions.assertThat(actualIntersection.isSamePiece(expectedIntersection))
                    .isTrue();
        }
    }
}
