import domain.game.FormationType;
import domain.game.Game;
import domain.game.GameStatus;
import domain.game.TurnManager;
import strategy.formation.InitialFormationStrategy;

public class NewGameFactory {

    public Game create(FormationType choFormation, FormationType hanFormation) {
        InitialFormationStrategy choStrategy = choFormation.createStrategy();
        InitialFormationStrategy hanStrategy = hanFormation.createStrategy();

        BoardInitializer boardInitializer = new BoardInitializer(choStrategy, hanStrategy);
        return new Game(
                boardInitializer.initialize(),
                new TurnManager(),
                GameStatus.IN_PROGRESS
        );
    }
}
