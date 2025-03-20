package janggi.controller;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.gererator.ChoPieceGenerator;
import janggi.domain.piece.gererator.HanPieceGenerator;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Map.Entry;

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
            Entry<Integer, Integer> source = inputView.inputHanMoveSource();
            Entry<Integer, Integer> destination = inputView.inputHanMoveDestination();
            janggiBoard.move(source.getKey(), source.getValue(), destination.getKey(), destination.getValue());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            moveHan(janggiBoard);
        }
    }

    private void moveCho(JanggiBoard janggiBoard) {
        try {
            Entry<Integer, Integer> source = inputView.inputChoMoveSource();
            Entry<Integer, Integer> destination = inputView.inputChoMoveDestination();
            janggiBoard.move(source.getKey(), source.getValue(), destination.getKey(), destination.getValue());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            moveCho(janggiBoard);
        }
    }
}
