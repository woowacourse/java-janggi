package model.fixture;

import model.Team;
import model.formation.MaSangMaSangStrategy;
import model.formation.MaSangSangMaStrategy;
import model.formation.SangMaMaSangStrategy;
import model.formation.SangMaSangMaStrategy;
import model.piece.Elephant;
import model.piece.Horse;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class FormationTestFixture {

    public static Stream<Arguments> 한나라_상차림_전략() {
        return Stream.of(
                Arguments.of(new SangMaSangMaStrategy(), Elephant.class, Horse.class, Elephant.class, Horse.class),
                Arguments.of(new MaSangMaSangStrategy(), Horse.class, Elephant.class, Horse.class, Elephant.class),
                Arguments.of(new MaSangSangMaStrategy(), Horse.class, Elephant.class, Elephant.class, Horse.class),
                Arguments.of(new SangMaMaSangStrategy(), Elephant.class, Horse.class, Horse.class, Elephant.class)
        );
    }

    public static Stream<Arguments> 초나라_상차림_전략() {
        return Stream.of(
                Arguments.of(new SangMaSangMaStrategy(), Elephant.class, Horse.class, Elephant.class, Horse.class),
                Arguments.of(new MaSangMaSangStrategy(), Horse.class, Elephant.class, Horse.class, Elephant.class),
                Arguments.of(new MaSangSangMaStrategy(), Horse.class, Elephant.class, Elephant.class, Horse.class),
                Arguments.of(new SangMaMaSangStrategy(), Elephant.class, Horse.class, Horse.class, Elephant.class)
        );
    }

    public static Stream<Arguments> 나라별_상차림_전략() {
        return Stream.of(
                Arguments.of(new SangMaSangMaStrategy(), Team.HAN),
                Arguments.of(new SangMaSangMaStrategy(), Team.CHO),
                Arguments.of(new MaSangMaSangStrategy(), Team.HAN),
                Arguments.of(new MaSangMaSangStrategy(), Team.CHO),
                Arguments.of(new MaSangSangMaStrategy(), Team.HAN),
                Arguments.of(new MaSangSangMaStrategy(), Team.CHO),
                Arguments.of(new SangMaMaSangStrategy(), Team.HAN),
                Arguments.of(new SangMaMaSangStrategy(), Team.CHO)
        );
    }
}
