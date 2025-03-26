package model.piece;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import model.Point;
import model.Team;

public class Ma extends Piece {

    public Ma(Team team) {
        super(team);
        pieceInfo = PieceInfo.MA;
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        List<Integer> horizontal = List.of(-1, 1, 2, 2, 1, -1, -2, -2);
        List<Integer> vertical = List.of(2, 2, 1, -1, -2, -2, -1, 1);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforePoint.x() == targetPoint.x()
                        && vertical.get(i) + beforePoint.y() == targetPoint.y());
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == 2) {
            return false;
        }
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            if (!piecesOnPathWithTargetOrNot.values()
                    .stream()
                    .findFirst()
                    .get()) {
                return false;
            }
            return piecesOnPathWithTargetOrNot.keySet()
                    .stream()
                    .findFirst()
                    .get()
                    .getTeam() != this.team;
        }
        return true;
    }
}
