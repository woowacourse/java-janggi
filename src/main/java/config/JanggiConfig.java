package config;

import controller.JanggiController;
import repository.InMemoryJanggiRepositoryImpl;
import repository.JanggiRepository;
import service.JanggiCommandService;
import service.JanggiQueryService;

public class JanggiConfig {

    public static JanggiController setupController() {
        JanggiRepository janggiRepository = new InMemoryJanggiRepositoryImpl();

        JanggiCommandService janggiCommandService = new JanggiCommandService(janggiRepository);
        JanggiQueryService janggiQueryService = new JanggiQueryService(janggiRepository);

        return new JanggiController(janggiCommandService, janggiQueryService);
    }
}
