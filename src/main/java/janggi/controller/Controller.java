package janggi.controller;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.gererator.DefaultChoPieceGenerator;
import janggi.domain.piece.gererator.DefaultHanPieceGenerator;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.Map.Entry;

public class Controller {

    private final InputView inputView;
    private final OutputView outputView;

    public Controller(
            InputView inputView,
            OutputView outputView
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
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
                new DefaultHanPieceGenerator(),
                new DefaultChoPieceGenerator(),
                hanKnightElephantSettingCommand.getKnightElephantSetting(),
                choKnightElephantSettingCommand.getKnightElephantSetting()
        );
    }

    private void playGame(JanggiBoard janggiBoard) {
        while (!janggiBoard.isEnd()) {
            moveHan(janggiBoard);
            outputView.printJanggiBoard(janggiBoard);

            if (janggiBoard.isEnd()) {
                break;
            }

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
