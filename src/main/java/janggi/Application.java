package janggi;

import janggi.controller.Controller;
import janggi.repository.GameRepository;
import janggi.repository.JdbcGameRepository;
import janggi.repository.JdbcPieceRepository;
import janggi.repository.PieceRepository;
import janggi.service.JanggiService;
import janggi.util.DBConnectionManager;
import janggi.util.DatabaseInitializer;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.sql.Connection;

public class Application {
    public static void main(String[] args) {

        Connection conn = DBConnectionManager.getConnection();
        DatabaseInitializer.initialize(conn);

        GameRepository gameRepository = new JdbcGameRepository();
        PieceRepository pieceRepository = new JdbcPieceRepository();
        JanggiService service = new JanggiService(gameRepository, pieceRepository);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        Controller controller = new Controller(inputView, outputView, service);

        controller.run();
    }
}
