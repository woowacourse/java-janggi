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
            outputView.printBoard(BoardMapper.toDto(game.getBoard()));
            Position startPosition = readUntilValid(() -> {
                Position pos = getStartPosition(game);
                game.getPossibleMoves(pos);
                return pos;
            });

            PossibleMovesDto possibleMovesDto = PossibleMovesMapper.toDto(game.getPossibleMoves(startPosition));
            Position destination = readUntilValid(() -> game.getDestination(inputView.requestPieceDestination(possibleMovesDto), possibleMovesDto));
            game.movePiece(startPosition, destination);
        }
    }

    private Position getStartPosition(Game game) {
        Position position = inputView.requestStartPiecePosition(game.getTurn());
        game.validateStartPosition(position);
        return position;
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
