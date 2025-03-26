package janggi.piece;

import janggi.board.Position;

import java.util.List;

public class Soldier extends Piece {
    private static final PieceType TYPE = PieceType.SOLDIER;

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public List<Position> findPositionsInPath(Position start, Position goal) {
        int columnDifference = start.calculatesColumnDifference(goal);
        int rowDifference = start.calculatesRowDifference(goal);

        if (!isValidMoveRule(columnDifference, rowDifference)) {
            throw new IllegalArgumentException("[ERROR] 병의 이동 규칙에 어긋나는 움직임입니다.");
        }

        return List.of(goal);
    }

    private boolean isValidMoveRule(int columnDifference, int rowDifference) {
        return isValidDirection(rowDifference) && isValidDistance(rowDifference, columnDifference);
    }

    private boolean isValidDirection(int rowDifference) {
        return (getTeam() == Team.GREEN && rowDifference >= 0) ||
                (getTeam() == Team.RED && rowDifference <= 0);
    }

    private boolean isValidDistance(int columnDifference, int rowDifference) {
        return Math.abs(columnDifference) + Math.abs(rowDifference) == 1;
    }

    @Override
    protected String getName() {
        return TYPE.getName();
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == TYPE;
    }
}
