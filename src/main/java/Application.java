import controller.Controller;

import repository.DBConnection;
import repository.GameDao;
import repository.GameRepository;
import repository.H2DBConnection;
import repository.PieceDao;
import view.InputView;
import view.OutputView;

import org.h2.tools.Server;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        Server h2Server;
        try {
            h2Server = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println("H2 콘솔 서버 실행에 성공했습니다.");
        } catch (SQLException e) {
            System.out.println("H2 콘솔 서버 실행에 실패했습니다.");
            return;
        }
        String dbUrl = "jdbc:h2:./janggi;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        DBConnection dbConnection = new H2DBConnection(dbUrl);
        GameRepository gameRepository = new GameRepository(new GameDao(dbConnection), new PieceDao(dbConnection));

        Controller controller = new Controller(inputView, outputView, gameRepository);
        controller.run();

        if (h2Server != null) {
            h2Server.stop();
            System.out.println("H2 콘솔 서버를 종료합니다.");
        }
    }
}
