package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JanggiBoardTest {

    @Test
    void 장기말은_이동시_목표_좌표로_위치가_바뀐다() {
        Piece pawn = new Piece(Team.HAN, PieceType.PAWN, new Position(4, 1));
        // given
        List<Piece> beforeBoard = new ArrayList<>();
        beforeBoard.add(pawn);
        List<Piece> afterBoard = new ArrayList<>();
        Piece pawn2 = new Piece(Team.HAN, PieceType.PAWN, new Position(5, 1));
        afterBoard.add(pawn2);

        JanggiBoard janggiBoard = JanggiBoard.create(beforeBoard);

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
        Piece choCha = new Piece(Team.CHO, PieceType.CHA, new Position(4, 1));
        Piece hanCha = new Piece(Team.HAN, PieceType.CHA, new Position(8, 1));

        List<Piece> beforeBoard = new ArrayList<>();
        beforeBoard.add(choCha);
        beforeBoard.add(hanCha);

        List<Piece> afterBoard = new ArrayList<>();
        Piece choCha2 = new Piece(Team.CHO, PieceType.CHA, new Position(8, 1));
        afterBoard.add(choCha2);

        JanggiBoard janggiBoard = JanggiBoard.create(beforeBoard);

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
        Piece choCha1 = new Piece(Team.CHO, PieceType.CHA, new Position(4, 1));
        Piece choCha2 = new Piece(Team.CHO, PieceType.CHA, new Position(8, 1));

        List<Piece> beforeBoard = new ArrayList<>();
        beforeBoard.add(choCha1);
        beforeBoard.add(choCha2);

        JanggiBoard janggiBoard = JanggiBoard.create(beforeBoard);
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
        Piece choPo = new Piece(Team.CHO, PieceType.PO, new Position(4, 1));

        List<Piece> beforeBoard = new ArrayList<>();
        beforeBoard.add(choPo);

        JanggiBoard janggiBoard = JanggiBoard.create(beforeBoard);

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
        Piece choCha = new Piece(Team.CHO, PieceType.CHA, new Position(1, 1));
        Piece choPawn = new Piece(Team.CHO, PieceType.CHA, new Position(4, 1));

        List<Piece> beforeBoard = new ArrayList<>();
        beforeBoard.add(choCha);
        beforeBoard.add(choPawn);

        JanggiBoard janggiBoard = JanggiBoard.create(beforeBoard);

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
        Piece choPo1 = new Piece(Team.CHO, PieceType.PO, new Position(8, 2));
        Piece choPo2 = new Piece(Team.CHO, PieceType.PO, new Position(8, 8));

        List<Piece> beforeBoard = new ArrayList<>();
        beforeBoard.add(choPo1);
        beforeBoard.add(choPo2);

        JanggiBoard janggiBoard = JanggiBoard.create(beforeBoard);

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
        Piece choPo1 = new Piece(Team.CHO, PieceType.PO, new Position(8, 2));
        Piece choPo2 = new Piece(Team.HAN, PieceType.PO, new Position(8, 8));
        Piece choGung = new Piece(Team.HAN, PieceType.GUNG, new Position(8, 5));

        List<Piece> beforeBoard = new ArrayList<>();
        beforeBoard.add(choPo1);
        beforeBoard.add(choGung);
        beforeBoard.add(choPo2);

        JanggiBoard janggiBoard = JanggiBoard.create(beforeBoard);

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
        Piece choPo = new Piece(Team.CHO, PieceType.PO, new Position(8, 2));
        Piece choGung = new Piece(Team.CHO, PieceType.GUNG, new Position(8, 5));

        List<Piece> beforeBoard = new ArrayList<>();

        List<Piece> afterBoard = new ArrayList<>();
        Piece choPo2 = new Piece(Team.CHO, PieceType.PO, new Position(8, 8));
        beforeBoard.add(choPo);
        beforeBoard.add(choGung);
        afterBoard.add(choPo2);
        afterBoard.add(choGung);

        JanggiBoard janggiBoard = JanggiBoard.create(beforeBoard);

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
        List<Piece> board = new ArrayList<>();
        board.add(new Piece(gungTeam, PieceType.GUNG, new Position(1, 1)));
        JanggiBoard janggiBoard = JanggiBoard.create(board);

        // when
        boolean actual = janggiBoard.existGung(team);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 시작_위치에_기물이_존재하지_않는_경우_예외를_발생시킨다() {
        List<Piece> board = new ArrayList<>();
        JanggiBoard janggiBoard = JanggiBoard.create(board);

        assertThatThrownBy(() -> janggiBoard.move(new Position(1, 1), new Position(1, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물이 존재하지 않는 위치입니다.");
    }

    @Test
    void 시작_위치에_있는_기물을_찾는다() {
        List<Piece> board = new ArrayList<>();
        Piece pawn = new Piece(Team.CHO, PieceType.PAWN, new Position(1, 1));
        board.add(pawn);
        JanggiBoard janggiBoard = JanggiBoard.create(board);

        Piece selectedPiece = janggiBoard.findSelectedPiece(new Position(1, 1));

        assertThat(selectedPiece).isEqualTo(pawn);
    }

    @Test
    void 시작_위치가_기물이_없는_위치라면_예외를_발생시킨다() {
        List<Piece> board = new ArrayList<>();
        JanggiBoard janggiBoard = JanggiBoard.create(board);
        assertThatThrownBy(() -> janggiBoard.findSelectedPiece(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물이 존재하지 않는 위치입니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "CHO, 10",
            "HAN, 5"
    })
    void 특정팀의_기물의_점수합을_계산할_수_있다(Team team, int score) {
        List<Piece> board = new ArrayList<>();
        board.add(new Piece(Team.CHO, PieceType.PAWN, new Position(1, 1)));
        board.add(new Piece(Team.CHO, PieceType.MA, new Position(1, 2)));
        board.add(new Piece(Team.CHO, PieceType.SANG, new Position(1, 3)));
        board.add(new Piece(Team.HAN, PieceType.PAWN, new Position(2, 1)));
        board.add(new Piece(Team.HAN, PieceType.GUNG, new Position(2, 2)));
        board.add(new Piece(Team.HAN, PieceType.SANG, new Position(2, 3)));

        JanggiBoard janggiBoard = JanggiBoard.create(board);
        int scoreSum = janggiBoard.calculateTeamScore(team);

        assertThat(scoreSum).isEqualTo(score);
    }
}
