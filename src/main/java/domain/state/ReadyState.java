package domain.state;

import domain.command.Command;
import domain.game.JanggiGame;
import domain.vo.Arrangement;
import domain.vo.Arrangements;
import domain.vo.Team;
import io.OutputView;

public class ReadyState implements GameState {
    private final Arrangements arrangements;

    public ReadyState(Arrangements arrangements) {
        this.arrangements = arrangements;
    }

    @Override
    public GameState handle(JanggiGame game, Command command) {
        Arrangement arrangement = Arrangement.toArrangement(command.getValue());

        if (!arrangements.hasArrangementFor(Team.HAN)) {
            Arrangements nextArrangements = arrangements.assignArrangement(Team.HAN, arrangement);
            return new ReadyState(nextArrangements);
        }

        game.setupBoard(arrangements.assignArrangement(Team.CHO, arrangement));
        return new PlayingState();
    }


    @Override
    public void display(JanggiGame game, OutputView outputView) {
        outputView.printSetupTable(game.getTurn().getTeam());
    }

}
