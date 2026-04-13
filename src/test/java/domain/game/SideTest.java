package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class SideTest {

    @Test
    void 진영은_초와_한만_가진다() {
        List<Side> sides = Arrays.stream(Side.values()).toList();
        assertThat(sides).containsExactlyInAnyOrder(Side.CHO, Side.HAN);
    }
}
