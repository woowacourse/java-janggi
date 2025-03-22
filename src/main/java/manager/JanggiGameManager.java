package manager;

import domain.board.Board;
import domain.board.BoardGenerator;
import domain.piece.Team;
import domain.util.ErrorHandler;
import view.InputView;
import view.MoveCommand;
import view.OutputView;
import view.SangMaOrderCommand;

public class JanggiGameManager {

    private static final Team START_TEAM = Team.CHO;

    private final Turn turn;

    public JanggiGameManager() {
        this.turn = new Turn(START_TEAM);
    }

    public void startGame() {
        OutputView.printStart();

        Board board = createBoard(new BoardGenerator());
        OutputView.printBoard(board);

        while (board.isRunning()) {
            processTurn(board, turn);
        }
    }

    private void processTurn(Board board, Turn turn) {
        ErrorHandler.retryUntilSuccess(() -> {
            MoveCommand moveCommand = InputView.inputMoveCommand(turn.team());

            if (!board.hasPieceInTeam(moveCommand.source(), turn.team())) {
                OutputView.printTurn(turn.team());
                return;
            }

            board.movePiece(moveCommand.source(), moveCommand.destination());
            OutputView.printBoard(board);

            if (board.existsWang(moveCommand.destination())) {
                OutputView.printMatchResult(turn.team());
                return;
            }
            turn.changeTurn();
        });
    }

    private Board createBoard(BoardGenerator boardGenerator) {
        return ErrorHandler.retryUntilSuccessWithReturn(() -> {
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
