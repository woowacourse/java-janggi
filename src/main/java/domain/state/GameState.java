package domain.state;

import domain.setup.Arrangement;
import domain.setup.Command;
import domain.game.JanggiGame;
import domain.piece.Team;
import io.OutputView;
import java.util.Optional;

public interface GameState {
    GameState handle(JanggiGame game, Command command);
    void display(JanggiGame game, OutputView outputView);

    GameStateName stateName();

    default boolean isFinished() {
        return false;
    }

    default Optional<Arrangement> getArrangementOf(Team team) {
        return Optional.empty();
    }
}
