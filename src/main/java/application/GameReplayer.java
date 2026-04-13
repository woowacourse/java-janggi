package application;

import domain.game.JanggiGame;
import domain.setup.Command;
import java.util.List;

public class GameReplayer {

    public JanggiGame replay(List<String> rawCommands) {
        JanggiGame game = new JanggiGame();
        rawCommands.forEach(rawCommand -> game.processCommand(new Command(rawCommand)));
        return game;
    }
}
