package controller;

import domain.board.AbstractBoardFactory;
import domain.board.Board;
import domain.game.Team;
import domain.game.Turn;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;
import util.Retry;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private Turn turn;

    private JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public static JanggiController of(InputView inputView, OutputView outputView) {
        return new JanggiController(inputView, outputView);
    }

    public void run() {
        Board board = createBoard();
        outputView.printBoard(board);
        turn = Turn.first();
        while (board.isGeneralAlive()) {
            playTurn(board);
        }
        board.calculateScore();
        outputView.printWinner(board.decideWinner());
        outputView.printScore(Team.CHO);
        outputView.printScore(Team.HAN);
    }

    private Board createBoard() {
        int choFormationNumber = Retry.untilSuccess(() -> inputView.initialFormation(Team.CHO));
        int hanFormationNumber = Retry.untilSuccess(() -> inputView.initialFormation(Team.HAN));
        AbstractBoardFactory choAbstractBoardFactory = AbstractBoardFactory.from(choFormationNumber);
        AbstractBoardFactory hanAbstractBoardFactory = AbstractBoardFactory.from(hanFormationNumber);
        Map<Position, Piece> board = AbstractBoardFactory.createFormation(choAbstractBoardFactory,
                hanAbstractBoardFactory);
        return new Board(board);
    }

    private void playTurn(Board board) {
        Retry.run(() -> {
            Position[] positions = inputView.askMovePiecePosition(turn.current());
            board.move(positions[0], positions[1]);
            outputView.printBoard(board);
            turn = turn.next();
        });
    }
}
