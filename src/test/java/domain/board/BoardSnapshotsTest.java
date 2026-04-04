package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Position;
import domain.country.CountryType;
import domain.piece.Piece;
import domain.piece.PieceInfo;
import domain.piece.PieceType;
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

        stubBoardStates.put(choRight, new Piece(new PieceInfo(PieceType.CHARIOT, CountryType.CHO)));
        stubBoardStates.put(hanRight, new Piece(new PieceInfo(PieceType.CHARIOT, CountryType.HAN)));

        Board board = new Board(stubBoardStates.create(), 72, 73.5);

        // A 포지션
        board.checkEndAndPlay(choRight, choLeft);
        boardSnapshots.addBoardSnapshot(new BoardSnapshot(board.getPieceInfos(), CountryType.CHO));
        // B 포지션
        board.checkEndAndPlay(hanRight, hanLeft);
        boardSnapshots.addBoardSnapshot(new BoardSnapshot(board.getPieceInfos(), CountryType.HAN));
        // C 포지션
        board.checkEndAndPlay(choLeft, choRight);
        boardSnapshots.addBoardSnapshot(new BoardSnapshot(board.getPieceInfos(), CountryType.CHO));
        // D 포지션
        board.checkEndAndPlay(hanLeft, hanRight);
        boardSnapshots.addBoardSnapshot(new BoardSnapshot(board.getPieceInfos(), CountryType.HAN));

        // A 포지션 2
        board.checkEndAndPlay(choRight, choLeft);
        boardSnapshots.addBoardSnapshot(new BoardSnapshot(board.getPieceInfos(), CountryType.CHO));
        // B 포지션 2
        board.checkEndAndPlay(hanRight, hanLeft);
        boardSnapshots.addBoardSnapshot(new BoardSnapshot(board.getPieceInfos(), CountryType.HAN));
        // C 포지션 2
        board.checkEndAndPlay(choLeft, choRight);
        boardSnapshots.addBoardSnapshot(new BoardSnapshot(board.getPieceInfos(), CountryType.CHO));
        // D 포지션 2
        board.checkEndAndPlay(hanLeft, hanRight);
        boardSnapshots.addBoardSnapshot(new BoardSnapshot(board.getPieceInfos(), CountryType.HAN));
        assertThat(boardSnapshots.appearSamePositionThreeTurn()).isFalse();

        // A 포지션 3
        board.checkEndAndPlay(choRight, choLeft);
        boardSnapshots.addBoardSnapshot(new BoardSnapshot(board.getPieceInfos(), CountryType.CHO));
        assertThat(boardSnapshots.appearSamePositionThreeTurn()).isTrue();
    }
}
