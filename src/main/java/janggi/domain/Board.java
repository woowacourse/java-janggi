package janggi.domain;

import janggi.exception.BusinessException;
import janggi.exception.EmptyPositionException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardState{
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> initialPieces) {
        this.board = new HashMap<>(initialPieces);
    }

    @Override
    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    @Override
    public Piece getPieceAt(Position position) {
        return board.get(position);
    }

    public void move(Position from, Position to) {
        validateMove(from, to);

        Piece movingPiece = getNonNullPiece(from);
        movingPiece.verifyMove(from, to, this);

        executeMove(from, to, movingPiece);
    }

    private void validateMove(Position from, Position to) {
        if (from.equals(to)) {
            throw new BusinessException("출발지와 목적지가 같을 수 없습니다.");
        }

        if (hasPieceAt(to) && isSameTeam(from, to)) {
            throw new BusinessException("목적지에 아군 기물이 위치하고 있습니다.");
        }
    }

    private boolean isSameTeam(Position from, Position to) {
        return board.get(from).isSameTeam(board.get(to));
    }

    private Piece getNonNullPiece(Position position) {
        Piece piece = board.get(position);
        if (piece == null) {
            throw new EmptyPositionException();
        }
        return piece;
    }

    private void executeMove(Position from, Position to, Piece movingPiece) {
        board.remove(from);
        board.put(to, movingPiece);
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }
}
