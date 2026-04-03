package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Position;
import domain.Team;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


class KingTest {
    /**
     * 왕 규칙 : 궁성 내에서 앞, 뒤, 양옆 한 칸씩 이동 가능
     * 1. 도착 지점이 한칸 앞뒤, 혹은 양옆인지 검증
     */
    @ParameterizedTest
    @MethodSource("validDirectionsPositions")
    void 도착_지점이_궁성_내_한칸거리인_경우_정상_테스트(Position to){
        // given
        Board board = new Board();
        Piece king = new King(Team.CHO);

        // when
        Position from = Position.from(9, 5);

        // then
        assertThat(king.canMove(from, to, board)).isEqualTo(true);
    }

    static Stream<Position> validDirectionsPositions() {
        return Stream.of(
                Position.from(9, 4), // 왼쪽 한칸
                Position.from(9, 6), // 오른쪽 한칸
                Position.from(10, 5),  // 아래 한칸
                Position.from(8, 5),  // 위로 한칸
                Position.from(8, 6),  // 오른쪽 위 대각선
                Position.from(8, 4),  // 왼쪽 위 대각선
                Position.from(10, 6),  // 오른쪽 아래 대각선
                Position.from(10, 4)  // 왼쪽 아래 대각선
        );
    }

    @Test
    void 도착_지점이_궁성이_아닌_경우_테스트(){
        // given
        Board board = new Board();
        Piece king = new King(Team.CHO);

        // when
        Position from = Position.from(8, 5);
        Position to = Position.from(7, 5);

        // then
        assertThat(king.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 목적지에 같은 팀이 있는 경우
     * 1. 목적지가 한칸 앞인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     * 2. 목적지가 한칸 뒤인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     * 3. 목적지가 한칸 오른쪽인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     * 4. 목적지가 한칸 왼쪽인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     */
    @ParameterizedTest
    @MethodSource("blockedBySameTeamProvider")
    void 목적지에_같은_팀이_있는_경우_이동할_수_없다(Position to) {
        // given
        Piece king = new King(Team.CHO);

        Position from = Position.from(9, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new King(Team.CHO));
        testPiece.put(to, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        // when & then
        assertThat(king.canMove(from, to, board)).isEqualTo(false);
    }

    static Stream<Position> blockedBySameTeamProvider() {
        return Stream.of(
                Position.from(9, 4),
                Position.from(8, 6),
                Position.from(10, 5)
        );
    }
}
