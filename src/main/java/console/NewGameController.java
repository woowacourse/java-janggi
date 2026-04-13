package console;

import static console.util.ExceptionHandler.handleRetry;
import static model.Team.CHO;
import static model.Team.HAN;

import application.JanggiService;
import console.view.InputView;
import console.view.OutputView;
import model.board.TeamFormation;

public class NewGameController implements JanggiController {

    private final JanggiExecutor janggiExecutor;
    private final JanggiService janggiService;

    public NewGameController(JanggiExecutor janggiExecutor, JanggiService janggiService) {
        this.janggiExecutor = janggiExecutor;
        this.janggiService = janggiService;
    }

    @Override
    public void run() {
        TeamFormation hanFormation = handleRetry(() -> InputView.readFormationByTeam(HAN), OutputView::displayError);
        TeamFormation choFormation = handleRetry(() -> InputView.readFormationByTeam(CHO), OutputView::displayError);
        Long janggiId = janggiService.createJanggiGame(hanFormation, choFormation);
        janggiExecutor.playGame(janggiId);
    }
}
