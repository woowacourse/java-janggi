package controller;

import domain.JanggiGame;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.gameState.BlueTurn;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.Soldier;
import dto.MoveCommandDTO;
import view.InputView;
import view.OutputView;

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

        Piece piece = createPieceFromTypeAndColor(commands.pieceName(), janggiGame.getTurnColor());
        janggiGame.move(piece, source, destination);
        outputView.printBorad(board);
    }

    private Piece createPieceFromTypeAndColor(String pieceType, PieceColor color) {
        if (pieceType.equals("마")) {
            return new Horse(color);
        }
        if (pieceType.equals("상")) {
            return new Elephant(color);
        }
        if (pieceType.equals("포")) {
            return new Cannon(color);
        }
        if (pieceType.equals("궁")) {
            return new General(color);
        }
        if (pieceType.equals("차")) {
            return new Chariot(color);
        }
        if (pieceType.equals("사")) {
            return new Guard(color);
        }
        if (pieceType.equals("졸")) {
            return new Soldier(color);
        }
        throw new IllegalArgumentException("해당하는 기물이 없습니다.");
    }
}
