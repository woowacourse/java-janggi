package janggi.domain.turn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.board.Board;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HanTurnTest {

    @Test
    @DisplayName("한나라 턴일때 초나라 기물 선택할 수 없다")
    void 한나라_턴일시_초나라_기물_선택_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position choZolPosition = new Position(1, 7);
        Position hanZolPosition = new Position(1, 4);
        Piece choZol = new Piece(Team.CHO, PieceType.ZOL);
        Piece hanZol = new Piece(Team.HAN, PieceType.ZOL);
        customBoard.put(choZolPosition, choZol);
        customBoard.put(hanZolPosition, hanZol);
        Board board = new Board(customBoard);

        Turn hanTurn = new HanTurn();

        //when & then
        assertThatThrownBy(() -> hanTurn.validateTurn(board.getPiece(choZolPosition)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상대팀 기물을 선택할 수 없습니다.");
    }

    @Test
    @DisplayName("기물이 없는 좌표를 입력할 수 없다.")
    void 기물이_없는_좌표_입력_시_오류() {
        //given
        Board emptyBoard = new Board(new HashMap<>());
        Turn hanTurn = new HanTurn();

        Position emptySource = new Position(1, 6);
        Position target = new Position(1, 4);

        //when && then
        assertThatThrownBy(() -> hanTurn.move(emptySource, target, emptyBoard))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 빈 칸을 선택할 수 없습니다.");
    }

    @Test
    @DisplayName("한나라 턴에서 초나라 왕을 잡았을 시 GameOverTurn반환 후 게임 종료")
    void 한나라_턴에서_초나라_왕_잡을_시_게임_종료() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        Position choKingPosition = new Position(5, 10);
        Position hanChaPosition = new Position(5, 9);
        state.put(choKingPosition, new Piece(Team.CHO, PieceType.KING));
        state.put(hanChaPosition, new Piece(Team.HAN, PieceType.CHA));
        Board board = new Board(state);
        Turn choTurn = new HanTurn();

        //when
        Turn nextTurn = choTurn.move(hanChaPosition, choKingPosition, board);

        //then
        assertAll(
                () -> assertThat(nextTurn).isInstanceOf(GameOverTurn.class),
                () -> assertThat(nextTurn.isFinished()).isTrue(),
                () -> assertThat(nextTurn.getTeam()).isEqualTo(Team.HAN)
        );
    }
}
