package janggi.service;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.dao.JanggiBoardDAO;
import janggi.domain.board.dao.TeamDAO;
import janggi.domain.board.dao.TurnDAO;
import janggi.domain.piece.Piece;
import janggi.domain.service.JanggiGameService;
import janggi.service.fake.FakeJanggiBoardTable;
import janggi.service.fake.FakeTeamTable;
import janggi.service.fake.FakeTurnTable;
import janggi.domain.setting.AssignType;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameServiceTest {
    private JanggiBoardDAO fakeBoardDAO;
    private TurnDAO fakeTurnDAO;
    private TeamDAO fakeTeamDAO;
    private JanggiGameService janggiGameService;

    @BeforeEach
    void setUp() {
        fakeBoardDAO = new FakeJanggiBoardTable();
        fakeTurnDAO = new FakeTurnTable();
        fakeTeamDAO = new FakeTeamTable();
        janggiGameService = new JanggiGameService(fakeBoardDAO, fakeTurnDAO, fakeTeamDAO);
    }

    @DisplayName("처음 게임을 시작하면 팀 테이블에 값을 추가하고, 턴 테이블에 초나라부터 시작하도록 한다.")
    @Test
    void test1() {
        //given
        CampType startCamp = CampType.CHO;

        //when
        janggiGameService.initializeGame();

        //then
        Assertions.assertThat(fakeTurnDAO.selectQuery()).isEqualTo(startCamp);
    }

    @DisplayName("게임이 진행중이면 true를 반환한다.")
    @Test
    void test2() {
        //given
        janggiGameService.initializeGame();
        JanggiBoard janggiBoard = janggiGameService.createBoard(AssignType.IN_SANG, AssignType.IN_SANG);
        janggiGameService.saveBoardState(janggiBoard);

        //when
        boolean isExistingGame = janggiGameService.hasExistingGame();

        //then
        Assertions.assertThat(isExistingGame).isTrue();
    }

    @DisplayName("게임이 진행중이 아니면 false를 반환한다.")
    @Test
    void test3() {
        //given
        janggiGameService.initializeGame();

        //when
        boolean isExistingGame = janggiGameService.hasExistingGame();

        //then
        Assertions.assertThat(isExistingGame).isFalse();
    }

    @DisplayName("기존에 저장된 장기판을 가져온다.")
    @Test
    void test4() {
        //given
        janggiGameService.initializeGame();
        JanggiBoard janggiBoard = janggiGameService.createBoard(AssignType.IN_SANG, AssignType.IN_SANG);
        janggiGameService.saveBoardState(janggiBoard);

        //when
        JanggiBoard existingJanggiBoard = janggiGameService.loadBoard();

        //then
        Assertions.assertThat(existingJanggiBoard.getChoPieces()).hasSize(16);
        Assertions.assertThat(existingJanggiBoard.getHanPieces()).hasSize(16);
    }

    @DisplayName("현재 턴을 가져온다.")
    @Test
    void test5() {
        //given
        CampType campType = CampType.CHO;
        janggiGameService.initializeGame();

        //when
        CampType currentTurn = janggiGameService.getCurrentTurn();

        //then
        Assertions.assertThat(currentTurn).isEqualTo(campType);
    }

    @DisplayName("턴을 업데이트 한다.")
    @Test
    void test6() {
        //given
        CampType campType = CampType.CHO;
        janggiGameService.initializeGame();

        //when
        janggiGameService.updateTurn(CampType.HAN);

        //then
        CampType currentTurn = janggiGameService.getCurrentTurn();
        Assertions.assertThat(currentTurn).isEqualTo(CampType.HAN);
    }

    @DisplayName("공격당한 장기말을 테이블에서 지운다.")
    @Test
    void test7() {
        //given
        janggiGameService.initializeGame();
        JanggiBoard janggiBoard = janggiGameService.createBoard(AssignType.IN_SANG, AssignType.IN_SANG);
        janggiGameService.saveBoardState(janggiBoard);
        JanggiPosition attackedPosition = new JanggiPosition(0,9);

        //when
        janggiGameService.deletePieceRecord(attackedPosition, 1);

        //then
        List<Piece> choPieces = fakeBoardDAO.selectChoRecords();
        boolean isAlivePiece = choPieces.stream()
                .noneMatch(piece -> piece.getPosition().equals(attackedPosition));
        Assertions.assertThat(isAlivePiece).isTrue();
    }

    @DisplayName("이동한 장기말의 위치를 업데이트한다.")
    @Test
    void test8() {
        //given
        janggiGameService.initializeGame();
        JanggiBoard janggiBoard = janggiGameService.createBoard(AssignType.IN_SANG, AssignType.IN_SANG);
        janggiGameService.saveBoardState(janggiBoard);
        JanggiPosition from = new JanggiPosition(0,9);
        JanggiPosition to = new JanggiPosition(0,7);
        int teamId = 1;

        //when
        janggiGameService.updatePiecePosition(from, to, teamId);

        //then
        List<Piece> choPieces = fakeBoardDAO.selectChoRecords();
        boolean isMovedPiece = choPieces.stream()
                .anyMatch(piece -> piece.getPosition().equals(to));
        Assertions.assertThat(isMovedPiece).isTrue();
    }

    @DisplayName("테이블을 지운다.")
    @Test
    void testClearGameData() {
        //given
        janggiGameService.initializeGame();
        JanggiBoard janggiBoard = janggiGameService.createBoard(AssignType.IN_SANG, AssignType.IN_SANG);
        janggiGameService.saveBoardState(janggiBoard);

        //when
        janggiGameService.clearGameData();

        //then
        Assertions.assertThat(fakeBoardDAO.selectChoRecords()).hasSize(0);
    }

}