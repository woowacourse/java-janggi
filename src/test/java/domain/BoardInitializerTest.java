package domain;

import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingInfo;
import domain.settingType.SettingType;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardInitializerTest {
    Piece sangOfCho;
    Piece sangOfHan;
    Piece maOfCho;
    Piece maOfHan;
    BoardInitializer initializer;


    @BeforeEach
    void setUp() {
        initializer = new BoardInitializer();
        sangOfCho = PieceFactory.create(PieceType.SANG, Team.CHO);
        sangOfHan = PieceFactory.create(PieceType.SANG, Team.HAN);
        maOfCho = PieceFactory.create(PieceType.MA, Team.CHO);
        maOfHan = PieceFactory.create(PieceType.MA, Team.HAN);
    }

    @Test
    void 입력된_왼상차림대로_생성되어야_한다() {
        //given
        SettingInfo choInfo = SettingInfo.of(SettingType.LEFT, Team.CHO);
        SettingInfo hanInfo = SettingInfo.of(SettingType.LEFT, Team.HAN);

        // when
        Map<Position, Piece> result = initializer.setup(SettingType.LEFT, SettingType.LEFT);

        //then
        assertSangAndMaPosition(result, choInfo, hanInfo);
        assertWithoutSangAndMaPosition(result);
    }

    @Test
    void 입력된_오른상차림대로_생성되어야_한다() {
        //given
        SettingInfo choInfo = SettingInfo.of(SettingType.RIGHT, Team.CHO);
        SettingInfo hanInfo = SettingInfo.of(SettingType.RIGHT, Team.HAN);

        // when
        Map<Position, Piece> result = initializer.setup(SettingType.RIGHT, SettingType.RIGHT);

        //then
        assertSangAndMaPosition(result, choInfo, hanInfo);
        assertWithoutSangAndMaPosition(result);
    }

    @Test
    void 입력된_안상차림대로_생성되어야_한다() {
        //given
        SettingInfo choInfo = SettingInfo.of(SettingType.INNER, Team.CHO);
        SettingInfo hanInfo = SettingInfo.of(SettingType.INNER, Team.HAN);

        // when
        Map<Position, Piece> result = initializer.setup(SettingType.INNER, SettingType.INNER);

        //then
        assertSangAndMaPosition(result, choInfo, hanInfo);
        assertWithoutSangAndMaPosition(result);

    }

    @Test
    void 입력된_바깥상차림대로_생성되어야_한다() {
        //given
        SettingInfo choInfo = SettingInfo.of(SettingType.OUTER, Team.CHO);
        SettingInfo hanInfo = SettingInfo.of(SettingType.OUTER, Team.HAN);

        // when
        Map<Position, Piece> result = initializer.setup(SettingType.OUTER, SettingType.OUTER);

        //then
        assertSangAndMaPosition(result, choInfo, hanInfo);
        assertWithoutSangAndMaPosition(result);
    }

    private void assertSangAndMaPosition(Map<Position, Piece> result, SettingInfo choInfo, SettingInfo hanInfo) {
        Assertions.assertThat(result.get(choInfo.getSang1())).isEqualTo(sangOfCho);
        Assertions.assertThat(result.get(choInfo.getSang2())).isEqualTo(sangOfCho);
        Assertions.assertThat(result.get(choInfo.getMa1())).isEqualTo(maOfCho);
        Assertions.assertThat(result.get(choInfo.getMa2())).isEqualTo(maOfCho);

        Assertions.assertThat(result.get(hanInfo.getSang1())).isEqualTo(sangOfHan);
        Assertions.assertThat(result.get(hanInfo.getSang2())).isEqualTo(sangOfHan);
        Assertions.assertThat(result.get(hanInfo.getMa1())).isEqualTo(maOfHan);
        Assertions.assertThat(result.get(hanInfo.getMa2())).isEqualTo(maOfHan);
    }

    private void assertWithoutSangAndMaPosition(Map<Position, Piece> result) {
        Map<Position, Piece> target = initPiecesWithoutSangAndMa();
        for (Position position : target.keySet()) {
            Assertions.assertThat(result.get(position)).isEqualTo(target.get(position));
        }
    }

    private Map<Position, Piece> initPiecesWithoutSangAndMa() {
        Map<Position, Piece> setting = new HashMap<>();

        // 초나라
        setting.put(Position.of(1, 1), PieceFactory.create(PieceType.CHA, Team.CHO));
        setting.put(Position.of(1, 9), PieceFactory.create(PieceType.CHA, Team.CHO));

        setting.put(Position.of(1, 4), PieceFactory.create(PieceType.SA, Team.CHO));
        setting.put(Position.of(1, 6), PieceFactory.create(PieceType.SA, Team.CHO));

        setting.put(Position.of(2, 5), PieceFactory.create(PieceType.JANG, Team.CHO));

        setting.put(Position.of(3, 2), PieceFactory.create(PieceType.PO, Team.CHO));
        setting.put(Position.of(3, 8), PieceFactory.create(PieceType.PO, Team.CHO));

        setting.put(Position.of(4, 1), PieceFactory.create(PieceType.JOL, Team.CHO));
        setting.put(Position.of(4, 3), PieceFactory.create(PieceType.JOL, Team.CHO));
        setting.put(Position.of(4, 5), PieceFactory.create(PieceType.JOL, Team.CHO));
        setting.put(Position.of(4, 7), PieceFactory.create(PieceType.JOL, Team.CHO));
        setting.put(Position.of(4, 9), PieceFactory.create(PieceType.JOL, Team.CHO));

        // 한나라
        setting.put(Position.of(10, 1), PieceFactory.create(PieceType.CHA, Team.HAN));
        setting.put(Position.of(10, 9), PieceFactory.create(PieceType.CHA, Team.HAN));

        setting.put(Position.of(10, 4), PieceFactory.create(PieceType.SA, Team.HAN));
        setting.put(Position.of(10, 6), PieceFactory.create(PieceType.SA, Team.HAN));

        setting.put(Position.of(9, 5), PieceFactory.create(PieceType.JANG, Team.HAN));

        setting.put(Position.of(8, 2), PieceFactory.create(PieceType.PO, Team.HAN));
        setting.put(Position.of(8, 8), PieceFactory.create(PieceType.PO, Team.HAN));

        setting.put(Position.of(7, 1), PieceFactory.create(PieceType.BYEONG, Team.HAN));
        setting.put(Position.of(7, 3), PieceFactory.create(PieceType.BYEONG, Team.HAN));
        setting.put(Position.of(7, 5), PieceFactory.create(PieceType.BYEONG, Team.HAN));
        setting.put(Position.of(7, 7), PieceFactory.create(PieceType.BYEONG, Team.HAN));
        setting.put(Position.of(7, 9), PieceFactory.create(PieceType.BYEONG, Team.HAN));

        return setting;
    }
}
