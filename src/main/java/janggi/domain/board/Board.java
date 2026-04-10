package janggi.domain.board;

import janggi.domain.position.Position;
import janggi.domain.space.Blank;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Piece;
import janggi.domain.space.piece.PieceType;
import janggi.domain.space.piece.Team;
import janggi.domain.strategy.InitializeStrategy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private static final int HORIZONTAL_LENGTH = 10;
    private static final int VERTICAL_LENGTH = 9;
    private static final int KING_COUNT = 2;

    private final Map<Position, Space> piecesInfo;

    public Board(InitializeStrategy initializeStrategy) {
        this.piecesInfo = generateBlankBoard();
        initializeStrategy.basicSetting(piecesInfo);
    }

    public Map<Position, Space> getPiecesInfo() {
        return Collections.unmodifiableMap(piecesInfo);
    }

    public void move(Position from, Position to, Team team) {
        Space spaceFrom = piecesInfo.get(from);
        validateBlankSpace(spaceFrom);

        Piece selectedPiece = (Piece) spaceFrom;
        validateTurn(team, selectedPiece);
        validatePieceRule(from, to, selectedPiece);

        applyMove(from, to, selectedPiece);
    }

    public boolean isGameOver() {
        List<Piece> arrivePieces = findArrivedPieces();

        return arrivePieces.stream()
                .filter(piece -> piece.isSameType(PieceType.KING))
                .count() != KING_COUNT;
    }

    public int calculatePieceScore(Team team) {
        List<Piece> arrivePieces = findArrivedPieces();

        return arrivePieces.stream()
                .filter(piece -> piece.isEqualTeam(team))
                .mapToInt(Piece::getScore)
                .sum();
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

    private static void validateTurn(Team team, Piece selectedPiece) {
        if(!selectedPiece.isEqualTeam(team)) {
            throw new IllegalStateException("상대 진영의 말은 움직일 수 없습니다.");
        }
    }

    private void validatePieceRule(Position from, Position to, Piece selectedPiece) {
        List<Piece> blockedPieces = getBlockedPiece(from, to, selectedPiece);
        Space targetSpace = piecesInfo.get(to);

        selectedPiece.verifyMove(from, to, blockedPieces, targetSpace);
    }

    private void validateBlankSpace(Space spaceFrom) {
        if (spaceFrom.isBlank()) {
            throw new IllegalStateException("해당 좌표에 말이 없습니다.");
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

    private List<Piece> findArrivedPieces() {
        return piecesInfo.values().stream()
                .filter(space -> !space.isBlank())
                .map(space -> (Piece) space)
                .toList();
    }
}
