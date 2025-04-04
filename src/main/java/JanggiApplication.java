import dao.PieceDao;
import dao.RoomDao;
import gameflow.JanggiGameFlow;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final PieceDao pieceDao = new PieceDao();
        final RoomDao roomDao = new RoomDao();
        final JanggiService janggiService = new JanggiService(pieceDao, roomDao);

        final JanggiGameFlow janggiGameFlow = new JanggiGameFlow(janggiService, inputView, outputView);

        try {
            String roomId = janggiGameFlow.selectGameRoom();
            janggiGameFlow.play(roomId);
            janggiGameFlow.endGame(roomId);
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
}
