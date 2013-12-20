package com.vaadin.testbenchexample.pageobjectexample.pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.TextFieldElement;
import com.vaadin.testbench.elements.WindowElement;

public class AddCommentPageObject extends TestBenchTestCase {

    public AddCommentPageObject(WebDriver driver) {
        setDriver(driver);
    }

    /**
     * Enters a comment in the comment text field. Does not submit the comment.
     * 
     * @param comment
     *            The text to enter in the comment field
     * @return the same AddCommentPageObject instance for method chaining.
     */
    public AddCommentPageObject enterComment(String comment) {

        // We want to find a textfield inside a Window instance - the only
        // Window we expect to be visible is the Add Comment modal dialog
        // window.
        findElement(WindowElement.class).findElement(TextFieldElement.class).sendKeys(comment,
                Keys.RETURN);
        return this;
    }

    /**
     * Clicks the 'Add' button to submit the comment entered in the text field.
     */
    public void submit() {
        findElementByCaption(ButtonElement.class, "OK").click();
    }

    /**
     * @return true if the add comment window is open.
     */
    public boolean isOpen() {
        /*
         * We get away easily here as there is only one window in the
         * application. In more complex applications we could check e.g. that
         * the element with the style name v-window-header contains the expected
         * window header string.
         */
        return isElementPresent(WindowElement.class);
    }
}
