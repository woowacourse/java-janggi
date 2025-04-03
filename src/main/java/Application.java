import controller.InitializerController;
import controller.JanggiController;
import db.connection.DBConnection;
import db.connection.MySQLConnection;
import db.repository.JanggiGameRepository;
import service.JanggiGameProgressService;
import service.JanggiGameStartService;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        // 뷰 생성
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        // DB 연결체 생성
        DBConnection dbConnection = MySQLConnection.getInstance();

        // repository 생성
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(dbConnection);

        // startService 및 서비스 기반 초기화 컨트롤러 생성
        JanggiGameStartService janggiGameStartService = new JanggiGameStartService(janggiGameRepository);
        InitializerController initializerController = new InitializerController(inputView, janggiGameStartService);

        // 게임 id 기반 게임 진행 서비스 생성
        Long gameId = initializerController.getGameId();
        JanggiGameProgressService janggiGameProgressService = new JanggiGameProgressService(gameId,
                janggiGameRepository);

        // 장기 게임 진행 컨트롤러 생성
        JanggiController controller = new JanggiController(janggiGameProgressService, inputView, outputView);

        while (!controller.isGameFinished()) {
            try {
                controller.printBoard();
                controller.selectOption();
            } catch (Exception e) {
                outputView.printErrorMessage(e);
            }
        }

        controller.printGameResult();
    }
}
