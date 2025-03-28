package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.board.Column;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.dto.MoveCommandDto;
import janggi.service.JanggiDBService;
import janggi.view.GameRunningView;
import janggi.view.PieceTypeName;
import java.util.Map;

public class JanggiController {
    private final GameRunningView gameRunningView;
    private final JanggiDBService janggiDBService;
    private final JanggiGame janggiGame;
    private final PlayingBoard playingBoard;

    public JanggiController(GameRunningView gameRunningView,
                            JanggiDBService janggiDBService, JanggiGame janggiGame) {
        this.gameRunningView = gameRunningView;
        this.janggiDBService = janggiDBService;
        this.janggiGame = janggiGame;
        this.playingBoard = janggiGame.getPlayingBoard();
    }

    public void run() {
        gameRunningView.printBoard(playingBoard);

        while (!janggiGame.isFinished()) {
            RetryUtil.processWithRetry(() -> playSingleCommand(janggiGame));
        }
        displayGameResult(janggiGame);

        janggiDBService.finishGame(janggiGame.getTurnColor());
    }

    private void playSingleCommand(JanggiGame janggiGame) {
        gameRunningView.printTurnNotice(janggiGame.getTurnColor());
        String input = gameRunningView.readCommand();
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

        gameRunningView.printBoard(playingBoard);

        janggiDBService.updateMoveResult(source, destination, pieceType, turnColor);
        janggiDBService.updateGameRoom(janggiGame.getTurnColor(), janggiGame.getTeamScore());
    }

    private Position createPosition(char rowInput, char colInput) {
        int rowInt = Character.getNumericValue(rowInput);
        Row row = Row.from(rowInt);

        int colInt = Character.getNumericValue(colInput);
        Column column = Column.from(colInt);

        return new Position(row, column);
    }

    private void displayGameResult(JanggiGame janggiGame) {
        gameRunningView.printWinner(janggiGame.getTurnColor());

        Map<TeamColor, Integer> teamScore = janggiGame.getTeamScore();
        gameRunningView.printGameResult(teamScore);
    }

    private void gameQuit() {
        System.out.println("게임 종료");
        System.exit(0);
    }
}
