package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public class PalacePiece extends ActivePiece {
    private static final List<Integer> dx = List.of(-1, 1, 0, 0);
    private static final List<Integer> dy = List.of(0, 0, -1, 1);

    private PalacePiece(Team team, PieceDefinition type) {
        super(team, type);
    }

    public static PalacePiece general(Team team) {
        return new PalacePiece(team, PieceDefinition.GENERAL);
    }

    public static PalacePiece guard(Team team) {
        return new PalacePiece(team, PieceDefinition.SA);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (int i = 0; i < dx.size(); i++) {
            if (dx.get(i) == rowDiff && dy.get(i) == colDiff) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        return List.of();
    }
}
