package janggi.controller;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Position;
import janggi.domain.piece.dao.PieceDao;
import janggi.domain.piece.generator.ChoPieceGenerator;
import janggi.domain.piece.generator.HanPieceGenerator;
import janggi.domain.piece.pieces.Pieces;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Controller {

    private final InputView inputView;
    private final OutputView outputView;

    private final HanPieceGenerator hanPieceGenerator;
    private final ChoPieceGenerator choPieceGenerator;

    private final PieceDao pieceDao = new PieceDao();

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

        JanggiBoard janggiBoard;
        if (inputView.inputGameContinue()) {
            janggiBoard = makeExistingJanggiBoard();
        } else {
            janggiBoard = makeJanggiBoard();
        }
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

    private JanggiBoard makeExistingJanggiBoard() {
        return new JanggiBoard(new Pieces(pieceDao.findAllToMap()));
    }

    private void playGame(JanggiBoard janggiBoard) {
        while (!janggiBoard.isEnd()) {

            if (inputView.inputGameExit()) {
                pieceDao.removeAll();
                pieceDao.saveAll(janggiBoard.getPlacedPieces().getValues());
                return;
            }

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
            janggiBoard.move(source, destination);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            moveHan(janggiBoard);
        }
    }

    private void moveCho(JanggiBoard janggiBoard) {
        try {
            Position source = inputView.inputChoMoveSource();
            Position destination = inputView.inputChoMoveDestination();
            janggiBoard.move(source, destination);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            moveCho(janggiBoard);
        }
    }
}
