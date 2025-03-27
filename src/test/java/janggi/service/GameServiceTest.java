package janggi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.BoardFixture;
import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Team;
import janggi.repository.MemoryRepository;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    @Test
    @DisplayName("피스를 움직일 수 있다.")
    void movePiece() {
        final var board = new BoardFixture()
            .addPiece(1, 1, PieceType.CHA, Team.CHO)
            .build();
        final var repository = new MemoryRepository(board);
        GameService service = new OnlineGameService(repository);

        service.movePiece(new Coordinate(1, 1), new Coordinate(1, 5));

        final var allPieces = service.allPieces();
        assertAll(
            () -> assertThat(allPieces).doesNotContainKey(new Coordinate(1, 1)),
            () -> assertThat(allPieces).containsValue(new Piece(Team.CHO, new Coordinate(1, 5), PieceType.CHA))
        );
    }

    @Test
    @DisplayName("게임이 종료되었는 지 알 수 있다.")
    void isGameOver() {
        final var board = new BoardFixture()
            .addPiece(5, 2, PieceType.GOONG, Team.HAN)
            .addPiece(5, 9, PieceType.GOONG, Team.CHO)
            .build();
        GameService service = new OnlineGameService(new MemoryRepository(board));

        boolean isGameOver = service.isGameOver();

        assertThat(isGameOver).isFalse();
    }

    @Test
    @DisplayName("게임을 초기화할 수 있다.")
    void clearGame() {
        final var board = new BoardFixture()
            .addPiece(5, 2, PieceType.GOONG, Team.HAN)
            .addPiece(5, 9, PieceType.GOONG, Team.CHO)
            .build();
        GameService service = new OnlineGameService(new MemoryRepository(board));

        service.clearGame();

        assertAll(
            () -> assertThat(service.allPieces()).isEmpty(),
            () -> assertThat(service.currentTurn()).isEqualTo(Team.CHO)
        );
    }

    @Test
    @DisplayName("점수가 더 높은 팀을 알 수 있다.")
    void higherScoreTeam() {
        final var board = new BoardFixture()
            .addPiece(5, 2, PieceType.CHA, Team.HAN)
            .addPiece(5, 9, PieceType.MA, Team.CHO)
            .build();
        GameService service = new OnlineGameService(new MemoryRepository(board));

        final var team = service.higherScoreTeam();

        assertThat(team).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("각 팀의 점수를 알 수 있다.")
    void scoreTeams() {
        final var board = new BoardFixture()
            .addPiece(5, 2, PieceType.CHA, Team.HAN)
            .addPiece(5, 9, PieceType.MA, Team.CHO)
            .build();
        GameService service = new OnlineGameService(new MemoryRepository(board));

        Map<Team, Double> teamScores = service.scoreTeams();

        assertAll(
            () -> assertThat(teamScores.get(Team.HAN)).isEqualTo(14.5),
            () -> assertThat(teamScores.get(Team.CHO)).isEqualTo(5)
        );
    }
}
