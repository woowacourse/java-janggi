package controller;

import domain.*;
import domain.piece.Piece;
import domain.piece.dao.JanggiDao;
import view.InputView;
import view.OutputView;

import java.util.Map;

import static domain.JanggiBoard.COL_SIZE;
import static domain.JanggiBoard.ROW_SIZE;

public class JanggiController {
    public final static JanggiCoordinate GAME_STOP_COORDINATE = new JanggiCoordinate(-1, -1);
    private final static JanggiDao dao = new JanggiDao();

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void startJanggiGame() {
        JanggiGame game = new JanggiGame(loadGame());

        while (!game.isGameOver()) {
            try {
                outputView.printCurrBoard(game.getBoard());
                outputView.printCurrTurn(game.getCurrTurn());
                JanggiCoordinate from = inputView.readMovePiece();
                if (from.equals(GAME_STOP_COORDINATE)) {
                    saveGame(game.getBoard());
                    return;
                }

                JanggiCoordinate to = inputView.readMoveDestination();
                game.movePlayerPiece(from, to);
                updateGame(game.getBoard());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }

        outputView.printWinner(game.getWinner());
        outputView.printScore(Country.CHO, game.getCountryScore(Country.CHO));
        outputView.printScore(Country.HAN, game.getCountryScore(Country.HAN));
        dao.clearBoard();
    }

    private void saveGame(JanggiBoard board) {
        for (int row = board.BOUNDARY_START; row <= ROW_SIZE; row++) {
            for (int col = board.BOUNDARY_START; col <= COL_SIZE; col++) {
                JanggiCoordinate coordinate = new JanggiCoordinate(row, col);
                if (board.isOccupied(coordinate)) {
                    Piece piece = board.findPieceByCoordinate(coordinate);
                    dao.addPiece(coordinate, piece);
                }
            }
        }
    }

    private Map<JanggiCoordinate, Piece> loadGame() {
        Map<JanggiCoordinate, Piece> board = dao.loadBoard();
        if (board.isEmpty()) {
            return PieceInitializer.init();
        }
        return board;
    }

    private void updateGame(JanggiBoard board) {
        dao.clearBoard();
        saveGame(board);
    }
}
