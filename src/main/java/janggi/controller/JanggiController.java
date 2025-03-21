package janggi.controller;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Command, Runnable> command;
    private final Board board;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.command = Map.of(
                Command.MOVE, this::movePiece,
                Command.END, this::end
        );
        this.board = initiateBoard();
    }

    private Board initiateBoard() {
        List<Piece> pieces = new ArrayList<>();
        for (Team team : Team.values()) {
            pieces.add(General.Default(team));
            pieces.addAll(Guard.Default(team));
            pieces.addAll(Soldier.Default(team));
            pieces.addAll(Horse.Default(team));
            pieces.addAll(Elephant.Default(team));
            pieces.addAll(Chariot.Default(team));
            pieces.addAll(Cannon.Default(team));
        }
        return Board.initialize(pieces);
    }

    public void run() {
        String command;
        do {
            outputView.printBoard(board.getPositionToPiece());
            command = inputView.readCommand();
            runCommand(command);
        } while (!command.equals("end"));
    }

    private void runCommand(String commandInput) {
        try {
            Command command = Command.getCommand(commandInput);
            Runnable commandRunner = this.command.get(command);
            commandRunner.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void movePiece() {
        Position departure = inputView.readStartPosition();
        Position destination = inputView.readMovePosition();
        board.movePiece(departure, destination);
    }

    private void end() {
        outputView.printEndMessage();
    }
}
