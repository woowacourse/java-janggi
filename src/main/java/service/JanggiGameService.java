package service;

import domain.game.JanggiGameRepository;
import domain.piece.PieceRepository;
import domain.player.PlayerRepository;

import javax.sql.DataSource;

public class JanggiGameService {

    private final DataSource dataSource;
    private final JanggiGameRepository janggiGameRepository;
    private final PlayerRepository playerRepository;
    private final PieceRepository pieceRepository;

    public JanggiGameService(
            final DataSource dataSource,
            final JanggiGameRepository janggiGameRepository,
            final PlayerRepository playerRepository,
            final PieceRepository pieceRepository
    ) {
        this.dataSource = dataSource;
        this.janggiGameRepository = janggiGameRepository;
        this.playerRepository = playerRepository;
        this.pieceRepository = pieceRepository;
    }
}
