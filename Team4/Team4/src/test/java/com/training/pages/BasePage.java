package com.training.pages;

import com.training.constants.ApplicationConstants;
import com.training.utils.WebUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

//this class should have only common web related option
public class BasePage {
    private static Logger logger = LoggerFactory.getLogger(BasePage.class);

    protected final WebUtil scriptAction;
    protected final WebDriver driver;
    String rubbishBinOperation = "//button[text()='%s Selected']";
    String pageNamePath = "//div[@class='ui menu']/a[text()='%s']";
    String valueNamePath = "//td/a[text()='%s']/ancestor::tr//td[@class='collapsing']";
    String rubbishBinIcon = "//button[@class='ui basic button item']";
    String recordName = "//a[text()='%s']";
    String popUpOperation = " //div[@class='ui page modals dimmer transition visible active']//button[text()='%s']";
    String publicButton = "//label[text()='Access']/parent::div//button[@class='ui small fluid positive toggle button']";
    String privateButton = "//label[text()='Access']/parent::div//button[@class='ui small fluid negative toggle button']";
    String accessDropDownItems = "//div[@class='visible menu transition']//span[text()='%s']";

    String accessDropDown = "//div[@class='ui fluid multiple selection dropdown']";
    String saveBtn = "//button[text()='Save']";

    //Left Pane or Page Navigation Method
    private String lnkLeftPaneEntityName = "//div[@id='main-nav']//span[text()='%s']";

    //Page verification method
    private String pageVerification = "//span[@class = 'selectable ' and text()='%s']";
    //DropDown
    private String dropDownValue = "//div[@aria-expanded='true']/div/div/span[text()='%s']";
    private String dropDownLabelName = "//label[text()='%s']/parent::div/div[@role='listbox']";
    //Pop-up method
    private String popUpHeader = "//div[@class='ui page modals dimmer transition visible active']//div[@class='header' and text()='%s']";
    //create Method
    //filter
    String showFilterButton = "//button[@class = 'ui linkedin button' and text() = 'Show Filters']";
    String searchDrpDwn = "//div[@name='name']";
    String searchEntity = "//div[@name='name']//div//div//span[text() = '%s']";
    String operatorDrpDwn = "//div[@name='operator']";
    String operatorEquals = "//span[@class = 'text' and text() = 'Equals']";
    String searchValue = "//input[@name = 'value']";
    String searchButton = "//i[@class= 'search small icon']";
    //table actions//a[text()='%s']/../following-sibling::td[@class='right aligned collapsing options-buttons-container']//i[@class='unhide icon']
    String viewXpath = "//a[text()='%s']/../following-sibling::td[@class='right aligned collapsing options-buttons-container']//i[@class='unhide icon']";
    String editXpath = "//a[text()='%s']/../following-sibling::td[@class='right aligned collapsing options-buttons-container']//i[@class='edit icon']";
    String deleteXpath = "//a[text()='%s']/../following-sibling::td[@class='right aligned collapsing options-buttons-container']//i[@class='trash icon']";
    String viewFieldValue = "//div[@class='ui label' and text()='%s'/parent::div";
    private String createButton = "//button[@class='ui linkedin button']//i[@class='edit icon']/ancestor::button";
    //SNSComboBox
    private String comboBox = "//label[text()='%s'] //parent:: div //div[@role='combobox']//input";
    private String comboBoxValue = "//div[@aria-selected='true']/span[text()='%s']";
    private String addButton = "//div[@aria-selected='true']/span[@class='text']/b";
    //Error Messages at fields
    private String errMessages = "//span[@class='inline-error-msg']";
    //Error Messages for max input
    private String lengthErrorMsg = "//div[@class='ui error floating icon message']";
    String withoutUsersXpath = "//div[text()='Access']/parent::div/span";
    String allDetailsVerification = "//div[text()='%s']/ancestor::div/div/p[text()='%s']";

    String filterSearchTextBox = "//input[@name='value']";
    String filterComboBox = "//div[@role='combobox'][@aria-expanded='true']";
    String filterDropDownBox = "//div[@role='listbox'][@aria-expanded='true']";
    String filterSeachBox = "//div[@class='field value']";
    String filterDropDownValue ="//span[text()='%s']";
    String filterSearchAndSelectComboValue = "//div[@class='field value']//div[@name='value']//input";

    public enum EntityPanel {
        Calendar,
        Contacts,
        Companies,
        Deals,
        Tasks,
        Cases,
        Products
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.scriptAction = new WebUtil(driver);
    }

    public String takePageScreenShot() {
        return this.scriptAction.takeScreenshotAndSave();
    }

    public void rubbishBin(String pageName, String valueName, String purpose, String popUpOperation) throws InterruptedException {
        scriptAction.clickElement(By.xpath(rubbishBinIcon));
        scriptAction.waitUntilElementIsVisible(By.xpath(String.format(pageNamePath, pageName)), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(String.format(pageNamePath, pageName)));
        scriptAction.waitUntilElementIsVisible(By.xpath(String.format(valueNamePath, valueName)), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(String.format(valueNamePath, valueName)));
        scriptAction.waitTillClickableAndClick(By.xpath(String.format(rubbishBinOperation, purpose)), ApplicationConstants.MEDIUM_TIMEOUT);
        performActionsOnPopUp(popUpOperation);
    }

    public void checkRecordDisplayed(String sSearchRecord) throws Exception {
        recordVerification(sSearchRecord, true);
    }

    public void
      checkRecordNotDisplayed(String sSearchRecord) throws Exception {
        recordVerification(sSearchRecord, false);
    }

    private boolean recordVerification(String recordValue, boolean expectedConditions) throws Exception {
        String record = String.format(recordName, recordValue);
        Thread.sleep(2000);
        boolean d = scriptAction.isElementVisible(By.xpath(record), ApplicationConstants.SHORT_TIMEOUT);
        if (!d == expectedConditions) {
            throw new Exception("Record verification failed");
        }
        return true;
    }

    public void performActionsOnPopUp(String operation) {
        // String operationLoc = String.format(popUpOperation,operation);
        scriptAction.waitUntilElementIsVisible(By.xpath(String.format(popUpOperation, operation)), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(String.format(popUpOperation, operation)));
    }

    //deleteRecord("qualizeal","Cancel" or "Delete")
    public void deleteRecord(String sValue, String popUpOperation) throws Exception {
        performTableOperation(sValue, "delete");
        checkPopupIsDisplayed("Confirm Deletion");
        performActionsOnPopUp(popUpOperation);
    }

    /*Page Navigation Method*/
    public void selectEntity(String sEntityName) {
        scriptAction.waitUntilElementIsVisible(By.xpath("//i[@class='home icon']"),ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath("//i[@class='home icon']"));
        String pageName = String.format(lnkLeftPaneEntityName, sEntityName);
        scriptAction.isElementVisible(By.xpath(pageName));
        scriptAction.clickElement(By.xpath(pageName));
        checkPageHeader(sEntityName);
    }

    /*Page Verification Method*/
    public void checkPageHeader(String sValue) {
        String pageName = String.format(pageVerification, sValue);
        scriptAction.waitUntilElementIsVisible(By.xpath(pageName), ApplicationConstants.MEDIUM_TIMEOUT);
    }

    /*DropDown Method*/




    public void selectItemFromDropdown(String sDropdownLocator, String sSearchValue) {
        /*Select DropDown*/
        String dpLabelLoc = String.format(dropDownLabelName, sDropdownLocator);
        scriptAction.waitUntilElementIsVisible(By.xpath(dpLabelLoc), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(dpLabelLoc));
        /*Select option from DropDown*/
        String value = String.format(dropDownValue, sSearchValue);
        scriptAction.waitUntilElementIsVisible(By.xpath(value), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(value));
    }

    /*Create button Method*/
    public void createButton() {
        scriptAction.waitUntilElementIsVisible(By.xpath(createButton), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(createButton));
    }

    public void scroll() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,400)");

    }

    //Usage selectToggleButton("Closed","ON") or selectToggleButton("Do not Text","OFF")
    public boolean selectToggleButton(String sToggleLabel, String sOperation) {
        //sOperation = "ON","OFF"

        return true;
    }

    //Table operations methods
    public void performTableOperation(String sSearchValue, String operation) {
        switch (operation.toLowerCase()) {
            case "view": {
                scriptAction.waitUntilElementIsVisible(By.xpath(String.format(viewXpath, sSearchValue)),ApplicationConstants.SHORT_TIMEOUT,"Record Not Found");
                scriptAction.clickElement(By.xpath(String.format(viewXpath, sSearchValue)));
                //Wait till the page is displayed
                break;
            }
            case "edit": {
                scriptAction.waitUntilElementIsVisible(By.xpath(String.format(editXpath, sSearchValue)),ApplicationConstants.SHORT_TIMEOUT,"Record Not Found");
                scriptAction.clickElement(By.xpath(String.format(editXpath, sSearchValue)));
                //Wait till the page is displayed
                break;
            }
            case "delete": {
                scriptAction.waitUntilElementIsVisible(By.xpath(String.format(deleteXpath, sSearchValue)),ApplicationConstants.SHORT_TIMEOUT,"Record Not Found");
                scriptAction.clickElement(By.xpath(String.format(deleteXpath, sSearchValue)));
                break;
            }
        }
    }

    //Search and Select ComboBox
    public boolean searchNSelectItemFromList(String sSearchLocator, String value) {
        /*Select ComboBox*/
        String cBName = String.format(comboBox, sSearchLocator);
        scriptAction.isElementVisible(By.xpath(cBName), ApplicationConstants.MEDIUM_TIMEOUT);
        /*Click on selected ComboBox*/
        scriptAction.clickElement(By.xpath(cBName));
        /*Enter value in ComboBox*/
        scriptAction.inputText(By.xpath(cBName), value);
        /*verify weather value is exists or not*/
        String valueOfCB = String.format(comboBoxValue, value);
        boolean verify = scriptAction.isElementVisible(By.xpath(valueOfCB));
        /*If value exists select from it*/
        if (verify) {
            scriptAction.clickElement(By.xpath(valueOfCB));
            /*If value doesn't exist Add it*/
        } else {
            scriptAction.waitTillClickableAndClick(By.xpath(addButton), ApplicationConstants.MEDIUM_TIMEOUT);
        }

        return true;
    }

    public void errorMessagesBoundary(String expErrMsgs) {
        scriptAction.waitUntilElementIsVisible(By.xpath(lengthErrorMsg), ApplicationConstants.SHORT_TIMEOUT);
        String actual = scriptAction.getText(By.xpath(lengthErrorMsg));
        Assert.assertEquals(actual, expErrMsgs);
    }

    public void errorMessage(String sExpectedErrorMessage) {
        String sActualErrorMessage = getFieldErrorMessages();
        //wait until the error message is display
        scriptAction.waitUntilElementIsVisible(By.xpath(errMessages), ApplicationConstants.MEDIUM_TIMEOUT);
        //Assert compare error messages
        //Assert.assertEquals(sExpectedErrorMessage,sActualErrorMessage);
        Assert.assertTrue(sExpectedErrorMessage.contains(sActualErrorMessage), "error message is not matched");
    }

    private String getFieldErrorMessages() {
        String sAllErrorMessage = "";
        List<WebElement> listOfElements = scriptAction.getMatchingWebElements(By.xpath(errMessages));
        //Loop all the error message
        for (int iCounter = 0; iCounter < listOfElements.size(); iCounter++) {
            String sCurrentMessage;
            sCurrentMessage = listOfElements.get(iCounter).getText();
            sAllErrorMessage = sAllErrorMessage.concat(sCurrentMessage).concat(",");
        }
        //Remove last character
        if (sAllErrorMessage.length() > 0) {
            sAllErrorMessage = sAllErrorMessage.substring(0, sAllErrorMessage.length() - 1);
        }
        return sAllErrorMessage;
    }

    /*Page Refresh Method*/
    public void pageRefresh() {
        scriptAction.refresh();
    }
    public void pageRefresh(By elementXpath,Long timeOut) {
        boolean verify = scriptAction.isElementVisible(elementXpath,timeOut);
        if (verify==false) {
            pageRefresh();
        }
    }

    /*PopUp verification*/
    public void checkPopupIsDisplayed(String sHeaderName) {
        scriptAction.waitUntilElementIsVisible(By.xpath(String.format(popUpHeader, sHeaderName)), ApplicationConstants.MEDIUM_TIMEOUT, "Its not Displayed");
    }

    /*Private or public access */
    public void setPrivateOrPublic(String operation, String accessNames) {
        //access operation public
        switch (operation) {
            case "public":
                //verify access in public or not if its in private change it to public
                boolean action = scriptAction.isElementVisible(By.xpath(publicButton), ApplicationConstants.MEDIUM_TIMEOUT);
                //access operation for private
                if (action==false){
                    scriptAction.clickElement(By.xpath(privateButton));
                }
            break;
            case "private":

                //click on public and make it private
                scriptAction.waitTillClickableAndClick(By.xpath(publicButton), ApplicationConstants.MEDIUM_TIMEOUT);
                //click accessDropdown

                if (accessNames.length() > 0){
                    String[] names = accessNames.split(",");
                    scriptAction.waitTillClickableAndClick(By.xpath(accessDropDown), ApplicationConstants.MEDIUM_TIMEOUT);
                //getting elements from the dropdown and storing in list as web elements
                //List<String> entity = new ArrayList<>(List.of(names));
                for (String sEntity : names) {
                    String updatedLoc = String.format(accessDropDownItems, sEntity);
                    scriptAction.clickElement(By.xpath(updatedLoc));
                    //check added or not
                }
                break;
                }
        }
    }

    public void privateAccessValidation(String byUser) {
        switch (byUser) {
            case "withOutUsers":
                scriptAction.waitUntilElementIsVisible(By.xpath(withoutUsersXpath), ApplicationConstants.MEDIUM_TIMEOUT);
                String actual = scriptAction.getText(By.xpath(withoutUsersXpath));
                String expected = "Only you can see this record.";
                Assert.assertEquals(actual, expected);
                break;

            case "withUser":
                scriptAction.waitUntilElementIsVisible(By.xpath(withoutUsersXpath), ApplicationConstants.MEDIUM_TIMEOUT);
                String actual1 = scriptAction.getText(By.xpath(withoutUsersXpath));
                System.out.println(actual1);
                break;
        }
    }

    public void saveButton() {
        scriptAction.waitUntilElementIsVisible(By.xpath(saveBtn), ApplicationConstants.MEDIUM_TIMEOUT);
        scriptAction.clickElement(By.xpath(saveBtn));
    }
    public void editRecordVerification(String labelName,String sValue) {
        String re = String.format(allDetailsVerification,labelName,sValue);
        System.out.println(re);
       // scriptAction.waitUntilElementIsVisible(By.xpath(re),ApplicationConstants.MEDIUM_TIMEOUT);
    }

    //Set Filters Method
    public void setFilters(String fieldName, String value) {
        //clicking on show filter
        scriptAction.waitUntilElementIsVisible(By.xpath(showFilterButton), ApplicationConstants.SHORT_TIMEOUT);
        scriptAction.clickElement(By.xpath(showFilterButton));
        //clicking on search combo-box
        scriptAction.waitUntilElementIsVisible(By.xpath(searchDrpDwn), ApplicationConstants.SHORT_TIMEOUT);
        scriptAction.clickElement(By.xpath(searchDrpDwn));
        //select value from the search combo-box
        scriptAction.clickElement(By.xpath(String.format(searchEntity, fieldName)));
        //clicking on operator combo-box
        scriptAction.waitUntilElementIsVisible(By.xpath(operatorDrpDwn), ApplicationConstants.SHORT_TIMEOUT);
        scriptAction.clickElement(By.xpath(operatorDrpDwn));
        //clicking on Equals in operator combo-box
        scriptAction.clickElement(By.xpath(operatorEquals));

        //click on searchBox
        scriptAction.waitTillClickableAndClick(By.xpath(filterSeachBox),ApplicationConstants.SHORT_TIMEOUT);
        //verifying the type of box presented
        boolean textBox = scriptAction.isElementVisible(By.xpath(filterSearchTextBox),ApplicationConstants.SHORT_TIMEOUT);
        boolean dropDown = scriptAction.isElementVisible(By.xpath(filterDropDownBox));
        boolean SNSComboBox = scriptAction.isElementVisible(By.xpath(filterComboBox));
        if(textBox == true){
            //if text box
            scriptAction.inputText(By.xpath(searchValue), value);
        }else if(dropDown == true) {
            //if dropDown
            String dropDownValue =String.format(filterDropDownValue,value);
            scriptAction.waitUntilElementIsVisible(By.xpath(dropDownValue),ApplicationConstants.SHORT_TIMEOUT);
            scriptAction.clickElement(By.xpath(dropDownValue));
        }else if(SNSComboBox == true) {
            //if searchAndSelectComboBox
            scriptAction.inputText(By.xpath(filterSearchAndSelectComboValue),value);
            //verify the value, whether it's presented or not
            String valueOfCB = String.format(comboBoxValue, value);
            boolean verify = scriptAction.isElementVisible(By.xpath(valueOfCB));
            //If value exists select from it
            if (verify) {
                scriptAction.clickElement(By.xpath(valueOfCB));
                //If value doesn't exist Add it
            } else {
                scriptAction.waitTillClickableAndClick(By.xpath(addButton), ApplicationConstants.MEDIUM_TIMEOUT);
            }
        }
        //Click on searchButton
        scriptAction.clickElement(By.xpath(searchButton));


    }

    public String getFieldValueFromView(String sFieldLabelName) {
        String spanFieldValue = String.format(viewFieldValue, sFieldLabelName);
        scriptAction.waitUntilElementIsVisible(By.xpath(spanFieldValue), ApplicationConstants.MEDIUM_TIMEOUT);
        String sValue = scriptAction.getText(By.xpath(spanFieldValue));
        System.out.println(sValue);
        // System.out.println(scriptAction.getText(By.xpath("//div[@class='ui label' and text()='Name']/parent::div")));
        // System.out.println(scriptAction.getText(By.xpath("//div[@class='ui label' and text()='Description']/parent::div")));
        // System.out.println(scriptAction.getText(By.xpath("//div[@class='ui label' and text()='Source']/parent::div")));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return sValue;
    }

}



