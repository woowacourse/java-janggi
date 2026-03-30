package domain.board;

import fixture.TestIntersectionGenerator;
import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    public static final int DEFAULT_ELEPHANT_AND_HORSE_ROW = 0;

    @Test
    void 장기판_기물_배치를_테스트한다() {
        Point point = new Point(1, 4);
        Piece general = new Piece(Team.HAN, PieceType.GENERAL);
        Intersection actualIntersection = new Intersection(point, general);
        TestIntersectionGenerator testIntersectionGenerator = new TestIntersectionGenerator(
                List.of(actualIntersection));

        JanggiBoard janggiBoard = new JanggiBoard(testIntersectionGenerator);
        Intersection expectedIntersection = janggiBoard.findIntersection(point);

        Assertions.assertThat(actualIntersection)
                .isEqualTo(expectedIntersection);
    }

    // TODO method 리팩토링
    @Test
    void 차림이_선택되었을_때_상과_마를_정확한_위치에_배치해야_한다() {
        Formation elephantHorseHorseElephant = Formation.ELEPHANT_HORSE_HORSE_ELEPHANT;
        JanggiIntersectionGenerator janggiIntersectionGenerator = new JanggiIntersectionGenerator(
                elephantHorseHorseElephant, elephantHorseHorseElephant
        );
        JanggiBoard janggiBoard = new JanggiBoard(janggiIntersectionGenerator);

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
                janggiIntersectionGenerator.createElephantAndHorseByFormation(Team.HAN, Formation.ELEPHANT_HORSE_HORSE_ELEPHANT);

        for (int i = 0; i < actual.size(); i++) {
            Intersection actualIntersection = actual.get(i);
            Intersection expectedIntersection = expected.get(i);

            Assertions.assertThat(actualIntersection)
                    .isEqualTo(expectedIntersection);
        }
    }

    @Test
    @DisplayName("모든 격자점에 한 팀의 장군이 존재하지 않으면 게임이 종료된다.")
    void gameWillEndWhenOneOfGeneralDoesntExist() {
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Piece hanGeneral = new Piece(Team.HAN, PieceType.GENERAL);

        Intersection from = new Intersection(start, hanGeneral);
        Intersection to = Intersection.empty(end);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(from, to)));

        Assertions.assertThat(janggiBoard.isGameOver())
                .isTrue();
    }

    @Test
    @DisplayName("모든 격자점에 양 팀의 장군이 존재하면 게임은 진행된다.")
    void gameWillProgressWhenOneOfGeneralDoesntExist() {
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Piece hanGeneral = new Piece(Team.HAN, PieceType.GENERAL);
        Piece choGeneral = new Piece(Team.CHO, PieceType.GENERAL);

        Intersection from = new Intersection(start, hanGeneral);
        Intersection to = new Intersection(end, choGeneral);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(from, to)));

        Assertions.assertThat(janggiBoard.isGameOver())
                .isFalse();
    }


    @Test
    @DisplayName("CHO팀의 장군이 없으면 HAN팀이 승리한다.")
    void hanWillWinWhenChoGeneralIsDead() {
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Team expectedWinner = Team.HAN;
        Piece hanGeneral = new Piece(expectedWinner, PieceType.GENERAL);

        Intersection from = new Intersection(start, hanGeneral);
        Intersection to = Intersection.empty(end);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(from, to)));
        Team actualWinner = janggiBoard.getWinner();

        Assertions.assertThat(actualWinner)
                .isEqualTo(expectedWinner);
    }

}
