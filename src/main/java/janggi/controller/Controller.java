package janggi.controller;

import janggi.domain.Position;
import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.generator.DefaultChoPieceGenerator;
import janggi.domain.piece.generator.DefaultHanPieceGenerator;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.Map.Entry;
import java.util.function.Consumer;

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
            computeException(this::moveHan, janggiBoard);
            outputView.printJanggiBoard(janggiBoard);

            if (janggiBoard.isEnd()) {
                break;
            }

            computeException(this::moveCho, janggiBoard);
            outputView.printJanggiBoard(janggiBoard);
        }
        outputView.printWinner(janggiBoard.getWinner());
    }

    private void moveHan(JanggiBoard janggiBoard) {
        Entry<Integer, Integer> source = inputView.inputHanMoveSource();
        Entry<Integer, Integer> destinationValues = inputView.inputHanMoveDestination();
        move(janggiBoard, source, destinationValues);
    }

    private void moveCho(JanggiBoard janggiBoard) {
        Entry<Integer, Integer> source = inputView.inputChoMoveSource();
        Entry<Integer, Integer> destinationValues = inputView.inputChoMoveDestination();
        move(janggiBoard, source, destinationValues);
    }

    private static void move(JanggiBoard janggiBoard, Entry<Integer, Integer> source, Entry<Integer, Integer> destinationValues) {
        Position start = new Position(source.getKey(), source.getValue());
        Position destination = new Position(destinationValues.getKey(), destinationValues.getValue());
        janggiBoard.move(start, destination);
    }

    private void computeException(Consumer<JanggiBoard> move, JanggiBoard janggiBoard) {
        boolean moveSuccess = false;

        while (!moveSuccess) {
            try {
                move.accept(janggiBoard);
                moveSuccess = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
