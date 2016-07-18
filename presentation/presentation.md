# [fit] Model Based Testing 
## [fit] With Graphwalker

---

# Native Instruments & Maschine

![original](maschine.jpg)

---

## [fit] Testing requires tooling that can cope with
## [fit] Combinatorial Complexity

---

# Model Based Testing

- Model system as a graph (finite state machine)
- Generate testcases by randomly walking over graph 
<br>
- Tests are more *flexible*
- Tests find *new bugs*
- Tests may not always be the same

---

## Building the Model

- Nodes are states to be verified
- Edges are actions to be taken
<br>
- Graphwalker takes a graphml file and generates an interface


![right](graph.png)

---

## Defining the Edges

```java
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
```

---

## Defining the Nodes

```java
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
```

---

## Writing a test

```java
	@Test
	public void stabilityTest() {
		new TestBuilder()
			.setModel(MODEL_PATH)
			.setContext(new EuroPythonLoginTest())
			.setPathGenerator(new RandomPath(new TimeDuration(30, TimeUnit.SECONDS)))
			.setStart("e_StartBrowser")
			.execute();
	}
```

---

## [fit] Demonstration :information_desk_person:

---

## [fit]Questions? :pray:
