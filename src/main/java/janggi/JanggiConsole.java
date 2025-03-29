package janggi;

import janggi.board.Board;
import janggi.board.BoardOrder;
import janggi.piece.PiecesFactory;
import janggi.piece.Players;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.turn.Turn;
import janggi.utils.ExceptionHandler;
import janggi.utils.StringParser;
import janggi.view.InputView;
import janggi.view.ResultView;
import java.util.List;
import java.util.Map;

public class JanggiConsole {

    private static final String EXIT = "exit";
    private final InputView inputView;
    private final ResultView resultView;

    public JanggiConsole(final InputView inputView, final ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    public void start() {
        final Players players = makePlayers();
        Turn turn = Turn.initialize();

        while (players.canContinue() && !turn.canExit()) {
            final Team currentTeam = turn.getTeam();
            resultView.printOrder(currentTeam);
            if (!movePiece(players, currentTeam)) {
                turn.wantExit();
            }
            resultView.printBoard(players.getChoPieces(), players.getHanPieces());
            turn = turn.moveNextTurn();
        }

        resultView.printJanggiResult(players.findWinningTeam(), players.calculateScore());
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

    private boolean movePiece(final Players players, final Team currentTeam) {
        return ExceptionHandler.retry(() -> moveOnePiece(players, currentTeam));
    }

    private Boolean moveOnePiece(final Players players, final Team currentTeam) {
        final String input = inputView.readMovingPosition();
        if (input.equals(EXIT)) {
            return false;
        }
        final List<Integer> positions = readPositions(input);
        players.move(Position.from(positions.getFirst()), Position.from(positions.getLast()), currentTeam);
        return true;
    }

    private List<Integer> readPositions(final String input) {
        final List<String> tokens = StringParser.split(input);
        return StringParser.parseInt(tokens);
    }
}
