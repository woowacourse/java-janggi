package janggi.manager;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.dto.PositionDto;
import janggi.factory.PieceInitFactory;
import janggi.factory.masang.MaSangFactory;
import janggi.util.RecoveryUtil;
import janggi.view.MaSangPosition;
import janggi.view.Option;
import janggi.view.Viewer;
import java.util.Map;

public class JanggiGame {

    private final Viewer viewer;

    public JanggiGame(Viewer viewer) {
        this.viewer = viewer;
    }

    public void start() {
        Board board = initializeBoard();

        Side turn = Side.CHO;

        repeatGameTurns(board, turn);

        result(board);
    }

    private Board initializeBoard() {
        Map<Position, Piece> initializeBoard = PieceInitFactory.initialize();
        initializeBoard.putAll(placeMaSangPiecesBySide(Side.CHO));
        initializeBoard.putAll(placeMaSangPiecesBySide(Side.HAN));

        return new Board(initializeBoard);
    }

    private Map<Position, Piece> placeMaSangPiecesBySide(Side side) {
        MaSangPosition maSangPosition = RecoveryUtil.executeWithRetry(() -> viewer.settingMaSangPlacement(side));

        return MaSangFactory.create(maSangPosition, side);
    }

    private void repeatGameTurns(Board board, Side turn) {
        boolean isNotClosed = true;
        while (isNotClosed && board.hasGeneral(turn.reverse())) {
            viewer.printBoard(board);
            viewer.printTurnInfo(turn);
            isNotClosed = chooseOption(board, turn);

            turn = turn.reverse();
        }
    }

    private boolean chooseOption(Board board, Side turn) {
        Option option = RecoveryUtil.executeWithRetry(viewer::readChooseOption);

        if (option == Option.SELECT_PIECE) {
            Position position = RecoveryUtil.executeWithRetry(() -> choosePiece(board, turn));
            RecoveryUtil.executeWithRetry(() -> movePiece(board, position));
        }

        if (option == Option.CHECK_SCORE) {
            viewer.printScore(board);
        }

        if (option == Option.CLOSE) {
            return false;
        }

        if (option != Option.SELECT_PIECE) {
            return chooseOption(board, turn);
        }

        return true;
    }

    private Position choosePiece(Board board, Side turn) {
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
        if (board.hasGeneral(Side.CHO) && board.hasGeneral(Side.HAN)) {
            viewer.result(board);
            return;
        }

        if (board.hasGeneral(Side.CHO)) {
            viewer.result(Side.CHO);
            return;
        }

        viewer.result(Side.HAN);
    }
}
