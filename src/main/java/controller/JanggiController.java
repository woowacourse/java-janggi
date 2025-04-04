package controller;

import dao.BoardDao;
import dao.CountryDao;
import dao.PieceDao;
import domain.board.Board;
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

        Board board = janggiService.initializeGame();
        Country currentTurn = Country.getDefaultTeam();
        int turnCount = 0;

        while (++turnCount < MAX_TRY_COUNT) {
            currentTurn = currentTurn.opposite();

            OutputView.printBoard(board, currentTurn);
            final List<Position> positions = InputView.readPositions();

            janggiService.processTurn(board, currentTurn, positions);
            janggiService.save(board);
        }
    }
}
