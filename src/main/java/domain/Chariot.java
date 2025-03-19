package domain;

import java.util.HashSet;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(final Position position, final Team team, final Board board) {
        super(position, team, board);
    }

    @Override
    public Set<Position> getMovablePositions() {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Set<Position> positions = new HashSet<>();
        for (int i = 0; i < directions.length; i++) {
            Position current = new Position(position.getX(), position.getY());
            while (true) {
                int nx = current.getX() + directions[i][0];
                int ny = current.getY() + directions[i][1];

                if (nx < Board.MIN_COLUMN || nx > Board.MAX_COLUMN || ny < Board.MIN_ROW || ny > Board.MAX_ROW) {
                    break;
                }
                Position newPosition = new Position(nx, ny);
                if (!board.isExists(newPosition)) {
                    positions.add(newPosition);
                    current = newPosition;
                    continue;
                }
                // 우리팀 or 상대팀
                if (board.isSameTeam(this, newPosition)) {
                    break;
                }
                positions.add(newPosition);
                break;
            }
        }
        return positions;
    }

}
