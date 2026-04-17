package controller;

import domain.Team;
import domain.position.Position;
import java.util.List;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        janggiService.startOrLoadGame();
        boolean isRunning = true;
        while (isRunning) {
            try {
                outputView.printBoard(janggiService.getBoardDto());
                Position from = createPosition(inputView.inputMovePiece());
                Position to = createPosition(inputView.inputTargetPosition());
                janggiService.play(from, to);
                if (janggiService.isGameOver()) {
                    isRunning = false;
                }
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
        outputView.printResult(
                janggiService.calculateScore(Team.CHO),
                janggiService.calculateScore(Team.HAN)
        );
    }

    private Position createPosition(List<Integer> coordinates) {
        return new Position(coordinates.get(0), coordinates.get(1));
    }
}
