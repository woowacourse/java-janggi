package janggi.controller;

import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.Player;
import janggi.domain.Position;
import janggi.domain.SetupType;
import janggi.domain.Team;
import janggi.domain.piece.Pieces;
import janggi.view.InputView;
import janggi.view.OutputView;
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
        } while (!"end".equals(command) && janggiGame.isContinue());
        outputView.printGameWinMessage(janggiGame.getGameStatus());
    }

    private JanggiGame initiateJanggiGame() {
        Player redPlayer = createPlayer(Team.RED);
        Player greenPlayer = createPlayer(Team.GREEN);
        Board board = initiateBoard();
        return new JanggiGame(board, redPlayer, greenPlayer);
    }

    private Board initiateBoard() {
        SetupType redSetupType = inputView.readSetupType(Team.RED);
        SetupType greenSetupType = inputView.readSetupType(Team.GREEN);
        Pieces pieces = Pieces.createPieces(redSetupType, greenSetupType);
        return new Board(pieces.getPieces());
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
        inputView.close();
        outputView.printEndMessage();
    }
}
