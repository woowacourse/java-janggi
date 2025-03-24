import domain.board.BoardGenerator;
import domain.board.Board;
import domain.board.Node;
import domain.piece.PieceType;
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

        Board board = createJanggiBoard();
        OutputView.printBoard(board);

        Turn turn = new Turn(START_TEAM);
        boolean isRunning = true;
        while (isRunning) {
            isRunning = movePieceByTurn(board, turn);
        }
    }

    private boolean movePieceByTurn(Board board, Turn turn) {
        return ErrorHandler.retryUntilSuccess(() -> {
            MoveCommand moveCommand = InputView.inputMoveCommand(turn.team());

            Node sourceNode = board.findNodeByPoint(moveCommand.source());
            Node destinationNode = board.findNodeByPoint(moveCommand.destination());

            if (!board.hasPieceTeamByNode(sourceNode, turn.team())) {
                OutputView.printTurn(turn.team());
                return true;
            }

            board.movePiece(sourceNode, destinationNode, board);
            if (board.existsPieceTypeByNode(destinationNode, PieceType.WANG)) {
                OutputView.printMatchResult(turn.team());
                return false;
            }
            OutputView.printBoard(board);

            turn.changeTurn();
            return true;
        });
    }

    private Board createJanggiBoard() {
        return ErrorHandler.retryUntilSuccess(() -> {
            BoardGenerator boardGenerator = new BoardGenerator();
            SangMaOrderCommand hanSangMaOrderCommand = InputView.inputSangMaOrder(Team.HAN);
            SangMaOrderCommand choSangMaOrderCommand = InputView.inputSangMaOrder(Team.CHO);
            return boardGenerator.generateBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
        });
    }

    private static class Turn {

        private Team team;

        public Turn(Team team) {
            this.team = team;
        }

        public void changeTurn() {
            this.team = team.inverse();
        }

        public Team team() {
            return team;
        }
    }
}
