import controller.JanggiController;
import db.MySQLConnection;
import db.dao.JanggiGameDao;
import db.dao.JanggiGameDao.GameEntity;
import janggiGame.arrangement.ArrangementOption;
import java.util.List;
import service.JanggiGameService;
import service.initializer.JanggiGameInitializer;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiGameService gameService;
        Long gameId;

        int startOption = inputView.readStartOption();

        if (startOption == 1) {
            JanggiGameDao gameDao = new JanggiGameDao(MySQLConnection.getInstance());
            List<GameEntity> games = gameDao.findNotFinishedGames();
            gameId = inputView.readSavedGameId(games);
        } else if (startOption == 2) {
            JanggiGameInitializer initializer = new JanggiGameInitializer();
            gameId = initializer.getNewGameId(
                    ArrangementOption.findBy(inputView.readHanArrangement()).getArrangementStrategy(),
                    ArrangementOption.findBy(inputView.readChoArrangement()).getArrangementStrategy()
            );
        } else {
            throw new IllegalArgumentException("[ERROR] 알맞은 옵션이 아닙니다.");
        }

        gameService = new JanggiGameService(gameId);

        JanggiController controller = new JanggiController(gameService, inputView, outputView);

        while (!gameService.isFinished()) {
            try {
                outputView.printBoard(gameService.getGame().getPieces());
                int option = inputView.getTurnOption(gameService.getGame().getCurrentDynasty());
                controller.selectOption(option);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        controller.printGameResult();
    }
}
