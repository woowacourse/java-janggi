package janggi.domain;

import janggi.domain.side.Chu;
import janggi.domain.side.Han;
import janggi.domain.side.Team;
import janggi.dto.BoardSpot;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Team chu;
    private final Team han;

    private Board(Team chu, Team han) {
        this.chu = chu;
        this.han = han;
    }

    public static Board createInitialBoard() {
        return new Board(Chu.createInitialChu(), Han.createInitialHan());
    }

    public List<BoardSpot> makeSpots() {
        List<BoardSpot> chuBoardSpots = chu.makeSpots();
        List<BoardSpot> hanBoardSpots = han.makeSpots();
        List<BoardSpot> boardSpots = new ArrayList<>(chuBoardSpots);
        boardSpots.addAll(hanBoardSpots);
        return boardSpots;
    }
}
