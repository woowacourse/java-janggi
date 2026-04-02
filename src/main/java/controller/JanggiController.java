package controller;

import domain.Game;
import domain.board.BasicBoardInitializer;
import domain.board.Side;
import domain.board.formation.InitialFormationType;
import domain.coordinate.Position;
import mapper.BoardMapper;
import mapper.PossibleMovesMapper;
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

    private void play(Game game) {
        while (true) {
            outputView.printBoard(BoardMapper.toDto(game.getBoard()));
            Position startPosition = InputHandler.readUntilValid(() ->
                    game.getValidatedStartPosition(inputView.requestStartPiecePosition(game.getTurn()))
            );

            List<Position> possibleMoves = game.getPossibleMoves(startPosition);
            Position endPosition = InputHandler.readUntilValid(() ->
                    game.getEndPosition(inputView.requestPieceDestination(PossibleMovesMapper.toDto(possibleMoves)), possibleMoves)
            );

            game.movePiece(startPosition, endPosition);
        }
    }
}
