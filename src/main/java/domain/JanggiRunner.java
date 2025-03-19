package domain;

import domain.piece.PieceFactory;
import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.piece.strategy.InnerElephantStrategy;
import domain.piece.strategy.OuterElephantStrategy;
import java.util.List;

public class JanggiRunner {

    public JanggiGame initializeGame() {
        Player player1 = new Player("코기", TeamType.HAN);
        Player player2 = new Player("루키", TeamType.CHO);
        PieceFactory factory = new PieceFactory();

        HorseElephantSetupStrategy choStrategy = new InnerElephantStrategy();
        HorseElephantSetupStrategy hanStrategy = new OuterElephantStrategy();

        List<Player> players = List.of(player1, player2);
        return new JanggiGame(players, factory.createAllPieces(choStrategy, hanStrategy));
    }
}
