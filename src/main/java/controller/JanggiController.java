package controller;

import domain.board.formation.FormationType;
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
            outputView.printBoard(game.getBoard());
        }

        outputView.printResult(game.scoreOf(Team.CHO), game.scoreOf(Team.HAN));
    }

    private JanggiGame createGame() {
        FormationType choFormation = FormationConverter.convert(inputView.initialFormation(Team.CHO));
        FormationType hanFormation = FormationConverter.convert(inputView.initialFormation(Team.HAN));
        return JanggiGame.of(choFormation, hanFormation);
    }

    private void playTurn(JanggiGame game) {
        boolean isTurnCompleted = false;
        while (!isTurnCompleted) {
            isTurnCompleted = executeTurn(game);
        }
    }

    private boolean executeTurn(JanggiGame game) {
        try {
            String input = inputView.askTurnInput(game.currentTurn());
            if (inputView.isPass(input)) {
                game.pass();
                return true;
            }
            List<Position> positions = inputView.parseMoveInput(input);
            game.move(positions.get(0), positions.get(1));
            return true;
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return false;
        }
    }
}
