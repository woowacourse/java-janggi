package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.King;
import domain.piece.Piece;
import domain.strategy.NoInitializeStrategy;
import java.util.Map;
import org.junit.jupiter.api.Test;
import strategy.InitializeStrategy;

class GameManagerTest {
    private final InitializeStrategy noInitializeStrategy = new NoInitializeStrategy();
    private final InitializeStrategy onlyChosKingExistInitializeStrategy = new OnlyChosKingExistInitializeStrategy();
    private final InitializeStrategy onlyHansKingExistInitializeStrategy = new OnlyHansKingExistInitializeStrategy();

    static class OnlyChosKingExistInitializeStrategy extends InitializeStrategy {
        @Override
        protected Map<Position, Piece> initializeElephantHorseFormation(Team team) {
            Position position = Position.from(9, 5);
            return Map.of(position, new King(Team.CHO));
        }
    }

    static class OnlyHansKingExistInitializeStrategy extends InitializeStrategy {
        @Override
        protected Map<Position, Piece> initializeElephantHorseFormation(Team team) {
            Position position = Position.from(2, 5);
            return Map.of(position, new King(Team.HAN));
        }
    }

    @Test
    void 초나라_턴인_경우_초나라의_궁이_존재하지_않는_경우_게임_종료() {
        //given
        Game game = new Game(Map.of(
                Team.CHO, noInitializeStrategy,
                Team.HAN, noInitializeStrategy
        ));

        //then
        assertThat(game.isGameFinished(Team.CHO)).isEqualTo(true);
    }

    @Test
    void 한나라_턴인_경우_한나라의_궁이_존재하지_않는_경우_게임_종료() {
        //given
        Game game = new Game(Map.of(
                Team.CHO, noInitializeStrategy,
                Team.HAN, noInitializeStrategy
        ));

        //then
        assertThat(game.isGameFinished(Team.HAN)).isEqualTo(true);
    }

    @Test
    void 초나라_턴인_경우_초나라의_궁이_존재하는_경우_게임_진행() {
        //given
        Game game = new Game(Map.of(
                Team.CHO, onlyChosKingExistInitializeStrategy,
                Team.HAN, onlyChosKingExistInitializeStrategy
        ));

        //then
        assertThat(game.isGameFinished(Team.CHO)).isEqualTo(false);
    }

    @Test
    void 한나라_턴인_경우_한나라의_궁이_존재하는_경우_게임_진행() {
        //given
        Game game = new Game(Map.of(
                Team.CHO, onlyChosKingExistInitializeStrategy,
                Team.HAN, onlyChosKingExistInitializeStrategy
        ));

        //then
        assertThat(game.isGameFinished(Team.HAN)).isEqualTo(false);
    }
}
