package janggi.domain.board;

import janggi.domain.PieceInfo;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.Empty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements BoardInterface {
    private static final String INVALID_PIECE_SIDE_MESSAGE = "자기 진영의 기물만 움직일 수 있습니다.";

    private final Map<Position, Piece> board;
    private final Map<Side, Boolean> isGungAlive = new HashMap<>();

    public Board(Map<Position, Piece> board) {
        this.board = board;

        isGungAlive.put(Side.CHO, true);
        isGungAlive.put(Side.HAN, true);
    }

    @Override
    public boolean isEmpty(Position position) {
        return board.get(position).isEqualPieceType(PieceType.NONE);
    }

    @Override
    public boolean isPo(Position position) {
        return board.get(position).isEqualPieceType(PieceType.PO);
    }

    @Override
    public boolean isEnemy(Side side, Position position) {
        return !board.get(position).isEqualSide(side);
    }

    @Override
    public boolean isAlly(Side side, Position position) {
        return board.get(position).isEqualSide(side);
    }

    @Override
    public PieceInfo[][] getCurrentBoard() {
        PieceInfo[][] currentBoard = new PieceInfo[10][9];

        board.forEach((position, piece) -> currentBoard[position.x() - 1][position.y() - 1] = piece.getPieceInfo());
        return currentBoard;
    }

    public void move(Position start, Position end, Side side) {
        Piece piece = board.get(start);
        if (!piece.isEqualSide(side)) {
            throw new IllegalArgumentException(INVALID_PIECE_SIDE_MESSAGE);
        }

        List<Position> route = piece.findRoute(start, end);
        piece.validateRoute(route, this);

        movePiece(start, end, piece, side);
    }

    public boolean isEndGame() {
        return isGungAlive.values().stream().anyMatch(isCaptured -> isCaptured == false);
    }

    private void movePiece(Position start, Position end, Piece piece, Side side) {
        Piece destinationPiece = board.get(end);

        if(destinationPiece.isGung()) {
            isGungAlive.put(side.getOppositeSide(), false);
        }
        board.put(end, piece);
        board.put(start, new Empty());
    }
}
