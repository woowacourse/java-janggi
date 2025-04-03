package janggi;

import janggi.dao.JanggiDatabaseManager;
import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.SangSetting;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import janggi.domain.team.Turn;
import janggi.utils.ExceptionHandler;
import janggi.view.InputView;
import janggi.view.Menu;
import janggi.view.ResultView;
import java.util.List;
import java.util.Map;

public class JanggiConsole {

    private final InputView inputView;
    private final ResultView resultView;
    private final JanggiDatabaseManager janggiDatabaseManager;

    public JanggiConsole(InputView inputView, ResultView resultView, JanggiDatabaseManager janggiDatabaseManager) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.janggiDatabaseManager = janggiDatabaseManager;
    }

    public void start() {
        JanggiGame janggiGame = createJanggiGame();

        printGameState(janggiGame);

        while (janggiGame.canContinueGame()) {
            Menu menu = ExceptionHandler.repeat(inputView::readMenu);
            if (menu == Menu.MOVE) {
                move(janggiGame);
            }
            if (menu == Menu.SAVE) {
                return;
            }
            if (menu == Menu.QUIT) {
                quit(janggiGame);
                return;
            }
        }

        resultView.printCatchingGungMessage();
        resultView.printJanggiResult(janggiGame.getWinningTeam());
        janggiDatabaseManager.endGame();
    }

    private JanggiGame createJanggiGame() {
        Map<Position, Piece> piecesForProgressingGame = janggiDatabaseManager.loadPiecesForProgressingGame();

        if (piecesForProgressingGame.isEmpty()) {
            return createInitialGame();
        }

        return loadProgressingGame(piecesForProgressingGame);
    }

    private JanggiGame createInitialGame() {
        final BoardFactory boardFactory = new BoardFactory();

        resultView.printSetting();
        SangSetting choSangSetting = ExceptionHandler.repeat(() -> SangSetting.selectSetting((
                inputView.readElephantSetting(TeamType.CHO))));
        SangSetting hanSangSetting = ExceptionHandler.repeat(() -> SangSetting.selectSetting((
                inputView.readElephantSetting(TeamType.HAN))));

        final Turn turn = new Turn();
        final Board board = boardFactory.makeBoard(choSangSetting, hanSangSetting);

        janggiDatabaseManager.saveInitialGame(turn.getCurrentTeam(), board.getPieces());
        return new JanggiGame(board, turn);
    }

    private JanggiGame loadProgressingGame(Map<Position, Piece> piecesForProgressingGame) {
        final BoardFactory boardFactory = new BoardFactory();
        final Turn turn = new Turn(janggiDatabaseManager.loadOrdersForProgressingGame());
        final Board board;

        board = boardFactory.loadProgressingBoard(piecesForProgressingGame);
        resultView.printLoadingDoneMessage();

        return new JanggiGame(board, turn);
    }

    private void move(JanggiGame janggiGame) {
        final TeamType currentTeamType = janggiGame.getCurrentTeam();
        resultView.printOrder(currentTeamType);
        ExceptionHandler.retry(() -> {
            List<Position> positions = ExceptionHandler.repeat(inputView::readMovingPosition);
            janggiGame.move(positions);
            janggiDatabaseManager.movePiece(positions.getFirst(), positions.getLast(), janggiGame.getCurrentTeam());
        });
        printGameState(janggiGame);
    }

    private void quit(JanggiGame janggiGame) {
        resultView.printScoreBoard(janggiGame.getScoreEachTeam());
        resultView.printJanggiResult(janggiGame.getWinningTeam());
        janggiDatabaseManager.endGame();
    }

    private void printGameState(JanggiGame janggiGame) {
        resultView.printBoard(janggiGame.getPieces());
        resultView.printScoreBoard(janggiGame.getScoreEachTeam());
    }
}
