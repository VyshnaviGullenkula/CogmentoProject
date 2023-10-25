package com.training.pages;

import com.training.constants.ApplicationConstants;
import com.training.utils.CommonUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class TasksPage extends BasePage {
    public TasksPage(WebDriver driver) {
        super(driver);
    }

    private static By textTitle = By.xpath("//input[@name='title']");
    private static By dueDate = By.xpath("//input[@class='calendarField']");

    public void navigateToTask() {
        selectEntity(BasePage.EntityPanel.Tasks.toString());
    }

    public void clickOnCreateButton() {
        createButton();
    }

    public void enterTaskDetails(HashMap<String, String> objTaskDetails) throws InterruptedException {

        if (objTaskDetails.containsKey("Title"))
            scriptAction.clearAndInputText(textTitle, objTaskDetails.get("Title"));
        if (objTaskDetails.containsKey("Assigned To"))
            selectItemFromDropdown("Assigned To", "Vyshnavi  Gullenkula ");
        if (objTaskDetails.containsKey("Type"))
            selectItemFromDropdown("Type", "General Support");
        if (objTaskDetails.containsKey("Due Date"))
            scriptAction.inputText(dueDate, objTaskDetails.get("Due Date"));
        if (objTaskDetails.containsKey("Company"))
            searchNSelectItemFromList("Company", "qualizeal");
    }

    public TasksPage saveTask() {

        saveButton();
        return this;
    }

    public TasksPage createTask(HashMap<String, String> taskDetails) throws InterruptedException {
        clickOnCreateButton();
        enterTaskDetails(taskDetails);
        //Thread.sleep(8000);
        saveTask();
        checkPageHeader(taskDetails.get("Title"));
        return this;
    }

    public void editTask(HashMap<String, String> editTaskDetails) throws InterruptedException {
        enterTaskDetails(editTaskDetails);
        saveTask();
    }

    public TasksPage deleteTask(String sTaskTitle) throws Exception {
//            performTableOperation(sTaskTitle,"Delete");
//            performActionsOnPopUp("Delete");
        deleteRecord(sTaskTitle, "Delete");
        return this;
    }

}
