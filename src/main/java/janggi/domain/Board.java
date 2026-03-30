package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.strategy.InitializeStrategy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int HORIZONTAL_LENGTH = 10;
    private static final int VERTICAL_LENGTH = 9;

    private final Map<Position, Space> piecesInfo;

    public Board(InitializeStrategy initializeStrategy) {
        piecesInfo = generateBlankBoard();
        initializeStrategy.basicSetting(piecesInfo);
    }

    public Map<Position, Space> getPiecesInfo() {
        return Collections.unmodifiableMap(piecesInfo);
    }

    private Map<Position, Space> generateBlankBoard() {
        Map<Position, Space> blankBoard = new HashMap<>();

        for (int y = 0; y < HORIZONTAL_LENGTH; y++) {
            putHorizontally(blankBoard, y);
        }

        return blankBoard;
    }

    private void putHorizontally(Map<Position, Space> blankBoard, int y) {
        for (int x = 0; x < VERTICAL_LENGTH; x++) {
            blankBoard.put(new Position(x, y), new Blank());
        }
    }

    public void move(Position from, Position to) {
        Space spaceFrom = piecesInfo.get(from);
        validateBlankSpace(spaceFrom);

        Piece selectedPiece = (Piece) spaceFrom;
        validatePieceRule(from, to, selectedPiece);

        applyMove(from, to, selectedPiece);
    }

    private void validatePieceRule(Position from, Position to, Piece selectedPiece) {
        selectedPiece.validateMove(from, to);

        List<Piece> blockedPiece = getBlockedPiece(from, to, selectedPiece);
        selectedPiece.validateRoutes(blockedPiece);

        Space spaceTo = piecesInfo.get(to);
        selectedPiece.validateArrival(spaceTo);
    }

    private void validateBlankSpace(Space spaceFrom) {
        if (spaceFrom.isBlank()) {
            throw new IllegalArgumentException("해당 좌표에 말이 없습니다.");
        }
    }

    private List<Piece> getBlockedPiece(Position from, Position to, Piece selectedPiece) {
        Path path = selectedPiece.getPath(from, to);
        return path.getBlockedPieces(piecesInfo);
    }

    private void applyMove(Position from, Position to, Piece selectedPiece) {
        piecesInfo.put(to, selectedPiece);
        piecesInfo.put(from, new Blank());
    }
}
