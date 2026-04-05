package janggi.domain.turn;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.board.Board;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChoTurnTest {

    @Test
    @DisplayName("초나라 턴일때 한나라 기물 선택할 수 없다")
    void 초나라_턴일시_한나라_기물_선택_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position choZolPosition = new Position(1, 7);
        Position hanZolPosition = new Position(1, 4);
        Piece choZol = new Piece(Team.CHO, PieceType.ZOL);
        Piece hanZol = new Piece(Team.HAN, PieceType.ZOL);
        customBoard.put(choZolPosition, choZol);
        customBoard.put(hanZolPosition, hanZol);
        Board board = new Board(customBoard);

        Turn choTurn = new ChoTurn();

        //when & then
        assertThatThrownBy(() -> choTurn.validateTurn(board.getPiece(hanZolPosition)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상대팀 기물을 선택할 수 없습니다.");
    }

    @Test
    @DisplayName("기물이 없는 좌표를 입력할 수 없다.")
    void 기물이_없는_좌표_입력_시_오류() {
        //given
        Board emptyBoard = new Board(new HashMap<>());
        Turn choTurn = new ChoTurn();

        Position emptySource = new Position(1, 6);
        Position target = new Position(1, 4);
        //when && then
        assertThatThrownBy(() -> choTurn.move(emptySource, target, emptyBoard))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 빈 칸을 선택할 수 없습니다.");
    }
}
