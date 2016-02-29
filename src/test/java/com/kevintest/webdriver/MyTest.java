package com.kevintest.webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.lang.String;

import org.apache.bcel.generic.Select;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;
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
 *
 */
public class MyTest {

    static WebDriver _driver;

   @Test
    public void beginDriver(){

        _driver = new FirefoxDriver();
        String strName = GenerateNames();
        String str = SegmentList(strName);//first list
        String str2 = SegmentList(strName);//second list

       closeFirefoxBrowser();
    }

    //generate names
    public static String SegmentList(String str){

        //wait for page to load
        // Put a Implicit wait, this means that any search for elements on the page
        //could take the time the implicit wait is set for before throwing exception
        _driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        _driver.get("https://pi.pardot.com/");
        _driver.manage().window().maximize();

        //declare elements
        WebElement username = _driver.findElement(By.id("email_address"));
        WebElement password = _driver.findElement(By.id("password"));
        WebElement login = _driver.findElement(By.cssSelector("input[type = 'submit']"));

        //perform login actions
        username.sendKeys("pardot.applicant@pardot.com");
        password.sendKeys("Applicant2012");
        login.click();

        //GOTO Marketing>Segmentation>Lists
        _driver.findElement(By.linkText("Marketing")).click();
        _driver.findElement(By.linkText("Segmentation")).click();
        _driver.findElement(By.linkText("Lists")).click();

        //create random lists
        _driver.findElement(By.id("listxistx_link_create")).click();

        //enter name
        WebElement name = _driver.findElement(By.id("name"));
        name.sendKeys(str);

        //enable Dynamic List
        _driver.findElement(By.id("enable_dynamic_list")).click();
        //set rules
        _driver.findElement(By.id("save_information")).click();

        //check for duplicate name
        WebElement thiserror = _driver.findElement(By.id("error_for_name"));
        if(_driver.findElement(By.id("error_for_name")).isDisplayed()){
            System.out.println(thiserror.getText());
            WebElement name2 = _driver.findElement(By.id("name"));
            String tempName2 = GenerateNames();
            name2.sendKeys("");
            name2.sendKeys(tempName2);
            //set rules
            _driver.findElement(By.id("save_information")).click();
            str = tempName2;
            System.out.println("Rename List to:" +str);
        }else{
            System.out.println("Name :" +str+" is unique.");
        }

        //Create prospects
        _driver.findElement(By.linkText("Prospects")).click();
        _driver.findElement(By.linkText("Prospect List")).click();
        _driver.findElement(By.id("pr_link_create")).click();//add prospect

        //prospect/////////////////////////////////////////////////////
        WebElement email = _driver.findElement(By.id("email"));
        email.sendKeys(str+"@pardot.com");
        WebElement campain = _driver.findElement(By.id("campaign_id"));
        campain.click();
        campain.sendKeys("Adil Yellow Jackets");
        WebElement profile = _driver.findElement(By.id("profile_id"));
        profile.click();
        profile.sendKeys("Adil Yellow Jackets 1");
        WebElement score = _driver.findElement(By.id("score"));
        score.sendKeys("1");
        WebElement Lists = _driver.findElement(By.id("toggle-inputs-lists-"));
        Lists.click();
        //create prospect button
        WebElement createprospect = _driver.findElement(By.cssSelector("input[type = 'submit']"));
        createprospect.click();
        _driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //send email in text format////////////////////////////////////////////
        _driver.findElement(By.linkText("Prospects")).click();
        _driver.findElement(By.linkText("Emails")).click();

        _driver.findElement(By.id("none_link_create")).click();
        //email name
        WebElement emailName = _driver.findElement(By.id("name"));
        emailName.sendKeys(str);
        //compaign
        WebElement emailcompain = _driver.findElement(By.id("campaign_id"));
        emailcompain.click();
        emailcompain.sendKeys("Adil Yellow Jackets");
        //subject
        WebElement subject = _driver.findElement(By.id("subject"));
        subject.sendKeys(str);

        //format type
        WebElement formatdropdown = _driver.findElement(By.id("message_format"));
        formatdropdown.isSelected();

        //sendemail;
       // _driver.findElement(By.xpath("//input[@type='button'][onclick='ckeditorSave'][@name='commit']")).click();
        WebElement select = _driver.findElement(By.cssSelector("select[id='message_format']"));
        select.click();


        //logout
        WebElement logout = _driver.findElement(By.linkText("pardot.applicant@pardot.com"));
        logout.click();
        _driver.findElement(By.linkText("Sign Out")).click();



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

    public static void closeFirefoxBrowser(){

        _driver.close();
        _driver.quit();

    }

}