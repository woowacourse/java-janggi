package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.vo.Position;
import java.lang.reflect.Field;
import java.util.List;

public class BoardFixture {

    private BoardFixture() {
    }

    public static void put(Board board, Position position, Piece piece) {
        try {
            Field field = Board.class.getDeclaredField("board");
            field.setAccessible(true);

            List<List<Piece>> actualBoard = (List<List<Piece>>) field.get(board);

            actualBoard.get(position.getRow()).set(position.getCol(), piece);
        } catch (Exception e) {
            throw new RuntimeException("테스트용 보드 세팅에 에러", e);
        }
    }
}
