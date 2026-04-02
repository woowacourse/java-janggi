package janggi;

import janggi.application.JanggiGameService;
import janggi.domain.board.BoardRepository;
import janggi.infra.JdbcBoardRepository;
import janggi.presentation.JanggiGameController;
import janggi.presentation.ui.InputView;
import janggi.presentation.ui.OutputView;
import java.sql.SQLException;
import org.h2.tools.Server;

public class JanggiApplication {
    public static void main(String[] args) throws SQLException {
        Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
        BoardRepository repository = new JdbcBoardRepository();
        JanggiGameService service = new JanggiGameService(repository);
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiGameController controller = new JanggiGameController(service, inputView, outputView);
        controller.run();
        server.stop();
    }
}
