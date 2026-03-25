package domain.piece;

import domain.board.Intersection;
import java.util.List;

public interface StaticPositioned {

    List<Intersection> initAt();
}
