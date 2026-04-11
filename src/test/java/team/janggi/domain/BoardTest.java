package team.janggi.domain;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import team.janggi.domain.board.Board;
import team.janggi.domain.board.BoardFactory;
import team.janggi.domain.board.BoardStatus;
import team.janggi.domain.board.LocalMemoryBoardStatus;
import team.janggi.domain.piece.Cannon;
import team.janggi.domain.piece.Chariot;
import team.janggi.domain.piece.Elephant;
import team.janggi.domain.piece.Guard;
import team.janggi.domain.piece.Horse;
import team.janggi.domain.piece.King;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.Soldier;

public class BoardTest {

    @Test
    void 기본_장기_보드_생성_테스트() {
        var boardStruct = new BoardFactory(JanggiFormation.HEEH, JanggiFormation.HEEH);

        Board board = new Board(boardStruct);
        board.initBoard();

        Assertions.assertEquals(90, board.getStatus().size());
    }

    @Test
    void 기본_장기_배치_테스트() {
        var boardStruct = new BoardFactory(JanggiFormation.HEEH, JanggiFormation.HEEH);

        Board board = new Board(boardStruct);
        board.initBoard();

        Map<Position, Piece> pieceMap = board.getStatus();
        // 초나라(CHO)가 아래쪽에 잘 배치되었는지 확인
        검증_초나라_기본_배치(pieceMap);
        // 한나라(HAN)가 위쪽에 잘 배치되었는지 확인
        검증_한나라_기본_배치(pieceMap);
    }

    private void 검증_초나라_기본_배치(Map<Position, Piece> pieceMap) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(new Chariot(Team.CHO), pieceMap.get(new Position(0, 9))),
                () -> Assertions.assertEquals(new Chariot(Team.CHO), pieceMap.get(new Position(8, 9))),
                () -> Assertions.assertEquals(new Cannon(Team.CHO), pieceMap.get(new Position(1, 7))),
                () -> Assertions.assertEquals(new Cannon(Team.CHO), pieceMap.get(new Position(7, 7))),
                () -> Assertions.assertEquals(new Soldier(Team.CHO), pieceMap.get(new Position(0, 6))),
                () -> Assertions.assertEquals(new Soldier(Team.CHO), pieceMap.get(new Position(2, 6))),
                () -> Assertions.assertEquals(new Soldier(Team.CHO), pieceMap.get(new Position(4, 6))),
                () -> Assertions.assertEquals(new Soldier(Team.CHO), pieceMap.get(new Position(6, 6))),
                () -> Assertions.assertEquals(new Soldier(Team.CHO), pieceMap.get(new Position(8, 6))),
                () -> Assertions.assertEquals(new King(Team.CHO), pieceMap.get(new Position(4, 8))),
                () -> Assertions.assertEquals(new Guard(Team.CHO), pieceMap.get(new Position(3, 9))),
                () -> Assertions.assertEquals(new Guard(Team.CHO), pieceMap.get(new Position(5, 9)))
        );
    }

    private void 검증_한나라_기본_배치(Map<Position, Piece> pieceMap) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(new Chariot(Team.HAN), pieceMap.get(new Position(0, 0))),
                () -> Assertions.assertEquals(new Chariot(Team.HAN), pieceMap.get(new Position(8, 0))),
                () -> Assertions.assertEquals(new Cannon(Team.HAN), pieceMap.get(new Position(1, 2))),
                () -> Assertions.assertEquals(new Cannon(Team.HAN), pieceMap.get(new Position(7, 2))),
                () -> Assertions.assertEquals(new Soldier(Team.HAN), pieceMap.get(new Position(0, 3))),
                () -> Assertions.assertEquals(new Soldier(Team.HAN), pieceMap.get(new Position(2, 3))),
                () -> Assertions.assertEquals(new Soldier(Team.HAN), pieceMap.get(new Position(4, 3))),
                () -> Assertions.assertEquals(new Soldier(Team.HAN), pieceMap.get(new Position(6, 3))),
                () -> Assertions.assertEquals(new Soldier(Team.HAN), pieceMap.get(new Position(8, 3))),
                () -> Assertions.assertEquals(new King(Team.HAN), pieceMap.get(new Position(4, 1))),
                () -> Assertions.assertEquals(new Guard(Team.HAN), pieceMap.get(new Position(3, 0))),
                () -> Assertions.assertEquals(new Guard(Team.HAN), pieceMap.get(new Position(5, 0)))
        );
    }

    @Test
    void 초_바깥_한_바깥() {
        runTest(JanggiFormation.HEEH, JanggiFormation.HEEH);
    }

    @Test
    void 초_바깥_한_안() {
        runTest(JanggiFormation.HEEH, JanggiFormation.EHHE);
    }

    @Test
    void 초_안_한_왼() {
        runTest(JanggiFormation.EHHE, JanggiFormation.EHEH);
    }

    @Test
    void 초_왼_한_오른() {
        runTest(JanggiFormation.EHEH, JanggiFormation.HEHE);
    }

    private void runTest(JanggiFormation choSetup, JanggiFormation hanSetup) {
        var boardFactory = new BoardFactory(choSetup, hanSetup);

        Board board = new Board(boardFactory);
        board.initBoard();

        Map<Position, Piece> pieceMap = board.getStatus();

        // 고정 기물 검증
        검증_초나라_기본_배치(pieceMap);
        검증_한나라_기본_배치(pieceMap);

        // 상/마 차림 검증 (초는 9번 행, 한은 0번 행)
        assertPieceSetup(pieceMap, 9, Team.CHO, choSetup);
        assertPieceSetup(pieceMap, 0, Team.HAN, hanSetup);
    }

    private void assertPieceSetup(Map<Position, Piece> pieceMap, int y, Team team, JanggiFormation setup) {
        switch (setup) {
            case EHEH -> Assertions.assertAll(
                    () -> assertPiece(pieceMap, y, team, 1, new Elephant(team)),
                    () -> assertPiece(pieceMap, y, team, 2, new Horse(team)),
                    () -> assertPiece(pieceMap, y, team, 6, new Elephant(team)),
                    () -> assertPiece(pieceMap, y, team, 7, new Horse(team))
            );

            case HEHE -> Assertions.assertAll(
                    () -> assertPiece(pieceMap, y, team, 1, new Horse(team)),
                    () -> assertPiece(pieceMap, y, team, 2, new Elephant(team)),
                    () -> assertPiece(pieceMap, y, team, 6, new Horse(team)),
                    () -> assertPiece(pieceMap, y, team, 7, new Elephant(team))
            );

            case EHHE -> Assertions.assertAll(
                    () -> assertPiece(pieceMap, y, team, 1, new Horse(team)),
                    () -> assertPiece(pieceMap, y, team, 2, new Elephant(team)),
                    () -> assertPiece(pieceMap, y, team, 6, new Elephant(team)),
                    () -> assertPiece(pieceMap, y, team, 7, new Horse(team))
            );

            case HEEH -> Assertions.assertAll(
                    () -> assertPiece(pieceMap, y, team, 1, new Elephant(team)),
                    () -> assertPiece(pieceMap, y, team, 2, new Horse(team)),
                    () -> assertPiece(pieceMap, y, team, 6, new Horse(team)),
                    () -> assertPiece(pieceMap, y, team, 7, new Elephant(team))
            );
        }
    }

    private void assertPiece(Map<Position, Piece> pieceMap, int y, Team team, int x, Piece expected) {
        int actualX = x;

        if (team == Team.HAN) {
            actualX = 8 - x;
        }

        Assertions.assertEquals(expected, pieceMap.get(new Position(actualX, y)));
    }


    @Test
    public void 기물_이동_테스트() {
        var boardStruct = new BoardFactory(JanggiFormation.HEEH, JanggiFormation.HEEH);
        var boardStatus = new LocalMemoryBoardStatus();
        var board = new Board(boardStatus, boardStruct);

        board.initBoard();

        // 졸 기물 세팅
        Soldier soldier = new Soldier(Team.CHO);
        boardStatus.setPiece(new Position(6, 5), soldier);

        // 초나라의 졸을 왼쪽으로 한칸 이동
        Position from = new Position(6, 5);
        Position to = new Position(5, 5);
        board.move(Team.CHO, from, to);

        // 이동 후 해당 위치에 병이 있는지 확인
        Map<Position, Piece> pieceMap = board.getStatus();
        Assertions.assertEquals(soldier, pieceMap.get(to));
    }

    @Test
    public void 상대_기물이_왕을_잡으면_왕의_부재를_확인한다() {
        // given
        EmptyBoardFactory boardFactory = new EmptyBoardFactory(JanggiFormation.HEEH,
                JanggiFormation.HEEH);
        BoardStatus boardStatus = new LocalMemoryBoardStatus();
        // when
        King Hanking = new King(Team.HAN);
        King Choking = new King(Team.CHO);

        Position HanKingPositon = Position.of(4, 1);
        Position ChokingPositon = Position.of(4, 8);

        boardStatus.setPiece(HanKingPositon, Hanking);
        boardStatus.setPiece(ChokingPositon, Choking);

        Soldier soldier = new Soldier(Team.HAN);
        Position soldierPositon = Position.of(3, 7);
        boardStatus.setPiece(soldierPositon, soldier);

        boardStatus.movePiece(Team.HAN, soldierPositon, ChokingPositon);

        // then
        Assertions.assertTrue(boardStatus.isKingDisappeared());
    }

    @Test
    public void 진영별_기물에_대한_점수를_반환한다() {
        // given
        BoardStatus boardStatus = new LocalMemoryBoardStatus();
        BoardFactory boardFactory = new BoardFactory(JanggiFormation.HEEH, JanggiFormation.HEEH);
        Board board = new Board(boardStatus, boardFactory);
        board.initBoard();

        // when

        double choScore = board.getScore(Team.CHO);
        double hanScore = board.getScore(Team.HAN);

        double expectedChoScore = 72;
        double expectedHanScore = 73.5;

        // then
        Assertions.assertEquals(expectedChoScore, choScore);
        Assertions.assertEquals(expectedHanScore, hanScore);
    }
}