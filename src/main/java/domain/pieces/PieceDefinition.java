package domain.pieces;

import domain.player.Player;
import domain.player.Team;
import java.util.function.Function;

public enum PieceDefinition {
    CHARIOT("車", "차", Chariot::new),
    CANNON("包", "포", Cannon::new),
    HORSE("馬", "마", Horse::new),
    ELEPHANT("象", "상", Elephant::new),
    GUARD("士", "사", Guard::new),
    SOLDIER("兵", "졸", Soldier::new),
    GENERAL("將", "궁", General::new);

    private final String nameForHan;
    private final String nameForCho;
    private final Function<Player, Piece> pieceFactory;

    PieceDefinition(final String nameForHan, final String nameForCho, final Function<Player, Piece> pieceFactory) {
        this.nameForHan = nameForHan;
        this.nameForCho = nameForCho;
        this.pieceFactory = pieceFactory;
    }

    public String getNameForTeam(final Team team) {
        if (team.equals(Team.HAN)) {
            return this.nameForHan;
        }
        return this.nameForCho;
    }

    public Piece apply(final Player player) {
        return pieceFactory.apply(player);
    }
}
