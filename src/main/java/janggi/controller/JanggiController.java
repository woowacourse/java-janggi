package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.board.Column;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.gameState.BlueTurn;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.dto.MoveCommandDto;
import janggi.service.JanggiDBService;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.PieceTypeName;
import java.util.Map;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiDBService janggiDBService;
    private final PlayingBoard playingBoard;

    public JanggiController(InputView inputView, OutputView outputView, JanggiDBService janggiDBService, PlayingBoard playingBoard) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiDBService = janggiDBService;
        this.playingBoard = playingBoard;
    }

    public void run() {
        JanggiGame janggiGame = new JanggiGame(new BlueTurn(playingBoard));

        while (!janggiGame.isFinished()) {
            processWithRetry(() -> playSingleCommand(janggiGame));
        }

        displayGameResult(janggiGame);
    }

    private void playSingleCommand(JanggiGame janggiGame) {
        outputView.printTurnNotice(janggiGame.getTurnColor());
        String input = inputView.readCommand();
        GameCommand command = GameCommand.from(input);

        Map<GameCommand, Runnable> commands = Map.of(
                GameCommand.MOVE, () -> playTurn(MoveCommandDto.from(input), janggiGame),
                GameCommand.QUIT, this::gameQuit
        );
        Runnable action = commands.get(command);
        action.run();
    }

    private void playTurn(MoveCommandDto commandDto, JanggiGame janggiGame) {
        TeamColor turnColor = janggiGame.getTurnColor();
        Position source = createPosition(commandDto.sourceRow(), commandDto.sourceCol());
        Position destination = createPosition(commandDto.destinationRow(), commandDto.destinationCol());
        PieceType pieceType = PieceTypeName.getTypeFrom(commandDto.pieceName());

        janggiGame.move(pieceType, source, destination);
        janggiDBService.updateMoveResult(source, destination, pieceType, turnColor);

        outputView.printBoard(playingBoard);
    }

    private Position createPosition(char rowInput, char colInput) {
        int rowInt = Character.getNumericValue(rowInput);
        Row row = Row.from(rowInt);

        int colInt = Character.getNumericValue(colInput);
        Column column = Column.from(colInt);

        return new Position(row, column);
    }

    private void displayGameResult(JanggiGame janggiGame) {
        outputView.printWinner(janggiGame.getTurnColor());

        Map<TeamColor, Integer> teamScore = janggiGame.getTeamScore();
        outputView.printGameResult(teamScore);
    }

    private void gameQuit() {
        System.out.println("게임 종료");
        System.exit(0);
    }

    private void processWithRetry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (Exception e) {
                System.out.println("[Error] " + e.getMessage() + "\n");
            }
        }
    }
}
