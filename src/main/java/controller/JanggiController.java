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
        List<String> playerNames = inputView.readPlayerNames();
        JanggiGame janggiGame = new JanggiGame(new JanggiBoardGenerator(), playerNames);
        outputView.displayPlayerInfo(playerNames);
        outputView.printJanggiBoard(janggiGame.getBoardState());
        while (true) {
            Player thisTurnPlayer = janggiGame.getThisTurnPlayer();
            List<Integer> startRowAndColumn = inputView.readMovePiecePosition(thisTurnPlayer);
            List<Integer> targetRowAndColumn = inputView.readTargetPosition(thisTurnPlayer);
            Position startPosition = new Position(startRowAndColumn.getFirst(), startRowAndColumn.getLast());
            Position targetPosition = new Position(targetRowAndColumn.getFirst(), targetRowAndColumn.getLast());
            janggiGame.move(startPosition, targetPosition);

            outputView.printJanggiBoard(janggiGame.getBoardState());
        }
    }
}
