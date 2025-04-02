package janggi.domain.board;

import janggi.domain.piece.Byeong;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jol;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
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

    public Board loadProgressingBoard(Map<Position, Piece> pieces) {
        return new Board(pieces);
    }

    private static void makeChoInitialPieces(Map<Position, Piece> pieces, SangSetting sangSetting) {
        final TeamType targetTeamType = TeamType.CHO;
        pieces.putAll(getSangSetting(targetTeamType, sangSetting));

        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO, 1), new Cha(targetTeamType));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO, 4), new Sa(targetTeamType));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO, 6), new Sa(targetTeamType));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO, 9), new Cha(targetTeamType));

        pieces.put(new Position(INITIAL_Y_OF_GUNG_FOR_CHO, 5), new Gung(targetTeamType));
        pieces.put(new Position(INITIAL_Y_OF_PO_FOR_CHO, 2), new Po(targetTeamType));
        pieces.put(new Position(INITIAL_Y_OF_PO_FOR_CHO, 8), new Po(targetTeamType));

        pieces.put(new Position(INITIAL_Y_OF_JOL_FOR_CHO, 1), new Jol());
        pieces.put(new Position(INITIAL_Y_OF_JOL_FOR_CHO, 3), new Jol());
        pieces.put(new Position(INITIAL_Y_OF_JOL_FOR_CHO, 5), new Jol());
        pieces.put(new Position(INITIAL_Y_OF_JOL_FOR_CHO, 7), new Jol());
        pieces.put(new Position(INITIAL_Y_OF_JOL_FOR_CHO, 9), new Jol());
    }

    private static void makeHanInitialPieces(Map<Position, Piece> pieces, SangSetting sangSetting) {
        final TeamType targetTeamType = TeamType.HAN;
        pieces.putAll(getSangSetting(targetTeamType, sangSetting));

        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN, 1), new Cha(targetTeamType));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN, 4), new Sa(targetTeamType));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN, 6), new Sa(targetTeamType));
        pieces.put(new Position(INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN, 9), new Cha(targetTeamType));

        pieces.put(new Position(INITIAL_Y_OF_GUNG_FOR_HAN, 5), new Gung(targetTeamType));
        pieces.put(new Position(INITIAL_Y_OF_PO_FOR_HAN, 2), new Po(targetTeamType));
        pieces.put(new Position(INITIAL_Y_OF_PO_FOR_HAN, 8), new Po(targetTeamType));

        pieces.put(new Position(INITIAL_Y_OF_BYEONG_FOR_HAN, 1), new Byeong());
        pieces.put(new Position(INITIAL_Y_OF_BYEONG_FOR_HAN, 3), new Byeong());
        pieces.put(new Position(INITIAL_Y_OF_BYEONG_FOR_HAN, 5), new Byeong());
        pieces.put(new Position(INITIAL_Y_OF_BYEONG_FOR_HAN, 7), new Byeong());
        pieces.put(new Position(INITIAL_Y_OF_BYEONG_FOR_HAN, 9), new Byeong());
    }

    private static Map<Position, Piece> getSangSetting(TeamType teamType, SangSetting sangSetting) {
        if (teamType == TeamType.HAN) {
            return sangSetting.getSangSetting(teamType, INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_HAN);
        }
        return sangSetting.getSangSetting(teamType, INITIAL_Y_OF_SANG_MA_CHA_SA_FOR_CHO);
    }
}
