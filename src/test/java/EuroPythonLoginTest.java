import java.util.concurrent.TimeUnit;

import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.*;
import org.graphwalker.core.model.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EuroPythonLoginTest extends ExecutionContext {

	WebDriver driver = null;

	//---------------------------------------------------------------------------------------------
	// EDGES (Actions)
	//---------------------------------------------------------------------------------------------

	public void e_StartBrowser() {

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("http://127.0.0.1:5000");
	}

	public void e_LoginFailed() {

		driver.findElement(By.xpath("//input[@value='Bad Login 👹']")).click();
	}

	public void e_LoginSucceeded() {

		driver.findElement(By.xpath("//input[@value='Good Login 😇']")).click();
	}

	public void e_Logout() {

		driver.findElement(By.xpath("//input[@value='Logout 👋']")).click();
	}

	//---------------------------------------------------------------------------------------------
	// Vertices (Checks)
	//---------------------------------------------------------------------------------------------

	public void v_Start() {

	}

	public void v_HomePage() {

		WebElement element = driver.findElement(By.id("title"));
		Assert.assertEquals(element.getText(),"Login 🤖");
	}

	public void v_ErrorPage() {

		WebElement element = driver.findElement(By.id("error"));
		Assert.assertEquals(element.getText(),"Login Error 💩");
	}

	public void v_LoggedInPage() {

		WebElement element = driver.findElement(By.id("title"));
		Assert.assertEquals(element.getText(),"Logged in 👻");
	}

	//---------------------------------------------------------------------------------------------
	// Build Model
	//---------------------------------------------------------------------------------------------

	private Model createModel() {

		// Create a new, empty model
		Model model = new Model();

		// Create vertices (nodes)
		Vertex v_Start = new Vertex().setName("v_Start");
		Vertex v_HomePage = new Vertex().setName("v_HomePage");
		Vertex v_ErrorPage = new Vertex().setName("v_ErrorPage");
		Vertex v_LoggedInPage = new Vertex().setName("v_LoggedInPage");

		// Create edges
		Edge e_StartBrowser = new Edge()
			.setName("e_StartBrowser")
			.setSourceVertex(v_Start)
			.setTargetVertex(v_HomePage);
		Edge e_LoginFailed = new Edge()
			.setName("e_LoginFailed")
			.setSourceVertex(v_HomePage)
			.setTargetVertex(v_ErrorPage);
		Edge e_LoginFailedAgain = new Edge()
			.setName("e_LoginFailed")
			.setSourceVertex(v_ErrorPage)
			.setTargetVertex(v_ErrorPage);
		Edge e_LoginSucceeded = new Edge()
			.setName("e_LoginSucceeded")
			.setSourceVertex(v_HomePage)
			.setTargetVertex(v_LoggedInPage);
		Edge e_LoginSucceededAfterFailure = new Edge()
			.setName("e_LoginSucceeded")
			.setSourceVertex(v_ErrorPage)
			.setTargetVertex(v_LoggedInPage);
		Edge e_Logout = new Edge()
			.setName("e_Logout")
			.setSourceVertex(v_LoggedInPage)
			.setTargetVertex(v_HomePage);

		// Add vertices to the model
		model.addVertex(v_Start);
		model.addVertex(v_HomePage);
		model.addVertex(v_ErrorPage);
		model.addVertex(v_LoggedInPage);

		// Add edges to the model
		model.addEdge(e_StartBrowser);
		model.addEdge(e_LoginFailed);
		model.addEdge(e_LoginFailedAgain);
		model.addEdge(e_LoginSucceeded);
		model.addEdge(e_LoginSucceededAfterFailure);
		model.addEdge(e_Logout);

		return model;
	}

	//---------------------------------------------------------------------------------------------
	// Test Code
	//---------------------------------------------------------------------------------------------

	@Test
	public void fullCoverageTest() {

		// Create an instance of our model
		Model model = createModel();
		this.setModel(model.build());

		// Tell GraphWalker to run the model in a random fashion for 20 seconds
		long run_time = 20;
		this.setPathGenerator(new RandomPath(new TimeDuration(run_time, TimeUnit.SECONDS)));

		// Get the starting vertex (v_Start)
		setNextElement(model.getVertices().get(0));

		// Run test until stop condition is reached
		Machine machine = new SimpleMachine(this);
		while (machine.hasNextStep()) {
			machine.getNextStep();
		}
	}

}