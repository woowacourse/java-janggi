package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.board.Board;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Piece;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Nested
    @DisplayName("특정 팀 기물 여부 테스트")
    class CheckPieceOfTeam {

        @Test
        @DisplayName("특정 팀의 기물인 경우")
        void success_1() {
            Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
            Position position = Position.valueOf(1, 1);
            Map<Position, Piece> positionPieceMap = Map.of(
                position, new Cannon(TeamType.RED)
            );
            Board board = new Board(positionPieceMap);
            boolean expected = true;

            boolean actual = board.checkPieceOfTeam(redTeam, position);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("특정 팀의 기물이 아닌 경우")
        void success_2() {
            Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
            Position position = Position.valueOf(1, 1);
            Map<Position, Piece> positionPieceMap = Map.of(
                position, new Cannon(TeamType.RED)
            );
            Board board = new Board(positionPieceMap);
            boolean expected = false;

            boolean actual = board.checkPieceOfTeam(blueTeam, position);

            assertThat(actual).isEqualTo(expected);
        }
    }
}
