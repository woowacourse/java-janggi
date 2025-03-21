package janggi.controller;

import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.Player;
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
import java.util.function.Consumer;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Command, Consumer<JanggiGame>> command;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.command = Map.of(
                Command.MOVE, this::movePiece,
                Command.END, this::end
        );
    }

    public void run() {
        JanggiGame janggiGame = initiateJanggiGame();
        String command;
        do {
            printCurrentGame(janggiGame);
            command = inputView.readCommand();
            runCommand(janggiGame, command);
        } while (!command.equals("end") && janggiGame.isContinue());
        outputView.printGameWinMessage(janggiGame.getGameStatus());
    }

    private JanggiGame initiateJanggiGame() {
        Player redPlayer = createPlayer(Team.RED);
        Player greenPlayer = createPlayer(Team.GREEN);
        Board board = initiateBoard();
        return new JanggiGame(board, redPlayer, greenPlayer);
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

    private Player createPlayer(Team team) {
        return inputView.readPlayer(team);
    }

    private void printCurrentGame(final JanggiGame janggiGame) {
        outputView.printBoard(janggiGame.getPositionToPiece());
        outputView.printCurrentTurn(janggiGame.getCurrentPlayer());
        janggiGame.checkWinCondition();
    }

    private void runCommand(final JanggiGame janggiGame, final String commandInput) {
        try {
            Command command = Command.getCommand(commandInput);
            Consumer<JanggiGame> commandRunner = this.command.get(command);
            commandRunner.accept(janggiGame);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void movePiece(JanggiGame janggiGame) {
        Position departure = inputView.readStartPosition();
        Position destination = inputView.readMovePosition();
        janggiGame.moveByPlayer(departure, destination);
    }

    private void end(JanggiGame janggiGame) {
        outputView.printEndMessage();
    }
}
