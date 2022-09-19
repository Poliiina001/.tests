package search;

import com.codeborne.selenide.*;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SearchTest {
    @Test
    public void firstTest() {
        Configuration.timeout = 5000;
        open("https://rozetka.com.ua/ua/");
        $("[name='search']").setValue("mi band 7").pressEnter();
        $x("//span[text()='Смарт-годинники']").click();
        Selenide.sleep(6000);
        $(".catalog-selection.ng-star-inserted").shouldHave(text("2"));
        int size = $$(".catalog-grid__cell.catalog-grid__cell_type_slim.ng-star-inserted").size();
        Assert.assertEquals(size, 2, "Elements aren`t found");
    }

    @Test
    public void firstHWTest() {
        Configuration.timeout = 10000;
        open("https://rozetka.com.ua/ua/");
        $("rz-cart.header-actions__component").click();
        $("h4.cart-dummy__heading").shouldHave(text("Кошик порожній"));
        // Selenide.sleep(6000);
        $("[aria-label='Закрити модальне вікно']").click();
        $("[name='search']").setValue("iphone").pressEnter();
        $("button.buy-button.goods-tile__buy-button.ng-star-inserted").click();
        $x("//span[contains(text(),'1')]");
        $("rz-cart.header-actions__component").click();
        $(".cart-receipt__submit.ng-star-inserted");
        $("[aria-controls='cartProductActions0']").click();
        $x("//button[contains(text(),'Видалити')]").click();
        $x("//h4[text()='Кошик порожній']");
    }

    @Test
    public void secondHWTest() {
        open("https://rozetka.com.ua/ua/");
        $("[name='search']").setValue("Apple").pressEnter();
        $$("li.portal-grid__cell").shouldHave(CollectionCondition.size(20));
        $("[title='iPad']").click();
        $x("//h1[contains(text(),'Apple iPad')]");
    }

    @Test
    public void thirdHWTest() {
        open("https://rozetka.com.ua/ua/");
        $("[name='search']").setValue("iphone 13").pressEnter();
        $x("//a[@class='catalog-selection__link'][contains(text(), 'iPhone 13')]");
        $("p.catalog-selection__label").shouldHave(text("Обрано 15 товарів"));
        $("[data-id='Rozetka']");
        int amountOfGoods = 15;
        $("button.compare-button").equals(amountOfGoods);
    }

    @Test
    public void fourthHWTest() {
        //Type ‘iphone 13’ to the search field.
        //Press Enter.
        //Assert size(width and height) of any product in search result.
        //Switch  grid view to big tile.
        //Assert size(width and height) of any product in search result.
        open("https://rozetka.com.ua/ua/");
        $("[name='search']").setValue("iphone 13").pressEnter();
        Dimension dimension = $(".catalog-grid__cell").getSize();
        Assert.assertEquals(dimension.getWidth(), 191, "Width Element aren`t found");
        Assert.assertEquals(dimension.getHeight(), 449, "Height Element aren`t found");

    }

    @Test
    public void fifthHWTest() {
        //Check that the first product price is bigger, that second.
        //Some additional products can be checked.
        open("https://rozetka.com.ua/ua/");
        $("[name='search']").setValue("iphone 13").pressEnter();
        $(".select-css").selectOptionByValue("2: expensive");
        $(".catalog-grid").shouldNotHave(cssClass("preloader_type_element"), Duration.ofSeconds(6));
        // sleep(5000);
        ElementsCollection prices = $$("span.goods-tile__price-value");
        //String Price2 = prices.get(1).getText().replace(" ","");
        int price1 = Integer.parseInt(prices.get(0).getText().replace(" ", ""));  // - конвертуємо в інт
        int price2 = Integer.parseInt(prices.get(1).getText().replace(" ", ""));
        Assert.assertTrue(price1 > price2, price1 + " must be bigger than " + price2);

    }
}
