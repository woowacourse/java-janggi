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

    private static final int INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO = 10;
    private static final int INITIAL_Y_OF_GUNG_FOR_CHO = 9;
    private static final int INITIAL_Y_OF_PO_FOR_CHO = 8;
    private static final int INITIAL_Y_OF_JOL_FOR_CHO = 7;

    private static final int INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN = 1;
    private static final int INITIAL_Y_OF_GUNG_FOR_HAN = 2;
    private static final int INITIAL_Y_OF_PO_FOR_HAN = 3;
    private static final int INITIAL_Y_OF_BYEONG_FOR_HAN = 4;

    public Board makeBoard(SangSetting choSangSetting, SangSetting hanSangSetting) {
        Map<Position, Piece> pieces = new HashMap<>();
        makeChoInitialPieces(pieces, hanSangSetting);
        makeHanInitialPieces(pieces, choSangSetting);
        return new Board(pieces);
    }

    private static void makeChoInitialPieces(Map<Position, Piece> pieces, SangSetting sangSetting) {
        final Team targetTeam = Team.CHO;
        pieces.putAll(getSangSetting(targetTeam, sangSetting));

        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO, 1), new Cha(targetTeam));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO, 4), new Sa(targetTeam));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO, 6), new Sa(targetTeam));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO, 9), new Cha(targetTeam));

        pieces.put(new Position(INITIAL_Y_OF_GUNG_FOR_CHO, 5), new Gung(targetTeam));
        pieces.put(new Position(INITIAL_Y_OF_PO_FOR_CHO, 2), new Po(targetTeam));
        pieces.put(new Position(INITIAL_Y_OF_PO_FOR_CHO, 8), new Po(targetTeam));

        pieces.put(new Position(INITIAL_Y_OF_JOL_FOR_CHO, 1), new Jol());
        pieces.put(new Position(INITIAL_Y_OF_JOL_FOR_CHO, 3), new Jol());
        pieces.put(new Position(INITIAL_Y_OF_JOL_FOR_CHO, 5), new Jol());
        pieces.put(new Position(INITIAL_Y_OF_JOL_FOR_CHO, 7), new Jol());
        pieces.put(new Position(INITIAL_Y_OF_JOL_FOR_CHO, 9), new Jol());
    }

    private static void makeHanInitialPieces(Map<Position, Piece> pieces, SangSetting sangSetting) {
        final Team targetTeam = Team.HAN;
        pieces.putAll(getSangSetting(targetTeam, sangSetting));

        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN, 1), new Cha(targetTeam));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN, 4), new Sa(targetTeam));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN, 6), new Sa(targetTeam));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN, 9), new Cha(targetTeam));

        pieces.put(new Position(INITIAL_Y_OF_GUNG_FOR_HAN, 5), new Gung(targetTeam));
        pieces.put(new Position(INITIAL_Y_OF_PO_FOR_HAN, 2), new Po(targetTeam));
        pieces.put(new Position(INITIAL_Y_OF_PO_FOR_HAN, 8), new Po(targetTeam));

        pieces.put(new Position(INITIAL_Y_OF_BYEONG_FOR_HAN, 1), new Byeong());
        pieces.put(new Position(INITIAL_Y_OF_BYEONG_FOR_HAN, 3), new Byeong());
        pieces.put(new Position(INITIAL_Y_OF_BYEONG_FOR_HAN, 5), new Byeong());
        pieces.put(new Position(INITIAL_Y_OF_BYEONG_FOR_HAN, 7), new Byeong());
        pieces.put(new Position(INITIAL_Y_OF_BYEONG_FOR_HAN, 9), new Byeong());
    }

    private static Map<Position, Piece> getSangSetting(Team team, SangSetting sangSetting) {
        if (team == Team.HAN) {
            return sangSetting.getSangSetting(team, INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN);
        }
        return sangSetting.getSangSetting(team, INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO);
    }
}
