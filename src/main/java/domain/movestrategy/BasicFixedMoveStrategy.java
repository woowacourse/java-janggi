package domain.movestrategy;

import domain.Position;
import domain.Team;
import domain.move.Move;
import domain.move.Moves;
import domain.player.Player;
import java.util.List;

public class BasicFixedMoveStrategy implements FixedMoveStrategy {
    private final List<Moves> blueTeamMoves = List.of(
            Moves.createMoves(Move.FRONT),
            Moves.createMoves(Move.RIGHT),
            Moves.createMoves(Move.LEFT)
    );
    private final List<Moves> redTeamMoves = List.of(
            Moves.createMoves(Move.BACK),
            Moves.createMoves(Move.RIGHT),
            Moves.createMoves(Move.LEFT)
    );

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition, Player player) {
        List<Moves> movesList = getMoveList(player);
        for (Moves moves : movesList) {
            boolean compareResult = moves.comparePath(startPosition, targetPosition);
            if (compareResult) {
                return moves.convertToPath(startPosition);
            }
        }
        throw new IllegalArgumentException("이 위치로 이동할 수 없습니다.");
    }

    public List<Moves> getMoveList(Player player) {
        if (player.getTeam() == Team.BLUE) {
            return blueTeamMoves;
        }
        return redTeamMoves;
    }
}
