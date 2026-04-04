package janggi.controller;

import janggi.domain.game.Game;
import janggi.domain.point.Point;
import janggi.service.MoveService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class MoveController {
    private final InputView inputView;
    private final OutputView outputView;
    private final MoveService moveService;

    public MoveController(InputView inputView, OutputView outputView, MoveService moveService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.moveService = moveService;
    }

    public void move(Game game) {
        Point from = readPoint(game);
        outputView.printBoardWithPath(game.getBoard(), game.destinations(from));

        Point to = inputView.readDestination();
        if (to == null) {
            return;
        }
        moveService.move(game, from, to);
    }

    private Point readPoint(Game game) {
        Point from = inputView.readPoint();
        if (!game.isTurnPiece(from)) {
            throw new IllegalArgumentException("움직일 수 없습니다.");
        }
        return from;
    }
}
