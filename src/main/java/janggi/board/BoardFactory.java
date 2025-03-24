package janggi.board;

import janggi.Team.Team;
import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Jol;
import janggi.piece.Piece;
import janggi.piece.Po;
import janggi.piece.Sa;
import janggi.position.Position;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public Board makeBoard(SangSetting choSangSetting, SangSetting hanSangSetting) {
        Map<Position, Piece> pieces = new HashMap<>();
        makeHanPieces(pieces, hanSangSetting);
        makeChoPieces(pieces, choSangSetting);
        return new Board(pieces);
    }

    private static void makeChoPieces(Map<Position, Piece> pieces, SangSetting sangSetting) {
        final Team targetTeam = Team.CHO;
        pieces.putAll(getSangSetting(targetTeam, sangSetting));

        pieces.put(new Position(10, 1), new Cha(targetTeam));
        pieces.put(new Position(10, 4), new Sa(targetTeam));
        pieces.put(new Position(10, 6), new Sa(targetTeam));
        pieces.put(new Position(10, 9), new Cha(targetTeam));

        pieces.put(new Position(9, 5), new Gung(targetTeam));
        pieces.put(new Position(8, 2), new Po(targetTeam));
        pieces.put(new Position(8, 8), new Po(targetTeam));

        pieces.put(new Position(7, 1), new Jol());
        pieces.put(new Position(7, 3), new Jol());
        pieces.put(new Position(7, 5), new Jol());
        pieces.put(new Position(7, 7), new Jol());
        pieces.put(new Position(7, 9), new Jol());
    }

    private static void makeHanPieces(Map<Position, Piece> pieces, SangSetting sangSetting) {
        final Team targetTeam = Team.HAN;
        pieces.putAll(getSangSetting(targetTeam, sangSetting));

        pieces.put(new Position(1, 1), new Cha(targetTeam));
        pieces.put(new Position(1, 4), new Sa(targetTeam));
        pieces.put(new Position(1, 6), new Sa(targetTeam));
        pieces.put(new Position(1, 9), new Cha(targetTeam));

        pieces.put(new Position(2, 5), new Gung(targetTeam));
        pieces.put(new Position(3, 2), new Po(targetTeam));
        pieces.put(new Position(3, 8), new Po(targetTeam));

        pieces.put(new Position(4, 1), new Byeong());
        pieces.put(new Position(4, 3), new Byeong());
        pieces.put(new Position(4, 5), new Byeong());
        pieces.put(new Position(4, 7), new Byeong());
        pieces.put(new Position(4, 9), new Byeong());
    }

    private static Map<Position, Piece> getSangSetting(Team team, SangSetting sangSetting) {
        if (team == Team.HAN) {
            return sangSetting.getElephantSetting(team, 1);
        }
        return sangSetting.getElephantSetting(team, 10);
    }
}
