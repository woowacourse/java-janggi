package janggi;

import janggi.domain.team.Team;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;

public class JanggiController {
    private static final String END_COMMAND = "end";

    private final InputView inputView;
    private final OutputView outputView;
    private JanggiGame game;

    public JanggiController(InputView inputView, OutputView outputView, JanggiGame game) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.game = game;
    }

    public void run() {
        initializeGame();
        outputView.printBoard(game.getBoard(), game.getScore());
        startGame();
    }

    private void initializeGame() {
        while (true) {
            try {
                String hanSetup = inputView.readHanSetup();
                String choSetup = inputView.readChoSetup();
                game.initialize(hanSetup, choSetup);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void startGame() {
        Team currentTeam = Team.FIRST_TURN;
        while (!game.isFinished(currentTeam)) {
            try {
                List<String> positions = inputView.readPosition(currentTeam);
                if (isEndCommand(positions)) {
                    outputView.printGameEnd();
                    return;
                }
                currentTeam = processTurn(positions, currentTeam);
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
        outputView.printWinner(currentTeam.convert());
    }

    private Team processTurn(List<String> positions, Team currentTeam) {
        game.playTurn(positions, currentTeam);
        outputView.printBoard(game.getBoard(), game.getScore());
        return currentTeam.convert();
    }

    private boolean isEndCommand(List<String> positions) {
        return positions.getFirst().equals(END_COMMAND);
    }
}
