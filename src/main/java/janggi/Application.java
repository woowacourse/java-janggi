package janggi;

import janggi.controller.JanggiController;
import janggi.repositiory.game.GameRepository;
import janggi.repositiory.game.JdbcGameRepository;
import janggi.repositiory.piece.JdbcPieceRepository;
import janggi.repositiory.piece.PieceRepository;
import janggi.service.JanggiService;
import org.h2.jdbcx.JdbcDataSource;

public class Application {
    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:./janggi;MODE=MySQL;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        GameRepository gameRepository = new JdbcGameRepository(dataSource);
        PieceRepository pieceRepository = new JdbcPieceRepository(dataSource);

        JanggiService janggiService = new JanggiService(gameRepository, pieceRepository);
        JanggiController janggiController = new JanggiController(janggiService);
        janggiController.run();
    }
}
