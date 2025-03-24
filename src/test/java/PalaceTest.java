import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PalaceTest {

    @Test
    @DisplayName("장기 말의 종류에는 궁이 있다.")
    void createPalaceTest(){
        Piece palace = new Palace();
    }

    //todo: position 먼저 구현 필요.
    @Test
    @DisplayName("궁은 상하좌우로 이동할 수 있다.")
    void movePalaceTest(){
        Piece palace = new Palace();
    }
}
