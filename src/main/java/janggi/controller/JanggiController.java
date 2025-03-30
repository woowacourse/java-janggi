package janggi.controller;

import janggi.db.Connection;
import janggi.db.PieceDao;
import janggi.db.Table;
import janggi.db.TurnDao;
import janggi.domain.Board;
import janggi.domain.InitialElephantSetting;
import janggi.domain.Pieces;
import janggi.domain.PiecesInitializer;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.UserContinueResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final PieceDao pieceDao;
    private final TurnDao turnDao;
    private final Connection connection;

    public JanggiController(InputView inputView, OutputView outputView, Connection connection) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.connection = connection;
        this.pieceDao = new PieceDao(connection);
        this.turnDao = new TurnDao(connection);
    }

    public void run() throws SQLException {
        Table table = new Table(connection);
        initializeDatabase(table);

        while (true) {
            Board board = readBoardFromDatabase();
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

            updateDatabase(board);
        }
    }

    private Board readBoardFromDatabase() {
        Team currentTurn = turnDao.readTeam();
        Pieces currentPieces = new Pieces(pieceDao.readAllPiece());
        return new Board(currentPieces, currentTurn);
    }

    private void showCurrentState(Board board) {
        outputView.printTeamScore(board.getTeamScore(Team.RED), board.getTeamScore(Team.BLUE));
        outputView.printBoard(board.getPieces());
        outputView.printTurn(board.getTurn());
    }

    private void updateDatabase(Board board) {
        pieceDao.deleteAllPiece();

        for (Piece piece : board.getPieces()) {
            pieceDao.addPiece(piece);
        }
        turnDao.updateTeam(board.getTurn());
    }

    private void initializeDatabase(Table table) throws SQLException {
        if (!table.isTableExist("piece") ||
                !table.isTableExist("turn")) {
            if (table.isTableExist("piece")) {
                table.dropTable("piece");
            }
            if (table.isTableExist("turn")) {
                table.dropTable("turn");
            }

            table.createPieceTable();
            table.createTurnTable();

            List<Piece> initialPieces = PiecesInitializer.initializePieces(InitialElephantSetting.INNER_ELEPHANT)
                    .getPieces();

            for (Piece initialPiece : initialPieces) {
                pieceDao.addPiece(initialPiece);
            }

            turnDao.addTeam(Team.BLUE);
        }
    }

    private void move(Set<Position> possibleDestinations, Board board, Piece selectedPiece) {
        if (possibleDestinations.isEmpty()) {
            outputView.printCannotMove();
        }
        if (!possibleDestinations.isEmpty()) {
            outputView.printPossibleRoutes(possibleDestinations);
            UserExceptionHandler.retryUntilSuccess(() -> movePiece(board, selectedPiece));
        }
    }

    private Piece selectPiece(Board board) {
        Position position = inputView.inputPiecePosition();
        return board.selectPiece(position);
    }

    private void movePiece(Board board, Piece selectedPiece) {
        Position destination = inputView.inputDestination();
        board.movePiece(destination, selectedPiece);
    }
}
