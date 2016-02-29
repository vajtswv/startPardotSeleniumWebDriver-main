package com.kevintest.webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.lang.String;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 *
 * Created by ksong on 2/27/2016.
 * /////////////////////////////////Test Steps//////////////////////////////////
 * 1. Create a list with a random name (Marketing > Segmentation > Lists)
 2. Attempt to create another list with that same name and ensure the system correctly gives a validation failure
 3. Rename the original list
 4. Ensure the system allows the creation of another list with the original name now that the original list is renamed
 5. Create a new prospect (Prospect > Prospect List)
 6. Add your new prospect to the newly created list
 7. Ensure the new prospect is successfully added to the list upon save
 8. Send a text only email
 */
public class MyTest {

   @Test
    public void startWebDriver(){

       //WebDriver driver = new FirefoxDriver();
       String strName = GenerateNames();
       String str = SegmentList(strName);//first list
       String str2 = SegmentList(strName);//second list

    }

    //generate names
    public static String SegmentList(String str){

        //create a new instance of firefox driver
        //create a new instance of firefox driver
       WebDriver driver = new FirefoxDriver();
        //wait for page to load
        // Put a Implicit wait, this means that any search for elements on the page
        //could take the time the implicit wait is set for before throwing exception
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://pi.pardot.com/");
        driver.manage().window().maximize();

        //declare elements
        WebElement username = driver.findElement(By.id("email_address"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.cssSelector("input[type = 'submit']"));

        //perform login actions
        username.sendKeys("pardot.applicant@pardot.com");
        password.sendKeys("Applicant2012");
        login.click();

        //GOTO Marketing>Segmentation>Lists
        driver.findElement(By.linkText("Marketing")).click();
        driver.findElement(By.linkText("Segmentation")).click();
        driver.findElement(By.linkText("Lists")).click();

        //create random lists
        driver.findElement(By.id("listxistx_link_create")).click();

        //enter name
        WebElement name = driver.findElement(By.id("name"));
        name.sendKeys(str);

        //enable Dynamic List
        driver.findElement(By.id("enable_dynamic_list")).click();
        //set rules
        driver.findElement(By.id("save_information")).click();

        //check for duplicate name
        WebElement thiserror = driver.findElement(By.id("error_for_name"));
        if(driver.findElement(By.id("error_for_name")).isDisplayed()){
            System.out.println(thiserror.getText());
            WebElement name2 = driver.findElement(By.id("name"));
            String tempName2 = GenerateNames();
            name2.sendKeys("");
            name2.sendKeys(tempName2);
            //set rules
            driver.findElement(By.id("save_information")).click();
            str = tempName2;
            System.out.println("Name is unique:" +str);
        }else{
            System.out.println("Name is not unique:" +str);
        }

        //Create prospects
        driver.findElement(By.linkText("Prospects")).click();
        driver.findElement(By.linkText("Prospect List")).click();
        driver.findElement(By.id("pr_link_create")).click();//add prospect

        //prospect/////////////////////////////////////////////////////
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(str+"@pardot.com");
        WebElement campain = driver.findElement(By.id("campaign_id"));
        campain.click();
        campain.sendKeys("Adil Yellow Jackets");
        WebElement profile = driver.findElement(By.id("profile_id"));
        profile.click();
        profile.sendKeys("Adil Yellow Jackets 1");
        WebElement score = driver.findElement(By.id("score"));
        score.sendKeys("1");
        WebElement Lists = driver.findElement(By.id("toggle-inputs-lists-"));
        Lists.click();
        //create prospect button
        WebElement createprospect = driver.findElement(By.cssSelector("input[type = 'submit']"));
        createprospect.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //send email in text format////////////////////////////////////////////
        driver.findElement(By.linkText("Prospects")).click();
        driver.findElement(By.linkText("Emails")).click();

        driver.findElement(By.id("none_link_create")).click();
        //email name
        WebElement emailName = driver.findElement(By.id("name"));
        emailName.sendKeys(str);
        //compaign
        WebElement emailcompain = driver.findElement(By.id("campaign_id"));
        emailcompain.click();
        emailcompain.sendKeys("Adil Yellow Jackets");
        //subject
        WebElement subject = driver.findElement(By.id("subject"));
        subject.sendKeys(str);

        //format type
        WebElement format = driver.findElement(By.id("message_format"));
        //format.click();
        //format.click();


        //sendemail;
        //driver.findElement(By.xpath("//input[@type='button'][onclick='ckeditorSave'][@name='commit']")).click();

        //logout
        WebElement logout = driver.findElement(By.linkText("pardot.applicant@pardot.com"));
        logout.click();
        driver.findElement(By.linkText("Sign Out")).click();

        driver.close();
        driver.quit();

        return str;

    }


    public static String GenerateNames() {

        String alphanum = RandomStringUtils.randomAlphanumeric(10);
        Random rnd = new Random(); // Initialize number generator

        String firstname = "Salesforce"; // Initialize the strings
        String lastname = "pardot";

        String result; // We'll be building on this string

        // We'll take the first character in the first name
        result = Character.toString(firstname.charAt(0)); // First char
        result+=alphanum;
        if (lastname.length() > 5)
            result += lastname.substring(0,5);
        else
            result += lastname; // You did not specify what to do, if the name is shorter than 5 chars

        result += Integer.toString(rnd.nextInt(99));
        return result;

    }

}