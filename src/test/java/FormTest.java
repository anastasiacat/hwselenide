import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.text.SimpleDateFormat;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;
import java.util.Calendar;

public class FormTest {

    static String dateOfVisit;

    @BeforeAll
    static void CalculateTheDate() {
        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        int days = 3;
        cal.add(Calendar.DAY_OF_MONTH, days);
        dateOfVisit = date.format(cal.getTime());
    }

    @BeforeEach
    void OpenBrowser() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldTestBePassed() {
        $x("//span[@data-test-id='city']//input").setValue("Казань");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("Имя Фамилия");
        $("[name='phone']").setValue("+79991112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//div[contains(text(), 'Встреча успешно забронирована')]").should(Condition.appear, Duration.ofSeconds(15));
        $x("//div[contains(@class,'notification_visible')]").shouldBe(Condition.visible);
    }

    @Test
    void shouldTestBePassed2() {
        $x("//span[@data-test-id='city']//input").setValue("Санкт-Петербург");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("Имя Фамилия-Фамилия");
        $("[name='phone']").setValue("+79991112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//div[contains(text(), 'Встреча успешно забронирована')]").should(Condition.appear, Duration.ofSeconds(15));
        $x("//div[contains(@class,'notification_visible')]").shouldBe(Condition.visible);
    }

    @Test
    void shouldTestEmptyForm() {
        $x("//span[@data-test-id='city']//input").setValue("");
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//span[text()='Поле обязательно для заполнения']").should(Condition.appear);
    }

    @Test
    void shouldTestInvalidCity() {
        $x("//span[@data-test-id='city']//input").setValue("Казан");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("Имя Фамилия");
        $("[name='phone']").setValue("+79991112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//span[text()='Доставка в выбранный город недоступна']").should(Condition.appear);
    }

    @Test
    void shouldTestEmptyCity() {
        $x("//span[@data-test-id='city']//input").setValue("");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("Имя Фамилия");
        $("[name='phone']").setValue("+79991112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//span[text()='Поле обязательно для заполнения']").should(Condition.appear);
    }

    @Test
    void shouldTestInvalidDate() {
        SimpleDateFormat errorDate = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        String invalidDateOfVisit = errorDate.format(cal.getTime());

        $x("//span[@data-test-id='city']//input").setValue("Казань");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(invalidDateOfVisit);
        $("[name='name']").setValue("Имя Фамилия");
        $("[name='phone']").setValue("+79991112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//span[text()='Заказ на выбранную дату невозможен']").should(Condition.appear);
    }

    @Test
    void shouldTestEmptyDate() {
        $x("//span[@data-test-id='city']//input").setValue("Казань");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue("");
        $("[name='name']").setValue("Имя Фамилия");
        $("[name='phone']").setValue("+79991112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//span[text()='Неверно введена дата']").should(Condition.appear);
    }

    @Test
    void shouldTestInvalidNameAndSurname() {
        $x("//span[@data-test-id='city']//input").setValue("Казань");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("Name Surname");
        $("[name='phone']").setValue("+79991112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//*[contains(text(), 'Имя и Фамилия указаные неверно')]").should(Condition.appear);
    }

    @Test
    void shouldTestEmptyNameAndSurname() {
        $x("//span[@data-test-id='city']//input").setValue("Казань");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("+79991112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//span[text()='Поле обязательно для заполнения']").should(Condition.appear);
    }

    @Test
    void shouldTestInvalidPhone() {
        $x("//span[@data-test-id='city']//input").setValue("Казань");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("Имя Фамилия");
        $("[name='phone']").setValue("79991112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//*[contains(text(), 'Телефон указан неверно')]").should(Condition.appear);
    }

    @Test
    void shouldTestEmptyPhone() {
        $x("//span[@data-test-id='city']//input").setValue("Казань");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("Имя Фамилия");
        $("[name='phone']").setValue("");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//span[text()='Поле обязательно для заполнения']").should(Condition.appear);
    }

    @Test
    void shouldTestInvalidPhoneCountOfNumbersMoreThanNeeded() {
        $x("//span[@data-test-id='city']//input").setValue("Казань");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("Имя Фамилия");
        $("[name='phone']").setValue("+799911122333");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//*[contains(text(), 'Телефон указан неверно')]").should(Condition.appear);
    }

    @Test
    void shouldTestInvalidPhoneCountOfNumbersLessThanNeeded() {
        $x("//span[@data-test-id='city']//input").setValue("Казань");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("Имя Фамилия");
        $("[name='phone']").setValue("+7999111223");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $x("//*[contains(text(), 'Телефон указан неверно')]").should(Condition.appear);
    }

    @Test
    void shouldTestUncheckedCheckbox() {
        $x("//span[@data-test-id='city']//input").setValue("Казань");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $x("//span[@data-test-id='date']//input").setValue(dateOfVisit);
        $("[name='name']").setValue("Имя Фамилия");
        $("[name='phone']").setValue("+79991112233");
        $("[class='button__text']").click();
        $x("//label[contains(@class,'input_invalid')]").should(Condition.appear);
    }
}
