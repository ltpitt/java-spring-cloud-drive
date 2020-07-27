package it.davidenastri.clouddrive.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    int timeOutInSeconds = 30;

    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTabButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTabButton;

    @FindBy(id = "nav-credential-tab")
    private WebElement navCredentialTabButton;

    @FindBy(id = "inputPassword")
    private WebElement passwordInputField;

    @FindBy(id = "submit-button")
    private WebElement loginButton;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "add-new-note-button")
    private WebElement addNewNoteButton;

    @FindBy(id = "note-title-modal")
    private WebElement noteTitleInputField;

    @FindBy(id = "note-description-modal")
    private WebElement noteDescriptionInputField;

    @FindBy(id = "noteTable")
    private WebElement noteTable;

    @FindBy(className = "note-title")
    private WebElement noteTitleText;

    @FindBy(className = "note-description")
    private WebElement noteDescriptionText;

    @FindBy(className = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(className = "delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(id = "note-save-changes-button")
    private WebElement noteSaveChangesButton;

    @FindBy(id = "add-new-credential-button")
    private WebElement addNewCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInputField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInputField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInputField;

    @FindBy(id = "credential-save-changes-button")
    private WebElement credentialSaveChangesButton;

    @FindBy(className = "credential-url")
    private WebElement credentialUrlText;

    @FindBy(className = "credential-username")
    private WebElement credentialUsernameText;

    @FindBy(className = "credential-password")
    private WebElement credentialPasswordText;

    @FindBy(className = "edit-credential-button")
    private WebElement editCredentialButton;

    @FindBy(className = "delete-credential-button")
    private WebElement deleteCredentialButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void addNewNote(String title, String description, WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(navNotesTabButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleInputField)).sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionInputField)).sendKeys(description);
        noteSaveChangesButton.click();
    }

    public void editNote(String title, String description, WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(navNotesTabButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleInputField)).sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionInputField)).sendKeys(description);
        noteSaveChangesButton.click();
    }

    public void deleteFirstNote(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();
    }

    public List<WebElement> getNotesList(WebDriver driver) {
        WebElement notesTable = driver.findElement(By.id("noteTable"));
        return notesTable.findElements(By.tagName("td"));
    }

    public String getFirstNoteTitle(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.visibilityOf(noteTitleText)).getText();
    }

    public String getFirstNoteDescription(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.visibilityOf(noteDescriptionText)).getText();
    }

    public void addNewCredential(String url, String username, String password, WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(navCredentialTabButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addNewCredentialButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrlInputField)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsernameInputField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(credentialPasswordInputField)).sendKeys(password);
        credentialSaveChangesButton.click();
    }

    public String getFirstCredentialUrl(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.visibilityOf(credentialUrlText)).getText();
    }

    public String getFirstCredentialUsername(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.visibilityOf(credentialUsernameText)).getText();
    }

    public String getFirstCredentialPassword(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.visibilityOf(credentialPasswordText)).getText();
    }

    public void editCredential(String url, String username, String password, WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(navCredentialTabButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrlInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrlInputField)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsernameInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUsernameInputField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(credentialPasswordInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(credentialPasswordInputField)).sendKeys(password);
        credentialSaveChangesButton.click();
    }

    public String getClearTextPassword(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(navCredentialTabButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
        return wait.until(ExpectedConditions.elementToBeClickable(credentialPasswordInputField)).getAttribute("value");
    }


    public void deleteFirstCredential(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(deleteCredentialButton)).click();
    }

}

