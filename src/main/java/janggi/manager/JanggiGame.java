package janggi.manager;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.dto.PositionDto;
import janggi.factory.PieceInitFactory;
import janggi.factory.masang.MaSangFactory;
import janggi.util.RecoveryUtil;
import janggi.view.MaSangPosition;
import janggi.view.PlayerOption;
import janggi.view.Viewer;
import java.util.Map;

public class JanggiGame {

    private final Viewer viewer;

    public JanggiGame(Viewer viewer) {
        this.viewer = viewer;
    }

    public void start() {


        Board board = initializeBoard();

        Team turn = Team.CHO;

        repeatGameTurns(board, turn);

        result(board);
    }




    private Board initializeBoard() {
        Map<Position, Piece> initializeBoard = PieceInitFactory.initialize();
        initializeBoard.putAll(placeMaSangPiecesBySide(Team.CHO));
        initializeBoard.putAll(placeMaSangPiecesBySide(Team.HAN));

        return new Board(initializeBoard);
    }

    private Map<Position, Piece> placeMaSangPiecesBySide(Team team) {
        MaSangPosition maSangPosition = RecoveryUtil.executeWithRetry(() -> viewer.settingMaSangPlacement(team));

        return MaSangFactory.create(maSangPosition, team);
    }

    private void repeatGameTurns(Board board, Team turn) {
        boolean isNotClosed = true;
        while (isNotClosed && board.hasGeneral(turn.reverse())) {
            viewer.printBoard(board);
            viewer.printTurnInfo(turn);
            isNotClosed = chooseOption(board, turn);

            turn = turn.reverse();
        }
    }

    // TODO 재귀 메모리 해제하도록하기
    private boolean chooseOption(Board board, Team turn) {
        PlayerOption playerOption = RecoveryUtil.executeWithRetry(viewer::readChooseOption);

        if (playerOption == PlayerOption.SELECT_PIECE) {
            Position position = RecoveryUtil.executeWithRetry(() -> choosePiece(board, turn));
            RecoveryUtil.executeWithRetry(() -> movePiece(board, position));
        }

        if (playerOption == PlayerOption.CHECK_SCORE) {
            viewer.printScore(board);
        }

        if (playerOption == PlayerOption.CLOSE) {
            return false;
        }

        if (playerOption != PlayerOption.SELECT_PIECE) {
            return chooseOption(board, turn);
        }

        return true;
    }

    private Position choosePiece(Board board, Team turn) {
        PositionDto positionDto = viewer.readPieceSelection();
        Position position = Position.of(positionDto.row(), positionDto.column());
        board.checkMoveablePiece(turn, position);

        return position;
    }

    private void movePiece(Board board, Position currentPosition) {
        PositionDto positionDto = viewer.readMove(board.getPiece(currentPosition));

        Position targetPosition = Position.of(positionDto.row(), positionDto.column());

        board.movePiece(currentPosition, targetPosition);
    }

    private void result(Board board) {
        if (board.hasGeneral(Team.CHO) && board.hasGeneral(Team.HAN)) {
            viewer.result(board);
            return;
        }

        if (board.hasGeneral(Team.CHO)) {
            viewer.result(Team.CHO);
            return;
        }

        viewer.result(Team.HAN);
    }
}
