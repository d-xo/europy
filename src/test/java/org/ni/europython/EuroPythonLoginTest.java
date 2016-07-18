package org.ni.europython;

import java.util.concurrent.TimeUnit;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.ReachedVertex;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.generator.AStarPath;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.test.TestBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EuroPythonLoginTest extends ExecutionContext implements Login{

	public final static Path MODEL_PATH = Paths.get("org/ni/europython.Login.graphml");
	WebDriver driver = null;

	//---------------------------------------------------------------------------------------------
	// EDGES (Actions)
	//---------------------------------------------------------------------------------------------

	@Override
	public void e_StartBrowser() {

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("http://127.0.0.1:5000");
	}

	@Override
	public void e_LoginFailed() {

		driver.findElement(By.xpath("//input[@value='Bad Login 👹']")).click();
	}

	@Override
	public void e_Login() {

		driver.findElement(By.xpath("//input[@value='Good Login 😇']")).click();
	}

	@Override
	public void e_Logout() {

		driver.findElement(By.xpath("//input[@value='Logout 👋']")).click();
	}

	//---------------------------------------------------------------------------------------------
	// Vertices (Checks)
	//---------------------------------------------------------------------------------------------

	@Override
	public void v_HomePage() {

		WebElement element = driver.findElement(By.id("title"));
		Assert.assertEquals(element.getText(),"Login 🤖");
	}

	@Override
	public void v_ErrorPage() {

		WebElement element = driver.findElement(By.id("error"));
		Assert.assertEquals(element.getText(),"Login Error 💩");
	}

	@Override
	public void v_LoggedInPage() {

		WebElement element = driver.findElement(By.id("title"));
		Assert.assertEquals(element.getText(),"Logged in 👻");
	}

	//---------------------------------------------------------------------------------------------
	// Test Code
	//---------------------------------------------------------------------------------------------

	@Test
	public void stabilityTest() {
		new TestBuilder()
			.setModel(MODEL_PATH)
			.setContext(new EuroPythonLoginTest())
			.setPathGenerator(new RandomPath(new TimeDuration(30, TimeUnit.SECONDS)))
			.setStart("e_StartBrowser")
			.execute();
	}
}