package it.davidenastri.clouddrive;

import io.github.bonigarcia.wdm.WebDriverManager;
import it.davidenastri.clouddrive.page.HomePage;
import it.davidenastri.clouddrive.page.LoginPage;
import it.davidenastri.clouddrive.page.SignUpPage;
import it.davidenastri.clouddrive.services.NoteService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	public String baseURL;

	@LocalServerPort
	private int port;
	private WebDriver driver;
	@Autowired
	NoteService noteService;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void verifyThatUnauthoziedUserCanAccessOnlyLoginAndSignUp() {
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void testUserSignupLoginLogout() throws InterruptedException {
		String username = "username1";
		String password = "password1";

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
	public void testNoteCreationAndView() throws InterruptedException {
		String username = "username2";
		String password = "password2";
		String noteTitle = "Note Title 2";
		String noteDescription = "Note Description 2";

		driver.get(baseURL + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signUp("User", "2", username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.addNewNote(noteTitle, noteDescription, driver);
		assertEquals(noteTitle, homePage.getFirstNoteTitle(driver));
		assertEquals(noteDescription, homePage.getFirstNoteDescription(driver));
	}

	@Test
	public void testNoteEdit() throws InterruptedException {
		String username = "username3";
		String password = "password3";
		String noteTitle = "Note Title 3";
		String noteDescription = "Note Description 3";
		String editedNoteTitle = "Edited Note Title 3";
		String editedNoteDescription = "Edited Note Description 3";

		driver.get(baseURL + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signUp("User", "3", username, password);

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

	}

	@Test
	public void testNoteDeletion() throws InterruptedException {
		String username = "username4";
		String password = "password4";
		String noteTitle = "Note Title 4";
		String noteDescription = "Note Description 4";

		driver.get(baseURL + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signUp("User", "4", username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.addNewNote(noteTitle, noteDescription, driver);

		assertEquals(2, homePage.getNotesList(driver).size());
		homePage.deleteFirstNote(driver);
		assertEquals(0, homePage.getNotesList(driver).size());

	}

	@Test
	public void testCredentialCreationAndView() throws InterruptedException {
		String username = "username5";
		String password = "password5";
		String credentialUrl = "Credential Url 5";
		String credentialUsername = "Credential Username 5";
		String credentialPassword = "Credential Password 5";

		driver.get(baseURL + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signUp("User", "2", username, password);

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
	}

	@Test
	public void testCredentialEdit() throws InterruptedException {
		String username = "username6";
		String password = "password6";
		String credentialUrl = "Credential Url 6";
		String credentialUsername = "Credential Username 6";
		String credentialPassword = "Credential Password 6";
		String editedCredentialUrl = "Edited Credential Url 6";
		String editedCredentialUsername = "Edited Credential Username 6";
		String editedCredentialPassword = "Edited Credential Password 6";

		driver.get(baseURL + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signUp("User", "2", username, password);

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
