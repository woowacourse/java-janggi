package janggi;

import fixture.PieceFixture;
import janggi.board.Board;
import janggi.piece.PieceType;
import janggi.piece.Pieces;
import janggi.player.Player;
import janggi.player.Players;
import janggi.player.Score;
import janggi.player.Team;
import janggi.player.Turn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @Test
    @DisplayName("기물들, 턴, 초/한의 점수로 Players 객체를 만들 수 있다")
    void of() {
        // given
        final Pieces pieces = Pieces.from(List.of(
                PieceFixture.createPiece(1, 1, PieceType.SOLDIER, Team.CHO),
                PieceFixture.createPiece(2, 2, PieceType.SOLDIER, Team.CHO),
                PieceFixture.createPiece(3, 3, PieceType.SOLDIER, Team.HAN),
                PieceFixture.createPiece(4, 4, PieceType.SOLDIER, Team.HAN)));

        final Turn turn = Turn.start(); // han
        turn.next(); // cho

        final Score choScore = Score.cannon().add(Score.cannon());
        final Score hanScore = Score.soldier().add(Score.soldier());

        // when
        final Players players = Players.of(pieces, turn, choScore, hanScore);

        // then
        assertAll(() -> {
            assertThat(players.getBothPieces().getPieces())
                    .containsExactlyElementsOf(pieces.getPieces());

            assertThat(players.getCurrentPlayer().getTeam()).isEqualTo(Team.CHO);
            assertThat(players.getScore(Team.CHO)).isEqualTo(choScore);
            assertThat(players.getScore(Team.HAN)).isEqualTo(hanScore);
        });
    }

    @Test
    @DisplayName("승리를 확인할 수 있다")
    void isWin() {
        // given
        final Score choScore = Score.general();
        final Score hanScore = Score.from(1);
        final Players players = Players.of(Pieces.empty(), Turn.start(), choScore, hanScore);

        // when
        // then
        assertThat(players.isWin(players.getPlayer(Team.CHO))).isTrue();
        assertThat(players.isWin(players.getPlayer(Team.HAN))).isFalse();
    }
}
