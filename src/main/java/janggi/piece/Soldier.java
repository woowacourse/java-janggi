package janggi.piece;

import janggi.board.Position;

import java.util.List;

public class Soldier extends Piece {
    private static final String NAME = "병";

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public List<Position> findPositionsInPath(Position start, Position goal) {
        int rowDifference = start.calculatesRowDifference(goal);
        int columnDifference = start.calculatesColumnDifference(goal);

        if (!isValidMoveRule(rowDifference, columnDifference)) {
            throw new IllegalArgumentException("[ERROR] 병의 이동 규칙에 어긋나는 움직임입니다.");
        }

        return List.of(goal);
    }

    private boolean isValidMoveRule(int rowDifference, int columnDifference) {
        return isValidDirection(rowDifference) && isValidDistance(rowDifference, columnDifference);
    }

    private boolean isValidDirection(int rowDifference) {
        return (getTeam() == Team.GREEN && rowDifference > 0) ||
                (getTeam() == Team.RED && rowDifference < 0);
    }

    private boolean isValidDistance(int rowDifference, int columnDifference) {
        return Math.abs(rowDifference) + Math.abs(columnDifference) == 1;
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == PieceType.SOLDIER;
    }
}
