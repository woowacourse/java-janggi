package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import janggi.TestDataInitializer;
import janggi.config.TestDataSourceConfig;
import janggi.domain.game.Game;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRepositoryTest {

    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        DataSource dataSource = TestDataSourceConfig.getDataSource();
        TestDataInitializer.initialize(dataSource);
        gameRepository = new GameRepository(dataSource);
    }

    @Test
    void 새_게임을_저장한다() {
        // given
        Game game = new Game(CampType.CHO, GameStatus.PLAYING, List.of(new Piece(PieceRule.CHARIOT, CampType.CHO)));
        // when
        long gameId = gameRepository.save(game);
        // then
        Game found = gameRepository.findById(gameId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(found.getCurrentTurn()).isEqualTo(CampType.CHO);
            softly.assertThat(found.getGameStatus()).isEqualTo(GameStatus.PLAYING);
        });
    }

    @Test
    void 특정_게임_상태를_가진_게임_아이디를_조회한다() {
        // when
        List<Long> result = gameRepository.findAllByGameStatus(GameStatus.PLAYING);
        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void 게임_아이디를_통해_게임을_조회한다() {
        // given
        Game game = new Game(CampType.CHO, GameStatus.PLAYING, List.of(new Piece(PieceRule.CHARIOT, CampType.CHO)));
        long gameId = gameRepository.save(game);
        // when
        Game result = gameRepository.findById(gameId);
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.getCurrentTurn()).isEqualTo(CampType.CHO);
            softly.assertThat(result.getGameStatus()).isEqualTo(GameStatus.PLAYING);
        });
    }

    @Test
    void 게임_데이터를_변경한다() {
        // given
        Game game = new Game(CampType.CHO, GameStatus.PLAYING, List.of(new Piece(PieceRule.CHARIOT, CampType.CHO)));
        long gameId = gameRepository.save(game);
        Game savedGame = gameRepository.findById(gameId);
        // when
        savedGame.changeTurn(CampType.HAN);
        gameRepository.update(savedGame);
        // then
        Game result = gameRepository.findById(gameId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.getCurrentTurn()).isEqualTo(CampType.HAN);
            softly.assertThat(result.getGameStatus()).isEqualTo(GameStatus.PLAYING);
        });
    }
}
