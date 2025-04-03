package domain.piece.rule;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JumpOverOnePieceRuleTest {

    @Test
    void 포가_다른_말을_하나_뛰어넘지_않으면_예외를_발생시킨다() {
        // given
        JumpOverOnePieceRule rule = new JumpOverOnePieceRule();
        List<Position> path = List.of(new Position(1, 1), new Position(3, 1));
        List<Piece> piecesInPath = new ArrayList<>();
        Piece piece = new Piece(Team.CHO, PieceType.PO, new Position(1, 1));
        // when & then
        Assertions.assertThatThrownBy(() -> rule.validate(path, piecesInPath, piece, Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 다른 말 하나를 뛰어넘어야 합니다.");
    }

    @Test
    void 포가_포를_건너뛰면_예외를_발생시킨다() {
        // given
        JumpOverOnePieceRule rule = new JumpOverOnePieceRule();
        List<Position> path = List.of(new Position(2, 1));
        List<Piece> piecesInPath = List.of(new Piece(Team.HAN, PieceType.PO, new Position(2, 1)));
        Piece piece = new Piece(Team.CHO, PieceType.PO, new Position(1, 1));
        // when & then
        Assertions.assertThatThrownBy(() -> rule.validate(path, piecesInPath, piece, Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포끼리 건너뛸 수 없습니다.");
    }

    @Test
    void 포가_포를_잡으면_예외를_발생시킨다() {
        // given
        JumpOverOnePieceRule rule = new JumpOverOnePieceRule();
        List<Position> path = List.of(new Position(2, 1));
        List<Piece> piecesInPath = List.of(new Piece(Team.HAN, PieceType.CHA, new Position(2, 1)));
        Piece piece = new Piece(Team.CHO, PieceType.PO, new Position(1, 1));
        Piece targetPiece = new Piece(Team.HAN, PieceType.PO, new Position(3, 1));
        // when & then
        Assertions.assertThatThrownBy(() -> rule.validate(path, piecesInPath, piece, Optional.of(targetPiece)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포끼리 잡을 수 없습니다");
    }

}