package controller;

import domain.game.JanggiGame;
import domain.game.Team;
import domain.position.Position;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        JanggiGame game = createGame();
        outputView.printBoard(game.getBoard());
        while (game.isRunning()) {
            playTurn(game);
        }
    }

    private JanggiGame createGame() {
        int choFormation = inputView.initialFormation(Team.CHO);
        int hanFormation = inputView.initialFormation(Team.HAN);
        return JanggiGame.of(choFormation, hanFormation);
    }

    private void playTurn(JanggiGame game) {
        while (true) {
            try {
                List<Position> positions = inputView.askMovePiecePosition(game.currentTurn());
                game.move(positions.get(0), positions.get(1));
                outputView.printBoard(game.getBoard());
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
