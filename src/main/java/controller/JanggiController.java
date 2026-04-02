package controller;

import model.GameStatus;
import model.Janggi;
import model.Team;
import model.board.Board;
import model.board.BoardFactory;
import model.board.ScoreResult;
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

    private void handleMove(Janggi janggi) {
        Team currentTurn = janggi.getTurn();

        Position current = inputView.readSource(currentTurn);
        Piece piece = janggi.findPieceAt(current, currentTurn);

        Position next = inputView.readDestination(currentTurn, piece);
        GameStatus status = janggi.move(current, next);

        outputView.displayBoard(janggi.board());

        if (status == GameStatus.WIN_BY_CAPTURE) {
            Team winner = janggi.getWinnerByCapture();
            outputView.displayWinner(winner.getName());
        }
    }

    private void handleScore(Janggi janggi) {
        ScoreResult scoreResult = janggi.calculateScoreResultOfTeams();

        outputView.displayScores(scoreResult.choScore(), scoreResult.hanScore());
        outputView.displayWinner(scoreResult.winner().getName());
    }

    private void handleQuit(Janggi janggi) {
        janggi.quit();
        outputView.displaySaved();
    }

    public void run() {
        List<JanggiFormation> formations = Arrays.asList(JanggiFormation.values());
        JanggiFormation hanFormation = retry(() -> inputView.readFormationNumber(HAN, formations), processError());
        JanggiFormation choFormation = retry(() -> inputView.readFormationNumber(CHO, formations), processError());

        Map<Position, Piece> pieceByFormation = FormationFactory.generateFormation(hanFormation, choFormation);
        Board board = BoardFactory.generatePieces(pieceByFormation);
        outputView.displayBoard(board.board());

        Janggi janggi = new Janggi(board);
        processCommand(janggi);
    }

    private void processCommand(Janggi janggi) {
        int turn = 0;
        while (janggi.isPlaying() && turn++ < MAX_RETRY) {
            CommandType commandType = retry(inputView::readCommand, processError());
            retry(() -> commandMap.get(commandType).accept(janggi), processError());
        }
        if (janggi.isPlaying()) {
            handleScore(janggi);
        }
    }

    private Consumer<IllegalArgumentException> processError() {
        return (e) -> outputView.displayError(e.getMessage());
    }
}
