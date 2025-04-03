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

class TeamTest {

    @Test
    @DisplayName("해당 좌표에 해당 팀 기물이 있다면 true를 반환한다.")
    void test1() {
        // given
        Team team = new Team(
                Country.CHO,
                Map.of(
                        new Coordinate(5, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                ),
                new Score(0)
        );

        // when
        boolean result = team.isMyPiece(new Coordinate(5, 7));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("해당 좌표에 해당 팀 기물이 없다면 false를 반환한다.")
    void test2() {
        // given
        Team team = new Team(
                Country.CHO,
                Map.of(
                        new Coordinate(5, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                ),
                new Score(0)
        );

        // when
        boolean result = team.isMyPiece(new Coordinate(5, 6));

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("좌표에 있는 기물을 가져온다.")
    void test3() {
        // given
        Piece jol = new Piece(Country.CHO, PieceType.JOL);
        Team team = new Team(
                Country.CHO,
                Map.of(
                        new Coordinate(5, 7), jol,
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                ),
                new Score(0)
        );

        // when
        Piece piece = team.getPiece(new Coordinate(5, 7));

        // then
        assertThat(piece).isEqualTo(jol);
    }

    @Test
    @DisplayName("좌표에 있는 기물이 없다면 예외가 발생한다.")
    void test4() {
        // given
        Team team = new Team(
                Country.CHO,
                Map.of(
                        new Coordinate(5, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                ),
                new Score(0)
        );

        // when & then
        assertThatThrownBy(() -> team.getPiece(new Coordinate(5, 8)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 좌표에 기물이 없습니다.");
    }

    @Test
    @DisplayName("좌표에 있는 기물을 없앤다.")
    void test5() {
        // given
        Team team = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                )),
                new Score(0)
        );

        // when
        team.removePiece(new Coordinate(3, 7));

        // then
        assertThatThrownBy(() -> team.getPiece(new Coordinate(3, 7)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 좌표에 기물이 없습니다.");
    }

    @Test
    @DisplayName("좌표에 있는 기물이 궁이라면 궁이 죽는다.")
    void test6() {
        // given
        Team team = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 9), new Piece(Country.CHO, PieceType.GOONG),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                )),
                new Score(0)
        );

        // when
        team.removePiece(new Coordinate(5, 9));
        boolean result = team.isGoongDead();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("기물을 추가한다.")
    void test7() {
        Team team = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 9), new Piece(Country.CHO, PieceType.GOONG),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                )),
                new Score(0)
        );

        // when
        Piece piece = new Piece(Country.CHO, PieceType.SA);
        team.putPiece(new Coordinate(7, 8), piece);

        // then
        Piece result = team.getPiece(new Coordinate(7, 8));
        assertThat(result).isEqualTo(piece);
    }

    @Test
    @DisplayName("점수를 추가한다.")
    void test8() {
        // given
        Team team = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 9), new Piece(Country.CHO, PieceType.GOONG),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                )),
                new Score(0)
        );

        // when
        team.addScore(3);

        // then
        assertThat(team.getScore()).isEqualTo(3);
    }

    @Test
    @DisplayName("같은 나라라면 true를 반환한다.")
    void test9() {
        // given
        Team team = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 9), new Piece(Country.CHO, PieceType.GOONG),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                )),
                new Score(0)
        );

        // when
        boolean result = team.isSameCountry(Country.CHO);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("같은 나라라면 true를 반환한다.")
    void test10() {
        // given
        Team team = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 9), new Piece(Country.CHO, PieceType.GOONG),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                )),
                new Score(0)
        );

        // when
        boolean result = team.isSameCountry(new Team(Country.CHO, Map.of(), new Score(0)));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("다른 나라라면 false를 반환한다.")
    void test11() {
        // given
        Team team = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 9), new Piece(Country.CHO, PieceType.GOONG),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                )),
                new Score(0)
        );

        // when
        boolean result = team.isSameCountry(Country.HAN);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("다른 나라라면 false를 반환한다.")
    void test12() {
        // given
        Team team = new Team(
                Country.CHO,
                new HashMap<>(Map.of(
                        new Coordinate(5, 9), new Piece(Country.CHO, PieceType.GOONG),
                        new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL),
                        new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)
                )),
                new Score(0)
        );

        // when
        boolean result = team.isSameCountry(new Team(Country.HAN, Map.of(), new Score(0)));

        // then
        assertThat(result).isFalse();
    }
}
