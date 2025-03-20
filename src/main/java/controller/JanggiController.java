package controller;

import domain.JanggiGame;
import domain.board.*;
import domain.gameState.BlueTurn;
import domain.piece.*;
import view.InputView;
import view.OutputView;

import java.util.List;

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
        while(!janggiGame.isFinished()) {
            try {
                playTurn(janggiGame, board);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        outputView.printWinner(janggiGame.getTurnColor());
    }

    private void playTurn(JanggiGame janggiGame, Board board) {
        outputView.printTurnNotice(janggiGame.getTurnColor());
        List<String> commands = inputView.readMoveCommand();

        Position source = createPosition(commands.get(0));
        Position destination = createPosition(commands.get(2));

        String pieceInput = commands.get(1);
        Piece piece = createPieceFromTypeAndColor(pieceInput, janggiGame.getTurnColor());
        janggiGame.move(piece, source, destination);
        outputView.printBorad(board);
    }

    private Piece createPieceFromTypeAndColor(String pieceType, PieceColor color) {
        if(pieceType.equals("마")) {
            return new Horse(color);
        }
        if(pieceType.equals("상")) {
            return new Elephant(color);
        }
        if(pieceType.equals("포")) {
            return new Cannon(color);
        }
        if(pieceType.equals("궁")) {
            return new General(color);
        }
        if(pieceType.equals("차")) {
            return new Chariot(color);
        }
        if(pieceType.equals("사")) {
            return new Guard(color);
        }
        if(pieceType.equals("졸")) {
            return new Soldier(color);
        }
        throw new IllegalArgumentException("해당하는 기물이 없습니다.");
    }

    private static Position createPosition(String input) {
        char rowInput = input.charAt(0);
        int rowInt = Integer.parseInt(String.valueOf(rowInput));
        if(rowInt == 0) {
            rowInt = 10;
        }
        Row row = Row.from(rowInt);

        char colInput = input.charAt(1);
        int colInt = Integer.parseInt(String.valueOf(colInput));
        Column column = Column.from(colInt);

        return new Position(row, column);
    }
}
