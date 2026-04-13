package controller;

import db.BoardDao;
import domain.board.JanggiGame;
import domain.Team;
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
        JanggiGame janggiGame = createGame();
        boolean isRunning = true;
        while (isRunning) {
            try {
                outputView.printBoard(janggiGame.getBoardDto());
                Position from = createPosition(inputView.inputMovePiece());
                Position to = createPosition(inputView.inputTargetPosition());
                janggiGame.playTurn(from, to);
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

    private JanggiGame createGame() {
        long latestId = new BoardDao().findLatestPlaying();
        if (latestId != -1) {
            System.out.println("이전 게임을 불러옵니다.");
            return new JanggiGame(latestId);
        }
        System.out.println("새 게임을 시작합니다.");
        return new JanggiGame();
    }

    private Position createPosition(List<Integer> coordinates) {
        return new Position(coordinates.get(0), coordinates.get(1));
    }
}
