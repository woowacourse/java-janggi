package domain;

import java.util.List;

public class HorseMoveStrategy {


    public List<Path> getAvailablePaths(final ChessPosition chessPosition1) {
        final Path path1 = new Path(List.of(new ChessPosition(5, 4), new ChessPosition(6, 5)));
        final Path path2 = new Path(List.of(new ChessPosition(5, 4), new ChessPosition(6, 3)));
        final Path path3 = new Path(List.of(new ChessPosition(3, 4), new ChessPosition(2, 5)));
        final Path path4 = new Path(List.of(new ChessPosition(3, 4), new ChessPosition(2, 3)));
        final Path path5 = new Path(List.of(new ChessPosition(4, 5), new ChessPosition(3, 6)));
        final Path path6 = new Path(List.of(new ChessPosition(4, 5), new ChessPosition(5, 6)));
        final Path path7 = new Path(List.of(new ChessPosition(4, 3), new ChessPosition(3, 2)));
        final Path path8 = new Path(List.of(new ChessPosition(4, 3), new ChessPosition(5, 2)));
        return List.of(path1, path2, path3, path4, path5, path6, path7, path8);
    }
}
