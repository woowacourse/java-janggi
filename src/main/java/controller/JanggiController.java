package controller;

import domain.JanggiGame;
import domain.Player;
import domain.Position;
import domain.boardgenerator.JanggiBoardGenerator;
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
        List<Player> playerNames = inputView.readPlayerNames();
        JanggiGame janggiGame = new JanggiGame(new JanggiBoardGenerator(), playerNames);
        outputView.displayPlayerInfo(playerNames);
        outputView.displayJanggiBoard(janggiGame.getBoardState());
        while (true) {
            Player thisTurnPlayer = janggiGame.getThisTurnPlayer();
            GameProcess(thisTurnPlayer, janggiGame);
            outputView.displayJanggiBoard(janggiGame.getBoardState());
            if (janggiGame.checkKingIsDead()) {
                outputView.displayJanggiBoard(janggiGame.getBoardState());
                outputView.displayGameResult(thisTurnPlayer);
                return;
            }
        }
    }

    private void GameProcess(Player thisTurnPlayer, JanggiGame janggiGame) {
        while (true) {
            try {
                List<Integer> startRowAndColumn = inputView.readMovePiecePosition(thisTurnPlayer);
                List<Integer> targetRowAndColumn = inputView.readTargetPosition(thisTurnPlayer);
                Position startPosition = new Position(startRowAndColumn.getFirst(), startRowAndColumn.getLast());
                Position targetPosition = new Position(targetRowAndColumn.getFirst(), targetRowAndColumn.getLast());
                janggiGame.move(startPosition, targetPosition);
                return;
            } catch (IllegalArgumentException iae) {
                outputView.displayErrorMessage(iae.getMessage());
            }
        }


    }
}
