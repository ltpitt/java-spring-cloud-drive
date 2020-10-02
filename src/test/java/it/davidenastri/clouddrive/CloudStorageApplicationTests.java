package it.davidenastri.clouddrive;

import io.github.bonigarcia.wdm.WebDriverManager;
import it.davidenastri.clouddrive.page.HomePage;
import it.davidenastri.clouddrive.page.LoginPage;
import it.davidenastri.clouddrive.page.SignUpPage;
import it.davidenastri.clouddrive.services.NoteService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	public String baseURL;

	@LocalServerPort
	private int port;
	private WebDriver driver;
	@Autowired
	NoteService noteService;

	@BeforeAll
	static void beforeAll() {

	}

	@BeforeEach
	public void beforeEach() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--headless");

		WebDriverManager.chromedriver().setup();

		this.driver = new ChromeDriver(options);
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void verifyThatUnauthoziedUserCanAccessOnlyLoginAndSignUp() {
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testUserSignupLoginLogout() throws InterruptedException {
		String username = "username";
		String password = "password";

		driver.get(baseURL + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signUp("User", "1", username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void testNoteCreationAndView() throws InterruptedException {
		String username = "username";
		String password = "password";
		String noteTitle = "Note Title";
		String noteDescription = "Note Description";

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.addNewNote(noteTitle, noteDescription, driver);
		assertEquals(noteTitle, homePage.getFirstNoteTitle(driver));
		assertEquals(noteDescription, homePage.getFirstNoteDescription(driver));
		homePage.logout();
	}

	@Test
	@Order(4)
	public void testNoteEdit() throws InterruptedException {
		String username = "username";
		String password = "password";
		String noteTitle = "Note Title";
		String noteDescription = "Note Description";
		String editedNoteTitle = "Edited Note Title";
		String editedNoteDescription = "Edited Note Description";

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.addNewNote(noteTitle, noteDescription, driver);

		homePage.editNote(editedNoteTitle, editedNoteDescription, driver);

		assertEquals(editedNoteTitle, homePage.getFirstNoteTitle(driver));
		assertEquals(editedNoteDescription, homePage.getFirstNoteDescription(driver));
		homePage.logout();
	}

	@Test
	@Order(5)
	public void testNoteDeletion() throws InterruptedException {
		String username = "username";
		String password = "password";
		String noteTitle = "Note Title";
		String noteDescription = "Note Description";

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.addNewNote(noteTitle, noteDescription, driver);

		assertEquals(6, homePage.getNotesList(driver).size());
		homePage.deleteFirstNote(driver);
		assertEquals(4, homePage.getNotesList(driver).size());
		homePage.logout();
	}

	@Test
	@Order(6)
	public void testCredentialCreationAndView() throws InterruptedException {
		String username = "username";
		String password = "password";
		String credentialUrl = "Credential Url";
		String credentialUsername = "Credential Username";
		String credentialPassword = "Credential Password";

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.addNewCredential(credentialUrl, credentialUsername, credentialPassword, driver);
		assertEquals(credentialUrl, homePage.getFirstCredentialUrl(driver));
		assertEquals(credentialUsername, homePage.getFirstCredentialUsername(driver));
		assertNotEquals(credentialPassword, homePage.getFirstCredentialPassword(driver));
		homePage.logout();
	}

	@Test
	@Order(7)
	public void testCredentialEdit() throws InterruptedException {
		String username = "username";
		String password = "password";
		String credentialUrl = "Credential Url";
		String credentialUsername = "Credential Username";
		String credentialPassword = "Credential Password";
		String editedCredentialUrl = "Edited Credential Url";
		String editedCredentialUsername = "Edited Credential Username";
		String editedCredentialPassword = "Edited Credential Password";

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.addNewCredential(credentialUrl, credentialUsername, credentialPassword, driver);

		homePage.editCredential(editedCredentialUrl, editedCredentialUsername, editedCredentialPassword, driver);
		assertEquals(editedCredentialUrl, homePage.getFirstCredentialUrl(driver));
		assertEquals(editedCredentialUsername, homePage.getFirstCredentialUsername(driver));
		assertNotEquals(editedCredentialPassword, homePage.getFirstCredentialPassword(driver));
		String clearTextPassword = homePage.getClearTextPassword(driver);
		assertEquals(editedCredentialPassword, clearTextPassword);
	}

}
