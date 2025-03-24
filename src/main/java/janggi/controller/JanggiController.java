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

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        BoardSetup redSetup = getBoardSetup(PieceColor.RED);
        BoardSetup blueSetup = getBoardSetup(PieceColor.BLUE);
        InitialBoard initialBoard = InitialBoard.createBoard(redSetup, blueSetup);

        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());
        outputView.printBoard(playingBoard);

        JanggiGame janggiGame = new JanggiGame(new BlueTurn(playingBoard));
        while (!janggiGame.isFinished()) {
            processWithRetry(() -> playTurn(janggiGame, playingBoard));
        }

        outputView.printWinner(janggiGame.getTurnColor());
    }

    private BoardSetup getBoardSetup(PieceColor teamColor) {
        int setNumber = 0;

        if (teamColor == PieceColor.RED) {
            setNumber = inputView.readRedSetup();
        }
        if (teamColor == PieceColor.BLUE) {
            setNumber = inputView.readBlueSetup();
        }

        return BoardSetup.from(setNumber);
    }

    private void playTurn(JanggiGame janggiGame, PlayingBoard playingBoard) {
        outputView.printTurnNotice(janggiGame.getTurnColor());

        MoveCommandDto command = inputView.readMoveCommand();
        Position source = createPosition(command.sourceRow(), command.sourceCol());
        Position destination = createPosition(command.destinationRow(), command.destinationCol());
        String pieceNameInput = command.pieceName();
        PieceType pieceType = PieceTypeName.getTypeFrom(pieceNameInput);

        janggiGame.move(pieceType, source, destination);

        outputView.printBoard(playingBoard);
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
