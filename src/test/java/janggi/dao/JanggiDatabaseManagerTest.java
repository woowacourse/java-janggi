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
import static org.assertj.core.api.Assertions.assertThatNoException;

class JanggiDatabaseManagerTest {

    private JanggiDatabaseManager janggiDatabaseManager;
    private FakePieceDao fakePieceDao;
    private FakePieceTypeDao fakePieceTypeDao;
    private FakeTeamDao fakeTeamDao;

    @BeforeEach
    void setUp() {
        fakePieceDao = new FakePieceDao();
        fakePieceTypeDao = new FakePieceTypeDao();
        fakeTeamDao = new FakeTeamDao();
        janggiDatabaseManager = new JanggiDatabaseManager(fakePieceDao, fakePieceTypeDao, fakeTeamDao,
                new JanggiMapper());
    }

    @Test
    void 초기_게임을_저장한다() {
        // Given
        Map<Position, Piece> pieces = Map.of(
                new Position(1, 1), new Byeong(),
                new Position(2, 1), new Byeong(),
                new Position(3, 1), new Jol()
        );

        // When
        janggiDatabaseManager.saveInitialGame(TeamType.CHO, pieces);

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fakeTeamDao.findTeams())
                    .isNotEmpty();
            softAssertions.assertThat(fakePieceDao.findAllPiece())
                    .isNotEmpty();
            softAssertions.assertThat(fakePieceTypeDao.getPieceTypes())
                    .isNotEmpty();
        });
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
        fakePieceDao.insertPieces(pieces);

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
    void 기물을_이동한다() {
        // Given
        final Position current = new Position(2, 1);
        final Position target = new Position(3, 1);

        Map<Position, Piece> pieces = Map.of(
                new Position(1, 1), new Byeong(),
                current, new Byeong(),
                target, new Jol()
        );
        janggiDatabaseManager.saveInitialGame(TeamType.CHO, pieces);

        // When & Then
        assertThatNoException()
                .isThrownBy(() -> janggiDatabaseManager.movePiece(current, target, TeamType.CHO));
    }

    @Test
    void 게임이_끝나_종료한다() {
        // Given
        Map<Position, Piece> pieces = Map.of(
                new Position(1, 1), new Byeong(),
                new Position(2, 1), new Byeong(),
                new Position(3, 1), new Jol()
        );
        janggiDatabaseManager.saveInitialGame(TeamType.CHO, pieces);

        // When
        janggiDatabaseManager.endGame();

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fakeTeamDao.findTeams())
                    .isEmpty();
            softAssertions.assertThat(fakePieceDao.findAllPiece())
                    .isEmpty();
            softAssertions.assertThat(fakePieceTypeDao.getPieceTypes())
                    .isEmpty();
        });
    }
}
