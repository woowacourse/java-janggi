package domain.board;

import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import domain.Side;
import domain.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicBoardInitializer implements BoardInitializer {

    private static final BoardBounds BOUNDS = BoardBounds.JANGGI;

    @Override
    public Topology createTopology() {
        Map<Position, List<Direction>> adjacencyMap = new HashMap<>();
        for (PalaceBounds palace : BOUNDS.getPalaces()) {
            addPalaceDiagonals(adjacencyMap, palace);
        }
        return new Topology(adjacencyMap);
    }

    private void addPalaceDiagonals(Map<Position, List<Direction>> map, PalaceBounds palace) {
        int midRow = (palace.startRow() + palace.endRow()) / 2;
        int midCol = (palace.startCol() + palace.endCol()) / 2;

        // 중앙: 4방향 대각선
        addDirections(map, midRow, midCol,
                Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT);

        // 꼭짓점: 중앙을 향하는 대각선 1방향
        addDirections(map, palace.startRow(), palace.startCol(), Direction.DOWN_RIGHT);
        addDirections(map, palace.startRow(), palace.endCol(), Direction.DOWN_LEFT);
        addDirections(map, palace.endRow(), palace.startCol(), Direction.UP_RIGHT);
        addDirections(map, palace.endRow(), palace.endCol(), Direction.UP_LEFT);
    }

    private void addDirections(Map<Position, List<Direction>> map, int row, int col, Direction... directions) {
        Position pos = new Position(row, col);
        List<Direction> base = List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        List<Direction> all = new ArrayList<>(base);
        all.addAll(List.of(directions));
        map.put(pos, List.copyOf(all));
    }

    @Override
    public Map<Position, Piece> initialize() {
        Map<Position, Piece> pieceInitPlacements = new HashMap<>();

        placeHan(pieceInitPlacements);
        placeChu(pieceInitPlacements);

        return pieceInitPlacements;
    }

    private void placeHan(Map<Position, Piece> pieceInitPlacements) {
        pieceInitPlacements.put(new Position(0, 0), new Chariot(Side.HAN));
        pieceInitPlacements.put(new Position(0, 1), new Horse(Side.HAN));
        pieceInitPlacements.put(new Position(0, 2), new Elephant(Side.HAN));
        pieceInitPlacements.put(new Position(0, 3), new Guard(Side.HAN));
        pieceInitPlacements.put(new Position(0, 5), new Guard(Side.HAN));
        pieceInitPlacements.put(new Position(0, 6), new Elephant(Side.HAN));
        pieceInitPlacements.put(new Position(0, 7), new Horse(Side.HAN));
        pieceInitPlacements.put(new Position(0, 8), new Chariot(Side.HAN));

        pieceInitPlacements.put(new Position(1, 4), new King(Side.HAN));

        pieceInitPlacements.put(new Position(2, 1), new Cannon(Side.HAN));
        pieceInitPlacements.put(new Position(2, 7), new Cannon(Side.HAN));

        pieceInitPlacements.put(new Position(3, 0), new Pawn(Side.HAN));
        pieceInitPlacements.put(new Position(3, 2), new Pawn(Side.HAN));
        pieceInitPlacements.put(new Position(3, 4), new Pawn(Side.HAN));
        pieceInitPlacements.put(new Position(3, 6), new Pawn(Side.HAN));
        pieceInitPlacements.put(new Position(3, 8), new Pawn(Side.HAN));
    }

    private void placeChu(Map<Position, Piece> placements) {
        Map<Position, Piece> chuPlacements = new HashMap<>();

        for (Map.Entry<Position, Piece> entry : placements.entrySet()) {
            Piece piece = entry.getValue();
            if (!piece.isHan()) {
                continue;
            }

            Position pos = entry.getKey();
            Position chuPosition = translateChuPosition(pos);

            chuPlacements.put(chuPosition, copyAsChu(piece));
        }

        placements.putAll(chuPlacements);
    }

    private Position translateChuPosition(Position pos) {
        int row = 9 - pos.row();
        int col = pos.col();
        return new Position(row, col);
    }


    private Piece copyAsChu(Piece piece) {
        return piece.withSide(Side.CHU);
    }

    public Side getFirstTurnSide() {
        return Side.CHU;
    }
}
