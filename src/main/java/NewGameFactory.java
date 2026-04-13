import domain.game.FormationType;
import domain.game.Game;
import domain.game.GameStatus;
import domain.game.TurnManager;
import strategy.formation.FormationStrategyFactory;
import strategy.formation.InitialFormationStrategy;

public class NewGameFactory {
    private final FormationStrategyFactory formationStrategyFactory;

    public NewGameFactory() {
        this(new FormationStrategyFactory());
    }

    public NewGameFactory(FormationStrategyFactory formationStrategyFactory) {
        this.formationStrategyFactory = formationStrategyFactory;
    }

    public Game create(FormationType choFormation, FormationType hanFormation) {
        InitialFormationStrategy choStrategy = formationStrategyFactory.create(choFormation);
        InitialFormationStrategy hanStrategy = formationStrategyFactory.create(hanFormation);

        BoardInitializer boardInitializer = new BoardInitializer(choStrategy, hanStrategy);
        return new Game(
                boardInitializer.initialize(),
                new TurnManager(),
                GameStatus.IN_PROGRESS
        );
    }
}
