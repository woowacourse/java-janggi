package controller;

import domain.JanggiGame;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.gameState.BlueTurn;
import domain.piece.PieceType;
import dto.MoveCommandDTO;
import view.InputView;
import view.OutputView;
import view.PieceName;

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
        outputView.printBorad(board);

        JanggiGame janggiGame = new JanggiGame(new BlueTurn(board));
        while (!janggiGame.isFinished()) {
            try {
                playTurn(janggiGame, board);
            } catch (Exception e) {
                outputView.printError("[ERROR] " + e.getMessage());
            }
        }

        outputView.printWinner(janggiGame.getTurnColor());
    }

    private void playTurn(JanggiGame janggiGame, Board board) {
        outputView.printTurnNotice(janggiGame.getTurnColor());
        MoveCommandDTO commands = inputView.readMoveCommand();

        Position source = new Position(Row.from(commands.sourceRow()), Column.from(commands.sourceColumn()));
        Position destination = new Position(Row.from(commands.destinationRow()),
                Column.from(commands.destinationColumn()));

        PieceType pieceType = PieceName.getPieceTypeFromName(commands.pieceName());
        janggiGame.move(pieceType, source, destination);
        outputView.printBorad(board);
    }
}
