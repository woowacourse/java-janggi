package janggi.controller;

import static janggi.controller.JanggiFunction.GIVE_UP;
import static janggi.controller.JanggiFunction.MOVE;
import static janggi.domain.GameStatus.PROGRESS;
import static janggi.domain.Input.Y;
import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.database.MySQLDatabaseConnection;
import janggi.database.QueryProcessor;
import janggi.database.dao.PieceDao;
import janggi.database.dao.TurnDao;
import janggi.domain.BoardSetup;
import janggi.domain.Game;
import janggi.domain.Pieces;
import janggi.domain.Team;
import janggi.domain.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PiecesInitializer;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import janggi.repository.JdbcPieceRepository;
import janggi.repository.JdbcTurnRepository;
import janggi.service.PieceService;
import janggi.service.TurnService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Set;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final PieceService pieceService = new PieceService(new JdbcPieceRepository(new PieceDao(new QueryProcessor(
            MySQLDatabaseConnection.getInstance()))));
    private final TurnService turnService = new TurnService(
            new JdbcTurnRepository(new TurnDao(new QueryProcessor(MySQLDatabaseConnection.getInstance()))));

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final Game game = generateBoard();
        final List<Piece> pieces = game.getPieces();
        boolean isProgress = true;
        while (isProgress) {
            try {
                isProgress = startGame(game, pieces);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
        outputView.printGameResult(game.getStatus(), game.getScoreByTeam(RED), game.getScoreByTeam(BLUE));
    }

    private Game generateBoard() {
        final List<Piece> pieces = pieceService.findAll();
        if (pieces.isEmpty()) {
            return setNewGame();
        }
        try {
            if (inputView.inputNewGame() == Y) {
                return setNewGame();
            }
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            generateBoard();
        }
        return new Game(new Pieces(pieces), turnService.find());
    }

    private Game setNewGame() {
        pieceService.deleteAll();
        turnService.delete();
        return generateNewBoard();
    }

    private boolean startGame(final Game game, final List<Piece> pieces) {
        displayGameState(game.getTurn().getTurn(), pieces);
        final JanggiFunction input = inputView.inputSelectFunction();
        if (input == MOVE) {
            final Piece selectedPiece = selectPieceToMove(game);
            final Set<Route> possibleRoutes = findPossibleRoutesForPiece(game, selectedPiece);
            movePieceIfValid(game, selectedPiece, possibleRoutes);
            return isGameOver(game);
        }
        if (input == GIVE_UP) {
            return stopGameIfAgreeEachOther();
        }
        return false;

    }

    private boolean isGameOver(final Game game) {
        return game.getStatus() == PROGRESS;
    }

    private void displayGameState(final Team team, final List<Piece> pieces) {
        outputView.printBoard(pieces);
        outputView.printTurn(team);
    }

    private Piece selectPieceToMove(final Game game) {
        final Position position = inputView.inputPiecePosition();
        return game.selectPiece(position);
    }

    private Set<Route> findPossibleRoutesForPiece(final Game game, final Piece selectedPiece) {
        final Set<Route> possibleRoutes = game.findPossibleRoutes(selectedPiece);
        outputView.printPossibleRoutes(possibleRoutes);
        return possibleRoutes;
    }

    private void movePieceIfValid(final Game game, final Piece selectedPiece, final Set<Route> possibleRoutes) {
        final Position destination = inputView.inputDestination();

        if (canMove(possibleRoutes, destination)) {
            pieceService.delete(selectedPiece.getPosition());
            pieceService.delete(destination);
            game.movePiece(destination, selectedPiece);
            pieceService.add(selectedPiece);
            game.changeTurn();
            turnService.updateTurn(game.getTurn());
            return;
        }
        throw new IllegalArgumentException("해당 위치로 갈 수 없습니다.");
    }

    private boolean canMove(final Set<Route> possibleRoutes, final Position destination) {
        return possibleRoutes.stream()
                .anyMatch(route -> route.isDestination(destination));
    }

    private Game generateNewBoard() {
        while (true) {
            try {
                final BoardSetup redBoardSetup = inputView.inputBoardSetup(RED);
                final BoardSetup blueBoardSetup = inputView.inputBoardSetup(BLUE);
                final List<Piece> pieces = PiecesInitializer.initializePieces(redBoardSetup, blueBoardSetup);
                final Turn turn = new Turn(BLUE);
                pieceService.addAll(pieces);
                turnService.add(turn);
                return new Game(new Pieces(pieces), new Turn(BLUE));
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private boolean stopGameIfAgreeEachOther() {
        if (inputView.inputStopGame() == Y & inputView.inputStopGame() == Y) {
            pieceService.deleteAll();
            turnService.delete();
            return false;
        }
        return true;
    }
}
