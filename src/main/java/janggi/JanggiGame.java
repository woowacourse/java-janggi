package janggi;

import janggi.dao.entity.GameEntity;
import janggi.domain.gamestatus.GameEnded;
import janggi.domain.gamestatus.GameStatus;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Point;
import janggi.service.JanggiService;
import janggi.view.InitializeView;
import janggi.view.JanggiBoardView;
import janggi.view.JanggiBoardView.Movement;

public class JanggiGame {

    private final InitializeView initializeView;
    private final JanggiBoardView janggiBoardView;
    private final JanggiService janggiService;

    public JanggiGame(InitializeView initializeView, JanggiBoardView janggiBoardView, JanggiService janggiService) {
        this.initializeView = initializeView;
        this.janggiBoardView = janggiBoardView;
        this.janggiService = janggiService;
    }

    public void start() {
        try {
            String gameName = initializeView.readGameName();
            if (gameName.equals("new")) {
                gameName = initializeView.readNewGameName();
                janggiService.createGame(
                        gameName,
                        initializeView.readBoardSetUp(Dynasty.CHU),
                        initializeView.readBoardSetUp(Dynasty.HAN));
            }
            GameEntity gameEntity = janggiService.getRunningGameByName(gameName);
            play(gameEntity);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void play(GameEntity gameEntity) {
        GameStatus gameStatus = janggiService.findJanggiStatusByGameId(gameEntity.getId());

        janggiBoardView.printGameStartMessage();
        janggiBoardView.printBoard(gameStatus.janggiBoard());

        while (!gameStatus.isEndGame()) {
            try {
                Movement movement = janggiBoardView.readPlayerMove(gameStatus.currentTurn());
                Point from = new Point(movement.startX(), movement.startY());
                Point to = new Point(movement.endX(), movement.endY());

                gameStatus = janggiService.move(gameEntity.getId(), from, to);

                janggiBoardView.printBoard(gameStatus.janggiBoard());
                janggiBoardView.printScore(gameStatus.janggiBoard());
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        janggiBoardView.printResult((GameEnded) gameStatus);
    }
}
