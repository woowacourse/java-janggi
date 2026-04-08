package domain.path;

import domain.vo.Position;
import java.util.List;

public interface Paths {

    List<Path> getPaths(Position position);

}
