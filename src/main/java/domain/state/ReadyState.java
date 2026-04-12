package domain.state;

import domain.game.JanggiGame;
import domain.piece.Team;
import domain.setup.Arrangement;
import domain.setup.Arrangements;
import domain.setup.Command;

public class ReadyState implements GameState {
    private final Arrangements arrangements;

    public ReadyState(Arrangements arrangements) {
        this.arrangements = arrangements;
    }

    @Override
    public GameState handle(JanggiGame game, Command command) {
        Arrangement arrangement = command.toArrangement();

        if (arrangements.needsArrangementFor(Team.HAN)) {
            Arrangements nextArrangements = arrangements.assignArrangement(Team.HAN, arrangement);
            game.nextTurn();
            return new ReadyState(nextArrangements);
        }

        game.setupBoard(arrangements.assignArrangement(Team.CHO, arrangement));
        return new PlayingState();
    }

    @Override
    public GamePhase phase() {
        return GamePhase.READY;
    }
}
