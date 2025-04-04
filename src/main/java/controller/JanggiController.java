package controller;

import domain.board.Board;
import dao.BoardDao;
import dao.CountryDao;
import dao.PieceDao;
import domain.piece.Country;
import domain.piece.Piece;
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

            janggiService.processTurn(updateBoard, currentTurn, positions);
            // test method
            List<Piece> pieceList = updateBoard.getPieceList();
            for (Piece piece : pieceList) {
                int x = piece.getPosition().x();
                int y = piece.getPosition().y();
                if (x == 1 && y == 4) {
                    System.out.println("1, 4를 찾았다");
                }
                if (x == 2 && y == 4) {
                    System.out.println("2, 4를 찾았다.");
                }
            }
            janggiService.save(updateBoard);
        }
    }
}
