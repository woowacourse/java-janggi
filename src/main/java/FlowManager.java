import domain.JanggiManager;
import domain.piece.Team;
import domain.util.ErrorHandler;
import view.InputView;
import view.MoveCommand;
import view.OutputView;
import view.SangMaOrderCommand;

public class FlowManager {

    private static final Team START_TEAM = Team.CHO;

    public void startGame() {
        OutputView.printStart();

        JanggiManager janggiManager = createJanggiManager();
        OutputView.printBoard(janggiManager.board());

        Turn turn = new Turn(START_TEAM);
        boolean isRunning = true;
        while (isRunning) {
            isRunning = movePieceByTurn(janggiManager, turn);
        }
    }

    private boolean movePieceByTurn(JanggiManager janggiManager, Turn turn) {
        return ErrorHandler.retryUntilSuccess(() -> {
            MoveCommand moveCommand = InputView.inputMoveCommand(turn.team());

            if (!janggiManager.hasTeamPieceByNode(moveCommand.source(), turn.team())) {
                OutputView.printTurn(turn.team());
                return true;
            }

            if (janggiManager.isThereWang(moveCommand.destination())) {
                OutputView.printMatchResult(turn.team());
                return false;
            }

            janggiManager.movePiece(moveCommand.source(), moveCommand.destination());
            OutputView.printBoard(janggiManager.board());

            turn.changeTurn();
            return true;
        });
    }

    private JanggiManager createJanggiManager() {
        return ErrorHandler.retryUntilSuccess(() -> {
            SangMaOrderCommand hanSangMaOrderCommand = InputView.inputSangMaOrder(Team.HAN);
            SangMaOrderCommand choSangMaOrderCommand = InputView.inputSangMaOrder(Team.CHO);
            return new JanggiManager(hanSangMaOrderCommand, choSangMaOrderCommand);
        });
    }
}
