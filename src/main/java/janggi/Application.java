package janggi;

import janggi.controller.JanggiController;
import janggi.domain.JanggiGameManager;
import janggi.domain.turn.ChoTurn;
import janggi.domain.board.Board;
import janggi.domain.strategy.BasicPlacementStrategy;
import janggi.infrastructure.BoardRepository;
import janggi.infrastructure.JDBCBoardRepository;
import janggi.service.JanggiService;

public class Application {
    public static void main(String[] args) {
        BoardRepository boardRepository = new JDBCBoardRepository();
        JanggiService janggiService = new JanggiService(boardRepository);
        JanggiController janggiController = new JanggiController(janggiService);

        janggiController.run();
    }
}
