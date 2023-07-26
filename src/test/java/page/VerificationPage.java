package page;

import com.codeborne.selenide.SelenideElement;
import data.AuthCode;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeField.shouldBe(visible);
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
