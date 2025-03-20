package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Column;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.gameState.BlueTurn;
import janggi.domain.piece.PieceType;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;
import janggi.view.PieceTypeName;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        outputView.printBoard(board);

        JanggiGame janggiGame = new JanggiGame(new BlueTurn(board));
        while(!janggiGame.isFinished()) {
            processWithRetry(() -> playTurn(janggiGame, board));
        }

        outputView.printWinner(janggiGame.getTurnColor());
    }

    private void playTurn(JanggiGame janggiGame, Board board) {
        outputView.printTurnNotice(janggiGame.getTurnColor());
        List<String> commands = inputView.readMoveCommand();

        Position source = createPosition(commands.get(0));
        Position destination = createPosition(commands.get(2));

        String pieceNameInput = commands.get(1);
        PieceType pieceType = PieceTypeName.getTypeFrom(pieceNameInput);
        janggiGame.move(pieceType, source, destination);
        outputView.printBoard(board);
    }

    private static Position createPosition(String input) {
        char rowInput = input.charAt(0);
        int rowInt = Integer.parseInt(String.valueOf(rowInput));
        Row row = Row.from(rowInt);

        char colInput = input.charAt(1);
        int colInt = Integer.parseInt(String.valueOf(colInput));
        Column column = Column.from(colInt);

        return new Position(row, column);
    }

    private void processWithRetry(Runnable runnable) {
        while(true) {
            try {
                runnable.run();
            } catch (Exception e) {
                System.out.println("[Error] " + e.getMessage() + "\n");
            }
        }
    }
}
