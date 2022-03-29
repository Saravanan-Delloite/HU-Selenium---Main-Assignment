import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.*;

import org.testng.annotations.BeforeTest;

public class BaseClass
{
    static WebDriver driver;
    static int count=0;
    static String Deposit="";
    static String DebitAmount="";
    static String FullName="";
    static ExtentTest test;
    static ExtentReports extent;
    static int number=1;
    static int dep;
    static int cre;
    static Logger logger = LogManager.getLogger(BaseClass.class);

    @BeforeTest
    public static ExtentHtmlReporter getHtmlReporter() throws Exception
    {
        ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter("C:\\Users\\ksaravanakumar\\Documents\\test report\\extentReports.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test = extent.createTest("MyFirstTest", "Sample description");

        return htmlReporter;
    }
    public static WebDriver intial() throws Exception
    {
        test.log(Status.INFO,"Starting of test cases");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ksaravanakumar\\Documents\\chromedriver.exe");
        test.pass("Web driver is initialized successfully");
        driver = new ChromeDriver();
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        driver.manage().window().maximize();
        logger.info("Website is opened and window is Maximized");
        test.pass("Web pages is opened and maximized");
        return null;
    }

    public static WebDriver passUserData() throws Exception
    {
        Thread.sleep(1000);
        String line = "";
        String splitBy = ",";
        String FirstName="";
        String LastName ="";
        String pincode="";
        test.log(Status.INFO,"Accessing the excel file to get user data");
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ksaravanakumar\\Documents\\Book.csv"));
        while ((line = br.readLine()) != null)
        {
            String[] Username = line.split(splitBy);
            FirstName= Username[0];
            LastName= Username[1];
            pincode= Username[2];
            FullName=Username[3];
            Deposit=Username[4];
            dep=Integer.parseInt(Deposit);
            DebitAmount=Username[5];
            cre=Integer.parseInt(DebitAmount);
        }
        logger.info("Required user data is fetched from the file");
        test.pass("All the required user data are accessed from the file and stored in the local variable");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='Bank Manager Login']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[@class='center']//button[@class='btn btn-lg tab'][1]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[@class='form-group']//input[@placeholder='First Name']")).sendKeys(FirstName);
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[@class='form-group']//input[@placeholder='Last Name']")).sendKeys(LastName);
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[@class='form-group']//input[@placeholder='Post Code']")).sendKeys(pincode);
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[text()='Add Customer']")).click();
        test.pass("Customer Details is added to the Bank data base successfully");
        logger.info("Customer Details is added to the Bank data base successfully");
        driver.switchTo().alert().accept();
        logger.info("Alert message is accepted");
        return driver;
    }
    public static WebDriver openAccount() throws Exception
    {
        test.info("Opening account is performed");
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[2]")).click();
        Thread.sleep(500);
        driver.findElement(By.id("currency")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//option[@value='Dollar']")).click();
        Thread.sleep(500);
        WebElement userselect= driver.findElement(By.id("userSelect"));
        Thread.sleep(500);
        Select select=new Select(userselect);
        select.selectByVisibleText(FullName);
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();
        test.pass("Account created successfully");
        logger.info("Account is created successfully");
        driver.switchTo().alert().accept();
        logger.info("Alert message is accepted");
        return null;
    }
    public static WebDriver customerLogin() throws Exception
    {
        test.info("Customer login is performed with the newly created account");
        driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='Customer Login']")).click();
        Thread.sleep(500);
        WebElement userselect=driver.findElement(By.id("userSelect"));
        Select select=new Select(userselect);
        select.selectByVisibleText(FullName);
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/button")).click();
        test.pass("Customer login performed successfully");
        logger.info("Logged in successfully");
        return null;
    }
    public static WebDriver deposit() throws Exception
    {
        test.info("Money is deposited in the account and testcases are performed");
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[2]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys(Deposit);
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
        Thread.sleep(300);
        String check=driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText();
        String balance=driver.findElement(By.xpath("//strong[@class='ng-binding'][2]")).getText();
        test.pass("Confirmation message for deposit shown");
        logger.info("Confirmation message for deposit shown");
        if(balance.equals(Deposit))
        {
            System.out.println(check);
            test.pass("Deposited amount is equal to the amount deposited by the user");
            logger.info("Deposited amount is verified");
        }
        else {
            test.fail("Deposited amount is not equal to the amount in the account");
            logger.error("Desposited amount is not equal to the amount shown");
        }
        return null;
    }
    public static WebDriver withdrawl() throws Exception
    {
        test.info("Withdrawl action is performed");
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[3]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys(DebitAmount);
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
        Thread.sleep(500);
        String Balance=driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText();
        if(dep<=cre)
        {
            System.out.println("Transaction successful");
            test.pass("Balance amount is successfully updated in the page");
            logger.info("Withdrawal is successfully perfomed");
            count++;
        }
        else
        {
            System.out.println("Transaction Failed. You can not withdraw amount more than the balance.");
            test.fail("Transaction failed");
            test.fail("details", MediaEntityBuilder.createScreenCaptureFromPath("screenshot.jpg").build());
            logger.error("Withdrawal is not performed");
            throw new Exception("***You can not withdraw amount more than the balance***");
        }
        return null;
    }
    public static WebDriver transactionHistory() throws Exception
    {
        test.info("Translation history is getting checked");
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[1]")).click();
        try {
            Thread.sleep(500);
            boolean isdisplayed = driver.findElement(By.xpath("//*[@id=\"anchor0\"]/td[3]")).isDisplayed();
            if (count == 1 && isdisplayed) {
                boolean isdisplayed2 = driver.findElement(By.xpath("//td[text()='Debit']")).isDisplayed();
                if (isdisplayed2)
                {
                    System.out.println("Transaction history shown here is working properly");
                    test.pass("Transaction history shown here is working properly");
                    logger.info("Transaction history shown here is working properly");
                }
            } else if (isdisplayed) {
                System.out.println("Transaction history shown here is working properly");
                test.pass("Transaction history shown here is working properly");
                logger.info("Transaction history shown here is working properly");
            }
            else
            {
                logger.error("Transaction history is incorrect");
                throw new Exception("Transaction history is not shown");
            }
        }
        catch (Exception e)
        {
            logger.error("Transaction history is incorrect");
            test.fail("Translation history is not shown properly");
            throw new Exception("***Transaction history is incorrect***");
        }
        return null;

    }
    public void getScreenShotPass() throws IOException
    {
        String filename="PassScreenshot"+""+number+".jpg";
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File des = new File(System.getProperty("user.dir")+"/PassedScreenshot/"+filename);
        FileUtils.copyFile(src,des);
        logger.info("Screenshot action is performed for pass condition");
        number+=1;
    }
    public void getScreenShotFail() throws IOException
    {
        String filename="FailedScreenshot"+""+number+".jpg";
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File des = new File(System.getProperty("user.dir")+"/FailedScreenshot/"+filename);
        FileUtils.copyFile(src,des);
        logger.info("Screenshot option is performed for fail condition");
        number+=1;
    }
    public static WebDriver closing()
    {
        test.info("Browser is closed");
        logger.info("Browser is getting closed");
        driver.close();
        extent.flush();
        return null;
    }


}
