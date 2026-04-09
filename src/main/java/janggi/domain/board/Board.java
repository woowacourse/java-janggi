package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Board implements BoardMediator {

    private static final List<Position> PALACE = List.of(
            Position.valueOf(1, 4),
            Position.valueOf(1, 5),
            Position.valueOf(1, 6),
            Position.valueOf(2, 4),
            Position.valueOf(2, 5),
            Position.valueOf(2, 6),
            Position.valueOf(3, 4),
            Position.valueOf(3, 5),
            Position.valueOf(3, 6),
            Position.valueOf(8, 4),
            Position.valueOf(8, 5),
            Position.valueOf(8, 6),
            Position.valueOf(9, 4),
            Position.valueOf(9, 5),
            Position.valueOf(9, 6),
            Position.valueOf(10, 4),
            Position.valueOf(10, 5),
            Position.valueOf(10, 6)
    );
    private final Map<Position, Piece> positionPieceMap;

    public Board(final Map<Position, Piece> positionPieceMap) {
        this.positionPieceMap = new LinkedHashMap<>(positionPieceMap);
    }

    public void movePiece(final Position from, final Position to) {
        positionPieceMap.put(to, positionPieceMap.remove(from));
    }

    public List<Position> calculateMovablePositions(Position from) {
        return findPieceByPosition(from).calculateMovablePositions(from, this);
    }

    public boolean hasGeneral(TeamType teamType) {
        return positionPieceMap.values().stream()
                .anyMatch(piece -> piece.isGeneral() && piece.isSameTeamType(teamType));
    }

    @Override
    public boolean hasPieceAt(final Position position) {
        return hasPieceIn(position);
    }

    @Override
    public boolean isCannon(Position position) {
        return findPieceByPosition(position).isCannon();
    }

    @Override
    public boolean isSameTeamType(Position position, TeamType teamType) {
        return findPieceByPosition(position).isSameTeamType(teamType);
    }

    @Override
    public boolean isPalace(Position position) {
        return PALACE.contains(position);
    }
    
    public Map<Position, Piece> getPositionPieceMap() {
        return Map.copyOf(positionPieceMap);
    }

    private boolean hasPieceIn(final Position position) {
        return positionPieceMap.containsKey(position);
    }

    private Piece findPieceByPosition(final Position position) {
        if (hasPieceIn(position)) {
            return positionPieceMap.get(position);
        }
        throw new IllegalArgumentException("요청된 위치에는 기물이 존재하지 않습니다.");
    }
}
