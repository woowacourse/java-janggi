package controller;

import model.Janggi;
import model.Team;
import model.board.Board;
import model.board.BoardFactory;
import model.coordinate.Position;
import model.formation.FormationFactory;
import model.formation.JanggiFormation;
import model.piece.Piece;
import view.InputView;
import view.OutputView;
import view.command.CommandType;

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
    private final Map<CommandType, Consumer<Janggi>> commandMap;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.commandMap = Map.of(
                CommandType.MOVE, this::handleMove,
                CommandType.SCORE, this::handleScore,
                CommandType.QUIT, this::handleQuit
        );
    }

    public void run() {
        List<JanggiFormation> formations = Arrays.asList(JanggiFormation.values());
        JanggiFormation hanFormation = retry(() -> inputView.readFormationNumber(HAN, formations), processError());
        JanggiFormation choFormation = retry(() -> inputView.readFormationNumber(CHO, formations), processError());

        Map<Position, Piece> pieceByFormation = FormationFactory.generateFormation(hanFormation, choFormation);
        Board board = BoardFactory.generatePieces(pieceByFormation);
        outputView.displayBoard(board.board());

        Janggi janggi = new Janggi(board);
        int turn = 0;
        while (!janggi.isFinished() && turn++ < MAX_RETRY) {
            CommandType commandType = retry(inputView::readCommand, processError());
            retry(() -> commandMap.get(commandType).accept(janggi), processError());
        }
        if (!janggi.isFinished()) {
            handleScore(janggi);
        }
    }

    private void handleMove(Janggi janggi) {
        Team currentTurn = janggi.getTurn();

        Position current = inputView.readSource(currentTurn);
        Piece piece = janggi.findPieceAt(current, currentTurn);

        Position next = inputView.readDestination(currentTurn, piece);
        janggi.move(current, next);

        outputView.displayBoard(janggi.board());

        if (janggi.isFinished()) {
            Team winner = janggi.getWinnerByCapture();
            outputView.displayWinner(winner.getName());
        }
    }

    private void handleScore(Janggi janggi) {
        double choScore = janggi.getScore(CHO);
        double hanScore = janggi.getScore(HAN);
        Team winner = janggi.determineWinnerByScore();

        outputView.displayScores(choScore, hanScore);
        outputView.displayWinner(winner.getName());
    }

    private void handleQuit(Janggi janggi) {
        janggi.quit();
        outputView.displaySaved();
    }

    private Consumer<IllegalArgumentException> processError() {
        return (e) -> outputView.displayError(e.getMessage());
    }
}
