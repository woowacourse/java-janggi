package model.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import model.Position;
import model.Team;
import model.piece.Piece;
import model.piece.PieceType;

class InitializerTest {

    @ParameterizedTest
    @CsvSource({
        "CHO,LEFT,2,0",
        "HAN,RIGHT,1,0",
    })
    @DisplayName("팀과 상차림에 맞는 기물들을 생성한다.")
    void settingWithTest(Team team, TableSetting setting, int x, int y) {
        Initializer initializer = new Initializer();
        List<Piece> pieces = initializer.generatePiecesOf(team, setting);

        assertThat(pieces.getFirst().getTeam()).isEqualTo(team);
        assertThat(getHorseOf(pieces).getPosition()).isEqualTo(new Position(team.onBaseX(x), team.onBaseY(y)));
    }

    private Piece getHorseOf(List<Piece> pieces) {
        return pieces.stream()
            .filter(piece -> piece.type() == PieceType.HORSE)
            .min(Comparator.comparing(a -> a.getPosition().x()))
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] HORSE 기물이 존재하지 않습니다."));
    }
}
