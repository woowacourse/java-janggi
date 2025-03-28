package domain;

import static domain.player.TeamType.CHO;
import static domain.player.TeamType.HAN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.board.Board;
import domain.board.Point;
import domain.pieces.Chariot;
import domain.pieces.Piece;
import domain.player.Player;
import domain.player.Score;
import domain.player.TeamType;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class JanggiGameTest {

    @Test
    @DisplayName("보드를 초기화할 경우, 플레이어 당 16개의 기물을 가진다.")
    void test_setup() {
        //given
        final EnumMap<TeamType, Integer> given = new EnumMap<>(TeamType.class);
        given.put(TeamType.HAN, 1);

        //when
        final JanggiGame janggiGame = JanggiGame.setup(given);

        //then
        assertThat(janggiGame.getBoard().size()).isEqualTo(16);
    }

    @Test
    @DisplayName("시작점과 도착점을 입력할 경우, 기물을 움직일 수 있는 지 반환한다.")
    void test_canMovePieceOnBoard() {
        //given
        final EnumMap<TeamType, Integer> given = new EnumMap<>(TeamType.class);
        given.put(CHO, 1);
        given.put(TeamType.HAN, 1);
        final JanggiGame janggiGame = JanggiGame.setup(given);
        final Point start = new Point(0, 0);
        final Point arrival = new Point(2, 0);

        //when&then
        assertThat(janggiGame.canMove(start, arrival)).isTrue();
    }

    @Test
    @DisplayName("시작점과 도착점을 입력할 경우, 기물을 움직일 수 있다.")
    void test_movePieceOnBoard() {
        //given
        final EnumMap<TeamType, Integer> given = new EnumMap<>(TeamType.class);
        given.put(CHO, 1);
        given.put(TeamType.HAN, 1);
        final JanggiGame janggiGame = JanggiGame.setup(given);
        final Map<Point, Piece> givenBoard = janggiGame.getBoard();
        final Point start = new Point(0, 0);
        final Point arrival = new Point(2, 0);

        //when
        janggiGame.movePieceOnBoard(start, arrival);
        //then
        final Map<Point, Piece> movedBoard = janggiGame.getBoard();
        assertThat(movedBoard.get(arrival)).isEqualTo(givenBoard.get(start));
    }

    @Test
    @DisplayName("기물을 움직여 상대 기물을 잡았을 때, 그에 맞는 스코어를 획득한다.")
    void test_getScoreWhenMovePieceOnBoard() {
        //given
        final List<Player> players = Arrays.stream(TeamType.values())
                .map(Player::new)
                .toList();

        final Point start = new Point(0, 0);
        final Point arrival = new Point(2, 0);
        final Map<Point, Piece> locations = new HashMap<>();
        locations.put(start, new Chariot(CHO));
        locations.put(arrival, new Chariot(HAN));

        final Board board = new Board(locations);
        final JanggiGame janggiGame = new JanggiGame(board, players);

        final Score expected = new Score(13.0);

        //when
        janggiGame.movePieceOnBoard(start, arrival);
        //then
        final Map<TeamType, Score> scores = janggiGame.wrapPlayersScore();
        final Score actual = scores.get(CHO);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("플레이어 턴이 교체한다.(초나라 선공)")
    void test_switchTurn() {
        //given
        final EnumMap<TeamType, Integer> given = new EnumMap<>(TeamType.class);
        given.put(CHO, 1);
        given.put(TeamType.HAN, 1);
        final JanggiGame janggiGame = JanggiGame.setup(given);
        final TeamType startTurn = janggiGame.getTeamOnCurrentTurn();

        //when
        janggiGame.switchTurn();
        final TeamType nextTurn = janggiGame.getTeamOnCurrentTurn();

        //then
        assertThat(startTurn).isEqualTo(CHO);
        assertThat(startTurn).isNotEqualTo(nextTurn);
        assertThat(nextTurn).isEqualTo(TeamType.HAN);
    }
}
