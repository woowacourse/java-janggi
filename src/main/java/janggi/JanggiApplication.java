package janggi;

import janggi.application.JanggiGameService;
import janggi.domain.board.BoardRepository;
import janggi.infra.datasource.H2DataSourceFactory;
import janggi.infra.JdbcBoardRepository;
import janggi.infra.transaction.TransactionTemplate;
import janggi.infra.dao.GameRoomDao;
import janggi.infra.dao.PiecesDao;
import janggi.presentation.JanggiGameController;
import janggi.presentation.ui.InputView;
import janggi.presentation.ui.OutputView;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.h2.tools.Server;

public class JanggiApplication {
    public static void main(String[] args) throws SQLException {
        Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
        DataSource manager = H2DataSourceFactory.create();
        GameRoomDao roomDao = new GameRoomDao();
        PiecesDao piecesDao = new PiecesDao();
        BoardRepository repository = new JdbcBoardRepository(roomDao, piecesDao);
        TransactionTemplate template = new TransactionTemplate(manager);
        JanggiGameService service = new JanggiGameService(template, repository);
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiGameController controller = new JanggiGameController(service, inputView, outputView);
        controller.run();
        server.stop();
    }
}
