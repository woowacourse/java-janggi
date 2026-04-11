package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BoardFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 5, -1, 99})
    @DisplayName("존재하지 않는 진형 번호면 예외가 발생한다.")
    void invalidFormationNumberThrowsException(int invalidNumber) {
        assertThatThrownBy(() -> BoardFactory.createFormation(invalidNumber, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("공통 초기 배치 검증")
    class CommonInitialSetupTest {
        private Map<Position, Piece> pieces;

        @BeforeEach
        void setUp() {
            pieces = BoardFactory.createFormation(1, 1);
        }

        @Test
        @DisplayName("보드 생성 시 총 32개의 기물이 존재해야 한다.")
        void countTotalPieces() {
            assertThat(pieces).hasSize(32);
        }

        @Test
        @DisplayName("배치된 기물들이 각 팀의 속성을 정확히 가지고 있는지 확인한다.")
        void verifyTeamOwnership() {
            assertThat(pieces.get(new Position(1, 1)).getTeam()).isEqualTo(Team.CHO);
            assertThat(pieces.get(new Position(10, 1)).getTeam()).isEqualTo(Team.HAN);
        }

        @Test
        @DisplayName("초 진영의 차는 1행 1열과 1행 9열에 배치된다.")
        void checkChoChariot() {
            assertThat(pieces.get(new Position(1, 1))).isInstanceOf(Chariot.class);
            assertThat(pieces.get(new Position(1, 9))).isInstanceOf(Chariot.class);
        }

        @Test
        @DisplayName("초 진영의 사는 1행 4열과 1행 6열에, 장은 2행 5열에 배치된다.")
        void checkChoGuardAndGeneral() {
            assertThat(pieces.get(new Position(1, 4))).isInstanceOf(Guard.class);
            assertThat(pieces.get(new Position(1, 6))).isInstanceOf(Guard.class);
            assertThat(pieces.get(new Position(2, 5))).isInstanceOf(General.class);
        }

        @Test
        @DisplayName("초 진영의 포는 3행 2열과 3행 8열에 배치된다.")
        void checkChoCannon() {
            assertThat(pieces.get(new Position(3, 2))).isInstanceOf(Cannon.class);
            assertThat(pieces.get(new Position(3, 8))).isInstanceOf(Cannon.class);
        }

        @Test
        @DisplayName("초 진영의 졸은 4행 1, 3, 5, 7, 9열에 배치된다.")
        void checkChoSoldier() {
            assertThat(pieces.get(new Position(4, 1))).isInstanceOf(Soldier.class);
            assertThat(pieces.get(new Position(4, 3))).isInstanceOf(Soldier.class);
            assertThat(pieces.get(new Position(4, 5))).isInstanceOf(Soldier.class);
            assertThat(pieces.get(new Position(4, 7))).isInstanceOf(Soldier.class);
            assertThat(pieces.get(new Position(4, 9))).isInstanceOf(Soldier.class);
        }

        @Test
        @DisplayName("한 진영의 차는 10행 1열과 10행 9열에 배치된다.")
        void checkHanChariot() {
            assertThat(pieces.get(new Position(10, 1))).isInstanceOf(Chariot.class);
            assertThat(pieces.get(new Position(10, 9))).isInstanceOf(Chariot.class);
        }

        @Test
        @DisplayName("한 진영의 사는 10행 4열과 10행 6열에, 장은 9행 5열에 배치된다.")
        void checkHanGuardAndGeneral() {
            assertThat(pieces.get(new Position(10, 4))).isInstanceOf(Guard.class);
            assertThat(pieces.get(new Position(10, 6))).isInstanceOf(Guard.class);
            assertThat(pieces.get(new Position(9, 5))).isInstanceOf(General.class);
        }

        @Test
        @DisplayName("한 진영의 포는 8행 2열과 8행 8열에 배치된다.")
        void checkHanCannon() {
            assertThat(pieces.get(new Position(8, 2))).isInstanceOf(Cannon.class);
            assertThat(pieces.get(new Position(8, 8))).isInstanceOf(Cannon.class);
        }

        @Test
        @DisplayName("한 진영의 졸은 7행 1, 3, 5, 7, 9열에 배치된다.")
        void checkHanSoldier() {
            assertThat(pieces.get(new Position(7, 1))).isInstanceOf(Soldier.class);
            assertThat(pieces.get(new Position(7, 3))).isInstanceOf(Soldier.class);
            assertThat(pieces.get(new Position(7, 5))).isInstanceOf(Soldier.class);
            assertThat(pieces.get(new Position(7, 7))).isInstanceOf(Soldier.class);
            assertThat(pieces.get(new Position(7, 9))).isInstanceOf(Soldier.class);
        }
    }

    @Nested
    @DisplayName("초 진영 상차림 변형 검증")
    class ChoFormationTest {

        @Test
        @DisplayName("귀마좌 진형에서 상은 2열과 7열에, 마는 3열과 8열에 배치된다.")
        void leftGwima() {
            Map<Position, Piece> pieces = BoardFactory.createFormation(1, 1);

            assertThat(pieces.get(new Position(1, 2))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(1, 3))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(1, 7))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(1, 8))).isInstanceOf(Horse.class);
        }

        @Test
        @DisplayName("귀마우 진형에서 마는 2열과 7열에, 상은 3열과 8열에 배치된다.")
        void rightGwima() {
            Map<Position, Piece> pieces = BoardFactory.createFormation(2, 1);

            assertThat(pieces.get(new Position(1, 2))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(1, 3))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(1, 7))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(1, 8))).isInstanceOf(Elephant.class);
        }

        @Test
        @DisplayName("원앙마 진형에서 상은 2열과 8열에, 마는 3열과 7열에 배치된다.")
        void wonangma() {
            Map<Position, Piece> pieces = BoardFactory.createFormation(3, 1);

            assertThat(pieces.get(new Position(1, 2))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(1, 3))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(1, 7))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(1, 8))).isInstanceOf(Elephant.class);
        }

        @Test
        @DisplayName("양귀마 진형에서 마는 2열과 8열에, 상은 3열과 7열에 배치된다.")
        void yanggwima() {
            Map<Position, Piece> pieces = BoardFactory.createFormation(4, 1);

            assertThat(pieces.get(new Position(1, 2))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(1, 3))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(1, 7))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(1, 8))).isInstanceOf(Horse.class);
        }
    }

    @Nested
    @DisplayName("한 진영 상차림 변형 검증")
    class HanFormationTest {

        @Test
        @DisplayName("귀마좌 진형에서 상은 8열과 3열에, 마는 7열과 2열에 배치된다.")
        void leftGwima() {
            Map<Position, Piece> pieces = BoardFactory.createFormation(1, 1);

            assertThat(pieces.get(new Position(10, 8))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(10, 7))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(10, 3))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(10, 2))).isInstanceOf(Horse.class);
        }

        @Test
        @DisplayName("귀마우 진형에서 마는 8열과 3열에, 상은 7열과 2열에 배치된다.")
        void rightGwima() {
            Map<Position, Piece> pieces = BoardFactory.createFormation(1, 2);

            assertThat(pieces.get(new Position(10, 8))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(10, 7))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(10, 3))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(10, 2))).isInstanceOf(Elephant.class);
        }

        @Test
        @DisplayName("원앙마 진형에서 상은 8열과 2열에, 마는 7열과 3열에 배치된다.")
        void wonangma() {
            Map<Position, Piece> pieces = BoardFactory.createFormation(1, 3);

            assertThat(pieces.get(new Position(10, 8))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(10, 7))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(10, 3))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(10, 2))).isInstanceOf(Elephant.class);
        }

        @Test
        @DisplayName("양귀마 진형에서 마는 8열과 2열에, 상은 7열과 3열에 배치된다.")
        void yanggwima() {
            Map<Position, Piece> pieces = BoardFactory.createFormation(1, 4);

            assertThat(pieces.get(new Position(10, 8))).isInstanceOf(Horse.class);
            assertThat(pieces.get(new Position(10, 7))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(10, 3))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(new Position(10, 2))).isInstanceOf(Horse.class);
        }
    }
}
