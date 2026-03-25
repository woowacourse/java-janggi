package janggi;

import janggi.domain.Board;
import janggi.domain.Turns;
import janggi.domain.piece.Piece;
import janggi.domain.side.TeamType;
import janggi.util.DelimiterParser;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.ArrayList;
import java.util.List;

public class JanggiRunner {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiRunner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void execute() {
        Board board = Board.createInitialBoard();
        outputView.printStartMessage();
        outputView.printBoard(board.makeSpots());

        Turns turns = new Turns(new ArrayList<>());
        TeamType nowTurn = turns.getFirstTurn();
        //
        while (true) {
            outputView.printTurnNotice(nowTurn.name());
            outputView.printAskPiecePosition();
            String rawPiecePosition = inputView.readLine();
            List<String> parsedPiecePosition = DelimiterParser.parse(rawPiecePosition);
            Piece piece = board.findPiece(parsedPiecePosition);

        }
//        TeamType nowTurn = turns.getPlayTurn();
    }
}
