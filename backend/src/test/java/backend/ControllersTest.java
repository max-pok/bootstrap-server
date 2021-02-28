package backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import backend.controllers.ClientController;
import backend.controllers.LicenceController;
import backend.controllers.MongoDBController;
import backend.controllers.RequestController;

@SpringBootTest
public class ControllersTest {

	@Autowired
	private ClientController clientController;
    @Autowired
	private LicenceController licenceController;
    @Autowired
	private MongoDBController mongoDBController;
    @Autowired
	private RequestController requestController;

	@Test
	public void clientLoads() throws Exception {
		assertThat(clientController).isNotNull();
	}
    
    @Test
	public void licenceLoads() throws Exception {
		assertThat(licenceController).isNotNull();
	}
    
    @Test
	public void mongoDBLoads() throws Exception {
		assertThat(mongoDBController).isNotNull();
	}
    
    @Test
	public void requestLoads() throws Exception {
		assertThat(requestController).isNotNull();
	}
}