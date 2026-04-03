package domain;

import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiGenerator;
import domain.fixture.TestIntersectionGenerator;
import domain.intersection.Intersection;
import domain.piece.General;
import domain.point.Point;
import domain.team.Team;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    public static final int DEFAULT_ELEPHANT_AND_HORSE_ROW = 0;

    @Test
    @DisplayName("장기판 기물 배치를 테스트한다.")
    void should_place_pieces_on_board() {
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
    @DisplayName("차림이 선택되었을 때 상과 마를 정확한 위치에 배치해야 한다.")
    void should_place_elephant_and_horse_positions_by_selected_formation() {
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
