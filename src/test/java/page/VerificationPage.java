package page;

import com.codeborne.selenide.SelenideElement;
import data.AuthCode;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement notify = $("[data-test-id=error-notification]");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void notifyShouldHaveAndVisible(String text) {
        notify.shouldHave(text(text)).shouldBe(visible);
    }


    public DashboardPage validVerify() {
        codeField.clear();
        codeField.setValue(AuthCode.getValidCode().getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerify() {
        codeField.clear();
        codeField.setValue(AuthCode.getInvalidCode().getCode());
        verifyButton.click();
    }

}
