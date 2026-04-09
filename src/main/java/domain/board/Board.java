package domain.board;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import dto.PieceDTO;

import java.util.*;

public class Board implements PathChecker {

    private static final String NO_PIECE_EXIST_ERROR_MESSAGE = "[ERROR] 해당 좌표에 기물이 없습니다.";
    private static final Palace PALACE = new Palace();
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    @Override
    public Camp findCamp(Position position) {
        return findBy(position).camp();
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

    public boolean isGeneral(Position to) {
        return findPiece(to)
                .map(piece -> piece.type() == PieceType.GENERAL)
                .orElse(false);
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
        Piece fromPiece = findBy(from);
        Piece toPiece = board.get(to);

        if (toPiece == null) {
            return false;
        }

        return fromPiece.camp() == toPiece.camp();
    }

    @Override
    public boolean isInPalace(Position position) {
        return PALACE.isInPalace(position);
    }

    @Override
    public boolean isOnPalaceCenter(Position position) {
        return PALACE.isOnPalaceCenter(position);
    }

    @Override
    public boolean isInDifferencePalace(Position from, Position to) {
        boolean isBothHanPalace = (from.y() <= 3 && to.y() <= 3);
        boolean isBothChoPalace = (from.y() >= 8 && to.y() >= 8);

        return !(isBothHanPalace || isBothChoPalace);
    }

    public boolean isOnlyGeneralAndGuard() {
        return board.values().stream()
                .noneMatch(piece -> piece.type() != PieceType.GENERAL && piece.type() != PieceType.GUARD);
    }

    public double calculateScore(Camp camp) {
        double totalScore = board.values().stream()
                .filter(piece -> piece.camp() == camp)
                .mapToDouble(piece -> piece.type().score())
                .sum();

        return totalScore + camp.bonusScore();
    }

    @Override
    public Position findPalaceCenter(Position from) {
        return PALACE.findPalaceCenter(from);
    }

    public List<PieceDTO> extractPieceDTOs() {
        return board.entrySet().stream()
                .map(entry -> new PieceDTO(
                        entry.getKey().x(),
                        entry.getKey().y(),
                        entry.getValue().camp().name(),
                        entry.getValue().type().name()
                ))
                .toList();
    }
}
