package janggi.controller;

import janggi.db.Connection;
import janggi.db.DatabaseInitializer;
import janggi.db.PieceDao;
import janggi.db.Table;
import janggi.db.TurnDao;
import janggi.domain.Board;
import janggi.domain.BoardService;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.UserContinueResponse;
import java.sql.SQLException;
import java.util.Set;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final PieceDao pieceDao;
    private final TurnDao turnDao;
    private final Connection connection;

    public JanggiController(final InputView inputView, final OutputView outputView, final Connection connection) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.connection = connection;
        this.pieceDao = new PieceDao(connection);
        this.turnDao = new TurnDao(connection);
    }

    public void run() throws SQLException {
        Table table = new Table(connection);
        BoardService boardService = new BoardService(pieceDao, turnDao);
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(table);
        databaseInitializer.initialize(pieceDao, turnDao);

        while (true) {
            Board board = boardService.readBoardFromDatabase();
            showCurrentState(board);
            UserContinueResponse userContinueResponse = UserExceptionHandler.retryUntilSuccess(inputView::continueGame);

            if (userContinueResponse == UserContinueResponse.QUIT) {
                outputView.printWinnerWithSurrender(board.getTurn());
                table.dropTable("piece");
                table.dropTable("turn");
                break;
            }

            Piece selectedPiece = UserExceptionHandler.retryUntilSuccess(() -> selectPiece(board));
            Set<Position> possibleDestinations = board.findDestinations(selectedPiece);

            move(possibleDestinations, board, selectedPiece);

            if (board.isGameEnd(board.getTurn())) {
                Team winner = board.getWinner(board.getTurn());
                outputView.printWinnerWithGameEnd(winner);
                table.dropTable("piece");
                table.dropTable("turn");
                break;
            }
            boardService.updateDatabase(board);
        }
    }

    private Piece selectPiece(final Board board) {
        Position position = inputView.inputPiecePosition();
        return board.selectPiece(position);
    }

    private void showCurrentState(final Board board) {
        outputView.printTeamScore(board.getTeamScore(Team.RED), board.getTeamScore(Team.BLUE));
        outputView.printBoard(board.getPieces());
        outputView.printTurn(board.getTurn());
    }

    private void move(final Set<Position> possibleDestinations, final Board board, final Piece selectedPiece) {
        if (possibleDestinations.isEmpty()) {
            outputView.printCannotMove();
        }
        if (!possibleDestinations.isEmpty()) {
            outputView.printPossibleRoutes(possibleDestinations);
            UserExceptionHandler.retryUntilSuccess(() -> movePiece(board, selectedPiece));
        }
    }

    private void movePiece(final Board board, final Piece selectedPiece) {
        Position destination = inputView.inputDestination();
        board.movePiece(destination, selectedPiece);
    }
}
