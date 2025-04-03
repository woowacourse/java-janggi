package object.team;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import object.coordinate.Coordinate;
import object.piece.Piece;
import object.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamsTest {

    @Test
    @DisplayName("좌표에 기물이 있다면 true를 반환한다.")
    void test1() {
        // given
        Team han = new Team(
                Country.HAN,
                Map.of(),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 7), new Piece(Country.CHO, PieceType.JOL)
                )),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when
        boolean result = teams.hasPiece(new Coordinate(5, 7));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("좌표에 기물이 없다면 false를 반환한다.")
    void test2() {
        // given
        Team han = new Team(
                Country.HAN,
                Map.of(),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                Map.of(),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when
        boolean result = teams.hasPiece(new Coordinate(1, 1));

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("기물을 찾는다.")
    void test3() {
        // given
        Piece jol = new Piece(Country.CHO, PieceType.JOL);
        Team han = new Team(
                Country.HAN,
                Map.of(),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 7), jol
                )),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when
        Piece piece = teams.findPiece(new Coordinate(5, 7));

        // then
        assertThat(piece).isEqualTo(jol);
    }

    @Test
    @DisplayName("좌표에 기물이 없으면 예외가 발생한다.")
    void test4() {
        // given
        Piece jol = new Piece(Country.CHO, PieceType.JOL);
        Team han = new Team(
                Country.HAN,
                Map.of(),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                Map.of(),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when & then
        Coordinate coordinate = new Coordinate(2, 2);
        assertThatThrownBy(() -> teams.findPiece(coordinate))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 좌표에 기물이 없습니다.");
    }

    @Test
    @DisplayName("도착 좌표에 기물이 없는 경우 기물을 움직인다.")
    void test5() {
        // given
        Piece jol = new Piece(Country.CHO, PieceType.JOL);
        Team han = new Team(
                Country.HAN,
                Map.of(),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 7), jol
                )),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when
        teams.movePiece(Country.CHO, jol, new Coordinate(5, 7), new Coordinate(5, 6));
        Piece piece = teams.findPiece(new Coordinate(5, 6));

        // then
        assertThat(piece).isEqualTo(jol);
    }

    @Test
    @DisplayName("도착 좌표에 상대 기물이 있는 경우 기물을 움직이면, 도착 좌표의 기물은 제거되고 해당 좌표에는 움직인 기물이 남는다.")
    void test6() {
        // given
        Piece jol = new Piece(Country.CHO, PieceType.JOL);
        Team han = new Team(
                Country.HAN,
                new HashMap<>(Map.of(
                        new Coordinate(5, 6), new Piece(Country.HAN, PieceType.JOL)
                )),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 7), jol
                )),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when
        teams.movePiece(Country.CHO, jol, new Coordinate(5, 7), new Coordinate(5, 6));
        boolean result1 = teams.hasPiece(new Coordinate(5, 7));
        boolean result2 = teams.hasPiece(new Coordinate(5, 6));
        Piece piece = teams.findPiece(new Coordinate(5, 6));

        // then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(piece).isEqualTo(jol);
    }

    @Test
    @DisplayName("도착 좌표에 상대 기물이 있는 경우 기물을 움직이면, 점수를 얻는다.")
    void test7() {
        // given
        Piece jol = new Piece(Country.CHO, PieceType.JOL);
        Team han = new Team(
                Country.HAN,
                new HashMap<>(Map.of(
                        new Coordinate(5, 6), new Piece(Country.HAN, PieceType.JOL)
                )),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 7), jol
                )),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when
        teams.movePiece(Country.CHO, jol, new Coordinate(5, 7), new Coordinate(5, 6));

        // then
        assertThat(cho.getScore()).isEqualTo(PieceType.JOL.getScore());
    }

    @Test
    @DisplayName("다른 팀의 기물을 움직이려고 시도하면 예외가 발생한다.")
    void test8() {
        // given
        Piece jol = new Piece(Country.CHO, PieceType.JOL);
        Team han = new Team(
                Country.HAN,
                Map.of(),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 7), jol
                )),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when & then
        assertThatThrownBy(() -> teams.movePiece(Country.HAN, jol, new Coordinate(5, 7), new Coordinate(5, 6)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("다른 팀의 말은 움직일 수 없습니다.");
    }

    @Test
    @DisplayName("도착 좌표에 같은 팀 말이 있으면 예외가 발생한다.")
    void test9() {
        // given
        Piece jol = new Piece(Country.CHO, PieceType.JOL);
        Team han = new Team(
                Country.HAN,
                Map.of(),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(5, 6), new Piece(Country.CHO, PieceType.JOL)
                )),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when & then
        assertThatThrownBy(() -> teams.movePiece(Country.CHO, jol, new Coordinate(5, 7), new Coordinate(5, 6)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("도착 좌표에 같은 팀 말이 있습니다.");
    }

    @Test
    @DisplayName("궁이 죽은 경우 게임이 끝난다.")
    void test10() {
        // given
        Piece jol = new Piece(Country.CHO, PieceType.JOL);
        Team han = new Team(
                Country.HAN,
                new HashMap<>(Map.of(
                        new Coordinate(5, 9), new Piece(Country.HAN, PieceType.GOONG)
                )),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 8), jol
                )),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when
        teams.movePiece(Country.CHO, jol, new Coordinate(5, 8), new Coordinate(5, 9));
        boolean result = teams.isEnd();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("점수가 높은 나라가 이긴다.")
    void test11() {
        // given
        Piece jol = new Piece(Country.CHO, PieceType.JOL);
        Team han = new Team(
                Country.HAN,
                new HashMap<>(Map.of(
                        new Coordinate(5, 9), new Piece(Country.HAN, PieceType.JOL)
                )),
                new Score(0)
        );
        Team cho = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 8), jol
                )),
                new Score(0)
        );
        Teams teams = new Teams(han, cho);

        // when
        teams.movePiece(Country.CHO, jol, new Coordinate(5, 8), new Coordinate(5, 9));
        Team team = teams.judgeWinner();

        // then
        assertThat(team).isEqualTo(cho);
    }
}
