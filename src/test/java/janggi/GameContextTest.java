package janggi;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.player.Player;
import janggi.player.Players;
import janggi.player.Score;
import janggi.player.Team;
import janggi.player.Turn;
import janggi.repository.dto.GameDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameContextTest {

    private static GameDto getGameDtoForTest() {
        return new GameDto(1, "1", 1, 1, 1, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("새 게임 생성 시 저장되지 않은 상태이다")
    void newGameIsNotSaved() {
        // given
        final Players players = Players.create(Turn.start());

        // when
        final GameContext context = GameContext.newGame(players);

        // then
        assertThat(context.isSaved()).isFalse();
    }

    @Test
    @DisplayName("불러온 게임은 저장된 상태이다")
    void loadGameIsSaved() {
        // given
        final Players players = Players.create(Turn.start());
        final GameDto dto = getGameDtoForTest();

        // when
        final GameContext context = GameContext.loadGame(dto, players);

        // then
        assertThat(context.isSaved()).isTrue();
        assertThat(context.getGameId().getValue()).isEqualTo(1);
    }

    @Test
    @DisplayName("현재 플레이어를 반환한다")
    void getCurrentPlayer() {
        // given
        final Players players = Players.create(Turn.start());
        final GameContext context = GameContext.newGame(players);

        // when
        final Player current = context.getCurrentPlayer();

        // then
        assertThat(current.getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("턴을 넘기면 다음 플레이어로 변경된다")
    void nextTurnSwitchesPlayer() {
        // given
        final Players players = Players.create(Turn.start());
        final GameContext context = GameContext.newGame(players);

        final Team currentTeam = context.getCurrentPlayer().getTeam();

        // when
        context.nextTurn();

        // then
        final Team nextTeam = context.getCurrentPlayer().getTeam();
        assertThat(nextTeam).isNotEqualTo(currentTeam);
    }

    @Test
    @DisplayName("팀별 점수를 조회할 수 있다")
    void getScore() {
        final Players players = Players.create(Turn.start());
        final GameContext context = GameContext.newGame(players);

        final Score choScore = context.getScore(Team.CHO);
        final Score hanScore = context.getScore(Team.HAN);

        assertThat(choScore).isEqualTo(Score.from(0));
        assertThat(hanScore).isEqualTo(Score.from(0));
    }

    @Test
    @DisplayName("살아있는 기물을 조회할 수 있다")
    void getAlivePieces() {
        // given
        final Players players = Players.create(Turn.start());
        final GameContext context = GameContext.newGame(players);

        final Pieces alivePieces = context.getAlivePieces();

        final List<Piece> expectedPieces = players.getPlayer(Team.CHO).getPieces()
                .addAll(players.getPlayer(Team.HAN).getPieces())
                .getPieces();

        // when
        final List<Piece> actualPieces = alivePieces.getPieces();

        // then
        assertThat(expectedPieces).containsExactlyInAnyOrderElementsOf(actualPieces);
    }

    @Test
    @DisplayName("보드를 조회할 수 있다")
    void getBoard() {
        // given
        final Players players = Players.create(Turn.start());
        final GameContext context = GameContext.newGame(players);

        // when
        final Board board = context.getBoard();

        // then
        assertThat(board).isNotNull();
    }

    @Test
    @DisplayName("턴 객체를 직접 조회할 수 있다")
    void getTurn() {
        // given
        final Players players = Players.create(Turn.start());
        final GameContext context = GameContext.newGame(players);

        // when
        final Turn turn = context.getTurn();

        // then
        assertThat(turn).isNotNull();
    }
}
