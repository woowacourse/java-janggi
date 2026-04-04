package domain.board;

import domain.intersection.palace.NormalIntersection;
import fixture.JanggiBoardFixture;
import fixture.TestIntersectionGenerator;
import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    public static final int DEFAULT_ELEPHANT_AND_HORSE_ROW = 0;

    @Test
    void 장기판_기물_배치를_테스트한다() {
        // given
        Point point = new Point(1, 4);
        Piece general = new Piece(Team.HAN, PieceType.GENERAL);
        Intersection actualIntersection = new NormalIntersection(point, general);
        TestIntersectionGenerator testIntersectionGenerator = new TestIntersectionGenerator(
                List.of(actualIntersection));
        JanggiBoard janggiBoard = new JanggiBoard(testIntersectionGenerator);

        // when
        Intersection expectedIntersection = janggiBoard.findIntersection(point);

        // then
        Assertions.assertThat(actualIntersection)
                .isEqualTo(expectedIntersection);
    }

    @Test
    void 차림이_선택되었을_때_상과_마를_정확한_위치에_배치해야_한다() {
        // when
        Formation elephantHorseHorseElephant = Formation.ELEPHANT_HORSE_HORSE_ELEPHANT;
        JanggiIntersectionGenerator janggiIntersectionGenerator = new JanggiIntersectionGenerator(
                elephantHorseHorseElephant, elephantHorseHorseElephant
        );
        JanggiBoard janggiBoard = new JanggiBoard(janggiIntersectionGenerator);

        // when
        List<Point> elephantAndHorsePoints = getFormationPoints(elephantHorseHorseElephant);
        List<Intersection> actual = elephantAndHorsePoints.stream()
                .map(janggiBoard::findIntersection)
                .toList();

        List<NormalIntersection> expected =
                janggiIntersectionGenerator.createElephantAndHorseByFormation(Team.HAN, Formation.ELEPHANT_HORSE_HORSE_ELEPHANT);

        // then
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
        // given
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Piece hanGeneral = new Piece(Team.HAN, PieceType.GENERAL);

        Intersection origin = new NormalIntersection(start, hanGeneral);
        Intersection destination = NormalIntersection.empty(end);

        // when
        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(origin, destination)));

        // then
        Assertions.assertThat(janggiBoard.isGameOver())
                .isTrue();
    }

    @Test
    @DisplayName("모든 격자점에 양 팀의 장군이 존재하면 게임은 진행된다.")
    void gameWillProgressWhenOneOfGeneralDoesntExist() {
        // given
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Piece hanGeneral = new Piece(Team.HAN, PieceType.GENERAL);
        Piece choGeneral = new Piece(Team.CHO, PieceType.GENERAL);

        Intersection origin = new NormalIntersection(start, hanGeneral);
        Intersection destination = new NormalIntersection(end, choGeneral);

        // when
        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(origin, destination)));

        // then
        Assertions.assertThat(janggiBoard.isGameOver())
                .isFalse();
    }


    @Test
    @DisplayName("CHO팀의 장군이 없으면 HAN팀이 승리한다.")
    void hanWillWinWhenChoGeneralIsDead() {
        // given
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Team expectedWinner = Team.HAN;
        Piece hanGeneral = new Piece(expectedWinner, PieceType.GENERAL);

        Intersection origin = new NormalIntersection(start, hanGeneral);
        Intersection destination = NormalIntersection.empty(end);

        // when
        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(origin, destination)));
        Team actualWinner = janggiBoard.getWinner();

        // then
        Assertions.assertThat(actualWinner)
                .isEqualTo(expectedWinner);
    }

    @Test
    @DisplayName("양 팀의 기물 점수가 모두 30점 미만(ex 29점)이면 장기는 무승부가 된다.")
    void shouldDrawBothOfTeamScoreIsLessThan30() {
        // given
        JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                //  13 + 13 + 3 = 29점
                generateIntersection(0, 0, Team.HAN, PieceType.CHARIOT),
                generateIntersection(0, 8, Team.HAN, PieceType.CHARIOT),
                generateIntersection(0, 1, Team.HAN, PieceType.ELEPHANT),

                // 13 + 13 + 3 = 29점
                generateIntersection(9, 0, Team.CHO, PieceType.CHARIOT),
                generateIntersection(9, 8, Team.CHO, PieceType.CHARIOT),
                generateIntersection(9, 1, Team.CHO, PieceType.ELEPHANT)
        );

        // when & then
        Assertions.assertThat(janggiBoard.isDraw()).isTrue();
    }

    @Test
    @DisplayName("한 팀이라도 기물 점수가 30점 이상이면 장기는 진행된다.")
    void shouldPlayOneOfTeamScoreIsMoreThan30() {
        // given
        JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                //  13 + 13 + 3 + 2= 31점
                generateIntersection(0, 0, Team.HAN, PieceType.CHARIOT),
                generateIntersection(0, 8, Team.HAN, PieceType.CHARIOT),
                generateIntersection(0, 1, Team.HAN, PieceType.ELEPHANT),
                generateIntersection(3, 0, Team.HAN, PieceType.SOLDIER),

                // 13 + 13 + 3 = 29점
                generateIntersection(9, 0, Team.CHO, PieceType.CHARIOT),
                generateIntersection(9, 8, Team.CHO, PieceType.CHARIOT),
                generateIntersection(9, 1, Team.CHO, PieceType.ELEPHANT)
        );

        // when & then
        Assertions.assertThat(janggiBoard.isDraw()).isFalse();
    }

    private static List<Point> getFormationPoints(Formation elephantHorseHorseElephant) {
        return Stream.concat(
                elephantHorseHorseElephant.elephantFormations().stream()
                        .map(x -> new Point(DEFAULT_ELEPHANT_AND_HORSE_ROW, x)),
                elephantHorseHorseElephant.horseFormations().stream()
                        .map(x -> new Point(DEFAULT_ELEPHANT_AND_HORSE_ROW, x))
        ).toList();
    }

    private Intersection generateIntersection(int row, int col, Team team, PieceType type) {
        return new NormalIntersection(new Point(row, col), new Piece(team, type));
    }

}
