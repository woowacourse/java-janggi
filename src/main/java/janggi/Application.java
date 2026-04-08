package janggi;

import janggi.controller.JanggiController;
import janggi.domain.JanggiGameManager;
import janggi.domain.turn.ChoTurn;
import janggi.domain.board.Board;
import janggi.domain.strategy.BasicPlacementStrategy;
import janggi.infrastructure.JDBCBoardRepository;
import janggi.service.JanggiService;

public class Application {
    public static void main(String[] args) {
        JanggiGameManager janggiGameManager = new JanggiGameManager(
                new ChoTurn(new Board(new BasicPlacementStrategy())));
        JanggiService janggiService = new JanggiService(janggiGameManager, new JDBCBoardRepository());
        JanggiController janggiController = new JanggiController(janggiService);

        janggiController.run();
    }
}
