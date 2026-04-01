package controller;

import domain.Game;
import domain.board.BasicBoardInitializer;
import domain.board.formation.InitialFormation;
import domain.board.Side;
import domain.coordinate.Position;
import dto.PossibleMovesDto;
import mapper.BoardMapper;
import mapper.PossibleMovesMapper;
import view.InputHandler;
import view.InputView;
import view.OutputView;

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
        InitialFormation hanInitialFormation = InputHandler.readUntilValid(() -> inputView.requestInitialType(Side.HAN));
        InitialFormation chuInitialFormation = InputHandler.readUntilValid(() -> inputView.requestInitialType(Side.CHU));
        return new Game(
                new BasicBoardInitializer(
                        hanInitialFormation,
                        chuInitialFormation
                )
        );
    }

    private void play(Game game) {
        while (true) {
            outputView.printBoard(BoardMapper.toDto(game.getBoard()));
            Position startPosition = InputHandler.readUntilValid(() ->
                    game.getValidatedStartPosition(inputView.requestStartPiecePosition(game.getTurn()))
            );

            PossibleMovesDto possibleMovesDto = PossibleMovesMapper.toDto(game.getPossibleMoves(startPosition));
            Position endPosition = InputHandler.readUntilValid(() ->
                    game.getEndPosition(inputView.requestPieceDestination(possibleMovesDto), possibleMovesDto)
            );

            game.movePiece(startPosition, endPosition);
        }
    }
}
