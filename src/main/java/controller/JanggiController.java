package controller;

import domain.board.Board;
import dao.BoardDao;
import dao.CountryDao;
import dao.PieceDao;
import domain.piece.Country;
import domain.position.Position;
import service.JanggiService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private static final int MAX_TRY_COUNT = 150;

    public void run() {
        PieceDao pieceDao = new PieceDao();
        BoardDao boardDao = new BoardDao();
        CountryDao countryDao = new CountryDao();
        JanggiService janggiService = new JanggiService(pieceDao, boardDao, countryDao);

        final Board board = janggiService.initializeGame();
        Country currentTurn = Country.getDefaultTeam();
        int turnCount = 0;

        Board updateBoard = board;
        while (++turnCount < MAX_TRY_COUNT) {
            currentTurn = currentTurn.opposite();

            OutputView.printBoard(updateBoard, currentTurn);
            final List<Position> positions = InputView.readPositions();

            updateBoard = janggiService.processTurn(updateBoard, currentTurn, positions);
            janggiService.save(updateBoard);
        }
    }
}
