package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceTest {
    @Test
    void 말을_생성할_수_있다() {
        // then & when
        Assertions.assertDoesNotThrow(() -> new Piece());
    }

    
}
