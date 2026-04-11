package team.janggi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import team.janggi.domain.board.Board;
import team.janggi.domain.board.BoardPieces;
import team.janggi.domain.board.BoardStateReader;
import team.janggi.domain.board.NormalBoardPiecesInitializer;
import team.janggi.domain.board.NormalSetup;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;

public class BoardTest {

    @Test
    void 기본_장기_보드_생성_테스트() {
        var boardStruct = new NormalBoardPiecesInitializer(NormalSetup.바깥상차림, NormalSetup.바깥상차림);

        Board board = new Board(boardStruct);

        Assertions.assertEquals(90, board.getStateReader().size());
    }

    @Test
    void 기본_장기_배치_테스트() {
        var boardStruct = new NormalBoardPiecesInitializer(NormalSetup.바깥상차림, NormalSetup.바깥상차림);

        Board board = new Board(boardStruct);

        BoardStateReader pieceMap = board.getStateReader();
        // 초나라(CHO)가 아래쪽에 잘 배치되었는지 확인
        검증_초나라_기본_배치(pieceMap);
        // 한나라(HAN)가 위쪽에 잘 배치되었는지 확인
        검증_한나라_기본_배치(pieceMap);
    }

    private void 검증_초나라_기본_배치(BoardStateReader pieceMap) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(Piece.of(PieceType.CHARIOT, Team.CHO), pieceMap.getPiece(new Position(0, 9))),
                () -> Assertions.assertEquals(Piece.of(PieceType.CHARIOT, Team.CHO), pieceMap.getPiece(new Position(8, 9))),
                () -> Assertions.assertEquals(Piece.of(PieceType.CANNON, Team.CHO), pieceMap.getPiece(new Position(1, 7))),
                () -> Assertions.assertEquals(Piece.of(PieceType.CANNON, Team.CHO), pieceMap.getPiece(new Position(7, 7))),
                () -> Assertions.assertEquals(Piece.of(PieceType.SOLDIER, Team.CHO), pieceMap.getPiece(new Position(0, 6))),
                () -> Assertions.assertEquals(Piece.of(PieceType.SOLDIER, Team.CHO), pieceMap.getPiece(new Position(2, 6))),
                () -> Assertions.assertEquals(Piece.of(PieceType.SOLDIER, Team.CHO), pieceMap.getPiece(new Position(4, 6))),
                () -> Assertions.assertEquals(Piece.of(PieceType.SOLDIER, Team.CHO), pieceMap.getPiece(new Position(6, 6))),
                () -> Assertions.assertEquals(Piece.of(PieceType.SOLDIER, Team.CHO), pieceMap.getPiece(new Position(8, 6))),
                () -> Assertions.assertEquals(Piece.of(PieceType.KING, Team.CHO), pieceMap.getPiece(new Position(4, 8))),
                () -> Assertions.assertEquals(Piece.of(PieceType.GUARD, Team.CHO), pieceMap.getPiece(new Position(3, 9))),
                () -> Assertions.assertEquals(Piece.of(PieceType.GUARD, Team.CHO), pieceMap.getPiece(new Position(5, 9)))
        );
    }

    private void 검증_한나라_기본_배치(BoardStateReader pieceMap) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(Piece.of(PieceType.CHARIOT, Team.HAN), pieceMap.getPiece(new Position(0, 0))),
                () -> Assertions.assertEquals(Piece.of(PieceType.CHARIOT, Team.HAN), pieceMap.getPiece(new Position(8, 0))),
                () -> Assertions.assertEquals(Piece.of(PieceType.CANNON, Team.HAN), pieceMap.getPiece(new Position(1, 2))),
                () -> Assertions.assertEquals(Piece.of(PieceType.CANNON, Team.HAN), pieceMap.getPiece(new Position(7, 2))),
                () -> Assertions.assertEquals(Piece.of(PieceType.SOLDIER, Team.HAN), pieceMap.getPiece(new Position(0, 3))),
                () -> Assertions.assertEquals(Piece.of(PieceType.SOLDIER, Team.HAN), pieceMap.getPiece(new Position(2, 3))),
                () -> Assertions.assertEquals(Piece.of(PieceType.SOLDIER, Team.HAN), pieceMap.getPiece(new Position(4, 3))),
                () -> Assertions.assertEquals(Piece.of(PieceType.SOLDIER, Team.HAN), pieceMap.getPiece(new Position(6, 3))),
                () -> Assertions.assertEquals(Piece.of(PieceType.SOLDIER, Team.HAN), pieceMap.getPiece(new Position(8, 3))),
                () -> Assertions.assertEquals(Piece.of(PieceType.KING, Team.HAN), pieceMap.getPiece(new Position(4, 1))),
                () -> Assertions.assertEquals(Piece.of(PieceType.GUARD, Team.HAN), pieceMap.getPiece(new Position(3, 0))),
                () -> Assertions.assertEquals(Piece.of(PieceType.GUARD, Team.HAN), pieceMap.getPiece(new Position(5, 0)))
        );
    }

    @Test void 초_바깥_한_바깥() {
        runTest(NormalSetup.바깥상차림, NormalSetup.바깥상차림);
    }

    @Test void 초_바깥_한_안() {
        runTest(NormalSetup.바깥상차림, NormalSetup.안상차림);
    }

    @Test void 초_안_한_왼() {
        runTest(NormalSetup.안상차림, NormalSetup.왼상차림);
    }

    @Test void 초_왼_한_오른() {
        runTest(NormalSetup.왼상차림, NormalSetup.오른상차림);
    }

    private void runTest(NormalSetup choSetup, NormalSetup hanSetup) {
        var boardStruct = new NormalBoardPiecesInitializer(choSetup, hanSetup);

        Board board = new Board(boardStruct);

        BoardStateReader pieceMap = board.getStateReader();

        // 고정 기물 검증
        검증_초나라_기본_배치(pieceMap);
        검증_한나라_기본_배치(pieceMap);

        // 상/마 차림 검증 (초는 ㅛ9번 , 한은 0번 행)
        assertPieceSetup(pieceMap, 9, Team.CHO, choSetup);
        assertPieceSetup(pieceMap, 0, Team.HAN, hanSetup);
    }

    private void assertPieceSetup(BoardStateReader pieceMap, int y, Team team, NormalSetup setup) {
        switch (setup) {
            case 왼상차림 -> Assertions.assertAll(
                    () -> assertPiece(pieceMap, y, team, 1, Piece.of(PieceType.ELEPHANT, team)),
                    () -> assertPiece(pieceMap, y, team, 2, Piece.of(PieceType.HORSE, team)),
                    () -> assertPiece(pieceMap, y, team, 6, Piece.of(PieceType.ELEPHANT, team)),
                    () -> assertPiece(pieceMap, y, team, 7, Piece.of(PieceType.HORSE, team))
            );

            case 오른상차림 -> Assertions.assertAll(
                    () -> assertPiece(pieceMap, y, team, 1, Piece.of(PieceType.HORSE, team)),
                    () -> assertPiece(pieceMap, y, team, 2, Piece.of(PieceType.ELEPHANT, team)),
                    () -> assertPiece(pieceMap, y, team, 6, Piece.of(PieceType.HORSE, team)),
                    () -> assertPiece(pieceMap, y, team, 7, Piece.of(PieceType.ELEPHANT, team))
            );

            case 안상차림 -> Assertions.assertAll(
                    () -> assertPiece(pieceMap, y, team, 1, Piece.of(PieceType.HORSE, team)),
                    () -> assertPiece(pieceMap, y, team, 2, Piece.of(PieceType.ELEPHANT, team)),
                    () -> assertPiece(pieceMap, y, team, 6, Piece.of(PieceType.ELEPHANT, team)),
                    () -> assertPiece(pieceMap, y, team, 7, Piece.of(PieceType.HORSE, team))
            );

            case 바깥상차림 -> Assertions.assertAll(
                    () -> assertPiece(pieceMap, y, team, 1, Piece.of(PieceType.ELEPHANT, team)),
                    () -> assertPiece(pieceMap, y, team, 2, Piece.of(PieceType.HORSE, team)),
                    () -> assertPiece(pieceMap, y, team, 6, Piece.of(PieceType.HORSE, team)),
                    () -> assertPiece(pieceMap, y, team, 7, Piece.of(PieceType.ELEPHANT, team))
            );
        }
    }

    private void assertPiece(BoardStateReader pieceMap, int y, Team team, int x, Piece expected) {
        int actualX = x;

        if (team == Team.HAN) {
            actualX = 8 - x;
        }

        Assertions.assertEquals(expected, pieceMap.getPiece(new Position(actualX, y)));
    }


    @Test
    public void 기물_이동_테스트() {
        var boardStruct = new EmptyBoardPiecesInitializer();
        var boardStatus = new BoardPieces();
        var board = new Board(boardStatus, boardStruct);

        // 졸 기물 세팅
        Piece soldier = Piece.of(PieceType.SOLDIER, Team.CHO);
        boardStatus.setPiece(new Position(6, 5), soldier);

        Piece king = Piece.of(PieceType.KING, Team.CHO);
        boardStatus.setPiece(new Position(4, 8), king);

        // 초나라의 졸을 왼쪽으로 한칸 이동
        Position from = new Position(6, 5);
        Position to = new Position(5, 5);
        board.move(Team.CHO, from, to);

        // 이동 후 해당 위치에 병이 있는지 확인
        BoardStateReader pieceMap = board.getStateReader();
        Assertions.assertEquals(soldier, pieceMap.getPiece(to));
    }

    @Test
    public void 궁을_잡으면_게임이_종료된다() {
        // Given
        var boardStatus = new BoardPieces();
        var board = new Board(boardStatus, new EmptyBoardPiecesInitializer());

        // When
        // 초나라 졸 기물 세팅
        Piece soldier = Piece.of(PieceType.SOLDIER, Team.CHO);
        Position soldierPosition = new Position(4, 2);
        boardStatus.setPiece(soldierPosition, soldier);

        // 초나라 왕 세팅
        Piece choKing = Piece.of(PieceType.KING, Team.CHO);
        Position choKingPosition = new Position(4, 8);
        boardStatus.setPiece(choKingPosition, choKing);

        // 한나라 왕 세팅
        Piece king = Piece.of(PieceType.KING, Team.HAN);
        Position kingPosition = new Position(4, 1);
        boardStatus.setPiece(kingPosition, king);

        // 초나라의 졸을 왼쪽으로 한칸 이동
        board.move(Team.CHO, soldierPosition, kingPosition);

        // 이동 후 해당 위치에 병이 있는지 확인
        Assertions.assertTrue(board.isGameFinished());
        Assertions.assertEquals(Team.CHO, board.getWinner());
    }
}