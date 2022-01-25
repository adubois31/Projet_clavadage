package test;

import java.util.concurrent.TimeoutException;
import org.junit.After;
import org.junit.Before;
import FloppaChat.floppeX.App;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class MainAppTest extends ApplicationTest{
	
	@Before
	public void SetUpClass() throws Exception{
		ApplicationTest.launch(App.class);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.show();
	}
	
	@After
	public void afterEachTest() throws TimeoutException {
		FxToolkit.hideStage();
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}
}
