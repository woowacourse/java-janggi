package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class CannonTest {

    @DisplayName("포의 팀을 확인한다.")
    @ParameterizedTest
    @CsvSource({"HAN", "CHO"})
    void 포의_팀을_확인한다(Team team) {
        // given
        Cannon cannon = new Cannon(team);

        // when & then
        assertThat(cannon.getTeam()).isEqualTo(team);
    }

    @DisplayName("입력받은 팀과 같은 팀인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHO, true"})
    void 입력받은_팀과_같은_팀인지_확인한다(Team team, boolean expected) {
        // given
        Cannon cannon = new Cannon(team);

        // when
        boolean result = cannon.isSameTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }


    @DisplayName("포의 타입은 CANNON이다.")
    @Test
    void 포의_타입은_CANNON이다() {
        // given
        Cannon cannon = new Cannon(Team.HAN);

        // when
        boolean result = cannon.isSameType(PieceType.CANNON);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("한 방향으로만 이동시키면 경로를 반환한다.")
    @Test
    void 한_방향으로만_이동시키면_경로를_반환한다() {
        // given
        Cannon cannon = new Cannon(Team.HAN);
        Movement movement = new Movement(Position.from("22"), Position.from("26"));

        //when
        Path path = cannon.getPath(movement);

        //then
        assertThat(path).containsExactly(
                Position.from("23"),
                Position.from("24"),
                Position.from("25"));
    }

    @DisplayName("궁성에서 대각선으로 이동하면 경로를 반환한다.")
    @ParameterizedTest(name = "from={0}, to={1}, 경로좌표={2}")
    @CsvSource({
            "14, 36, 25",
            "04, 86, 95"})
    void 궁성에서_대각선으로_이동하면_경로를_반환한다(String from, String to, String expectedPosition) {
        // given
        Cannon cannon = new Cannon(Team.HAN);
        Movement movement = new Movement(Position.from(from), Position.from(to));

        // when
        Path path = cannon.getPath(movement);

        // then
        assertThat(path).containsExactly(Position.from(expectedPosition));
    }

    @DisplayName("궁성 이동이 아닐 때 직선이 아닌 방향으로 이동시키면 예외가 발생한다.")
    @Test
    void 궁성_이동이_아닐_때_직선이_아닌_방향으로_이동시키면_예외가_발생한다() {
        // given
        Cannon cannon = new Cannon(Team.HAN);
        Movement movement = new Movement(Position.from("22"), Position.from("44"));

        // when & then
        assertThatThrownBy(() -> cannon.getPath(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포은(는) 해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("이동이 가능하면 예외가 발생하지 않는다.")
    @Test
    void 이동_가능하면_예외가_발생하지_않는다() {
        // given
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new Soldier(Team.HAN));

        // when & then
        assertThatNoException().isThrownBy(
                () -> cannon.validateCanMove(pieceOnPath, new Chariot(Team.CHO)));
    }

    @DisplayName("경로에 존재하는 기물 중 빈 기물이 아닌 기물이 2개 이상이면 예외가 발생한다.")
    @Test
    void 경로에_존재하는_기물_중_빈_기물이_아닌_기물이_2개_이상이면_예외가_발생한다() {
        //given
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new Soldier(Team.HAN));
        pieceOnPath.add(new Elephant(Team.HAN));

        // when & then
        assertThatThrownBy(() -> cannon.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
    }

    @DisplayName("경로에 존재하는 기물이 모두 빈 기물이면 예외가 발생한다.")
    @Test
    void 경로에_존재하는_기물이_모두_빈_기물이면_예외가_발생한다() {
        // given
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new EmptyPiece());

        // when & then
        assertThatThrownBy(() -> cannon.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
    }

    @DisplayName("오직 한 칸만 이동하면 예외가 발생한다.")
    @Test
    void 오직_한_칸만_이동하면_예외가_발생한다() {
        // given
        Cannon cannon = new Cannon(Team.HAN);

        // when & then
        assertThatThrownBy(() -> cannon.validateCanMove(new PieceOnPath(), new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
    }

    @DisplayName("경로에 존재하는 기물이 포면 예외가 발생한다.")
    @Test
    void 경로에_존재하는_기물이_포면_예외가_발생한다() {
        // given
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new EmptyPiece());
        pieceOnPath.add(new Cannon(Team.HAN));

        // when & then
        assertThatThrownBy(() -> cannon.validateCanMove(pieceOnPath, new EmptyPiece()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
    }

    @DisplayName("이동할 위치에 같은 팀이 있으면 예외가 발생한다.")
    @Test
    void 이동할_위치에_같은_팀이_있으면_예외가_발생한다() {
        // given
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new Guard(Team.HAN));

        // when & then
        assertThatThrownBy(() -> cannon.validateCanMove(pieceOnPath, new Chariot(Team.HAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 기물로 이동할 수 없습니다.");
    }

    @DisplayName("이동할 위치에 적의 포가 있으면 예외가 발생한다.")
    @Test
    void 이동할_위치에_적의_포가_있으면_예외가_발생한다() {
        // given
        Cannon cannon = new Cannon(Team.HAN);
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(new Guard(Team.HAN));

        // when & then
        assertThatThrownBy(() -> cannon.validateCanMove(pieceOnPath, new Cannon(Team.CHO)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 잡을 수 없습니다.");
    }
}
