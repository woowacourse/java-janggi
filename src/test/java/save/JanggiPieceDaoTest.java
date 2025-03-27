package save;

import move.JolMoveBehavior;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.player.Team;
import piece.position.JanggiPosition;

class JanggiPieceDaoTest {

    private JanggiPieceDao janggiPieceDao;
    private JanggiTurnDao janggiTurnDao;

    @BeforeEach
    void setUp() {
        MySQLConnection connection = new TestJanggiConnection();
        janggiPieceDao = new JanggiPieceDao(connection);
        janggiTurnDao = new JanggiTurnDao(connection);
        janggiPieceDao.deleteAll();
        janggiTurnDao.deleteAll();
    }

    @Test
    void 피스_추가및_출력_테스트() {
        Team team = Team.BLUE;
        int turn = 1;
        int score = 100;

        Piece createPiece = new Piece(new JanggiPosition(0, 0), new JolMoveBehavior(), Team.BLUE);
        janggiTurnDao.addTurnScore(team, turn, score);
        janggiPieceDao.savePiece(createPiece, turn);

        int latestTurnId = janggiTurnDao.getLatestTurnId().get();
        Pieces piecesByTeamTurn = janggiPieceDao.findPiecesByTeamTurn(latestTurnId);

        Piece firstPiece = piecesByTeamTurn.getFirstPiece();
        Assertions.assertEquals(firstPiece, createPiece);
    }
}
