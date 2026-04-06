package domain.board;

import domain.SettingInfo;
import domain.SettingType;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class BoardInitializer {
    private final Map<PieceType, Piece> choPieces;
    private final Map<PieceType, Piece> hanPieces;

    public BoardInitializer() {
        choPieces = initPieceByTeam(Team.CHO);
        hanPieces = initPieceByTeam(Team.HAN);
    }

    private Map<PieceType, Piece> initPieceByTeam(Team team) {
        Map<PieceType, Piece> pieces = new HashMap<>();
        for (PieceType type : PieceType.values()) {
            if (type != PieceType.EMPTY) {
                pieces.put(type, PieceFactory.create(type, team));
            }
        }
        return pieces;
    }

    public Map<Position, Piece> setup(SettingType choSettingType, SettingType hanSettingType) {
        Map<Position, Piece> defaultSetting = initPiecesWithoutSangAndMa();
        initSangAndMa(choSettingType, hanSettingType, defaultSetting);

        return defaultSetting;
    }

    private Map<Position, Piece> initPiecesWithoutSangAndMa() {
        Map<Position, Piece> setting = new HashMap<>();

        // 초나라
        setting.put(Position.of(1, 1), choPieces.get(PieceType.CHA));
        setting.put(Position.of(1, 9), choPieces.get(PieceType.CHA));

        setting.put(Position.of(1, 4), choPieces.get(PieceType.SA));
        setting.put(Position.of(1, 6), choPieces.get(PieceType.SA));

        setting.put(Position.of(2, 5), choPieces.get(PieceType.JANG));

        setting.put(Position.of(3, 2), choPieces.get(PieceType.PO));
        setting.put(Position.of(3, 8), choPieces.get(PieceType.PO));

        setting.put(Position.of(4, 1), choPieces.get(PieceType.JOL));
        setting.put(Position.of(4, 3), choPieces.get(PieceType.JOL));
        setting.put(Position.of(4, 5), choPieces.get(PieceType.JOL));
        setting.put(Position.of(4, 7), choPieces.get(PieceType.JOL));
        setting.put(Position.of(4, 9), choPieces.get(PieceType.JOL));

        // 한나라
        setting.put(Position.of(10, 1), hanPieces.get(PieceType.CHA));
        setting.put(Position.of(10, 9), hanPieces.get(PieceType.CHA));

        setting.put(Position.of(10, 4), hanPieces.get(PieceType.SA));
        setting.put(Position.of(10, 6), hanPieces.get(PieceType.SA));

        setting.put(Position.of(9, 5), hanPieces.get(PieceType.JANG));

        setting.put(Position.of(8, 2), hanPieces.get(PieceType.PO));
        setting.put(Position.of(8, 8), hanPieces.get(PieceType.PO));

        setting.put(Position.of(7, 1), hanPieces.get(PieceType.BYEONG));
        setting.put(Position.of(7, 3), hanPieces.get(PieceType.BYEONG));
        setting.put(Position.of(7, 5), hanPieces.get(PieceType.BYEONG));
        setting.put(Position.of(7, 7), hanPieces.get(PieceType.BYEONG));
        setting.put(Position.of(7, 9), hanPieces.get(PieceType.BYEONG));
        return setting;
    }

    private void initSangAndMa(SettingType choSettingType, SettingType hanSettingType,
                               Map<Position, Piece> defaultSetting) {
        SettingInfo choSettingInfo = choSettingType.generate(Team.CHO);
        SettingInfo hanSettingInfo = hanSettingType.generate(Team.HAN);
        putSangAndMaByTeam(defaultSetting, choSettingInfo, choPieces);
        putSangAndMaByTeam(defaultSetting, hanSettingInfo, hanPieces);
    }

    private void putSangAndMaByTeam(Map<Position, Piece> defaultSetting, SettingInfo settingInfo,
                                    Map<PieceType, Piece> pieces) {
        defaultSetting.put(settingInfo.ma1(), pieces.get(PieceType.MA));
        defaultSetting.put(settingInfo.ma2(), pieces.get(PieceType.MA));
        defaultSetting.put(settingInfo.sang1(), pieces.get(PieceType.SANG));
        defaultSetting.put(settingInfo.sang2(), pieces.get(PieceType.SANG));
    }
}
