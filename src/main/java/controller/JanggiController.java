package controller;

import dao.BoardDao;
import dao.CountryDirectionDao;
import dao.GameStatusDao;
import dao.PieceDao;
import database.ConnectionManager;
import domain.board.Board;
import domain.board.GameStatus;
import domain.piece.Country;
import domain.position.Position;
import service.GameData;
import service.JanggiService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private static final int MAX_TRY_COUNT = 150;

    private final ConnectionManager manager;

    public JanggiController(ConnectionManager manager) {
        this.manager = manager;
    }

    public void run() {
        PieceDao pieceDao = new PieceDao(manager);
        BoardDao boardDao = new BoardDao(manager);
        CountryDirectionDao countryDao = new CountryDirectionDao(manager);
        GameStatusDao gameStatusDao = new GameStatusDao(manager);

        JanggiService janggiService = new JanggiService(pieceDao, boardDao, countryDao, gameStatusDao);

        GameData gameData = janggiService.initializeGame();
        Board board = gameData.board();

        GameStatus status = gameData.gameStatus();
        int turnCount = status.turnCount();
        Country turnCountry = status.country();

        while (++turnCount < MAX_TRY_COUNT) {
            turnCountry = turnCountry.opposite();

            OutputView.printBoard(board, turnCountry);
            final List<Position> positions = InputView.readPositions();

            janggiService.processTurn(board, turnCountry, positions);
            janggiService.save(board, turnCountry, turnCount);
        }
    }
}
