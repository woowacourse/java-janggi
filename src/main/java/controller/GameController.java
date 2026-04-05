package controller;

import domain.board.SangSetup;
import domain.board.SangSetupType;
import domain.game.JanggiGame;
import domain.pieces.Side;
import domain.position.Position;
import view.BoardViewMapper;
import view.InputView;
import view.OutputView;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;
    private final BoardViewMapper boardViewMapper = new BoardViewMapper();

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        SangSetup choSangSetup = readSangSetup(Side.CHO);
        SangSetup hanSangSetup = readSangSetup(Side.HAN);

        JanggiGame game = JanggiGame.of(choSangSetup, hanSangSetup);
        outputView.printBoard(boardViewMapper.map(game.board()));

        while (true) {
            try {
                outputView.printTurn(game.currentTurn());
                outputView.printMoveGuide();

                Position departure = inputView.readDeparturePosition();
                Position destination = inputView.readDestinationPosition();

                game.move(departure, destination);
                outputView.printBoard(boardViewMapper.map(game.board()));
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private SangSetup readSangSetup(Side side) {
        try {
            outputView.printSangSetupType(side);
            SangSetupType type = inputView.readSangSetupType();
            return type.create();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return readSangSetup(side);
        }
    }
}
