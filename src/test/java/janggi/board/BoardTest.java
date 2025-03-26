package janggi.board;

import janggi.piece.Chariot;
import janggi.piece.Piece;
import janggi.piece.PieceGenerator;
import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BoardTest {

    private static final PieceGenerator pieceGenerator = new PieceGenerator();

    @Test
    @DisplayName("보드에서 선택한 기물을 다른 기물로 이동 시킬 수 있는지 확인")
    void boardAttackTest() {
        //given
        Board board = new Board(pieceGenerator.generateInitialPieces(TableOption.EHHE, TableOption.HEEH));
        Position startPosition = new Position(10, 1);
        Position arrivedPosition = new Position(8, 1);
        //when
        board.attack(Team.CHO, startPosition, arrivedPosition);
        List<Piece> positionedPieces = board.getPositionedPieces();
        Piece findPiece = positionedPieces.stream()
                .filter(piece -> piece.matchesPosition(arrivedPosition))
                .findFirst()
                .orElseThrow();
        //then
        Assertions.assertThat(findPiece).isEqualTo(new Chariot(Team.CHO,new Position(8, 1)));
    }

    @Test
    @DisplayName("현재 턴에 맞지 않은 기물 선택 시 예외 발생")
    void checkTurnTest() {
        //given
        Board board = new Board(pieceGenerator.generateInitialPieces(TableOption.EHHE, TableOption.HEEH));
        Position startPosition = new Position(10, 1);
        Position arrivedPosition = new Position(8, 1);
        //when & then
        Assertions.assertThatThrownBy(() -> board.attack(Team.HAN, startPosition, arrivedPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기물이 없는 위치를 선택할 경우 예외 발생")
    void notExistsPieceTest() {
        //given
        Board board = new Board(pieceGenerator.generateInitialPieces(TableOption.EHHE, TableOption.HEEH));
        Position startPosition = new Position(9, 1);
        Position arrivedPosition = new Position(8,1);
        //when & then
        Assertions.assertThatThrownBy(() -> board.attack(Team.CHO, startPosition, arrivedPosition));
    }
}
