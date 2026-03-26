import domain.JanggiGame;
import domain.SettingType;
import java.util.List;
import view.InputView;

public class Controller {
    private final InputView inputView;

    public Controller(InputView inputView) {
        this.inputView = inputView;
    }

    public void initializeGame() {
        List<SettingType> settingTypes = inputView.readSettings();
        SettingType choSettingType = settingTypes.getFirst();
        SettingType hanSettingType = settingTypes.getLast();
        
        JanggiGame game = JanggiGame.init(choSettingType, hanSettingType);
        game.getJanggiGameStatus();
        //TODO : Print game status.
    }
}
