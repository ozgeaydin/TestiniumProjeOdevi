package com.test;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Author: Özge Aydın
 */

public class AppTest 
{
    protected WebDriver driver;

    //Test otomasyonundan once gerekli olan hazirliklarin yapildigi bolum.
    @Before
    public void setUp() throws Exception{
        DesiredCapabilities capabilities=DesiredCapabilities.chrome();
        System.setProperty("webdriver.chrome.driver","C:\\Users\\ozgea\\IdeaProjects\\testuygulama\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        //driver.manage().timeouts().setScriptTimeout(60,TimeUnit.SECONDS);
        //driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    }

    //Test fonksiyonlarini tek tek calistirdigim icin bunu kapattim.
    /*@After
    public void tarayiciKapa(){
        driver.quit();
    }*/


    //Gittigidiyor.com sitesi acilir.
    @Test
    public void siteyeGir(){
        driver.get("http://www.gittigidiyor.com");
    }

    //Siteye login olunur. Login islemi kontrol edilir.
    //Test icin bir ornek hesap olusturdum kendime. Oradaki bilgilerle giris islemi yapiyorum.
    @Test
    public void loginKontrol(){
        driver.get("https://www.gittigidiyor.com/uye-girisi");
        WebElement txtbox_kullaniciAdi=driver.findElement(By.name("kullanici"));
        txtbox_kullaniciAdi.sendKeys("ozgeaydin335891");
        driver.findElement(By.name("sifre")).sendKeys("deneme123deneme");
        driver.findElement(By.name("pass")).click();
    }

    //Arama kutucuguna bilgisayar kelimesi girilir. Arama butonuna basilir.
    @Test
    public void aramaYap(){
        driver.get("https://www.gittigidiyor.com/");
        WebElement bileseniBul=driver.findElement(By.name("k"));
        bileseniBul.sendKeys("bilgisayar");
        driver.findElement(By.cssSelector("button.qjixn8-0.sc-1bydi5r-0.hKfdXF")).click();
    }

    //Sayfadaki 1 2 3 diye ilerleyen butonlar link yonlendirmesi seklindeydi.
    //Ileri geri fonksiyonlarini kullanamadim bu nedenle dogrudan sayfaya yonlendirdim.
    //Cookieleri temizlemedigim icin login sessioni devam ediyordu.
    @Test
    public void sayfaDegistir(){
        driver.get("https://www.gittigidiyor.com/arama/?k=bilgisayar");
        driver.get("https://www.gittigidiyor.com/arama/?k=bilgisayar&sf=2");
    }

    // Baslangicta, 2.sayfadan rastgele bir urun secilip sepete at tusuna basilacak
    // diye dusundugum icin bu sekilde denedim. Fakat hover durumundaki
    // butonun bilgilerini alip urunu sepete ekleyemedim. Sizinle gorusup
    // isteneni anladiktan sonra o sekilde ilerledim.
    @Test
    public void urunSecSepeteAt(){
        //Random rnd=new Random();
        //int index=rnd.nextInt(45)+1; //48 ürün var
        //driver.get("https://www.gittigidiyor.com/arama/?k=bilgisayar&sf=2");
        //List<WebElement> urunListesi=driver.findElements(By.cssSelector("ul[class='button gg-ui-button gg-ui-btn-primary']"));
        //urunListesi.get(index).click();
    }


    @Test
    public void sepettekileriKarsilastir (){
        driver.get("https://www.gittigidiyor.com/dizustu-laptop-notebook-bilgisayar/i-life-zedair-cx5_spp_840732?id=646934313");

        // Sergilenen urunlerden rastgele bir urun secilir. Secilen urun sepete eklenir.
        WebElement myelement = driver.findElement(By.id("add-to-basket"));
        WebDriverWait wait2 = new WebDriverWait(driver, 10);
        JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        jse2.executeScript("arguments[0].scrollIntoView()", myelement);
        myelement.click();
        WebDriverWait wait3 = new WebDriverWait(driver, 10);
        myelement.click();

        //Eklenen urun her debugta farkli sonuc verdigi icin araya bekleme molalari eklendi.
        WebDriverWait wait4 = new WebDriverWait(driver, 30);
        //Urun sayfasındaki fiyat.
        WebElement orjinalFiyat=driver.findElement(By.xpath("//*[@id=\"sp-price-highPrice\"]"));
        WebDriverWait wait5 = new WebDriverWait(driver, 40);
        //Sepete giden fonksiyon.
        WebElement sepeteGidis=driver.findElement(By.xpath("//*[@id=\"header_wrapper\"]/div[4]/div[3]/a"));
        sepeteGidis.click();
        WebDriverWait wait6 = new WebDriverWait(driver, 30);
        //Sepette yer alan urunun fiyati.
        WebElement sepettekiFiyat=driver.findElement(By.xpath("//*[@id=\"submit-cart\"]/div/div/div[3]/div/div[1]/div/div[5]/div[1]/div/ul/li[1]/div[2]"));

        //Urun sayfasindaki fiyat ile sepetteki urun fiyatinin dogrulugu karsilastirilir.
        if(sepettekiFiyat.equals(orjinalFiyat))
            System.out.println("Comparision successful");
        else
            System.out.println("Comparision unsuccessful");

        //Adet arttirilarak urun adedinin 2 olduğu doğrulanir.
        WebDriverWait wait7 = new WebDriverWait(driver, 30);
        WebElement urunMiktariArttirma=driver.findElement(By.xpath("//*[@id=\"cart-item-455094516\"]/div[2]/div[4]/div/span[2]"));
        urunMiktariArttirma.click();

        //Urun sepetten silinerek sepetin bos oldugu kontrol edilir.
        WebElement urunSilme=driver.findElement(By.xpath("//*[@id=\"cart-item-455084778\"]/div[2]/div[3]/div/div[2]/div/a[1]/span"));
        urunSilme.click();

    }

}
