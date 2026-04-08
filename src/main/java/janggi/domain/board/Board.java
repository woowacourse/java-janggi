package janggi.domain.board;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceScoreCalculator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardInfo {

    private static final int PALACE_MAX_COUNT = 2;

    private final Map<Position, Piece> piecePosition;

    public Board(Map<Position, Piece> piecePosition) {
        this.piecePosition = new HashMap<>(piecePosition);
    }

    public static Board initialize() {
        Map<Position, Piece> initialBoard = new HashMap<>(initializeEach());
        return new Board(initialBoard);
    }

    private static Map<Position, Piece> initializeEach() {
        Map<Position, Piece> initialPiecePosition = new HashMap<>();
        Arrays.stream(InitialBoardInfo.values())
                .forEach(boardInfo -> initialPiecePosition.putAll(boardInfo.generateInitialPiecePositions()));
        return initialPiecePosition;
    }

    @Override
    public boolean isEmpty(Position position) {
        return !piecePosition.containsKey(position);
    }

    @Override
    public boolean isAlly(Position currentPosition, Position targetPosition) {
        if (!piecePosition.containsKey(currentPosition) || !piecePosition.containsKey(targetPosition)) {
            return false;
        }
        Piece currentPiece = piecePosition.get(currentPosition);
        Piece targetPiece = piecePosition.get(targetPosition);
        return currentPiece.isAlly(targetPiece);
    }

    @Override
    public boolean isCannon(Position position) {
        if (!piecePosition.containsKey(position)) {
            return false;
        }
        return piecePosition.get(position).isCannon();
    }

    public boolean isPieceExist(Position position) {
        return piecePosition.containsKey(position);
    }

    public Piece findPieceBy(Position selectedPosition) {
        return piecePosition.get(selectedPosition);
    }

    public boolean isBothPalaceExist() {
        long palaceCount = piecePosition.values().stream()
                .filter(Piece::isPalace)
                .count();
        return palaceCount == PALACE_MAX_COUNT;
    }

    public Destinations moveablePositions(Position currentPosition) {
        Piece piece = piecePosition.get(currentPosition);
        return piece.determineDestinations(currentPosition, this);
    }

    public boolean isMoveablePiece(Position selectedPosition) {
        Piece piece = piecePosition.get(selectedPosition);
        return !piece.determineDestinations(selectedPosition, this).isEmpty();
    }

    public void movePiece(Position selected, Position target, Destinations destinations) {
        if (!destinations.containsDestination(target)) {
            throw new IllegalArgumentException("[ERROR] 표시된 이동 가능 좌표를 선택해주세요.");
        }
        Piece movingPiece = piecePosition.remove(selected);
        piecePosition.put(target, movingPiece);
    }

    public double calculateScore(Side side) {
        return PieceScoreCalculator.calculateScore(side, piecePosition.values());
    }

    public Map<Position, Piece> piecePosition() {
        return Map.copyOf(piecePosition);
    }
}
