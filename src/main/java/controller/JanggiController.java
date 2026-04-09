package controller;

import domain.piece.Side;
import domain.position.Movement;
import domain.position.Position;
import service.JanggiService;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static global.util.Retry.retry;

public class JanggiController {

    private final JanggiService janggiService;

    private static final int CHOICE_1 = 1;
    private static final int CHOICE_2 = 2;
    private static final int CHOICE_3 = 3;

    public JanggiController(JanggiService janggiService) {
        this.janggiService = janggiService;
    }

    public void run() {
        while (true) {
            int startChoice = retry(InputView::inputGameStartChoice);
            if (startChoice == CHOICE_1) {
                selectSide();
                Long gameId = initialGame();
                playGame(gameId);
            }
            if (startChoice == CHOICE_2) {
                Long gameId = InputView.inputExistingGameId();
                outputBoardStateBy(gameId);
                playGame(gameId);
            }
            if (startChoice == CHOICE_3) {
                return;
            }
        }
    }


    private void selectSide() {
        retry(() -> {
            int sideCode = InputView.inputSideChoice();
            Side side = generateSide(sideCode);
            OutputView.printSideChoiceResult(side);
        });
    }

    private Side generateSide(int sideCode) {
        List<Side> sides = Arrays.asList(Side.values());
        Collections.shuffle(sides);
        return sides.get(sideCode - 1);
    }

    private Long initialGame() {
        return retry(() -> {
            int hanPlacementCode = InputView.inputPlacementCodeBy(Side.HAN);
            int choPlacementCode = InputView.inputPlacementCodeBy(Side.CHO);
            Long gameId = janggiService.initialBoardState(hanPlacementCode, choPlacementCode);
            outputBoardStateBy(gameId);
            return gameId;
        });
    }

    private void playGame(Long gameId) {
        retry(() -> {
            while (!janggiService.isFinished(gameId)) {
                Movement movement = inputAndParseToMove(gameId);
                janggiService.playGame(gameId, movement);
                outputBoardStateBy(gameId);
            }
        });
    }

    private void outputBoardStateBy(Long gameId) {
        OutputView.printBoard(janggiService.getBoardState(gameId));
    }

    private Movement inputAndParseToMove(Long gameId) {
        Side currentTurn = janggiService.getWhoseTurn(gameId);
        Position startPosition = InputView.inputStartPosition(currentTurn);
        Position endPosition = InputView.inputEndPosition(currentTurn);
        return new Movement(startPosition, endPosition);
    }
}
