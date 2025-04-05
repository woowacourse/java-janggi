package janggi;

import janggi.dao.piece.PieceHistoryManager;
import janggi.dao.turn.TurnManager;
import janggi.domain.board.Board;
import janggi.domain.board.BoardOrder;
import janggi.domain.piece.PiecesFactory;
import janggi.domain.piece.position.Position;
import janggi.domain.players.Players;
import janggi.domain.players.Team;
import janggi.domain.players.Turn;
import janggi.dto.PieceMove;
import janggi.utils.ExceptionHandler;
import janggi.utils.StringParser;
import janggi.view.InputView;
import janggi.view.ResultView;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiConsole {

    private static final String EXIT = "exit";

    private final InputView inputView;
    private final ResultView resultView;
    private final PieceHistoryManager pieceHistoryManager;
    private final TurnManager turnManager;

    public JanggiConsole(final InputView inputView, final ResultView resultView,
                         final PieceHistoryManager pieceHistoryManager,
                         final TurnManager turnManager) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.pieceHistoryManager = pieceHistoryManager;
        this.turnManager = turnManager;
    }

    public void start() {
        final Players players = initialize(inputView.readInitialize());
        resultView.printBoard(players.getChoPieces(), players.getHanPieces());
        Turn turn = turnManager.findCurrentTurn();

        while (players.canContinue() && !turn.canExit()) {
            final Team currentTeam = turn.getTeam();
            resultView.printOrder(currentTeam);
            final Optional<PieceMove> pieceMove = movePiece(players, currentTeam);
            if (pieceMove.isEmpty()) {
                turn = turn.wantExit();
                continue;
            }
            pieceHistoryManager.updatePiece(pieceMove.get());
            resultView.printBoard(players.getChoPieces(), players.getHanPieces());
            turn = turn.moveNextTurn();
            turnManager.updateCurrentTurn(turn);
        }

        resultView.printJanggiResult(players.findWinningTeam(), players.calculateScore());
    }

    private Players initialize(final boolean wantInitialize) {
        if (wantInitialize || pieceHistoryManager.mustBeInitialize()) {
            final Players players = initializePlayers();
            pieceHistoryManager.initialize(players);
            turnManager.initialize();
            return players;
        }
        return pieceHistoryManager.loadPlayers();
    }

    private Players initializePlayers() {
        final PiecesFactory piecesFactory = new PiecesFactory();
        final int choOrder = StringParser.parseInt(inputView.readChoBoardOrder());
        final int hanOrder = StringParser.parseInt(inputView.readHanBoardOrder());
        final Board choBoard = piecesFactory.initializeChoPieces(BoardOrder.from(choOrder));
        final Board hanBoard = piecesFactory.initializeHanPieces(BoardOrder.from(hanOrder));
        return new Players(Map.of(Team.CHO, choBoard, Team.HAN, hanBoard));
    }

    private Optional<PieceMove> movePiece(final Players players, final Team currentTeam) {
        return ExceptionHandler.retry(() -> moveOnePiece(players, currentTeam));
    }

    private Optional<PieceMove> moveOnePiece(final Players players, final Team currentTeam) {
        final String input = inputView.readMovingPosition();
        if (input.equals(EXIT)) {
            return Optional.empty();
        }
        final List<Integer> positions = readPositions(input);
        final Position currentPosition = Position.from(positions.getFirst());
        final Position arrivalPosition = Position.from(positions.getLast());
        return Optional.of(players.move(currentPosition, arrivalPosition, currentTeam));
    }

    private List<Integer> readPositions(final String input) {
        final List<String> tokens = StringParser.split(input);
        return StringParser.parseInt(tokens);
    }
}
