package domain.board;

import domain.piece.Camp;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Board implements PathChecker {

    private static final String NO_PIECE_EXIST_ERROR_MESSAGE = "[ERROR] 해당 좌표에 기물이 없습니다.";
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    @Override
    public Camp findCamp(Position position) {
        return findBy(position).camp();
    }

    public Piece findBy(Position position) {
        Piece piece = board.get(position);

        if (piece == null) {
            throw new NoSuchElementException(NO_PIECE_EXIST_ERROR_MESSAGE);
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
        Piece fromPiece = board.get(from);
        Piece toPiece = board.get(to);

        return fromPiece.camp() == toPiece.camp();
    }
}
