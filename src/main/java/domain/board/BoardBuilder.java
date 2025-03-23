package domain.board;

import domain.Coordinate;
import domain.Team;
import domain.board.maSangStrategy.MaSangStrategy;
import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Goong;
import domain.piece.Jol;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import java.util.HashMap;
import java.util.Map;

public class BoardBuilder {

    private final Map<Coordinate, Piece> board = new HashMap<>();

    public BoardBuilder initTeam(Team team, MaSangStrategy strategy) {
        Map<Coordinate, Piece> defaultBoard = createDefaultBoard(team);
        Map<Coordinate, Piece> maAndSangPieces = strategy.createMaAndSang(team);

        board.putAll(defaultBoard);
        board.putAll(maAndSangPieces);
        return this;
    }

    public Board build() {
        return new Board(board);
    }

    private static Map<Coordinate, Piece> createDefaultBoard(Team team) {
        if (team.equals(Team.HAN)) {
            return createHanDefaultBoard();
        }
        return createChoDefaultBoard();
    }

    private static Map<Coordinate, Piece> createHanDefaultBoard() {
        return Map.ofEntries(
            Map.entry(new Coordinate(1, 1), new Cha(Team.HAN)),
            Map.entry(new Coordinate(4, 1), new Sa(Team.HAN)),
            Map.entry(new Coordinate(6, 1), new Sa(Team.HAN)),
            Map.entry(new Coordinate(9, 1), new Cha(Team.HAN)),
            Map.entry(new Coordinate(5, 2), new Goong(Team.HAN)),
            Map.entry(new Coordinate(2, 3), new Po(Team.HAN)),
            Map.entry(new Coordinate(8, 3), new Po(Team.HAN)),
            Map.entry(new Coordinate(1, 4), new Byeong()),
            Map.entry(new Coordinate(3, 4), new Byeong()),
            Map.entry(new Coordinate(5, 4), new Byeong()),
            Map.entry(new Coordinate(7, 4), new Byeong()),
            Map.entry(new Coordinate(9, 4), new Byeong())
        );
    }

    private static Map<Coordinate, Piece> createChoDefaultBoard() {
        return Map.ofEntries(
            Map.entry(new Coordinate(1, 10), new Cha(Team.CHO)),
            Map.entry(new Coordinate(4, 10), new Sa(Team.CHO)),
            Map.entry(new Coordinate(6, 10), new Sa(Team.CHO)),
            Map.entry(new Coordinate(9, 10), new Cha(Team.CHO)),
            Map.entry(new Coordinate(5, 9), new Goong(Team.CHO)),
            Map.entry(new Coordinate(2, 8), new Po(Team.CHO)),
            Map.entry(new Coordinate(8, 8), new Po(Team.CHO)),
            Map.entry(new Coordinate(1, 7), new Jol()),
            Map.entry(new Coordinate(3, 7), new Jol()),
            Map.entry(new Coordinate(5, 7), new Jol()),
            Map.entry(new Coordinate(7, 7), new Jol()),
            Map.entry(new Coordinate(9, 7), new Jol())
        );
    }
}
