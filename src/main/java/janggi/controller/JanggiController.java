package janggi.controller;

import janggi.db.Connection;
import janggi.db.PieceDao;
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
    PieceDao pieceDao = new PieceDao();
    TurnDao turnDao = new TurnDao();

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() throws SQLException {
        Connection connection = new Connection();
        initializeDatabase(connection);

        while (true) {
            Board board = readBoardFromDatabase();
            showCurrentState(board);

            UserContinueResponse userContinueResponse = UserExceptionHandler.retryUntilSuccess(inputView::continueGame);

            if (userContinueResponse == UserContinueResponse.QUIT) {
                outputView.printWinnerWithSurrender(board.getTurn());
                connection.dropTable("piece");
                connection.dropTable("turn");
                break;
            }

            Piece selectedPiece = UserExceptionHandler.retryUntilSuccess(() -> selectPiece(board));
            Set<Position> possibleDestinations = board.findDestinations(selectedPiece);

            move(possibleDestinations, board, selectedPiece);

            if (board.isGameEnd(board.getTurn())) {
                Team winner = board.getWinner(board.getTurn());
                outputView.printWinnerWithGameEnd(winner);

                connection.dropTable("piece");
                connection.dropTable("turn");
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

    private void initializeDatabase(Connection connection) throws SQLException {
        if (!connection.isTableExist("piece") ||
                !connection.isTableExist("turn")) {
            if (connection.isTableExist("piece")) {
                connection.dropTable("piece");
            }
            if (connection.isTableExist("turn")) {
                connection.dropTable("turn");
            }

            // 피스 테이블 생성, 턴 테이블 생성
            connection.createPieceTable();
            connection.createTurnTable();

            List<Piece> initialPieces = PiecesInitializer.initializePieces(InitialElephantSetting.INNER_ELEPHANT)
                    .getPieces();

            // 피스 테이블에 초기 장기말 저장
            for (Piece initialPiece : initialPieces) {
                pieceDao.addPiece(initialPiece);
            }

            // 턴 테이블에 블루팀 저장
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
