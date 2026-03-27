package domain;

import domain.piece.Cha;
import domain.piece.Jang;
import domain.piece.Jolbyeong;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.Team;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardInitializerTest {
    Sang sangOfCho;
    Sang sangOfHan;
    Ma maOfCho;
    Ma maOfHan;
    BoardInitializer initializer;


    @BeforeEach
    void setUp() {
        initializer = new BoardInitializer();
        sangOfCho = new Sang(Team.CHO);
        sangOfHan = new Sang(Team.HAN);
        maOfCho = new Ma(Team.CHO);
        maOfHan = new Ma(Team.HAN);
    }

    @Test
    void 입력된_왼상차림대로_생성되어야_한다() {
        //given
        SettingInfo choInfo = new LeftSettingOfChoInfo();
        SettingInfo hanInfo = new LeftSettingOfHanInfo();

        Map<Position, Piece> result = initializer.setup(SettingType.LEFT, SettingType.LEFT);

        //then
        assertSangAndMaPosition(result, choInfo, hanInfo);
        assertWithoutSangAndMaPosition(result);
    }

    @Test
    void 입력된_오른상차림대로_생성되어야_한다() {
        //given
        SettingInfo choInfo = new RightSettingOfChoInfo();
        SettingInfo hanInfo = new RightSettingOfHanInfo();

        Map<Position, Piece> result = initializer.setup(SettingType.RIGHT, SettingType.RIGHT);

        //then
        assertSangAndMaPosition(result, choInfo, hanInfo);
        assertWithoutSangAndMaPosition(result);
    }

    @Test
    void 입력된_안상차림대로_생성되어야_한다() {
        //given
        SettingInfo choInfo = new InnerSettingOfChoInfo();
        SettingInfo hanInfo = new InnerSettingOfHanInfo();

        Map<Position, Piece> result = initializer.setup(SettingType.INNER, SettingType.INNER);

        //then
        assertSangAndMaPosition(result, choInfo, hanInfo);
        assertWithoutSangAndMaPosition(result);

    }

    @Test
    void 입력된_바깥상차림대로_생성되어야_한다() {
        //given
        SettingInfo choInfo = new OuterSettingOfChoInfo();
        SettingInfo hanInfo = new OuterSettingOfHanInfo();

        Map<Position, Piece> result = initializer.setup(SettingType.OUTER, SettingType.OUTER);

        //then
        assertSangAndMaPosition(result, choInfo, hanInfo);
        assertWithoutSangAndMaPosition(result);
    }

    private void assertSangAndMaPosition(Map<Position, Piece> result, SettingInfo choInfo, SettingInfo hanInfo) {
        Assertions.assertThat(result.get(choInfo.sang1)).isEqualTo(sangOfCho);
        Assertions.assertThat(result.get(choInfo.sang2)).isEqualTo(sangOfCho);
        Assertions.assertThat(result.get(choInfo.ma1)).isEqualTo(maOfCho);
        Assertions.assertThat(result.get(choInfo.ma2)).isEqualTo(maOfCho);

        Assertions.assertThat(result.get(hanInfo.sang1)).isEqualTo(sangOfHan);
        Assertions.assertThat(result.get(hanInfo.sang2)).isEqualTo(sangOfHan);
        Assertions.assertThat(result.get(hanInfo.ma1)).isEqualTo(maOfHan);
        Assertions.assertThat(result.get(hanInfo.ma2)).isEqualTo(maOfHan);
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
        setting.put(Position.of(1, 1), new Cha(Team.CHO));
        setting.put(Position.of(1, 9), new Cha(Team.CHO));

        setting.put(Position.of(1, 4), new Sa(Team.CHO));
        setting.put(Position.of(1, 6), new Sa(Team.CHO));

        setting.put(Position.of(2, 5), new Jang(Team.CHO));

        setting.put(Position.of(3, 2), new Po(Team.CHO));
        setting.put(Position.of(3, 8), new Po(Team.CHO));

        setting.put(Position.of(4, 1), new Jolbyeong(Team.CHO));
        setting.put(Position.of(4, 3), new Jolbyeong(Team.CHO));
        setting.put(Position.of(4, 5), new Jolbyeong(Team.CHO));
        setting.put(Position.of(4, 7), new Jolbyeong(Team.CHO));
        setting.put(Position.of(4, 9), new Jolbyeong(Team.CHO));

        // 한나라
        setting.put(Position.of(10, 1), new Cha(Team.HAN));
        setting.put(Position.of(10, 9), new Cha(Team.HAN));

        setting.put(Position.of(10, 4), new Sa(Team.HAN));
        setting.put(Position.of(10, 6), new Sa(Team.HAN));

        setting.put(Position.of(9, 5), new Jang(Team.HAN));

        setting.put(Position.of(8, 2), new Po(Team.HAN));
        setting.put(Position.of(8, 8), new Po(Team.HAN));

        setting.put(Position.of(7, 1), new Jolbyeong(Team.HAN));
        setting.put(Position.of(7, 3), new Jolbyeong(Team.HAN));
        setting.put(Position.of(7, 5), new Jolbyeong(Team.HAN));
        setting.put(Position.of(7, 7), new Jolbyeong(Team.HAN));
        setting.put(Position.of(7, 9), new Jolbyeong(Team.HAN));

        return setting;
    }
}
