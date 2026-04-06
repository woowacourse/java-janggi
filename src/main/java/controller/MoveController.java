package controller;

import domain.Game;
import domain.coordinate.Position;
import mapper.PossibleMovesMapper;
import view.InputHandler;
import view.InputView;
import view.OutputView;

import java.util.List;

public class MoveController implements GameCommand {

    @Override
    public void execute(InputView inputView, OutputView outputView, Game game) {
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

        if (!game.isSafe()) {
            outputView.printCheckMessage();
        }
    }

    private Position createPosition(List<Integer> inputs) {
        int col = inputs.get(0);
        int row = inputs.get(1);
        return Position.of(col, row);
    }
}
