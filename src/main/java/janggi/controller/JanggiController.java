package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.board.BoardSetup;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.InitialBoard;
import janggi.domain.board.Column;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.gameState.BlueTurn;
import janggi.domain.piece.PieceColor;
import janggi.domain.piece.PieceType;
import janggi.dto.MoveCommandDto;
import janggi.view.InputView;
import janggi.view.OutputView;

import janggi.view.PieceTypeName;
import java.util.Map;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        InitialBoard initialBoard = setupBoard();
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());
        outputView.printBoard(playingBoard);

        JanggiGame janggiGame = new JanggiGame(new BlueTurn(playingBoard));

        while (!janggiGame.isFinished()) {
            String input = inputView.readCommand();
            GameCommand command = GameCommand.from(input);

            Map<GameCommand, Runnable> commands = Map.of(
                    GameCommand.MOVE, () -> playMove(MoveCommandDto.from(input), janggiGame, playingBoard),
                    GameCommand.QUIT, this::gameQuit
            );

            Runnable action = commands.get(command);
            action.run();
        }

        outputView.printWinner(janggiGame.getTurnColor());
    }

    private void playMove(MoveCommandDto commandDto, JanggiGame janggiGame, PlayingBoard playingBoard) {
        processWithRetry(() -> playTurn(janggiGame, commandDto));
        outputView.printBoard(playingBoard);
    }

    private void playTurn(JanggiGame janggiGame, MoveCommandDto commandDto) {
        //MoveCommandDto commandDto = readMoveCommand(janggiGame.getTurnColor());

        Position source = createPosition(commandDto.sourceRow(), commandDto.sourceCol());
        Position destination = createPosition(commandDto.destinationRow(), commandDto.destinationCol());
        PieceType pieceType = PieceTypeName.getTypeFrom(commandDto.pieceName());

        janggiGame.move(pieceType, source, destination);
    }

    private void gameQuit() {
        System.out.println("게임 종료");
        System.exit(0);
    }

    private InitialBoard setupBoard() {
        BoardSetup redSetup = getBoardSetup(PieceColor.RED);
        BoardSetup blueSetup = getBoardSetup(PieceColor.BLUE);
        return InitialBoard.createBoard(redSetup, blueSetup);
    }

    private BoardSetup getBoardSetup(PieceColor teamColor) {
        int setNumber = inputView.readBoardSetup(teamColor);
        return BoardSetup.from(setNumber);
    }

    private MoveCommandDto readMoveCommand(PieceColor turnColor) {
        outputView.printTurnNotice(turnColor);
        return inputView.readMoveCommand();
    }

    private static Position createPosition(char rowInput, char colInput) {
        int rowInt = Character.getNumericValue(rowInput);
        Row row = Row.from(rowInt);

        int colInt = Character.getNumericValue(colInput);
        Column column = Column.from(colInt);

        return new Position(row, column);
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
