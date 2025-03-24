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

        turn = repeatGameTurns(board, turn);

        viewer.winner(turn);
    }

    private Board initializeBoard() {
        Map<Position, Piece> initializeBoard = PieceInitFactory.initialize();
        initializeBoard.putAll(placeMaSangPiecesBySide(Side.CHO));
        initializeBoard.putAll(placeMaSangPiecesBySide(Side.HAN));

        return new Board(initializeBoard);
    }

    private Map<Position, Piece> placeMaSangPiecesBySide(Side side) {
        MaSangPosition maSangPosition = RecoveryUtil.executeWithRetry(() ->viewer.settingMaSangPlacement(side));

        return MaSangFactory.create(maSangPosition, side);
    }

    private Side repeatGameTurns(Board board, Side turn) {
        while (board.hasGeneral(turn.reverse())) {
            viewer.printBoard(board);
            viewer.printTurnInfo(turn);

            Side finalTurn = turn;
            Position position = RecoveryUtil.executeWithRetry(() -> choosePiece(board, finalTurn));
            RecoveryUtil.executeWithRetry(() -> movePiece(board, position));

            turn = turn.reverse();
        }
        return turn;
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
}
