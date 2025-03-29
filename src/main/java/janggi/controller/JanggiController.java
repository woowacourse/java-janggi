package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.Player;
import janggi.domain.Position;
import janggi.domain.SetupType;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.dto.PieceDto;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Map;
import java.util.function.Consumer;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Command, Consumer<JanggiGame>> command;
    private final JanggiService janggiService;

    public JanggiController(final InputView inputView, final OutputView outputView, final JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.command = Map.of(
                Command.MOVE, this::movePiece,
                Command.END, this::end
        );
        this.janggiService = janggiService;
    }

    public void run() {
        JanggiGame janggiGame = initiateJanggiGame();
        String command;
        do {
            printCurrentGame(janggiGame);
            printCurrentScore(janggiGame);
            command = inputView.readCommand();
            runCommand(janggiGame, command);
        } while (!"end".equals(command) && janggiGame.isContinue());
        outputView.printGameWinMessage(janggiGame.getGameStatus());
    }

    private JanggiGame initiateJanggiGame() {
        Player redPlayer = createPlayer(Team.RED);
        Player greenPlayer = createPlayer(Team.GREEN);
        if (janggiService.existsContinuedJanggiGame(redPlayer.getName(), greenPlayer.getName())) {
            outputView.printExistBeforeGame(redPlayer, greenPlayer);
            return janggiService.loadJanggiGame(redPlayer.getName(), greenPlayer.getName());
        }
        SetupType redSetupType = inputView.readSetupType(Team.RED);
        SetupType greenSetupType = inputView.readSetupType(Team.GREEN);
        return janggiService.initJanggiGame(redPlayer.getName(), greenPlayer.getName(), redSetupType, greenSetupType);
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

    private void printCurrentScore(final JanggiGame janggiGame) {
        outputView.printScore(janggiGame.getScore(Team.RED), janggiGame.getScore(Team.GREEN));
    }

    private void movePiece(JanggiGame janggiGame) {
        Position departure = inputView.readStartPosition();
        Position destination = inputView.readMovePosition();
        PieceDto pieceDto = janggiGame.moveByPlayer(departure, destination);
        if (pieceDto.removed().isPresent()) {
            removePiece(janggiGame.getRedPlayer().getName(),
                    janggiGame.getGreenPlayer().getName(),
                    destination,
                    pieceDto.removed().get());
        }
        janggiService.savePiece(janggiGame.getRedPlayer().getName(),
                janggiGame.getGreenPlayer().getName(),
                departure,
                destination,
                pieceDto.moved(),
                true);
        janggiService.saveJanggiGame(janggiGame);
    }

    private void removePiece(final String redPlayerName,
                             final String greenPlayerName,
                             final Position destination,
                             final Piece removed) {
        janggiService.updateDiedPiece(redPlayerName, greenPlayerName, destination, removed);
    }

    private void end(JanggiGame janggiGame) {
        inputView.close();
        outputView.printEndMessage();
    }
}
