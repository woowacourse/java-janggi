package janggi.repository;

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
    void 새_게임을_저장할_수_있다() {
        // given
        Game game = new Game(CampType.CHO, GameStatus.PLAYING, List.of(new Piece(PieceRule.CHARIOT, CampType.CHO)));
        // when
        long gameId = gameRepository.save(game);
        // then
        Game found = gameRepository.findByGameId(gameId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(found.getCurrentTurn()).isEqualTo(CampType.CHO);
            softly.assertThat(found.getGameStatus()).isEqualTo(GameStatus.PLAYING);
        });
    }

}
