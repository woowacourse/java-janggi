package domain;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.board.Board;
import domain.board.Point;
import domain.pieces.Chariot;
import domain.pieces.Piece;
import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import dto.Choice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class JanggiGameTest {

    @Test
    @DisplayName("보드를 초기화할 경우, 플레이어 당 16개의 기물을 가진다.")
    void test_setup() {
        //given
        final Map<Player, Choice> given = new LinkedHashMap<>();
        given.put(new Player(1, HAN), new Choice(1));
        final List<Player> players = new ArrayList<>(given.keySet());

        //when
        final JanggiGame janggiGame = JanggiGame.setup(0, given, players);

        //then
        assertThat(janggiGame.getBoard().size()).isEqualTo(16);
    }

    @Test
    @DisplayName("시작점과 도착점을 입력할 경우, 기물을 움직일 수 있는 지 반환한다.")
    void test_canMovePiecePieceOnBoard() {
        //given
        final JanggiGame janggiGame = getJanggiGame();

        final Point start = new Point(0, 0);
        final Point arrival = new Point(2, 0);

        //when&then
        assertThat(janggiGame.canMovePiece(start, arrival)).isTrue();
    }

    @Test
    @DisplayName("시작점과 도착점을 입력할 경우, 기물을 움직일 수 있다.")
    void test_movePieceOnBoard() {
        //given
        final JanggiGame janggiGame = getJanggiGame();
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
        final List<Player> players = Arrays.stream(Team.values())
                .map(team -> new Player(1, team))
                .toList();

        final Point start = new Point(0, 0);
        final Point arrival = new Point(2, 0);
        final Map<Point, Piece> locations = new HashMap<>();
        locations.put(start, new Chariot(new Player(1, CHO)));
        locations.put(arrival, new Chariot(new Player(1, HAN)));

        final Board board = new Board(locations);
        final JanggiGame janggiGame = new JanggiGame(0, board, players);

        final Score expected = new Score(13.0);

        //when
        janggiGame.movePieceOnBoard(start, arrival);
        //then
        final Map<Team, Score> scores = janggiGame.wrapPlayersScore();
        final Score actual = scores.get(CHO);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("플레이어 턴이 교체한다.(초나라 선공)")
    void test_switchTurn() {
        //given
        final JanggiGame janggiGame = getJanggiGame();
        final Team startTurn = janggiGame.getTeamOnCurrentTurn();

        //when
        janggiGame.switchTurn();
        final Team nextTurn = janggiGame.getTeamOnCurrentTurn();

        //then
        assertThat(startTurn).isEqualTo(Team.CHO);
        assertThat(startTurn).isNotEqualTo(nextTurn);
        assertThat(nextTurn).isEqualTo(Team.HAN);
    }

    private static JanggiGame getJanggiGame() {
        final Map<Player, Choice> given = new LinkedHashMap<>();
        given.put(new Player(1, CHO), new Choice(1));
        given.put(new Player(1, HAN), new Choice(1));
        final List<Player> players = new ArrayList<>(given.keySet());

        return JanggiGame.setup(0, given, players);
    }
}
