package domain;

import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Jang;
import domain.piece.Jol;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.Team;
import domain.piece.strategy.ByeongMoveStrategy;
import domain.piece.strategy.JolMoveStrategy;
import domain.piece.strategy.MaMoveStrategy;
import domain.piece.strategy.SangMoveStrategy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.piece.strategy.component.PalaceMoveRule;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        sangOfCho = new Sang(new SangMoveStrategy(), Team.CHO);
        sangOfHan = new Sang(new SangMoveStrategy(), Team.HAN);
        maOfCho = new Ma(new MaMoveStrategy(), Team.CHO);
        maOfHan = new Ma(new MaMoveStrategy(), Team.HAN);
    }

    @Test
    @DisplayName("입력된 왼상차림대로 생성되어야 한다")
    void setUp_success_with_SettingTypeLeft() {
        //given
        SettingType choSettingType = SettingType.LEFT;
        SettingType hangSettingType = SettingType.LEFT;

        SettingInfo choInfo = choSettingType.generate(Team.CHO);
        SettingInfo hanInfo = hangSettingType.generate(Team.HAN);

        Map<Position, Piece> expect = new HashMap<>();
        putWithoutSangAndMa(expect);
        putSangAndMa(expect, choInfo, hanInfo);

        Map<Position, Piece> result = initializer.setup(choSettingType, hangSettingType);

        //then
        Assertions.assertThat(result).containsAllEntriesOf(expect);
    }

    @Test
    @DisplayName("입력된 오른상차림대로 생성되어야 한다")
    void setUp_success_with_SettingTypeRight() {
        //given
        SettingType choSettingType = SettingType.RIGHT;
        SettingType hangSettingType = SettingType.RIGHT;

        SettingInfo choInfo = choSettingType.generate(Team.CHO);
        SettingInfo hanInfo = hangSettingType.generate(Team.HAN);

        Map<Position, Piece> expect = new HashMap<>();
        putWithoutSangAndMa(expect);
        putSangAndMa(expect, choInfo, hanInfo);

        Map<Position, Piece> result = initializer.setup(choSettingType, hangSettingType);

        //then
        Assertions.assertThat(result).containsAllEntriesOf(expect);
    }

    @Test
    @DisplayName("setUp_success_with_SettingTypeInner")
    void setUp_success_with_SettingTypeInner() {
        //given
        SettingType choSettingType = SettingType.INNER;
        SettingType hangSettingType = SettingType.INNER;

        SettingInfo choInfo = choSettingType.generate(Team.CHO);
        SettingInfo hanInfo = hangSettingType.generate(Team.HAN);

        Map<Position, Piece> expect = new HashMap<>();
        putWithoutSangAndMa(expect);
        putSangAndMa(expect, choInfo, hanInfo);

        Map<Position, Piece> result = initializer.setup(choSettingType, hangSettingType);

        //then
        Assertions.assertThat(result).containsAllEntriesOf(expect);
    }

    @Test
    @DisplayName("입력된 바깥상차림대로 생성되어야 한다")
    void setUp_success_with_SettingTypeOuter() {
        //given
        SettingType choSettingType = SettingType.OUTER;
        SettingType hangSettingType = SettingType.OUTER;

        SettingInfo choInfo = choSettingType.generate(Team.CHO);
        SettingInfo hanInfo = hangSettingType.generate(Team.HAN);

        Map<Position, Piece> expect = new HashMap<>();
        putWithoutSangAndMa(expect);
        putSangAndMa(expect, choInfo, hanInfo);

        Map<Position, Piece> result = initializer.setup(choSettingType, hangSettingType);

        //then
        Assertions.assertThat(result).containsAllEntriesOf(expect);
    }

    private void putSangAndMa(Map<Position, Piece> piecesWithoutSangAndMa, SettingInfo choInfo, SettingInfo hanInfo) {
        piecesWithoutSangAndMa.put(choInfo.sang1(), sangOfCho);
        piecesWithoutSangAndMa.put(choInfo.sang2(), sangOfCho);
        piecesWithoutSangAndMa.put(choInfo.ma1(), maOfCho);
        piecesWithoutSangAndMa.put(choInfo.ma2(), maOfCho);
        piecesWithoutSangAndMa.put(hanInfo.sang1(), sangOfHan);
        piecesWithoutSangAndMa.put(hanInfo.sang2(), sangOfHan);
        piecesWithoutSangAndMa.put(hanInfo.ma1(), maOfHan);
        piecesWithoutSangAndMa.put(hanInfo.ma2(), maOfHan);
    }

    private void putWithoutSangAndMa(Map<Position, Piece> expect) {
        PalaceMoveRule palaceMoveRule = new PalaceMoveRule();

        // 초나라
        expect.put(Position.of(1, 1), new Cha(new SlidingMoveStrategy(palaceMoveRule), Team.CHO));
        expect.put(Position.of(1, 9), new Cha(new SlidingMoveStrategy(palaceMoveRule), Team.CHO));

        expect.put(Position.of(1, 4), new Sa(new SingleStepMoveStrategy(palaceMoveRule), Team.CHO));
        expect.put(Position.of(1, 6), new Sa(new SingleStepMoveStrategy(palaceMoveRule), Team.CHO));

        expect.put(Position.of(2, 5), new Jang(new SingleStepMoveStrategy(palaceMoveRule), Team.CHO));

        expect.put(Position.of(3, 2), new Po(new SlidingMoveStrategy(palaceMoveRule), Team.CHO));
        expect.put(Position.of(3, 8), new Po(new SlidingMoveStrategy(palaceMoveRule), Team.CHO));

        expect.put(Position.of(4, 1), new Jol(new JolMoveStrategy(palaceMoveRule), Team.CHO));
        expect.put(Position.of(4, 3), new Jol(new JolMoveStrategy(palaceMoveRule), Team.CHO));
        expect.put(Position.of(4, 5), new Jol(new JolMoveStrategy(palaceMoveRule), Team.CHO));
        expect.put(Position.of(4, 7), new Jol(new JolMoveStrategy(palaceMoveRule), Team.CHO));
        expect.put(Position.of(4, 9), new Jol(new JolMoveStrategy(palaceMoveRule), Team.CHO));

        // 한나라
        expect.put(Position.of(10, 1), new Cha(new SlidingMoveStrategy(palaceMoveRule), Team.HAN));
        expect.put(Position.of(10, 9), new Cha(new SlidingMoveStrategy(palaceMoveRule), Team.HAN));

        expect.put(Position.of(10, 4), new Sa(new SingleStepMoveStrategy(palaceMoveRule), Team.HAN));
        expect.put(Position.of(10, 6), new Sa(new SingleStepMoveStrategy(palaceMoveRule), Team.HAN));

        expect.put(Position.of(9, 5), new Jang(new SingleStepMoveStrategy(palaceMoveRule), Team.HAN));

        expect.put(Position.of(8, 2), new Po(new SlidingMoveStrategy(palaceMoveRule), Team.HAN));
        expect.put(Position.of(8, 8), new Po(new SlidingMoveStrategy(palaceMoveRule), Team.HAN));

        expect.put(Position.of(7, 1), new Byeong(new ByeongMoveStrategy(palaceMoveRule), Team.HAN));
        expect.put(Position.of(7, 3), new Byeong(new ByeongMoveStrategy(palaceMoveRule), Team.HAN));
        expect.put(Position.of(7, 5), new Byeong(new ByeongMoveStrategy(palaceMoveRule), Team.HAN));
        expect.put(Position.of(7, 7), new Byeong(new ByeongMoveStrategy(palaceMoveRule), Team.HAN));
        expect.put(Position.of(7, 9), new Byeong(new ByeongMoveStrategy(palaceMoveRule), Team.HAN));
    }
}
