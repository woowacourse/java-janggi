package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Cha;
import domain.piece.Gung;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Po;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JanggiBoardTest {

    @Test
    void 장기말은_이동시_목표_좌표로_위치가_바뀐다() {
        Pawn pawn = new Pawn(Team.HAN);
        // given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), pawn);
        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(5, 1), pawn);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(5, 1);

        // when
        janggiBoard.move(startPosition, targetPosition);

        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }

    @Test
    void 최종_좌표에_상대_말이_있으면_상대말을_없애고_해당_위치로_이동한다() {
        //given
        Cha choCha = new Cha(Team.CHO);
        Cha hanCha = new Cha(Team.HAN);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), choCha);
        beforeBoard.put(new Position(8, 1), hanCha);

        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(8, 1), choCha);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(8, 1);

        // when
        janggiBoard.move(startPosition, targetPosition);

        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }

    @Test
    void 최종_좌표에_아군_말이_있으면_위치로_이동하지_못한다() {
        //given
        Cha choCha1 = new Cha(Team.CHO);
        Cha choCha2 = new Cha(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), choCha1);
        beforeBoard.put(new Position(8, 1), choCha2);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(8, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치는 아군의 말이 있으므로 이동 불가능 합니다.");
    }

    @Test
    void 포가_건너뛸_말이_없으면_예외를_발생시킨다() {
        //given
        Po choPo = new Po(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), choPo);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(8, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 다른 말 하나를 뛰어넘어야 합니다.");
    }

    @Test
    void 장기말_이동중_다른_장기말을_만나면_예외를_발생한다() {
        //given
        Cha choCha = new Cha(Team.CHO);
        Pawn choPawn = new Pawn(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(1, 1), choCha);
        beforeBoard.put(new Position(4, 1), choPawn);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(1, 1);
        Position targetPosition = new Position(8, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다른 말이 존재해서 해당 좌표로 갈 수가 없습니다.");
    }

    @Test
    void 뛰어넘을_장기말이_포라면_예외를_발생한다() {
        //given
        Po choPo1 = new Po(Team.CHO);
        Po choPo2 = new Po(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(8, 2), choPo1);
        beforeBoard.put(new Position(8, 8), choPo2);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(8, 2);
        Position targetPosition = new Position(8, 9);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포끼리 건너뛸 수 없습니다.");
    }

    @Test
    void 목표_좌표의_장기말이_포라면_예외를_발생한다() {
        //given
        Po choPo1 = new Po(Team.CHO);
        Po choPo2 = new Po(Team.HAN);
        Gung choGung = new Gung(Team.HAN);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(8, 2), choPo1);
        beforeBoard.put(new Position(8, 5), choGung);
        beforeBoard.put(new Position(8, 8), choPo2);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(8, 2);
        Position targetPosition = new Position(8, 8);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포끼리 잡을 수 없습니다");
    }

    @Test
    void 포는_장기말을_뛰어넘어_이동한다() {
        //given
        Po choPo = new Po(Team.CHO);
        Gung choGung = new Gung(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        Map<Position, Piece> afterBoard = new HashMap<>();

        beforeBoard.put(new Position(8, 2), choPo);
        beforeBoard.put(new Position(8, 5), choGung);
        afterBoard.put(new Position(8, 8), choPo);
        afterBoard.put(new Position(8, 5), choGung);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(8, 2);
        Position targetPosition = new Position(8, 8);

        // when & then
        janggiBoard.move(startPosition, targetPosition);
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }

    @ParameterizedTest
    @CsvSource({
            "HAN, HAN, true",
            "CHO, CHO, true",
            "CHO, HAN, false",
            "HAN, CHO, false",
    })
    void 특정_팀의_궁이_생존했는지_알_수_있다(Team gungTeam, Team team, boolean expected) {
        //given
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 1), new Gung(gungTeam));
        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(board);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        // when
        boolean actual = janggiBoard.existGung(team);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 시작_위치에_기물이_존재하지_않는_경우_예외를_발생시킨다() {
        Map<Position, Piece> board = new HashMap<>();
        JanggiBoard janggiBoard = new JanggiBoard(new FakeBoardGenerator(board));

        assertThatThrownBy(() -> janggiBoard.move(new Position(1, 1), new Position(1, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물이 존재하지 않는 위치입니다.");
    }

    @Test
    void 시작_위치에_있는_기물을_찾는다() {
        Map<Position, Piece> board = new HashMap<>();
        Pawn pawn = new Pawn(Team.CHO);
        board.put(new Position(1, 1), pawn);
        JanggiBoard janggiBoard = new JanggiBoard(new FakeBoardGenerator(board));

        Piece selectedPiece = janggiBoard.findSelectedPiece(new Position(1, 1));

        assertThat(selectedPiece).isEqualTo(pawn);
    }

    @Test
    void 시작_위치가_기물이_없는_위치라면_예외를_발생시킨다() {
        Map<Position, Piece> board = new HashMap<>();
        JanggiBoard janggiBoard = new JanggiBoard(new FakeBoardGenerator(board));

        assertThatThrownBy(() -> janggiBoard.findSelectedPiece(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물이 존재하지 않는 위치입니다.");
    }
}
