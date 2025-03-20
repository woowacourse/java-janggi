import domain.JanggiManager;
import domain.piece.Team;
import view.InputView;
import view.MoveCommand;
import view.OutputView;
import view.SangMaOrderCommand;

public class FlowManager {

    private static final Team startTeam = Team.CHO;

    public void startGame() {
        OutputView.printStart();

        JanggiManager janggiManager = createJanggiManager();
        OutputView.printBoard(janggiManager.board());

        startMove(janggiManager);
    }

    private void startMove(JanggiManager janggiManager) {
        boolean isRunning = true;
        Team turn = startTeam;

        while (isRunning) {
            MoveCommand moveCommand = InputView.inputMoveCommand(turn);

            if (!janggiManager.canMove(moveCommand.source(), moveCommand.destination())) {
                continue;
            }

            if (janggiManager.isThereWang(moveCommand.destination())) {
                OutputView.printMatchResult(turn);
                break;
            }

            janggiManager.movePiece(moveCommand.source(), moveCommand.destination());
            OutputView.printBoard(janggiManager.board());

            turn = turn.inverse();
        }
    }

    private JanggiManager createJanggiManager() {
        SangMaOrderCommand hanSangMaOrderCommand = InputView.inputSangMaOrder(Team.HAN);
        SangMaOrderCommand choSangMaOrderCommand = InputView.inputSangMaOrder(Team.CHO);
        return new JanggiManager(hanSangMaOrderCommand, choSangMaOrderCommand);
    }
}
