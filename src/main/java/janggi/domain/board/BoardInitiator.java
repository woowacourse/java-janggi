package janggi.domain.board;

import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardInitiator {

    private final Map<PieceType, List<Integer>> defaultXPositions = Map.of(
            PieceType.KING, List.of(5),
            PieceType.SA, List.of(4, 6),
            PieceType.CHA, List.of(1, 9),
            PieceType.PO, List.of(2, 8),
            PieceType.ZOL, List.of(1, 3, 5, 7, 9)
    );

    private final Map<PieceType, Integer> defaultYPosition = Map.of(
            PieceType.KING, 9,
            PieceType.SA, 10,
            PieceType.SANG, 10,
            PieceType.MA, 10,
            PieceType.CHA, 10,
            PieceType.PO, 8,
            PieceType.ZOL, 7
    );

    public void initializeByFormation(Board board, BoardFormation formation, Team team) {
        Map<PieceType, List<Integer>> xPositions = initXPositionsByFormation(formation);
        placeByPieceType(board, xPositions, team);
    }

    private Map<PieceType, List<Integer>> initXPositionsByFormation(BoardFormation formation) {
        Map<PieceType, List<Integer>> result = new HashMap<>();
        for (PieceType pieceType : PieceType.values()) {
            result.put(pieceType, getXPositions(formation, pieceType));
        }
        return result;
    }

    private void placeByPieceType(Board board, Map<PieceType, List<Integer>> xPositions, Team team) {
        for (PieceType pieceType : PieceType.values()) {
            placeByPieceType(board, xPositions, team, pieceType);
        }
    }

    private void placeByPieceType(Board board, Map<PieceType, List<Integer>> xPositions, Team team,
                                  PieceType pieceType) {
        for (Integer x : xPositions.get(pieceType)) {
            int y = team.calculateYPosition(defaultYPosition.get(pieceType));
            Position position = new Position(x, y);
            board.place(position, new Piece(team, pieceType));
        }
    }

    private List<Integer> getXPositions(BoardFormation formation, PieceType pieceType) {
        if (pieceType == PieceType.MA) {
            return formation.getMaXPositions();
        }
        if (pieceType == PieceType.SANG) {
            return formation.getSangXPositions();
        }
        return defaultXPositions.get(pieceType);
    }
}
