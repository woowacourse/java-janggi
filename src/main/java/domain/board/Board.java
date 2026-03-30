package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.*;

public class Board implements BoardChecker {
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(board.get(position));
    }

    @Override
    public boolean isTargetType(Position position, PieceType pieceType) {
        Piece piece = board.get(position);

        if (piece == null) {
            return false;
        }

        return piece.type() == pieceType;
    }

    public void move(Position from, Position to) {
        Piece targetPiece = findBy(from);

        targetPiece.move(from, to, this);

        board.remove(from);
        board.put(to, targetPiece);
    }

    public Piece findBy(Position position) {
        Piece piece = board.get(position);

        if (piece == null) {
            throw new NoSuchElementException("[ERROR] 해당 좌표에 기물이 없습니다.");
        }

        return piece;
    }

    @Override
    public List<Piece> findPiecesInPath(List<Position> path) {
        List<Piece> piecesInPath = new ArrayList<>();

        for (Position position : path) {
            addPieceIfExists(position, piecesInPath);
        }

        return piecesInPath;
    }

    private void addPieceIfExists(Position position, List<Piece> piecesInPath) {
        Piece piece = board.get(position);

        if (piece != null) {
            piecesInPath.add(piece);
        }
    }

    @Override
    public boolean isSameCamp(Position from, Position to) {
        Piece fromPiece = findBy(from);
        Piece toPiece = board.get(to);

        if (toPiece == null) {
            return false;
        }

        return fromPiece.camp() == toPiece.camp();
    }
}
