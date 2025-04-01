package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.BoardFixture;
import janggi.domain.Coordinate;
import janggi.domain.PieceType;
import janggi.domain.Team;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardGameTest {

    @Test
    @DisplayName("피스를 움직이면 차례가 바뀌고 다음 라운드가 된다.")
    void movePiece() {
        final var board = new BoardFixture()
            .addPiece(5, 2, PieceType.GOONG, Team.HAN)
            .addPiece(5, 9, PieceType.GOONG, Team.CHO)
            .addPiece(1, 1, PieceType.CHA, Team.CHO)
            .build();
        final var playingTurn = new PlayingTurn(Team.CHO, 1);
        BoardGame boardGame = new BoardGame(board, playingTurn);

        boardGame.movePiece(new Coordinate(1, 1), new Coordinate(1, 5));

        assertAll(
            () -> assertThat(playingTurn.currentTeam()).isEqualTo(Team.HAN),
            () -> assertThat(playingTurn.currentRound()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("양팀 궁이 살아있고 라운드가 30을 넘지 않으면 게임 오버가 아니다.")
    void isGameOver() {
        final var board = new BoardFixture()
            .addPiece(5, 2, PieceType.GOONG, Team.HAN)
            .addPiece(5, 9, PieceType.GOONG, Team.CHO)
            .build();
        BoardGame boardGame = new BoardGame(board, new PlayingTurn(Team.HAN, 30));

        boolean isGameOver = boardGame.isGameOver();

        assertThat(isGameOver).isFalse();
    }

    @Test
    @DisplayName("한쪽 팀 궁이 죽었다면 게임 오버이다.")
    void gameOverWhenGoongDead() {
        final var board = new BoardFixture()
            .addPiece(5, 2, PieceType.GOONG, Team.HAN)
            .build();
        BoardGame boardGame = new BoardGame(board, new PlayingTurn(Team.HAN, 5));

        boolean isGameOver = boardGame.isGameOver();

        assertThat(isGameOver).isTrue();
    }

    @Test
    @DisplayName("라운드가 30을 넘으면 게임 오버이다.")
    void gameOverWhenRoundOver30() {
        final var board = new BoardFixture()
            .addPiece(5, 2, PieceType.GOONG, Team.HAN)
            .addPiece(5, 9, PieceType.GOONG, Team.CHO)
            .build();
        BoardGame boardGame = new BoardGame(board, new PlayingTurn(Team.HAN, 31));

        boolean isGameOver = boardGame.isGameOver();

        assertThat(isGameOver).isTrue();
    }

    @Test
    @DisplayName("점수가 더 높은 팀을 알 수 있다.")
    void higherScoreTeam() {
        final var board = new BoardFixture()
            .addPiece(5, 2, PieceType.CHA, Team.HAN)
            .addPiece(5, 9, PieceType.MA, Team.CHO)
            .build();
        BoardGame boardGame = new BoardGame(board, new PlayingTurn(Team.HAN, 1));

        final var team = boardGame.higherScoreTeam();

        assertThat(team).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("각 팀의 점수를 알 수 있다.")
    void scoreTeams() {
        final var board = new BoardFixture()
            .addPiece(5, 2, PieceType.CHA, Team.HAN)
            .addPiece(5, 9, PieceType.MA, Team.CHO)
            .build();
        BoardGame boardGame = new BoardGame(board, new PlayingTurn(Team.HAN, 1));

        Map<Team, Double> teamScores = boardGame.scoreTeams();

        assertAll(
            () -> assertThat(teamScores.get(Team.HAN)).isEqualTo(14.5),
            () -> assertThat(teamScores.get(Team.CHO)).isEqualTo(5)
        );
    }
}
