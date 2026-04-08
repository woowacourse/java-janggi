package domain.state;

import domain.setup.Command;
import domain.game.JanggiGame;
import domain.setup.Arrangement;
import domain.setup.Arrangements;
import domain.piece.Team;
import io.OutputView;
import java.util.Optional;

public class ReadyState implements GameState {
    private final Arrangements arrangements;

    public ReadyState(Arrangements arrangements) {
        this.arrangements = arrangements;
    }

    @Override
    public GameState handle(JanggiGame game, Command command) {
        Arrangement arrangement = command.toArrangement();

        if (!arrangements.hasArrangementFor(Team.HAN)) {
            Arrangements nextArrangements = arrangements.assignArrangement(Team.HAN, arrangement);
            return new ReadyState(nextArrangements);
        }

        game.setupBoard(arrangements.assignArrangement(Team.CHO, arrangement));
        game.nextTurn();
        return new PlayingState();
    }

    @Override
    public void display(JanggiGame game, OutputView outputView) {
        outputView.printSetupTable(game.getTurn());
    }

    @Override
    public GameStateName stateName() {
        if (arrangements.hasArrangementFor(Team.HAN)) {
            return GameStateName.READY_CHO;
        }
        return GameStateName.READY_HAN;
    }

    @Override
    public Optional<Arrangement> getArrangementOf(Team team) {
        if (!arrangements.hasArrangementFor(team)) {
            return Optional.empty();
        }
        return Optional.of(arrangements.arrangeFor(team));
    }
}
