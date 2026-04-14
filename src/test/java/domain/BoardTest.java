package domain;

import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiGenerator;
import domain.fixture.TestIntersectionGenerator;
import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {
    public static final int DEFAULT_ELEPHANT_AND_HORSE_ROW = 0;

    @Test
    @DisplayName("장기판 기물 배치를 테스트한다.")
    void should_place_pieces_on_board() {
        Point point = new Point(1, 4);
        Piece general = new Piece(Team.HAN, PieceType.GENERAL);
        Intersection actualIntersection = new Intersection(point, general);
        TestIntersectionGenerator testIntersectionGenerator = new TestIntersectionGenerator(
                Arrays.asList(actualIntersection));

        JanggiBoard janggiBoard = new JanggiBoard(testIntersectionGenerator);
        Intersection expectedIntersection = janggiBoard.findIntersection(point);

        Assertions.assertThat(actualIntersection.isSamePiece(expectedIntersection))
                .isTrue();
    }

    @Test
    @DisplayName("한(HAN) 진영은 4가지 차림 중 선택된 차림에 따라 상과 마를 정확한 위치에 배치해야 한다.")
    void should_place_elephant_and_horse_positions_for_han_by_all_formations() {
        for (Formation formation : Formation.values()) {
            JanggiGenerator janggiGenerator = new JanggiGenerator(formation, Formation.ELEPHANT_HORSE_HORSE_ELEPHANT);
            JanggiBoard janggiBoard = new JanggiBoard(janggiGenerator);

            int row = 0;
            List<Point> elephantAndHorsePoints = Stream.concat(
                    formation.elephantFormations().stream().map(x -> new Point(row, x)),
                    formation.horseFormations().stream().map(x -> new Point(row, x))
            ).toList();

            List<Intersection> actual = elephantAndHorsePoints.stream()
                    .map(janggiBoard::findIntersection)
                    .toList();

            List<Intersection> expected =
                    janggiGenerator.createElephantAndHorseByFormation(Team.HAN, formation);

            for (int i = 0; i < actual.size(); i++) {
                Intersection actualIntersection = actual.get(i);
                Intersection expectedIntersection = expected.get(i);

                Assertions.assertThat(actualIntersection.isSamePiece(expectedIntersection))
                        .as("Formation: " + formation.name() + " mismatch at index " + i)
                        .isTrue();
            }
        }
    }

    @Test
    @DisplayName("초(CHO) 진영은 4가지 차림 중 선택된 차림에 따라 상과 마를 정확한 위치에 배치해야 한다.")
    void should_place_elephant_and_horse_positions_for_cho_by_all_formations() {
        for (Formation formation : Formation.values()) {
            JanggiGenerator janggiGenerator = new JanggiGenerator(Formation.ELEPHANT_HORSE_HORSE_ELEPHANT, formation);
            JanggiBoard janggiBoard = new JanggiBoard(janggiGenerator);

            int row = 9;
            List<Point> elephantAndHorsePoints = Stream.concat(
                    formation.elephantFormations().stream().map(x -> new Point(row, x)),
                    formation.horseFormations().stream().map(x -> new Point(row, x))
            ).toList();

            List<Intersection> actual = elephantAndHorsePoints.stream()
                    .map(janggiBoard::findIntersection)
                    .toList();

            List<Intersection> expected =
                    janggiGenerator.createElephantAndHorseByFormation(Team.CHO, formation);

            for (int i = 0; i < actual.size(); i++) {
                Intersection actualIntersection = actual.get(i);
                Intersection expectedIntersection = expected.get(i);

                Assertions.assertThat(actualIntersection.isSamePiece(expectedIntersection))
                        .as("Formation: " + formation.name() + " mismatch at index " + i)
                        .isTrue();
            }
        }
    }
}
