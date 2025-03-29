package janggi;

import janggi.board.BoardOrder;
import janggi.piece.Board;
import janggi.piece.PiecesFactory;
import janggi.piece.Team;
import janggi.piece.Players;
import janggi.position.Position;
import janggi.turn.Turn;
import janggi.utils.ExceptionHandler;
import janggi.utils.StringParser;
import janggi.view.InputView;
import janggi.view.ResultView;
import java.util.List;
import java.util.Map;

public class JanggiConsole {

    private final InputView inputView;
    private final ResultView resultView;

    public JanggiConsole(final InputView inputView, final ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    public void start() {
        final Players players = makePlayers();
        Turn turn = Turn.initialize();

        while (players.canContinue()) {
            final Team currentTeam = turn.getTeam();
            resultView.printOrder(currentTeam);
            movePieces(players, currentTeam);
            resultView.printBoard(players.getChoPieces(), players.getHanPieces());
            turn = turn.moveNextTurn();
        }

        resultView.printJanggiResult(players.findWinningTeam());
    }

    private Players makePlayers() {
        final PiecesFactory piecesFactory = new PiecesFactory();
        final int choOrder = StringParser.parseInt(inputView.readChoBoardOrder());
        final int hanOrder = StringParser.parseInt(inputView.readHanBoardOrder());
        final Board choBoard = piecesFactory.makeChoPieces(BoardOrder.from(choOrder));
        final Board hanBoard = piecesFactory.makeHanPieces(BoardOrder.from(hanOrder));
        final Players players = new Players(Map.of(Team.CHO, choBoard, Team.HAN, hanBoard));
        resultView.printBoard(players.getChoPieces(), players.getHanPieces());
        return players;
    }

    private void movePieces(final Players players, final Team currentTeam) {
        ExceptionHandler.retry(() -> {
            final List<Integer> positions = inputView.readMovingPosition();
            players.move(Position.from(positions.getFirst()), Position.from(positions.getLast()), currentTeam);
        });
    }
}
