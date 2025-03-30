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
        // 테이블이 있는지 없는지(턴 테이블과 기물 테이블이 둘 다 존재해야함) 체크
        if (!connection.isTableExist("piece") ||
                !connection.isTableExist("turn")) {// if (테이블이 둘 중 하나라도 없다면 테이블 모두 삭제 후 새로 생성)
            if (connection.isTableExist("piece")) { // piece가 있다면 그 테이블 삭제
                connection.dropTable("piece");
            }
            if (connection.isTableExist("turn")) { // turn 이 있다면 그 테이블 삭제
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

        // if (테이블이 있다면? 테이블에서 데이터를 불러와서 게임 진행) // 이건 어차피 반드시 실행되어야 할 프로세스임
        // 불러올 데이터 -> 기물들, 현재 턴
        Pieces currentPieces = new Pieces(pieceDao.readAllPiece());
        Team currentTurn = turnDao.readTeam();
        Board board = new Board(currentPieces, currentTurn);
        List<Piece> pieces = board.getPieces();

        // 이하는 게임 진행
        outputView.printBoard(pieces);

        while (true) {
            currentTurn = turnDao.readTeam(); // db에서 턴 읽어오기
            outputView.printTurn(currentTurn);
            UserContinueResponse userContinueResponse = UserExceptionHandler.retryUntilSuccess(inputView::continueGame);

            if (userContinueResponse == UserContinueResponse.QUIT) { // 게임종료
                outputView.printWinnerWithSurrender(currentTurn);
                connection.dropTable("piece");
                connection.dropTable("turn");
                break;
            }

            Board finalBoard = board;
            Piece selectedPiece = UserExceptionHandler.retryUntilSuccess(() -> selectPiece(finalBoard));
            Set<Position> possibleDestinations = board.findDestinations(selectedPiece);

            move(possibleDestinations, board, selectedPiece);

            if (board.isGameEnd(currentTurn)) { // 게임 종료
                Team winner = board.getWinner(currentTurn);
                outputView.printWinnerWithGameEnd(winner);

                connection.dropTable("piece");
                connection.dropTable("turn");
                break;
            }

            outputView.printTeamScore(board.getTeamScore(Team.RED), board.getTeamScore(Team.BLUE));
            outputView.printBoard(board.getPieces());

            // -- 한 턴 종료

            // piece db 데이터 전체 삭제
            pieceDao.deleteAllPiece();
            // piece db 새로 생성
            for (Piece piece : board.getPieces()) {
                pieceDao.addPiece(piece);
            }

            // piece 읽어오기
            List<Piece> piecesFromDb = pieceDao.readAllPiece();
            board = new Board(new Pieces(piecesFromDb), Team.getOtherTeam(currentTurn));

            // 턴 db에 업데이트
            turnDao.updateTeam(currentTurn);
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
