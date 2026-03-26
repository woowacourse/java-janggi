package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.strategy.InitializeStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Space> piecesInfo;

    public Board(InitializeStrategy initializeStrategy) {
        piecesInfo = generateBlankBoard();
        initializeStrategy.basicSetting(piecesInfo);
    }

    private Map<Position, Space> generateBlankBoard() {
        Map<Position, Space> blankBoard = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                blankBoard.put(new Position(j, i), new Blank());
            }
        }

        return blankBoard;
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
