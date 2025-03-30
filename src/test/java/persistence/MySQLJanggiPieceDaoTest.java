package persistence;

import move.JolMoveBehavior;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.player.Team;
import piece.position.JanggiPosition;

class MySQLJanggiPieceDaoTest {

    private final DatabaseConnection connection = new TestMySQLConnection();
    private final MySQLJanggiPieceDao janggiPieceDao = new MySQLJanggiPieceDao(connection);
    private final MySQLJanggiTurnDao janggiTurnDao = new MySQLJanggiTurnDao(connection);

    @AfterEach
    void clearDatabases() {
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

        int latestTurnId = janggiTurnDao.findLatestTurnId().get();
        Pieces piecesByTeamTurn = janggiPieceDao.findPiecesByTeamTurn(latestTurnId);

        Piece firstPiece = piecesByTeamTurn.getFirstPiece();
        Assertions.assertEquals(firstPiece, createPiece);
    }
}
