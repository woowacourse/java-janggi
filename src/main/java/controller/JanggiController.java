package controller;

import domain.Game;
import domain.board.BoardInitializer;
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
    private final BoardInitializer boardInitializer;

    public JanggiController(InputView inputView, OutputView outputView, BoardInitializer boardInitializer) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardInitializer = boardInitializer;
    }

    public void play() {
        Game game = new Game(boardInitializer);

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

    private static <T> T readUntilValid(Supplier<T> reader) {
        while (true) {
            try {
                return reader.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
