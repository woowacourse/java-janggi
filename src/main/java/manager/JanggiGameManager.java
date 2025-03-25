package manager;

import domain.board.Board;
import domain.board.BoardGenerator;
import domain.board.Point;
import domain.piece.Team;
import view.Command;
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

    private Board createBoard(BoardGenerator boardGenerator) {
        SangMaOrderCommand hanSangMaOrderCommand = InputView.inputSangMaOrder(Team.HAN);
        SangMaOrderCommand choSangMaOrderCommand = InputView.inputSangMaOrder(Team.CHO);

        return boardGenerator.generateBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
    }

    public void startGame() {

        OutputView.printStart();
        Board board = createBoard(new BoardGenerator());

        while (board.isPlaying()) {
            Command command = InputView.inputCommand();

            if (command.isEnd()) {
                OutputView.printStatus(board.calculateScore(Team.HAN), board.calculateScore(Team.CHO));
                OutputView.printMatchResult(board.findWinTeam());
                return;
            }

            if (command.isMove()) {
                move(board);
                continue;
            }

            if (command.isStatus()) {
                OutputView.printStatus(board.calculateScore(Team.HAN), board.calculateScore(Team.CHO));
            }
        }

        OutputView.printMatchResult(board.findWinTeam());
    }

    private void move(Board board) {
        OutputView.printBoard(board);
        MoveCommand moveCommand = InputView.inputMoveCommand(turn.team);
        Point source = moveCommand.source();
        Point destination = moveCommand.destination();

        if (!board.existsPiece(source)) {
            OutputView.printEmpty(source);
        }

        if (!board.matchTeam(source, turn.team())) {
            OutputView.printTurn(turn.team());
            return;
        }
        if (!board.canMove(source, destination)) {
            OutputView.printCannotMove(source, destination);
            return;
        }

        board.movePiece(source, destination);
        turn.changeTurn();
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
