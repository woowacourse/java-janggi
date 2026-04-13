package model.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import model.Team;
import model.coordinate.Position;
import model.piece.General;
import model.piece.Piece;
import model.testdouble.FakePiece;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Position source = new Position(5, 0);
    private Position destination = new Position(5, 4);

    @Test
    void 해당_위치에_기물이_있으면_반환한다() {
        // given
        FakePiece piece = FakePiece.createFake(Team.CHO);
        Board board = new Board(Map.of(source, piece));

        // when
        Piece pick = board.pickPiece(source);

        // then
        assertThat(pick).isEqualTo(piece);
    }

    @Test
    void 해당_위치에_기물이_없으면_예외가_발생한다() {
        // given
        Board board = new Board(Map.of());

        // when & then
        assertThatThrownBy(() -> board.pickPiece(source))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 보드에서_도착_위치에_기물이_없으면_이동한다() {
        // given
        FakePiece piece = FakePiece.createFake(Team.CHO);

        Board board = new Board(Map.of(source, piece));

        // when
        board.move(source, destination);

        // then
        assertSuccessMoved(board, source, piece, destination);
    }

    @Test
    void 보드에서_도착_위치에_아군이_있으면_이동할_수_없다() {
        // given
        FakePiece piece = FakePiece.createFake(Team.CHO);
        FakePiece otherPiece = FakePiece.createFake(Team.CHO);
        Board board = new Board(Map.of(source, piece, destination, otherPiece));

        // when & then
        assertThatThrownBy(() -> board.move(source, destination));
    }

    @Test
    void 보드에서_도착_위치에_적군이_있으면_이동한다() {
        // given
        FakePiece piece = FakePiece.createFake(Team.CHO);
        FakePiece enemy = FakePiece.createFake(Team.HAN);

        Board board = new Board(Map.of(source, piece, destination, enemy));

        // when
        board.move(source, destination);

        // then
        assertSuccessMoved(board, source, piece, destination);
    }

    @Test
    void 여러_기물을_한번에_배치한다() {
        // given
        Board board = new Board(Map.of());
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(0, 1);
        FakePiece piece1 = FakePiece.createFake(Team.CHO);
        FakePiece piece2 = FakePiece.createFake(Team.HAN);

        // when
        board.arrangePieces(Map.of(pos1, piece1, pos2, piece2));

        // then
        assertThat(board.pickPiece(pos1)).isEqualTo(piece1);
        assertThat(board.pickPiece(pos2)).isEqualTo(piece2);
    }

    @Test
    void 경로상에_존재하는_기물들을_추출한다() {
        // given
        Position path1 = new Position(5, 1);
        Position path2 = new Position(5, 2);    // 경로에 기물이 없는 경우
        Position path3 = new Position(5, 3);

        FakePiece hurdle1 = FakePiece.createFake(Team.HAN);
        FakePiece hurdle2 = FakePiece.createFake(Team.CHO);

        Board board = new Board(Map.of(path1, hurdle1, path3, hurdle2));
        List<Position> path = List.of(path1, path2, path3);

        // when
        List<Piece> extracted = board.extractPiecesByPath(path);

        // then
        assertThat(extracted).hasSize(2)
                .containsExactly(hurdle1, hurdle2);
    }

    @Test
    void 팀의_왕이_살아있는지_알_수_있다() {
        // given
        Position generalPos = new Position(1, 4);
        Board board = new Board(Map.of(generalPos, new General(Team.HAN)));

        // when
        boolean alive = board.isAliveGeneral(Team.HAN);

        // then
        assertThat(alive).isTrue();
    }

    @Test
    void 해당_팀의_왕이_죽었는지_알_수_잇다() {
        // given
        Board board = new Board(Map.of());

        // when
        boolean alive = board.isAliveGeneral(Team.HAN);

        // then
        assertThat(alive).isFalse();
    }

    @Test
    void 팀의_왕의_위치를_찾을_수_있다() {
        // given
        Position generalPos = new Position(1, 4);
        General general = new General(Team.HAN);
        Board board = new Board(Map.of(generalPos, general));

        // when
        Position foundPosition = board.findGeneralPositionByTeam(Team.HAN);

        // then
        assertThat(foundPosition).isEqualTo(generalPos);
    }

    @Test
    void 팀의_왕이_없으면_위치를_찾을_때_예외가_발생한다() {
        // given
        Board board = new Board(Map.of());

        // when & then
        assertThatThrownBy(() -> board.findGeneralPositionByTeam(Team.HAN))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 특정_팀의_기물_점수_합계를_계산한다() {
        // given
        Board board = BoardFactory.generateDefaultPieces();
        Map<Position, Piece> choPieces = FormationType.SANG_MA_MA_SANG.generateByTeam(Team.CHO);
        board.arrangePieces(choPieces);

        // when
        double score = board.calculateBaseScore(Team.CHO);

        // then: 기본 총 점수는 72점이다
        assertThat(score).isEqualTo(72);
    }

    private void assertSuccessMoved(Board board, Position source, FakePiece piece, Position destination) {
        assertThat(board.pickPiece(destination)).isEqualTo(piece);
        assertThat(board.board()).doesNotContainKey(source);
    }
}