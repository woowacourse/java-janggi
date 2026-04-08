package domain.board;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board implements BoardChecker {
    private final Map<Position, Piece> board;
    private final Palace palace;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
        this.palace = new Palace();
    }

    public Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(board.get(position));
    }

    public List<BoardPiece> pieces() {
        List<BoardPiece> boardPieces = new ArrayList<>();

        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            boardPieces.add(new BoardPiece(position, piece.camp(), piece.type()));
        }

        return boardPieces;
    }

    @Override
    public boolean isTargetType(Position position, PieceType pieceType) {
        return findPiece(position)
                .map(piece -> piece.type() == pieceType)
                .orElse(false);
    }

    public Optional<Piece> move(Position from, Position to) {
        Piece targetPiece = findBy(from);
        Optional<Piece> capturedPiece = findPiece(to);

        targetPiece.move(from, to, this);

        board.remove(from);
        board.put(to, targetPiece);

        return capturedPiece;
    }

    public Piece findBy(Position position) {
        return findPiece(position)
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 해당 좌표에 기물이 없습니다."));
    }

    @Override
    public List<Piece> findPiecesInPath(List<Position> path) {
        return path.stream()
                .map(this::findPiece)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isSameCamp(Position from, Position to) {
        Piece fromPiece = findBy(from);

        return findPiece(to)
                .map(toPiece -> fromPiece.camp() == toPiece.camp())
                .orElse(false);
    }

    @Override
    public Optional<List<Position>> findMovePath(Position from, Position to) {
        if (from.isOrthogonallyAligned(to)) {
            return Optional.of(from.findOrthogonalPath(to));
        }

        return palace.findDiagonalPath(from, to);
    }

    @Override
    public boolean isInsidePalace(Position position) {
        return palace.contains(position);
    }

    public int scoreOf(Camp camp) {
        return board.values().stream()
                .filter(piece -> piece.camp() == camp)
                .mapToInt(Piece::score)
                .sum();
    }
}
