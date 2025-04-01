package domain.board;

import domain.board.pathfinder.PathFinderFactory;
import view.SangMaOrderCommand;

public interface BoardFactory {

    Board createBoard(final PathFinderFactory pathFinderFactory,
                      final SangMaOrderCommand choSangMaOrderCommand,
                      final SangMaOrderCommand hanSangMaOrderCommand);
}
