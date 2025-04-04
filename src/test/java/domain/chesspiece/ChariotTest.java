package domain.chesspiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.path.Path;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    @DisplayName("차의 이동 경로를 계산한다")
    void test1() {
        //given
        final ChessPosition chessPosition = new ChessPosition(1, 4);
        final Path expected = new Path(List.of(
                new ChessPosition(2, 4),
                new ChessPosition(3, 4),
                new ChessPosition(4, 4),
                new ChessPosition(5, 4),
                new ChessPosition(6,4),
                new ChessPosition(7,4),
                new ChessPosition(8,4),
                new ChessPosition(9,4))
        );

        //when
        final Chariot chariot = new Chariot(ChessTeam.RED, chessPosition);
        final List<Path> coordinatePaths = chariot.calculateCoordinatePaths(chessPosition);

        //then
        assertThat(coordinatePaths).contains(expected);
    }


}
