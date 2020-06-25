package com.example.hotel.webTest;

import com.example.hotel.data.hotel.RoomMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/23 22:37
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutoTest {

    private static ChromeDriver browser;

    private static int port_ = 8000;

    private static String url = "http://localhost:";

    private static String name = "pkun";

    private static String phoneNumber = "12345678911";


    @BeforeClass
    public static void openBrowser(){
        System.setProperty("webdriver.chrome.driver", "E:\\不想用英文的文件夹\\大学\\大二下\\软工与计算二\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().deleteAllCookies();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        url = url + port_;
    }

    @AfterClass
    public static void closeBrowser(){
        browser.quit();
    }

    private void loginAsHotelManager(){
        browser.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("333@qq.com");
        browser.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("123456");
        browser.findElement(By.xpath("//*[@id=\"formLogin\"]/div/div[3]/div[1]/div[4]/div/div/span/button")).click();
    }

    private void loginAsAdmin(){
        browser.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("admin@qq.com");
        browser.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("123456");
        browser.findElement(By.xpath("//*[@id=\"formLogin\"]/div/div[3]/div[1]/div[4]/div/div/span/button")).click();
    }

    private void loginAsSenior(){
        browser.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("1012681@qq.com");
        browser.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("123456");
        browser.findElement(By.xpath("//*[@id=\"formLogin\"]/div/div[3]/div[1]/div[4]/div/div/span/button")).click();
    }

    private void loginAsClient(){
        browser.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("123@qq.com");
        browser.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("123456");
        browser.findElement(By.xpath("//*[@id=\"formLogin\"]/div/div[3]/div[1]/div[4]/div/div/span/button")).click();
    }

    private void userInfoCenterBottom(){
        browser.findElement(By.xpath("/html/body/div/div/div[1]/ul/li[4]")).click();
    }

    private final static String changeUserInfoBottom = "/html/body/div[1]/div/div[2]/div/div[3]/div[1]/form/div[6]/div/div/span";

    private void hotelInfoCenterBottom(){
        browser.findElement(By.xpath("/html/body/div/div/div[1]/ul/li[4]/a")).click();
    }

    private void couponStrategyBottom(){
        browser.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[1]/div[3]/div[1]/form/div[7]/div/div/span/button[3]")).click();
    }

    private void addCouponBottom(){
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/div[1]/button")).click();
    }

    //TODO 用browser打开网页，然后用selenium进行页面测试，在springbooot web测试中似乎没法使用mapper层或者service层的类



    @Test
    public void Test01BookHotel(){
        browser.get(url);
        loginAsSenior();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/section/main/div[2]/div/div/div/div[1]")).click();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/section/main/div/div[3]/div[3]/div[1]/div[2]/div[2]/div/div/div/div/div/div/table/tbody/tr[1]/td[6]/span/button")).click();
        browser.findElement(By.xpath("//*[@id=\"orderModal_clientName\"]")).sendKeys(name);
        browser.findElement(By.xpath("//*[@id=\"orderModal_phoneNumber\"]")).sendKeys(phoneNumber);
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/form/div[4]/div[2]")).click();
        browser.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div[1]/div[2]/div[2]/table/tbody/tr[4]/td[7]")).click();
        browser.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[5]")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/form/div[5]/div[2]")).click();
        browser.findElement(By.xpath("/html/body/div[4]/div/div/div/ul/li[2]")).click();
        browser.findElement(By.xpath("//*[@id=\"orderModal_haveChild\"]/label[2]/span[1]")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/form/div[7]/div[2]")).click();
        browser.findElement(By.xpath("/html/body/div[5]/div/div/div/ul/li[2]")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[3]/div/button[2]")).click();
        browser.navigate().refresh();
        Assert.assertEquals("18", browser.findElement(By.xpath("//*[@id=\"layout\"]/section/main/div/div[3]/div[3]/div[1]/div[2]/div[2]/div/div/div/div/div/div/table/tbody/tr[1]/td[2]")).getText());
    }

    @Test
    public void Test02AnnualOrder() throws InterruptedException{
        browser.get(url);
        loginAsSenior();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[1]/ul/li[4]")).click();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div/div[1]/div/div/div/div/div[1]/div[2]")).click();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div/div[3]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr[1]/td[9]/span/button[2]")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div[2]/button[2]")).click();
        Thread.sleep(1000);
        Assert.assertEquals("撤销成功", browser.findElement(By.xpath("/html/body/div[2]/span")).getText());
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div/div[1]/div/div/div/div/div[1]/div[1]")).click();
        browser.navigate().refresh();
        Thread.sleep(1000);
        Assert.assertEquals("-99.5", browser.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/div[1]/form/div[5]/div[2]/div/span/span")).getText());
    }

    @Test
    public void Test03ShowHotelsList() throws InterruptedException{
        browser.get(url);
        loginAsSenior();
        browser.findElement(By.xpath("/html/body/div[1]/div/div[2]/section/main/div[1]/span[1]/input")).sendKeys("栖霞");
        browser.findElement(By.xpath("/html/body/div/div/div[2]/section/main/div[1]/span[1]/button[1]")).click();
        Thread.sleep(1000);
        Assert.assertEquals("桂圆酒店", browser.findElement(By.xpath("/html/body/div/div/div[2]/section/main/div[2]/div/div/div/div[1]/div[2]/div/div/div[1]")).getText());
        Assert.assertEquals("南大会议国际中心", browser.findElement(By.xpath("/html/body/div/div/div[2]/section/main/div[2]/div/div/div/div[2]/div[2]/div/div/div[1]")).getText());
    }

    @Test
    public void Test04ShowHotelDetail() throws InterruptedException{
        browser.get(url);
        loginAsSenior();
        browser.findElement(By.xpath("/html/body/div/div/div[2]/section/main/div[2]/div/div/div/div[1]")).click();
        browser.findElement(By.xpath("/html/body/div/div/section/main/div/div[3]/div[1]/div/div/div/div/div[1]/div[2]")).click();
        Thread.sleep(1000);
        Assert.assertEquals("酒店名称 : 汉庭酒店", browser.findElement(By.xpath("/html/body/div/div/section/main/div/div[3]/div[3]/div[2]/div[2]/span[1]")).getText());
    }

    @Test
    public void Test05SearchHotel() throws InterruptedException{
        browser.get(url);
        loginAsSenior();
        browser.findElement(By.xpath("/html/body/div[1]/div/div[2]/section/main/div[1]/span[1]/input")).sendKeys("栖霞");
        browser.findElement(By.xpath("/html/body/div/div/div[2]/section/main/div[1]/span[1]/button[1]")).click();
        Thread.sleep(1000);
        Assert.assertEquals("桂圆酒店", browser.findElement(By.xpath("/html/body/div/div/div[2]/section/main/div[2]/div/div/div/div[1]/div[2]/div/div/div[1]")).getText());
        Assert.assertEquals("南大会议国际中心", browser.findElement(By.xpath("/html/body/div/div/div[2]/section/main/div[2]/div/div/div/div[2]/div[2]/div/div/div[1]")).getText());
    }

    @Test
    public void Test06ShowOrderDetail() throws InterruptedException {
        browser.get(url);
        loginAsSenior();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[1]/ul/li[4]")).click();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div/div[1]/div/div/div/div/div[1]/div[2]")).click();
        browser.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[3]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr[1]/td[9]/span/button[1]")).click();
        Thread.sleep(1500);
        Assert.assertEquals("酒店名称 : 桂圆酒店", browser.findElement(By.xpath("/html/body/div[6]/div/div[2]/div/div[2]/div[2]/div/span[2]")).getText());
        Assert.assertEquals("预定入住时间 : 2020-06-03", browser.findElement(By.xpath("/html/body/div[6]/div/div[2]/div/div[2]/div[2]/div/span[3]")).getText());
    }

    @Test
    public void Test07evaluateOrder(){
        browser.get(url);
        loginAsHotelManager();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[1]/ul/li[4]/a")).click();
        browser.findElement(By.xpath("/html/body/div/div/div[2]/div[1]/div[1]/div/div/div/div/div[1]/div[2]")).click();
        browser.findElement(By.xpath("/html/body/div/div/div[2]/div[1]/div[3]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr[6]/td[10]/span/button[1]")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/div/span[1]/span[2]/span[2]/button")).click();
        Assert.assertEquals("订单状态 : 已预订", browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/div/span[1]/span[2]/span")).getText());
    }

    @Test
    public void Test08RegisterSenior(){
        browser.get(url);
        loginAsClient();
        browser.findElement(By.xpath("/html/body/div/div/div[1]/ul/li[4]")).click();
        browser.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/div[1]/form/div[2]/div[2]/div/span/button")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/div/div/div[3]/div[1]/form/div/div[2]/div/span/span")).click();
        browser.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[2]/div[3]/span/a")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[3]/div/button[2]")).click();
        Assert.assertEquals("commonSeniorClient", browser.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[3]/div[1]/form/div[2]/div[2]/div/span/span")).getText());
    }

    @Test
    public void Test09ManageUserInfo(){
        browser.get(url);
        loginAsSenior();
        userInfoCenterBottom();
        browser.findElement(By.xpath(changeUserInfoBottom)).click();
        browser.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[3]/div[1]/form/div[4]/div[2]/div/span/input")).sendKeys("");
        browser.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[3]/div[1]/form/div[4]/div[2]/div/span/input")).sendKeys("11987654321");
        browser.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[3]/div[1]/form/div[7]/div/div/span/button[1]")).click();
        browser.navigate().refresh();
        Assert.assertEquals("1234567891911987654321", browser.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[3]/div[1]/form/div[4]/div[2]/div/span")).getText());
    }

    @Test
    public void Test10AddCoupon(){
        browser.get(url);
        loginAsHotelManager();
        hotelInfoCenterBottom();
        couponStrategyBottom();
        addCouponBottom();
        browser.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div[2]/div[2]/form/div[1]/div[2]/div/span/div/div/div")).click();
        browser.findElement(By.xpath("/html/body/div[4]/div/div/div/ul/li[2]")).click();
        browser.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div[2]/div[2]/form/div[2]/div[2]/div/span/input")).sendKeys("测试");
        browser.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div[2]/div[2]/form/div[3]/div[2]/div/span/textarea")).sendKeys("testtesttest");
        browser.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div[2]/div[2]/form/div[4]/div[2]/div/span/input")).sendKeys("150");
        browser.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div[2]/div[2]/form/div[5]/div[2]/div/span/input")).sendKeys("150");
        browser.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div[2]/div[3]/div/button[2]")).click();
        //TODO
    }

    @Test
    public void Test11ManageHotelInfo(){

    }

    @Test
    public void Test12ManageRoomInfo(){

    }

    @Test
    public void Test13ExecuteOrder(){

    }

    @Test
    public void Test14ShowExceptionOrder(){

    }

    @Test
    public void Test15ManageUserInfoAsAdmin(){

    }

    @Test
    public void Test16ManageHotelManager(){

    }


}
