package controller;

import domain.JanggiGame;
import domain.Team;
import domain.position.Position;
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
        JanggiGame janggiGame = new JanggiGame();
        boolean isRunning = true;
        while (isRunning) {
            try {
                outputView.printBoard(janggiGame.getBoardDto());
                Position from = inputMovePosition();
                Position to = inputTargetPosition();
                janggiGame.playTurn(from, to);  // 턴 전체를 JanggiGame에 위임
                if (janggiGame.isGameOver()) {
                    isRunning = false;
                }
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
        outputView.printResult(
                janggiGame.calculateScore(Team.CHO),
                janggiGame.calculateScore(Team.HAN)
        );
    }

    private Position inputMovePosition() {
        String input = inputView.inputMovePiece();
        String[] parts = input.split(",");
        return new Position(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }

    private Position inputTargetPosition() {
        String input = inputView.inputTargetPosition();
        String[] parts = input.split(",");
        return new Position(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }
}
