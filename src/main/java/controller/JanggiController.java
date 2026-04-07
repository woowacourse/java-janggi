package controller;

import domain.Game;
import domain.board.BasicBoardInitializer;
import domain.state.Side;
import domain.board.formation.InitialFormationType;
import mapper.BoardMapper;
import mapper.ScoreMapper;
import service.JanggiService;
import view.InputHandler;
import view.InputView;
import view.OutputView;

import java.util.Optional;

public class JanggiController {

    private static final Long DEFAULT_GAME_ID = 1L;

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        Game game = loadOrInitializeGame();
        play(game);
        outputView.printVictoryMessage(game.getSide());
    }

    private void play(Game game) {
        while (!game.isFinished()) {
            outputView.printBoard(BoardMapper.toDto(game.getBoard()));
            outputView.printScore(ScoreMapper.toDto(game.calculateScore(Side.CHU), game.calculateScore(Side.HAN)));

            GameCommand gameCommand = GameCommandFactory.create(
                    InputHandler.readUntilValid(() ->
                    inputView.requestGameCommand(game.getSide()))
            );
            gameCommand.execute(inputView, outputView, game);
            handleCheckMate(game);

            janggiService.save(game);
        }
    }

    private void handleCheckMate(Game game) {
        if (game.isKingDead()) {
            game.end();
            outputView.printKingDeadMessage(game.getSide());
        }

        if (!game.isSafe()) {
            outputView.printCheckMessage();
        }

        if (game.isCheckmate()) {
            outputView.printCheckMateMessage();
            game.end();
        }
    }

    private Game loadOrInitializeGame() {
        Optional<Game> savedGame = janggiService.load(DEFAULT_GAME_ID);

        if (savedGame.isPresent() && !savedGame.get().isFinished()) {
            outputView.printLoadGameMessage();
            return savedGame.get();
        }

        return initializeGame();
    }

    private Game initializeGame() {
        InitialFormationType hanInitialFormation = InputHandler.readUntilValid(() -> inputView.requestInitialType(Side.HAN));
        InitialFormationType chuInitialFormation = InputHandler.readUntilValid(() -> inputView.requestInitialType(Side.CHU));
        Game game = new Game(
                new BasicBoardInitializer(
                        hanInitialFormation.create(Side.HAN),
                        chuInitialFormation.create(Side.CHU)
                )
        );

        game.assignId(DEFAULT_GAME_ID);
        return game;
    }
}
