package view;

import domain.Team;
import domain.UnitType;
import domain.position.Position;
import domain.position.Route;
import java.util.List;

public class OutputView {

    public void printAvailableRoute(List<Route> routes) {
        for (Route route : routes) {
            Position endPoint = route.searchEndPoint();
            System.out.printf("x = %d, y = %d\n"
                    , endPoint.getX(), endPoint.getY());
        }
    }

    private String typeToName(UnitType unitType) {
        if (unitType == UnitType.GENERAL) {
            return "궁";
        }
        if (unitType == UnitType.GUARD) {
            return "사";
        }
        if (unitType == UnitType.CHARIOT) {
            return "차";
        }
        if (unitType == UnitType.HORSE) {
            return "마";
        }
        if (unitType == UnitType.ELEPHANT) {
            return "상";
        }
        if (unitType == UnitType.CANNON) {
            return "포";
        }
        if (unitType == UnitType.SOLDIER) {
            return "병";
        }
        throw new IllegalStateException("");
    }

    private String teamToName(Team team) {
        if (team == Team.HAN) {
            return "한나라";
        }
        if (team == Team.CHO) {
            return "초나라";
        }
        throw new IllegalStateException("");
    }
}
