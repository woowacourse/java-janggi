package controller;

import application.GamePersistenceService;
import domain.JanggiGame;
import domain.Position;
import java.util.List;
import utils.Parser;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final JanggiGame janggiGame;
    private final GamePersistenceService gamePersistenceService;

    public JanggiController(JanggiGame janggiGame, GamePersistenceService gamePersistenceService) {
        this.janggiGame = janggiGame;
        this.gamePersistenceService = gamePersistenceService;
    }

    public void run() {
        OutputView.printRemainScore(janggiGame.showTeamPieceScores());
        while (!janggiGame.isGameFinished()) {
            playGame();
            janggiGame.checkGameFinished();
            gamePersistenceService.save(janggiGame);
        }

        OutputView.printRemainScore(janggiGame.showTeamPieceScores());
        OutputView.printGameResult(janggiGame.gameStatus().description());
    }

    private void playGame() {
        OutputView.printBoard(janggiGame.allFactors());
        OutputView.printCurrentPlayerTurn(janggiGame.gameStatus().description());
        execute(this::playerPhase);
    }

    private void playerPhase() {
        Position selectedPosition = readSelectedPiecePosition();

        Position targetPosition = getTargetPosition();

        janggiGame.move(selectedPosition, targetPosition);
    }

    private Position readSelectedPiecePosition() {
        String rawSelectPiecePosition = InputView.selectPiecePosition();
        List<Integer> selectPiecePosition = Parser.parseDelimitedToIntegersStrict(rawSelectPiecePosition);
        Position selectedPosition = new Position(selectPiecePosition.getFirst(), selectPiecePosition.getLast());
        janggiGame.validatePieceSelection(selectedPosition);
        return selectedPosition;
    }

    private static Position getTargetPosition() {
        String rawTargetPosition = InputView.selectTargetPosition();
        List<Integer> target = Parser.parseDelimitedToIntegersStrict(rawTargetPosition);
        return new Position(target.getFirst(), target.getLast());
    }


    private void execute(ExecutableTask task) {
        while (true) {
            try {
                task.execute();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
