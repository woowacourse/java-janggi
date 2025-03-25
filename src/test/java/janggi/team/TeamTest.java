package janggi.team;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.BoardSetup;
import janggi.board.Position;
import janggi.palace.PalaceArea;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamTest {
    @DisplayName("정상: 팀 초기화 테스트")
    @Test
    void initBoard() {
        Team teamHan = new TeamHan(BoardSetup.of(TeamName.HAN, "EHEH"));

        assertThat(teamHan).isNotNull();
    }

    @DisplayName("정상: 특정 위치가 궁성 안에 있는지 확인")
    @Test
    void isInPalaceArea() {
        Team teamHan = new TeamHan(BoardSetup.of(TeamName.HAN, "EHEH"));

        assertThat(teamHan.isInPalaceArea(new Position(4, 8))).isEqualTo(PalaceArea.INSIDE);
    }

    @DisplayName("정상: 말의 이동이 가능한지 확인")
    @Test
    void validatePieceMovement() {
        Team teamCho = new TeamCho(BoardSetup.of(TeamName.CHO, "HEHE"));

        assertThatCode(() -> teamCho.validatePieceMovement("H", new Position(1, 0),
                new Position(2, 2))).doesNotThrowAnyException();
    }

    @DisplayName("정상: 말의 이름으로 말을 찾아서 반환하는지 확인")
    @Test
    void findPieceByName() {
        Team teamCho = new TeamCho(BoardSetup.of(TeamName.CHO, "HEHE"));

        assertThat(teamCho.findPieceByName("H", new Position(1, 0))).isNotNull();
    }

    @DisplayName("예외: 말의 이름을 찾을 수 없는 경우")
    @Test
    void findPieceByName_Invalid() {
        Team teamCho = new TeamCho(BoardSetup.of(TeamName.CHO, "HEHE"));

        assertThatThrownBy(() -> teamCho.findPieceByName("J", new Position(1, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: 특정 말이 해당 위치에 살아있는지 확인")
    @Test
    void validatePiece() {
        Team teamHan = new TeamHan(BoardSetup.of(TeamName.HAN, "HEEH"));

        assertThatCode(() -> teamHan.validatePiece("E", new Position(2, 9))).doesNotThrowAnyException();
    }

    @DisplayName("정상: 왕과 사인 경우 도착 좌표가 궁성 안에 있는지 확인")
    @Test
    void validateKingGuardDestinationIsInPalace() {
        Team teamHan = new TeamHan(BoardSetup.of(TeamName.HAN, "HEEH"));

        assertThatCode(() -> teamHan.validateKingGuardDestinationIsInPalace("K",
                new Position(4, 7))).doesNotThrowAnyException();
    }

    @DisplayName("정상: 도착 좌표에 같은 팀의 말이 이미 자리하고 있는지 확인")
    @Test
    void validateDestinationIsNotOccupiedBySameTeam() {
        Team teamCho = new TeamCho(BoardSetup.of(TeamName.CHO, "HEEH"));

        teamCho.move("G", new Position(3, 0), new Position(3, 1));

        assertThatThrownBy(() -> teamCho.validateDestinationIsNotOccupiedBySameTeam(new Position(3, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예외: 이동 경로에 다른 말이 장애물로 존재하는지 확인")
    @Test
    void validateLegalMove() {
        Team teamCho = new TeamCho(BoardSetup.of(TeamName.CHO, "HEEH"));

        List<Position> positionsOnPath = List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        );

        assertThatThrownBy(() -> teamCho.validateLegalMove("C", positionsOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예외: 포의 이동 경로에 다른 말(다른 팀의 포)이 존재하는 경우")
    @Test
    void validateLegalMoveForCannon() {
        Team teamCho = new TeamCho(BoardSetup.of(TeamName.CHO, "HEEH"));

        List<Position> positionsOnPath = List.of(
                new Position(1, 1),
                new Position(1, 2),
                new Position(1, 3),
                new Position(1, 4),
                new Position(1, 5),
                new Position(1, 6),
                new Position(1, 7)
        );

        assertThatThrownBy(() -> teamCho.validateLegalMove("P", positionsOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: 말을 이동시키는지 확인")
    @Test
    void move() {
        Team teamCho = new TeamCho(BoardSetup.of(TeamName.CHO, "HEEH"));

        teamCho.move("S", new Position(2, 3), new Position(3, 3));

        assertThatCode(() -> teamCho.validatePiece("S", new Position(3, 3))).doesNotThrowAnyException();
    }

    @DisplayName("예외: 말이 포획되어 존재하지 않게 된 경우")
    @Test
    void updateStatusIfCaught() {
        Team teamHan = new TeamHan(BoardSetup.of(TeamName.HAN, "EHHE"));

        teamHan.updateStatusIfCaught(new Position(2, 3));

        assertThatThrownBy(() -> teamHan.validatePiece("S", new Position(2, 3)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: 왕이 포획되었는지 확인")
    @Test
    void isKingCaught() {
        Team teamHan = new TeamHan(BoardSetup.of(TeamName.HAN, "EHHE"));

        teamHan.updateStatusIfCaught(new Position(4, 8));

        assertThat(teamHan.isKingCaught()).isTrue();
    }

    @DisplayName("정상: 팀 점수를 계산하는지 확인")
    @Test
    void trackTeamScore() {
        Team teamHan = new TeamHan(BoardSetup.of(TeamName.HAN, "EHHE"));

        teamHan.trackTeamScore(TeamName.HAN);

        assertThat(teamHan.getTeamScore()).isEqualTo(73.5);
    }
}
