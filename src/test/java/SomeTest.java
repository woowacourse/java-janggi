import static org.assertj.core.api.Assertions.assertThat;

import model.Jang;
import org.junit.jupiter.api.Test;

public class SomeTest {


    @Test
    public void UnitTest() {

        String team = "red";
        Jang jang  = new Jang(team);
        assertThat(jang.getTeam()).isEqualTo(team);
    }
}
