package controller;

import domain.board.Game;
import domain.board.BasicBoardInitializer;
import domain.board.strategy.InitialStrategyType;
import domain.board.Side;
import domain.coordinate.Position;
import dto.PossibleMovesDto;
import mapper.BoardMapper;
import mapper.PossibleMovesMapper;
import view.InputView;
import view.OutputView;

import java.util.function.Supplier;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Game game = initializeGame();
        playGame(game);
    }

    private Game initializeGame() {
        InitialStrategyType hanSideInitialType = readUntilValid(() -> inputView.requestInitialType(Side.HAN));
        InitialStrategyType chuSideInitialType = readUntilValid(() -> inputView.requestInitialType(Side.CHU));
        return new Game(
                new BasicBoardInitializer(
                        hanSideInitialType.from(Side.HAN),
                        chuSideInitialType.from(Side.CHU)
                )
        );
    }

    private void playGame(Game game) {
        while (true) {
            outputView.printBoard(BoardMapper.toDto(game.getBoard().getBoard()));
            Position startPosition = readUntilValid(() ->
                    game.getValidatedStartPosition(inputView.requestStartPiecePosition(game.getTurn()))
            );

            PossibleMovesDto possibleMovesDto = PossibleMovesMapper.toDto(game.getPossibleMoves(startPosition));
            Position endPosition = readUntilValid(() ->
                    game.getEndPosition(inputView.requestPieceDestination(possibleMovesDto), possibleMovesDto)
            );
            game.movePiece(startPosition, endPosition);
        }
    }

    private <T> T readUntilValid(Supplier<T> reader) {
        while (true) {
            try {
                return reader.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
