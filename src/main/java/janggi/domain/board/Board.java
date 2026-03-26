package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.strategy.BoardAssembler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Location, Piece> boardState;
    private final int height;
    private final int width;

    private Board(Map<Location, Piece> boardState, int height, int width) {
        this.boardState = boardState;
        this.height = height;
        this.width = width;
    }

    public static Board create(BoardAssembler assembler) {
        Piece[][] pieces = assembler.assemble();
        Map<Location, Piece> boardState = new HashMap<>();

        int height = pieces.length;
        int width = pieces[0].length;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                boardState.put(new Location(row, col), pieces[row][col]);
            }
        }

        return new Board(boardState, height, width);
    }

    public List<List<Piece>> to2DArray() {
        List<List<Piece>> pieces = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            List<Piece> line = new ArrayList<>();
            for (int col = 0; col < width; col++) {
                Piece piece = boardState.get(new Location(row, col));
                line.add(piece);
            }
            pieces.add(List.copyOf(line));
        }
        return List.copyOf(pieces);
    }

    private record Location(int row, int col) {
    }
}
