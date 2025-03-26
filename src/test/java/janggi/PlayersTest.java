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
        Players players = Players.create(Turn.start());

        // when
        Player choPlayer = players.getPlayer(Team.CHO);
        Player hanPlayer = players.getPlayer(Team.HAN);

        // then
        assertThat(choPlayer.getTeam()).isEqualTo(Team.CHO);
        assertThat(hanPlayer.getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("두 플레이어의 피스를 가져올 수 있다")
    void getBothPiece() {
        // given
        Players players = Players.create(Turn.start());

        // when
        Pieces bothPieces = players.getBothPieces();

        // then
        assertThat(bothPieces.getPieces().size()).isEqualTo(32);
    }

    @Test
    @DisplayName("팀으로 플레이어의 점수를 가져올 수 있다")
    void getScore() {
        // given
        Players players = Players.create(Turn.start());
        Player cho = players.getPlayer(Team.CHO);
        cho.addScore(new Score(11111));
        Player han = players.getPlayer(Team.HAN);
        han.addScore(new Score(22222));

        // when
        Score choScore = players.getScore(Team.CHO);
        Score hanScore = players.getScore(Team.HAN);

        // then
        assertThat(choScore).isEqualTo(new Score(11111));
        assertThat(hanScore).isEqualTo(new Score(22222));
    }

    @Test
    @DisplayName("현재 턴에 맞는 플레이어를 찾을 수 있다")
    void getCurrentPlayer() {
        // given
        Turn turn = Turn.start(); // 한나라부터
        Players players = Players.create(turn);

        // when
        Player firstPlayer = players.getCurrentPlayer();
        turn.next();
        Player secondPlayer = players.getCurrentPlayer();

        // then
        assertThat(firstPlayer).isEqualTo(players.getPlayer(Team.HAN));
        assertThat(secondPlayer).isEqualTo(players.getPlayer(Team.CHO));
    }

    @Test
    @DisplayName("보드를 생성할 수 있다")
    void createBoard() {
        // given
        Players players = Players.create(Turn.start());

        Board board = Board.from(players.getBothPieces());

        // when
        Board boardByPlayers = players.createBoard();

        // then
        assertThat(boardByPlayers.getPositionToPiece())
                .isEqualTo(board.getPositionToPiece());
    }
}
