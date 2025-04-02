package janggi.dao;

import janggi.dao.fake.FakePieceDao;
import janggi.dao.fake.FakePieceTypeDao;
import janggi.dao.fake.FakeTeamDao;
import janggi.dao.utils.JanggiMapper;
import janggi.domain.piece.Byeong;
import janggi.domain.piece.Jol;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiDatabaseManagerTest {

    private JanggiDatabaseManager janggiDatabaseManager;
    private FakePieceDao fakeJanggiDao;
    private FakePieceTypeDao fakePieceTypeDao;
    private FakeTeamDao fakeTeamDao;

    @BeforeEach
    void setUp() {
        fakeJanggiDao = new FakePieceDao();
        fakePieceTypeDao = new FakePieceTypeDao();
        fakeTeamDao = new FakeTeamDao();
        janggiDatabaseManager = new JanggiDatabaseManager(fakeJanggiDao, fakePieceTypeDao, fakeTeamDao,
                new JanggiMapper());
    }

    @Test
    void 기존_저장된_게임이_있다면_기물을_불러온다() {
        // Given
        Map<Position, Piece> pieces = Map.of(
                new Position(1, 1), new Byeong(),
                new Position(2, 1), new Byeong(),
                new Position(3, 1), new Jol()
        );
        fakeTeamDao.insertInitialTeam(TeamType.CHO);
        fakePieceTypeDao.insertInitialPieceType();
        fakeJanggiDao.insertPieces(pieces);

        // When & Then
        assertThat(janggiDatabaseManager.loadPiecesForProgressingGame().size())
                .isEqualTo(pieces.size());
    }

    @Test
    void 기존_저장된_게임이_있다면_순서를_불러온다() {
        // Given
        final TeamType currentTeam = TeamType.CHO;
        fakeTeamDao.insertInitialTeam(currentTeam);
        fakeTeamDao.updateTeamOrder(currentTeam);

        // When & Then
        assertThat(janggiDatabaseManager.loadOrdersForProgressingGame().getFirst())
                .isEqualTo(currentTeam);
    }

    @Test
    void 진행_중인_게임을_저장한다() {
        // Given
        Map<Position, Piece> pieces = Map.of(
                new Position(1, 1), new Byeong(),
                new Position(2, 1), new Byeong(),
                new Position(3, 1), new Jol()
        );
        fakeTeamDao.insertInitialTeam(TeamType.CHO);
        fakePieceTypeDao.insertInitialPieceType();
        fakeJanggiDao.insertPieces(pieces);

        // When
        janggiDatabaseManager.saveGame(TeamType.CHO, pieces);

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fakeTeamDao.findTeams())
                    .isNotEmpty();
            softAssertions.assertThat(fakeJanggiDao.findPieces())
                    .isNotEmpty();
            softAssertions.assertThat(fakePieceTypeDao.getPieceTypes())
                    .isNotEmpty();
        });
    }

    @Test
    void 게임이_끝나_종료한다() {
        // Given
        Map<Position, Piece> pieces = Map.of(
                new Position(1, 1), new Byeong(),
                new Position(2, 1), new Byeong(),
                new Position(3, 1), new Jol()
        );
        fakeTeamDao.insertInitialTeam(TeamType.CHO);
        fakePieceTypeDao.insertInitialPieceType();
        fakeJanggiDao.insertPieces(pieces);

        // When
        janggiDatabaseManager.endGame();

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fakeTeamDao.findTeams())
                    .isEmpty();
            softAssertions.assertThat(fakeJanggiDao.findPieces())
                    .isEmpty();
            softAssertions.assertThat(fakePieceTypeDao.getPieceTypes())
                    .isEmpty();
        });
    }
}
