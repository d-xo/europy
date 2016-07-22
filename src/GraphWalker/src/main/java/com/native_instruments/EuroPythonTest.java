package com.native_instruments;

import java.util.concurrent.TimeUnit;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

@GraphWalker(value = "random(edge_coverage(100))", start = "e_StartBrowser")
public class EuroPythonTest extends ExecutionContext implements EuroPython {

	WebDriver driver = null;

	//
	// Edges
	//
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

	//
	// Nodes
	//
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

}
