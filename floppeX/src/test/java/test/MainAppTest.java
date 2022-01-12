package test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import FloppaChat.floppeX.App;
import javafx.application.Platform;
import javafx.scene.control.Button;
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
