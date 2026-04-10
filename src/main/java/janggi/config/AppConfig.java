package janggi.config;

import janggi.controller.JanggiController;
import janggi.repository.game.GameRepository;
import janggi.repository.game.JdbcGameRepository;
import janggi.repository.movement.JdbcMovementRepository;
import janggi.repository.movement.MovementRepository;
import janggi.repository.piece.JdbcPieceRepository;
import janggi.repository.piece.PieceRepository;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class AppConfig {

    private JanggiController controller;
    private JanggiService service;

    private GameRepository gameRepository;
    private PieceRepository pieceRepository;
    private MovementRepository movementRepository;

    private InputView inputView;
    private OutputView outputView;

    public JanggiController controller() {
        if (controller == null) {
            controller = new JanggiController(service(), inputView(), outputView());
        }
        return controller;
    }

    private JanggiService service() {
        if (service == null) {
            service = new JanggiService(gameRepository(), pieceRepository(), movementRepository());
        }
        return service;
    }

    private GameRepository gameRepository() {
        if (gameRepository == null) {
            gameRepository = new JdbcGameRepository();
        }
        return gameRepository;
    }

    private PieceRepository pieceRepository() {
        if (pieceRepository == null) {
            pieceRepository = new JdbcPieceRepository();
        }
        return pieceRepository;
    }

    private MovementRepository movementRepository() {
        if (movementRepository == null) {
            movementRepository = new JdbcMovementRepository();
        }
        return movementRepository;
    }

    private InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    private OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }

}
