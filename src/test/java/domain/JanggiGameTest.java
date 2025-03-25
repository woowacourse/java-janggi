package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.board.Point;
import domain.pieces.Piece;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class JanggiGameTest {

    @Test
    @DisplayName("보드를 초기화할 경우, 플레이어 당 16개의 기물을 가진다.")
    void test_setup() {
        //given
        final EnumMap<Team, Integer> given = new EnumMap<>(Team.class);
        given.put(Team.HAN, 1);

        //when
        final JanggiGame janggiGame = JanggiGame.setup(given);

        //then
        assertThat(janggiGame.getBoard().size()).isEqualTo(16);
    }

    @Test
    @DisplayName("시작점과 도착점을 입력할 경우, 기물을 움직일 수 있다.")
    void test_move() {
        //given
        final EnumMap<Team, Integer> given = new EnumMap<>(Team.class);
        given.put(Team.CHO, 1);
        given.put(Team.HAN, 1);
        final JanggiGame janggiGame = JanggiGame.setup(given);
        final Map<Point, Piece> givenBoard = janggiGame.getBoard();
        final Point start = new Point(0, 0);
        final Point arrival = new Point(2, 0);

        //when
        janggiGame.move(start, arrival);

        //then
        final Map<Point, Piece> movedBoard = janggiGame.getBoard();
        assertThat(movedBoard.get(arrival)).isEqualTo(givenBoard.get(start));
    }

    @Test
    @DisplayName("기물을 움직일 경우, 플레이어 턴이 교체된다.(초나라 선공)")
    void test_() {
        //given
        final EnumMap<Team, Integer> given = new EnumMap<>(Team.class);
        given.put(Team.CHO, 1);
        given.put(Team.HAN, 1);
        final JanggiGame janggiGame = JanggiGame.setup(given);
        final Map<Point, Piece> givenBoard = janggiGame.getBoard();
        final Team startTurn = janggiGame.getTeamOnCurrentTurn();

        //when
        janggiGame.move(new Point(0, 0), new Point(2, 0));
        final Team nextTurn = janggiGame.getTeamOnCurrentTurn();

        //then
        assertThat(startTurn).isEqualTo(Team.CHO);
        assertThat(startTurn).isNotEqualTo(nextTurn);
        assertThat(nextTurn).isEqualTo(Team.HAN);
    }
}
