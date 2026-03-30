package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceMapper;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Board {

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

    public List<Position> calculateDestinations(Position currentPosition) {
        Piece piece = piecePosition.get(currentPosition);
        Paths moveablePaths = piece.calculatePaths(currentPosition);
        Map<Position, Piece> boardState = generateStateByPaths(moveablePaths);
        return piece.determineDestinations(moveablePaths, boardState);
    }

    private Map<Position, Piece> generateStateByPaths(Paths moveablePaths) {
        Map<Position, Piece> boardState = new HashMap<>();
        moveablePaths.forEach(path -> {
            generateStateByPath(path, boardState);
        });
        return boardState;
    }

    private void generateStateByPath(Path path, Map<Position, Piece> boardState) {
        path.forEach(position -> generateStateIfExist(boardState, position));
    }

    private void generateStateIfExist(Map<Position, Piece> boardState, Position position) {
        if (piecePosition.containsKey(position)) {
            boardState.put(position, piecePosition.get(position));
        }
    }

    public void movePiece(Position selected, Position target, List<Position> destinations) {
        if (!destinations.contains(target)) {
            throw new IllegalArgumentException("[ERROR] 표시된 이동 가능 좌표를 선택해주세요.");
        }
        Piece movingPiece = piecePosition.remove(selected);
        piecePosition.put(target, movingPiece);
    }

    public boolean isPieceExist(Position position) {
        return piecePosition.containsKey(position);
    }

    public boolean isBothPalaceExist() {
        long palaceCount = piecePosition.values().stream()
                .filter(Piece::isPalace)
                .count();
        return palaceCount == 2;
    }

    public Piece findPieceBy(Position selectedPosition) {
        return piecePosition.get(selectedPosition);
    }

    public <K, V> Map<K, V> exportBoardState(BiFunction<Integer, Integer, K> positionMapper,
                                             PieceMapper<V> pieceMapper) {
        return piecePosition.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().map(positionMapper), // Position 상태 Push
                        entry -> entry.getValue().map(pieceMapper)   // Piece 상태 Push
                ));
    }
}
