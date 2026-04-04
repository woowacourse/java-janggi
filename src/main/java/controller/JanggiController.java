package controller;

import domain.Game;
import domain.board.BasicBoardInitializer;
import domain.state.Side;
import domain.board.formation.InitialFormationType;
import domain.coordinate.Position;
import mapper.BoardMapper;
import mapper.PossibleMovesMapper;
import mapper.ScoreMapper;
import view.InputHandler;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Game game = initializeGame();
        play(game);
    }

    private void play(Game game) {
        while (!game.isFinished()) {
            outputView.printBoard(BoardMapper.toDto(game.getBoard()));
            outputView.printScore(ScoreMapper.toDto(game.calculateScore(Side.CHU), game.calculateScore(Side.HAN)));
            GameCommand command = InputHandler.readUntilValid(() -> inputView.requestGameCommand(game.getSide()));

            if (command == GameCommand.MOVE) {
                handleMove(game);
                continue;
            }

            if (command == GameCommand.PASS) {
                outputView.printTurnPassMessage(game.getSide());
                game.pass();
                continue;
            }

            if (command == GameCommand.SURRENDER) {
                outputView.printSurrenderMessage(game.getSide());
                game.end();
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

        if (game.isCheckMate()) {
            outputView.printCheckMateMessage();
            game.end();
        }

        if (game.isCheck()) {
            outputView.printCheckMessage();
        }
    }

    private Game initializeGame() {
        InitialFormationType hanInitialFormation = InputHandler.readUntilValid(() -> inputView.requestInitialType(Side.HAN));
        InitialFormationType chuInitialFormation = InputHandler.readUntilValid(() -> inputView.requestInitialType(Side.CHU));
        return new Game(
                new BasicBoardInitializer(
                        hanInitialFormation.create(Side.HAN),
                        chuInitialFormation.create(Side.CHU)
                )
        );
    }

    private Position createPosition(List<Integer> inputs) {
        int col = inputs.get(0);
        int row = inputs.get(1);
        return Position.of(col, row);
    }
}
