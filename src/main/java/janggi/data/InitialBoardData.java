package janggi.data;

import janggi.data.dto.BoardDto;
import java.util.ArrayList;
import java.util.List;

public class InitialBoardData {

    public static final List<BoardDto> INITIAL_BOARD = new ArrayList<>() {{
        add(new BoardDto(1, 2, 5));
        add(new BoardDto(2, 1, 1));
        add(new BoardDto(2, 1, 9));
        add(new BoardDto(3, 1, 4));
        add(new BoardDto(3, 1, 6));
        add(new BoardDto(4, 3, 2));
        add(new BoardDto(4, 3, 8));
        add(new BoardDto(5, 4, 1));
        add(new BoardDto(5, 4, 3));
        add(new BoardDto(5, 4, 5));
        add(new BoardDto(5, 4, 7));
        add(new BoardDto(5, 4, 9));
        add(new BoardDto(6, 9, 5));
        add(new BoardDto(7, 10, 1));
        add(new BoardDto(7, 10, 9));
        add(new BoardDto(8, 10, 4));
        add(new BoardDto(8, 10, 6));
        add(new BoardDto(9, 8, 2));
        add(new BoardDto(9, 8, 8));
        add(new BoardDto(10, 7, 1));
        add(new BoardDto(10, 7, 3));
        add(new BoardDto(10, 7, 5));
        add(new BoardDto(10, 7, 7));
        add(new BoardDto(10, 7, 9));
    }};
}
