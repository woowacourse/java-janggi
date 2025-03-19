package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseMoveStrategyTest {

    @Test
    @DisplayName("마의 이동 경로를 반환한다")
    void test1() {
        //given
        final ChessPosition chessPosition1 = new ChessPosition(4, 4);
        final Path path1 = new Path(List.of(new ChessPosition(5, 4), new ChessPosition(6, 5)));
        final Path path2 = new Path(List.of(new ChessPosition(5, 4), new ChessPosition(6, 3)));
        final Path path3 = new Path(List.of(new ChessPosition(3, 4), new ChessPosition(2, 5)));
        final Path path4 = new Path(List.of(new ChessPosition(3, 4), new ChessPosition(2, 3)));
        final Path path5 = new Path(List.of(new ChessPosition(4, 5), new ChessPosition(3, 6)));
        final Path path6 = new Path(List.of(new ChessPosition(4, 5), new ChessPosition(5, 6)));
        final Path path7 = new Path(List.of(new ChessPosition(4, 3), new ChessPosition(3, 2)));
        final Path path8 = new Path(List.of(new ChessPosition(4, 3), new ChessPosition(5, 2)));
        final List<Path> allPath = List.of(path1, path2, path3, path4, path5, path6, path7, path8);
//        final ChessPosition chessPosition2 = new ChessPosition(8, 7);

        //when
        final HorseMoveStrategy horseMoveStrategy = new HorseMoveStrategy();
        final List<Path> availablePaths1 = horseMoveStrategy.getAvailablePaths(chessPosition1);
//        final List<Path> availablePaths2 = horseMoveStrategy.getAvailablePaths(chessPosition2);

        //then
        assertThat(availablePaths1).containsAll(allPath);

    }

}
