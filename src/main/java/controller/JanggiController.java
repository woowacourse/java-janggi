package controller;

import model.Board;
import model.BoardFactory;
import model.Janggi;
import model.Team;
import model.coordinate.Position;
import model.formation.FormationFactory;
import model.formation.JanggiFormation;
import model.piece.Piece;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static controller.Retrier.retry;
import static model.Team.CHO;
import static model.Team.HAN;

public class JanggiController {
    private static final int MAX_RETRY = 200;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        List<JanggiFormation> formations = Arrays.asList(JanggiFormation.values());
        JanggiFormation hanFormation = retry(() -> inputView.readFormationNumber(HAN, formations), processError());
        JanggiFormation choFormation = retry(() -> inputView.readFormationNumber(CHO, formations), processError());

        Map<Position, Piece> pieceByFormation = FormationFactory.generateFormation(hanFormation, choFormation);
        Board board = BoardFactory.generatePieces(pieceByFormation);
        outputView.displayBoard(board.board());

        Janggi janggi = new Janggi(board);
        int trial = 0;
        while (trial++ < MAX_RETRY) {
            retry(() -> playByTurn(janggi), processError());
            outputView.displayBoard(board.board());
        }
    }

    private void playByTurn(Janggi janggi) {
        Team currentTurn = janggi.getTurn();

        Position current = inputView.readSource(currentTurn);
        Piece piece = janggi.findPieceAt(current, currentTurn);

        Position next = inputView.readDestination(currentTurn, piece);
        janggi.move(current, next);
    }

    private Consumer<IllegalArgumentException> processError() {
        return (e) -> outputView.displayError(e.getMessage());
    }
}
