package domain.piece;

import domain.Coordinate;
import domain.Team;
import java.util.Set;

public class Sang extends Piece {

    public Sang(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return Set.of(
                departure.change(2, -3), // 상우
                departure.change(2, 3), // 하우
                departure.change(-2, -3), //상좌
                departure.change(-2, 3), //하좌
                departure.change(3, -2), // 우상
                departure.change(3, 2), //우하
                departure.change(-3, -2), //좌상
                departure.change(-3, 2) //좌하
        );
    }

    @Override
    public Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int dx = arrival.getX() - departure.getX();
        int dy = arrival.getY() - departure.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            //좌우시작
            if (dx < 0 && dy < 0) {
                //좌상
                return Set.of(departure.change(-1, 0), departure.change(-2, -1));
            } else if (dx < 0 && dy > 0) {
                //좌하
                return Set.of(departure.change(-1, 0), departure.change(-2, 1));
            } else if (dx > 0 && dy < 0) {
                //우상
                return Set.of(departure.change(1, 0), departure.change(2, -1));
            } else if (dx > 0 && dy > 0) {
                //우하
                return Set.of(departure.change(1, 0), departure.change(2, 1));
            }
        } else {
            //상하시작
            if (dx < 0 && dy < 0) {
                //상좌
                return Set.of(departure.change(0, -1), departure.change(-1, -2));
            } else if (dx < 0 && dy > 0) {
                //하좌
                return Set.of(departure.change(0, 1), departure.change(-1, 2));
            } else if (dx > 0 && dy < 0) {
                //상우
                return Set.of(departure.change(0, -1), departure.change(1, -2));
            } else if (dx > 0 && dy > 0) {
                //하우
                return Set.of(departure.change(0, 1), departure.change(1, 2));
            }
        }
        throw new IllegalArgumentException("여기까지 못옴");
    }
}
