package controller;

import domain.Game;
import domain.coordinate.Position;
import mapper.PossibleMovesMapper;
import view.InputHandler;
import view.InputView;
import view.OutputView;

import java.util.List;

public class MoveController implements GameCommand {

    private final InputView inputView;
    private final OutputView outputView;

    public MoveController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
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
        handleGameStateChange(game);
    }

    private void handleGameStateChange(Game game) {
        if (game.isKingDead()) {
            game.end();
            outputView.printKingDeadMessage(game.getSide().opposite());
            return;
        }

        if (game.isCheckmate()) {
            game.end();
            outputView.printCheckMateMessage();
            return;
        }

        if (!game.isSafe()) {
            outputView.printCheckMessage();
        }

        game.pass();
    }

    private Position createPosition(List<Integer> inputs) {
        int col = inputs.get(0);
        int row = inputs.get(1);
        return Position.of(col, row);
    }
}
