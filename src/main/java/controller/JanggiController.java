package controller;

import domain.Game;
import domain.board.BasicBoardInitializer;
import domain.state.Side;
import domain.board.formation.InitialFormationType;
import domain.coordinate.Position;
import mapper.BoardMapper;
import mapper.PossibleMovesMapper;
import mapper.ScoreMapper;
import service.JanggiService;
import view.InputHandler;
import view.InputView;
import view.OutputView;

import java.util.List;

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
    }

    private void play(Game game) {
        while (!game.isFinished()) {
            janggiService.save(game);
            outputView.printBoard(BoardMapper.toDto(game.getBoard()));
            outputView.printScore(ScoreMapper.toDto(game.calculateScore(Side.CHU), game.calculateScore(Side.HAN)));
            GameCommand command = InputHandler.readUntilValid(() -> inputView.requestGameCommand(game.getSide()));

            if (command == GameCommand.MOVE) {
                handleMove(game);
                janggiService.save(game);
                continue;
            }

            if (command == GameCommand.PASS) {
                outputView.printTurnPassMessage(game.getSide());
                game.pass();
                janggiService.save(game);
                continue;
            }

            if (command == GameCommand.SURRENDER) {
                outputView.printSurrenderMessage(game.getSide());
                game.end();
                janggiService.save(game);
            }
        }

        outputView.printVictoryMessage(game.getSide());
    }

    private void handleMove(Game game) {
        Position start = InputHandler.readUntilValid(() ->
                game.validateMoveable(createPosition(
                        inputView.requestStartPiecePosition(game.getSide())))
        );

        List<Position> possibleMoves = game.getPossibleMoves(start);
        Position dest = InputHandler.readUntilValid(() ->
                game.getEndPosition(
                        inputView.requestPieceDestination(PossibleMovesMapper.toDto(possibleMoves)),
                        possibleMoves)
        );

        game.movePiece(start, dest);

        if (game.isCheckmate()) {
            outputView.printCheckMateMessage();
            game.end();
        }

        if (game.isCheck()) {
            outputView.printCheckMessage();
        }
    }

    private Game loadOrInitializeGame() {
        Game savedGame = janggiService.load(DEFAULT_GAME_ID);

        if (savedGame != null && !savedGame.isFinished()) {
            outputView.printLoadGameMessage();
            return savedGame;
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

        game.assignId(1L);
        return game;
    }

    private Position createPosition(List<Integer> inputs) {
        int col = inputs.get(0);
        int row = inputs.get(1);
        return Position.of(col, row);
    }
}
