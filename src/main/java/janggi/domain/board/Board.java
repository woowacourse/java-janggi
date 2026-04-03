package janggi.domain.board;

import janggi.domain.MoveResult;
import janggi.domain.PieceInfo;
import janggi.domain.Position;
import janggi.domain.ScoreStatus;
import janggi.domain.Side;
import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.List;
import java.util.Map;

public class Board implements BaseBoard {
    public static final int BOARD_START_ROWS = 1;
    public static final int BOARD_START_COLS = 1;
    public static final int ARRAY_INDEX_OFFSET = 1;
    public static final int BOARD_END_ROWS = 10;
    public static final int BOARD_END_COLS = 9;

    private static final String INVALID_PIECE_SIDE_MESSAGE = "자기 진영의 기물만 움직일 수 있습니다.";
    private final Map<Position, Piece> board;
    private final Map<Side, Double> scoresBySide;

    public Board(Map<Position, Piece> board, Map<Side, Double> scoresBySide) {
        this.board = board;
        this.scoresBySide = scoresBySide;
    }

    @Override
    public boolean isEmpty(Position position) {
        return board.get(position).isEqualPieceType(PieceType.NONE);
    }

    @Override
    public boolean isEqualPieceType(Position position, PieceType pieceType) {
        return board.get(position).isEqualPieceType(pieceType);
    }

    @Override
    public boolean isAlly(Side side, Position position) {
        return board.get(position).isEqualSide(side);
    }

    @Override
    public PieceInfo[][] getCurrentBoard() {
        PieceInfo[][] currentBoard = new PieceInfo[BOARD_END_ROWS][BOARD_END_COLS];

        board.forEach((position, piece) -> {
            int rowIndex = position.x() - ARRAY_INDEX_OFFSET;
            int colIndex = position.y() - ARRAY_INDEX_OFFSET;
            currentBoard[rowIndex][colIndex] = piece.getPieceInfo();
        });

        return currentBoard;
    }

    @Override
    public ScoreStatus getScoreStatus() {
        return new ScoreStatus(scoresBySide.get(Side.CHO), scoresBySide.get(Side.HAN));
    }

    public MoveResult move(Position start, Position end, Side side) {
        Piece startPiece = board.get(start);
        Piece targetPiece = board.get(end);
        if (!startPiece.isEqualSide(side)) {
            throw new IllegalArgumentException(INVALID_PIECE_SIDE_MESSAGE);
        }

        List<Position> route = startPiece.findRoute(start, end);
        startPiece.validateRoute(route, this);
        movePiece(start, end, startPiece);

        MoveResult moveResult = targetPiece.capturedResult();
        updateScore(moveResult, side.reverse());
        return moveResult;
    }

    private void movePiece(Position start, Position end, Piece startPiece) {
        board.put(end, startPiece);
        board.put(start, new None());
    }

    private void updateScore(MoveResult moveResult, Side side) {
        scoresBySide.put(side, scoresBySide.get(side) - moveResult.getCapturedPieceScore());
    }
}
