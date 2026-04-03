package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.country.CountryType;
import domain.piece.Chariot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardSnapshotsTest {
    @Test
    @DisplayName("게임 중 같은 포지션-같은 진영 차례가 3번 이상 나타났는지 정확히 확인한다.")
    void samePositionThreeTurnInAllTheGame() {
        StubBoardStates stubBoardStates = new StubBoardStates();
        BoardSnapshots boardSnapshots = new BoardSnapshots();

        Position choRight = new Position(4, 1);
        Position choLeft = new Position(3, 1);
        Position hanRight = new Position(4, 2);
        Position hanLeft = new Position(3, 2);

        stubBoardStates.put(choRight, new Chariot(CountryType.CHO));
        stubBoardStates.put(hanRight, new Chariot(CountryType.HAN));

        Board board = new Board(stubBoardStates.create());

        // A 포지션
        board.checkEndAndPlay(choRight, choLeft);
        assertThat(boardSnapshots.appearSamePositionThreeTurn(board.getBoardSnapshot(CountryType.CHO))).isFalse();
        // B 포지션
        board.checkEndAndPlay(hanRight, hanLeft);
        assertThat(boardSnapshots.appearSamePositionThreeTurn(board.getBoardSnapshot(CountryType.HAN))).isFalse();
        // C 포지션
        board.checkEndAndPlay(choLeft, choRight);
        assertThat(boardSnapshots.appearSamePositionThreeTurn(board.getBoardSnapshot(CountryType.CHO))).isFalse();
        // D 포지션
        board.checkEndAndPlay(hanLeft, hanRight);
        assertThat(boardSnapshots.appearSamePositionThreeTurn(board.getBoardSnapshot(CountryType.HAN))).isFalse();

        // A 포지션 2
        board.checkEndAndPlay(choRight, choLeft);
        assertThat(boardSnapshots.appearSamePositionThreeTurn(board.getBoardSnapshot(CountryType.CHO))).isFalse();
        // B 포지션 2
        board.checkEndAndPlay(hanRight, hanLeft);
        assertThat(boardSnapshots.appearSamePositionThreeTurn(board.getBoardSnapshot(CountryType.HAN))).isFalse();
        // C 포지션 2
        board.checkEndAndPlay(choLeft, choRight);
        assertThat(boardSnapshots.appearSamePositionThreeTurn(board.getBoardSnapshot(CountryType.CHO))).isFalse();
        // D 포지션 2
        board.checkEndAndPlay(hanLeft, hanRight);
        assertThat(boardSnapshots.appearSamePositionThreeTurn(board.getBoardSnapshot(CountryType.HAN))).isFalse();

        // A 포지션 3
        board.checkEndAndPlay(choRight, choLeft);
        assertThat(boardSnapshots.appearSamePositionThreeTurn(board.getBoardSnapshot(CountryType.CHO))).isTrue();
    }
}
