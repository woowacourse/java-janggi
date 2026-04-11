package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.movement.Palace;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Board implements BoardMediator {

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
        return Palace.isPalacePosition(position);
    }

    public Map<Position, Piece> getPositionPieceMap() {
        return Map.copyOf(positionPieceMap);
    }

    public double calculateScore(TeamType teamType) {
        return positionPieceMap.values().stream()
                .filter(piece -> piece.isSameTeamType(teamType))
                .mapToDouble(Piece::score)
                .sum() + teamType.bonusScore();
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
