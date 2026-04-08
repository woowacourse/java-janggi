package controller;

import domain.Game;
import domain.coordinate.Position;
import mapper.PossibleMovesMapper;
import view.InputHandler;
import view.InputView;

import java.util.List;

public class MoveController implements GameCommand {

    private final InputView inputView;

    public MoveController(InputView inputView) {
        this.inputView = inputView;
    }

    @Override
    public void execute(Game game) {
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
    }

    private Position createPosition(List<Integer> inputs) {
        int col = inputs.get(0);
        int row = inputs.get(1);
        return Position.of(col, row);
    }
}
