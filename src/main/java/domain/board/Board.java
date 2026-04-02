package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Board implements BoardChecker {
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(board.get(position));
    }

    @Override
    public boolean isTargetType(Position position, PieceType pieceType) {
        return findPiece(position)
                .map(piece -> piece.type() == pieceType)
                .orElse(false);
    }

    public void move(Position from, Position to) {
        Piece targetPiece = findBy(from);

        targetPiece.move(from, to, this);

        board.remove(from);
        board.put(to, targetPiece);
    }

    public Piece findBy(Position position) {
        return findPiece(position)
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 해당 좌표에 기물이 없습니다."));
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
        findPiece(position).ifPresent(piecesInPath::add);
    }

    @Override
    public boolean isSameCamp(Position from, Position to) {
        Piece fromPiece = findBy(from);

        return findPiece(to)
                .map(toPiece -> fromPiece.camp() == toPiece.camp())
                .orElse(false);
    }
}
