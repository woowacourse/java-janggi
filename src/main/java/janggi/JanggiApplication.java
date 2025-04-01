package janggi;

import janggi.controller.JanggiController;
import janggi.dao.BoardSnapshotDAO;
import janggi.dao.GameDAO;
import janggi.dao.PieceDAO;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Scanner;

public class JanggiApplication {
    public static void main(final String[] args) {
        InputView inputView = new InputView(new Scanner(System.in));
        OutputView outputView = new OutputView();
        GameDAO gameDAO = new GameDAO();
        PieceDAO pieceDAO = new PieceDAO();
        BoardSnapshotDAO boardSnapshotDAO = new BoardSnapshotDAO(pieceDAO);
        JanggiService janggiService = new JanggiService(gameDAO, boardSnapshotDAO, pieceDAO);
        JanggiController janggiController = new JanggiController(inputView, outputView, janggiService);
        janggiController.startJanggi();
    }
}
