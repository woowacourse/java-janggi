package domain;

import domain.piece.Piece;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import strategy.InitializeStrategy;

public class Board {
    private static final int MAX_ROW = 10;
    private static final int MIN_ROW = 1;
    private static final int MAX_COLUMN = 9;
    private static final int MIN_COLUMN = 1;

    protected final Map<Position, Piece> pieces = new HashMap<>();

    public Board(InitializeStrategy choInitializeStrategy, InitializeStrategy hanInitializeStrategy) {
        initTeamBoard(choInitializeStrategy, Team.CHO);
        initTeamBoard(hanInitializeStrategy, Team.HAN);
    }

    public void move(Position from, Position to, PieceType pieceType) {
        Piece piece = validateMovablePiece(from, to, pieceType);
        validateCanMove(from, to, piece);
        movePiece(from, to, piece);
    }

    private Piece validateMovablePiece(Position from, Position to, PieceType pieceType) {
        Piece piece = pieces.get(from);

        if (piece == null) {
            throw new IllegalArgumentException("해당 위치에 피스가 없습니다.");
        }

        if (piece.getType() != pieceType) {
            throw new IllegalArgumentException("해당 위치에 해당 타입이 없습니다.");
        }

        if (!to.isPossiblePosition(MAX_ROW, MIN_ROW, MAX_COLUMN, MIN_COLUMN)) {
            throw new IllegalArgumentException("기물의 도착지점이 판 범위를 넘어섰습니다.");
        }

        return piece;
    }

    private void validateCanMove(Position from, Position to, Piece piece) {
        if (!piece.canMove(from, to, this)) {
            throw new IllegalArgumentException("해당 위치로 옮길 수 없습니다.");
        }
    }

    private void movePiece(Position from, Position to, Piece piece) {
        pieces.remove(from);
        pieces.put(to, piece);
    }

    public boolean isExistSameType(Position position, Piece piece) {
        if (hasPieceInPosition(position)) {
            return pieces.get(position).getType()
                    .equals(piece.getType());
        }

        return false;
    }

    public boolean isEmpty(Position position) {
        return !pieces.containsKey(position);
    }

    public boolean hasSameTeamOn(Position position, Piece piece) {
        if (hasPieceInPosition(position)) {
            return pieces.get(position).isSameTeam(piece);
        }

        return false;
    }

    private boolean hasPieceInPosition(Position position) {
        return pieces.containsKey(position);
    }

    private void initTeamBoard(InitializeStrategy strategy, Team team) {
        pieces.putAll(strategy.initialize(team));
    }

    public List<Piece> findPiecesInLinePath(Position from, Position to) {
        if (from.isSameRow(to)) {
            return findPiecesInRow(from, to);
        }

        if (from.isSameColumn(to)) {
            return findPiecesInColumn(from, to);
        }
        return new ArrayList<>();
    }

    private List<Piece> findPiecesInRow(Position from, Position to) {
        List<Piece> result = new ArrayList<>();

        int row = from.getRow();
        int start = Math.min(from.getColumn(), to.getColumn());
        int end = Math.max(from.getColumn(), to.getColumn());

        for (int column = start + 1; column < end; column++) {
            Position searchPosition = Position.from(row, column);
            if (pieces.containsKey(searchPosition)) {
                result.add(pieces.get(searchPosition));
            }
        }
        return result;
    }

    private List<Piece> findPiecesInColumn(Position from, Position to) {
        List<Piece> result = new ArrayList<>();

        int column = from.getColumn();
        int start = Math.min(from.getRow(), to.getRow());
        int end = Math.max(from.getRow(), to.getRow());

        for (int row = start + 1; row < end; row++) {
            Position searchPosition = Position.from(row, column);
            if (pieces.containsKey(searchPosition)) {
                result.add(pieces.get(searchPosition));
            }
        }
        return result;
    }
}
