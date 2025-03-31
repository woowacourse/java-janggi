package janggi.controller;

import janggi.domain.piece.Side;
import janggi.domain.board.JanggiBoard;
import janggi.domain.position.Position;
import janggi.domain.piece.generator.DefaultChoPieceGenerator;
import janggi.domain.piece.generator.DefaultHanPieceGenerator;
import janggi.service.JanggiBoardService;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.Map.Entry;
import java.util.function.Consumer;

public class Controller {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiBoardService janggiBoardService;

    public Controller(
        InputView inputView,
        OutputView outputView,
        JanggiBoardService janggiBoardService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiBoardService = janggiBoardService;
    }

    public void run() {
        JanggiBoard janggiBoard = makeJanggiBoard();
        outputView.printJanggiBoard(janggiBoard);
        janggiBoardService.createJanggiTables();
        playGame(janggiBoard);
    }

    private JanggiBoard makeJanggiBoard() {
        if (janggiBoardService.hasGameData()) {
            return janggiBoardService.loadGame();
        }
        JanggiBoard janggiBoard = initiateJanggiBoard();
        janggiBoardService.saveInitialBoard(janggiBoard);
        return janggiBoard;
    }

    private JanggiBoard initiateJanggiBoard() {
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
            computeException(this::moveSide, janggiBoard);
            outputView.printJanggiBoard(janggiBoard);
            outputView.printGameScore(janggiBoard.calculateGameScore());
            if (janggiBoard.isEnd()) {
                break;
            }
        }
        outputView.printWinner(janggiBoard.getWinner());
        janggiBoardService.resetGame();
    }

    private void moveSide(JanggiBoard janggiBoard) {
        Side turn = janggiBoard.getTurn();
        if (turn == Side.HAN) {
            moveHan(janggiBoard, turn);
            return;
        }
        moveCho(janggiBoard, turn);
    }

    private void moveHan(JanggiBoard janggiBoard, Side turn) {
        Entry<Integer, Integer> source = inputView.inputHanMoveSource();
        Entry<Integer, Integer> destinationValues = inputView.inputHanMoveDestination();
        move(janggiBoard, source, destinationValues, turn);
    }

    private void moveCho(JanggiBoard janggiBoard, Side turn) {
        Entry<Integer, Integer> source = inputView.inputChoMoveSource();
        Entry<Integer, Integer> destinationValues = inputView.inputChoMoveDestination();
        move(janggiBoard, source, destinationValues, turn);
    }

    private void move(
        JanggiBoard janggiBoard,
        Entry<Integer, Integer> source,
        Entry<Integer, Integer> destinationValues,
        Side turn
        ) {
        Position start = new Position(source.getKey(), source.getValue());
        Position destination = new Position(destinationValues.getKey(), destinationValues.getValue());
        janggiBoard.move(start, destination);
        janggiBoardService.updateGame(start, destination, turn);
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
