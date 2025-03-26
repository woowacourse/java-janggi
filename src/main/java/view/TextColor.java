package view;

import domain.Team;
import domain.player.Player;

public class TextColor {
    public static final String red = "\u001B[31m";
    public static final String blue = "\u001B[34m";
    public static final String white = "\u001B[37m";
    public static final String exit = "\u001B[0m";

    public static String specifyTeamColor(Player player) {
        if (player.getTeam() == Team.BLUE) {
            return TextColor.blue;
        }
        return TextColor.red;
    }
}
