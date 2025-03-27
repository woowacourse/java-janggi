package janggi.controller;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Position;
import janggi.domain.piece.generator.ChoPieceGenerator;
import janggi.domain.piece.generator.HanPieceGenerator;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Controller {

    private final InputView inputView;
    private final OutputView outputView;

    private final HanPieceGenerator hanPieceGenerator;
    private final ChoPieceGenerator choPieceGenerator;

    public Controller(
        InputView inputView,
        OutputView outputView,
        HanPieceGenerator hanPieceGenerator,
        ChoPieceGenerator choPieceGenerator
    ) {

        this.inputView = inputView;
        this.outputView = outputView;
        this.hanPieceGenerator = hanPieceGenerator;
        this.choPieceGenerator = choPieceGenerator;
    }

    public void run() {

        JanggiBoard janggiBoard = makeJanggiBoard();
        outputView.printJanggiBoard(janggiBoard);
        playGame(janggiBoard);
    }

    private JanggiBoard makeJanggiBoard() {
        KnightElephantSettingCommand hanKnightElephantSettingCommand = inputView.inputHanKnightElephantSetting();
        KnightElephantSettingCommand choKnightElephantSettingCommand = inputView.inputChoKnightElephantSetting();

        return new JanggiBoard(
            hanPieceGenerator,
            choPieceGenerator,
            hanKnightElephantSettingCommand.getKnightElephantSetting(),
            choKnightElephantSettingCommand.getKnightElephantSetting()
        );
    }

    private void playGame(JanggiBoard janggiBoard) {
        while (!janggiBoard.isEnd()) {
            moveHan(janggiBoard);

            outputView.printJanggiBoard(janggiBoard);
            moveCho(janggiBoard);

            outputView.printJanggiBoard(janggiBoard);
        }
        outputView.printWinner(janggiBoard.getWinner());
    }

    private void moveHan(JanggiBoard janggiBoard) {
        try {
            Position source = inputView.inputHanMoveSource();
            Position destination = inputView.inputHanMoveDestination();
            janggiBoard.move(source.x(), source.y(), destination.x(), destination.y());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            moveHan(janggiBoard);
        }
    }

    private void moveCho(JanggiBoard janggiBoard) {
        try {
            Position source = inputView.inputChoMoveSource();
            Position destination = inputView.inputChoMoveDestination();
            janggiBoard.move(source.x(), source.y(), destination.x(), destination.y());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            moveCho(janggiBoard);
        }
    }
}
