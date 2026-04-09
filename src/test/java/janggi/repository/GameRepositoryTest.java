package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.JanggiGame;
import janggi.domain.piece.King;
import janggi.domain.piece.Team;
import janggi.infrastructure.DBInitializer;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRepositoryTest {

    private final GameRepository gameRepository = new GameRepository();

    @BeforeEach
    void setUp() {
        DBInitializer.initialize();
    }

    @Test
    void 새게임을_저장하고_ID로_조회한다() {
        JanggiGame game = gameRepository.save(new JanggiGame());

        JanggiGame found = gameRepository.findById(game.findGameId());

        assertThat(found.findGameId()).isEqualTo(game.findGameId());
        assertThat(found.findCurrentTeam()).isEqualTo(Team.CHO);
        assertThat(found.isFinished()).isFalse();
        assertThat(found.findWinner()).isNull();
    }

    @Test
    void 턴_변경_후_디비에반영이된다() {
        JanggiGame game = gameRepository.save(new JanggiGame());
        game.changeTurn();

        gameRepository.updateTurn(game);
        JanggiGame found = gameRepository.findById(game.findGameId());

        assertThat(found.findCurrentTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 게임_종료_후_갱신한다() {
        JanggiGame game = gameRepository.save(new JanggiGame());
        game.processCaptured(new King(Team.HAN));

        gameRepository.updateFinished(game);

        JanggiGame found = gameRepository.findById(game.findGameId());
        assertThat(found.isFinished()).isTrue();
        assertThat(found.findWinner()).isEqualTo(Team.CHO);
    }

    @Test
    void 진행_중인_게임_목록을_조회한다() {
        JanggiGame game1 = gameRepository.save(new JanggiGame());
        JanggiGame game2 = gameRepository.save(new JanggiGame());

        game2.processCaptured(new King(Team.HAN));
        gameRepository.updateFinished(game2);

        List<JanggiGame> playingGames = gameRepository.findPlayingGames();

        boolean containsGame1 = playingGames.stream()
                .anyMatch(g -> g.findGameId().equals(game1.findGameId()));
        assertThat(containsGame1).isTrue();

        boolean containsGame2 = playingGames.stream()
                .anyMatch(g -> g.findGameId().equals(game2.findGameId()));
        assertThat(containsGame2).isFalse();
    }
}
