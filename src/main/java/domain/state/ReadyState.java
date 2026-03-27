package domain.state;

import domain.command.Command;
import domain.game.JanggiGame;
import domain.vo.Arrangement;
import domain.vo.Arrangements;
import domain.vo.Team;
import io.OutputView;
import java.util.Map;

public class ReadyState implements GameState {
    private final Map<Team, Arrangement> arrangements;

    public ReadyState(Map<Team, Arrangement> arrangements) {
        this.arrangements =  arrangements;
    }

    @Override
    public GameState handle(JanggiGame game, Command command) {
        Com
        arrangements.putIfAbsent()


        Arrangements arrangements = new Arrangements(
                readArrangement(Team.HAN),
                readArrangement(Team.CHO)
        );

        game.setupBoard(arrangements);

        return null;
    }


    @Override
    public void display(JanggiGame game, OutputView outputView) {
        outputView.printSetupTable(game.getTurn().getTeam());
    }

}
