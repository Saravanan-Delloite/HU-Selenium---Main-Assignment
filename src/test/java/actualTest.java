import org.testng.annotations.*;

public class actualTest extends BaseClass
{

    @Test(priority = 1)
    public void initailization() throws Exception
    {
        intial();
    }
    @Test(priority = 2)
    public void passData() throws Exception
    {
        passUserData();
    }
    @Test(priority = 3)
    public void openaccount() throws Exception
    {
        openAccount();
    }

    @Test(priority = 4)
    public void CustomerLogin() throws Exception
    {
        customerLogin();
    }
    @Test(priority = 5)
    public void depositMoney() throws Exception
    {
        deposit();
    }
    @Test(priority = 6)
    public void withdrawMoney() throws Exception
    {
        withdrawl();
    }
    @Test(priority = 7)
    public void checkTransaction() throws Exception
    {
        transactionHistory();
    }
    @AfterClass
    public void close()
    {
        closing();
    }
}
