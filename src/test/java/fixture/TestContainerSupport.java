package fixture;

import org.junit.jupiter.api.BeforeEach;

public class TestContainerSupport {

    @BeforeEach
    void setUp() {
        TestContainer.truncateAll();
    }
}
