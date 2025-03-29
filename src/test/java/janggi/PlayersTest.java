package janggi;

import janggi.piece.Pieces;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayersTest {

    @Test
    @DisplayName("팀으로 플레이어를 찾을 수 있다")
    void create() {
        // given
        final Players players = Players.create(Turn.start());

        // when
        final Player choPlayer = players.getPlayer(Team.CHO);
        final Player hanPlayer = players.getPlayer(Team.HAN);

        // then
        assertThat(choPlayer.getTeam()).isEqualTo(Team.CHO);
        assertThat(hanPlayer.getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("두 플레이어의 피스를 가져올 수 있다")
    void getBothPiece() {
        // given
        final Players players = Players.create(Turn.start());

        // when
        final Pieces bothPieces = players.getBothPieces();

        // then
        assertThat(bothPieces.getPieces().size()).isEqualTo(32);
    }

    @Test
    @DisplayName("팀으로 플레이어의 점수를 가져올 수 있다")
    void getScore() {
        // given
        final Players players = Players.create(Turn.start());
        final Player cho = players.getPlayer(Team.CHO);
        cho.addScore(new Score(11111));
        final Player han = players.getPlayer(Team.HAN);
        han.addScore(new Score(22222));

        // when
        final Score choScore = players.getScore(Team.CHO);
        final Score hanScore = players.getScore(Team.HAN);

        // then
        assertThat(choScore).isEqualTo(new Score(11111));
        assertThat(hanScore).isEqualTo(new Score(22222));
    }

    @Test
    @DisplayName("현재 턴에 맞는 플레이어를 찾을 수 있다")
    void getCurrentPlayer() {
        // given
        final Turn turn = Turn.start(); // 한나라부터
        final Players players = Players.create(turn);

        // when
        final Player firstPlayer = players.getCurrentPlayer();
        turn.next();
        final Player secondPlayer = players.getCurrentPlayer();

        // then
        assertThat(firstPlayer).isEqualTo(players.getPlayer(Team.HAN));
        assertThat(secondPlayer).isEqualTo(players.getPlayer(Team.CHO));
    }

    @Test
    @DisplayName("보드를 생성할 수 있다")
    void createBoard() {
        // given
        final Players players = Players.create(Turn.start());

        final Board board = Board.from(players.getBothPieces());

        // when
        final Board boardByPlayers = players.createBoard();

        // then
        assertThat(boardByPlayers.getPositionToPiece())
                .isEqualTo(board.getPositionToPiece());
    }
}
