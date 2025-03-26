package fixture;

import janggiGame.JanggiGame;
import janggiGame.arrangement.InnerElephantStrategy;
import janggiGame.arrangement.OuterElephantStrategy;

public class JanggiGameFixture {
    public static JanggiGame getRunningJanggiGame() {
        JanggiGame janggiGame = new JanggiGame();
        janggiGame.arrangePieces(new InnerElephantStrategy(), new OuterElephantStrategy());
        return janggiGame;
    }
}
