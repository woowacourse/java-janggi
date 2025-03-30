package janggi;

import janggi.dao.entity.GameEntity;
import janggi.domain.JanggiEnded;
import janggi.domain.JanggiStatus;
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
            GameEntity gameEntity = janggiService.findRunningGame();
            if (gameEntity == null) {
                janggiService.createGame(
                        initializeView.readBoardSetUp(Dynasty.CHU),
                        initializeView.readBoardSetUp(Dynasty.HAN));
                gameEntity = janggiService.findRunningGame();
            }
            play(gameEntity);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void play(GameEntity gameEntity) {
        JanggiStatus janggiStatus = janggiService.findJaggiStatusByGameId(gameEntity.getId());

        janggiBoardView.printGameStartMessage();
        janggiBoardView.printBoard(janggiStatus.janggiBoard());

        while (!janggiStatus.isEndGame()) {
            try {
                Movement movement = janggiBoardView.readPlayerMove(janggiStatus.currentTurn());
                Point from = new Point(movement.startX(), movement.startY());
                Point to = new Point(movement.endX(), movement.endY());

                janggiStatus = janggiService.move(gameEntity.getId(), from, to);

                janggiBoardView.printBoard(janggiStatus.janggiBoard());
                janggiBoardView.printScore(janggiStatus.janggiBoard());
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        janggiBoardView.printResult((JanggiEnded) janggiStatus);
    }
}
