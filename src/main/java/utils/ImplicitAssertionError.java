package utils;

/**
 * This should be thrown when Implicit Assertion fails.
 * 
 * <p>Implicit assertion is written as part of the DSL API implementation.
 * Every action should make sure it leaves the application in a ready state.
 * To achieve that we write code that takes the application to a ready state.
 * If this fails for some reason Implicit Assertion is thrown
 * 
 * @see com.plateau.common.test.automation.framework.assertion.ExplicitAssertionError
 * @author VDevarajan
 *
 */
public class ImplicitAssertionError extends AssertionError {

	private static final long serialVersionUID = 1L;

	public ImplicitAssertionError(final String errorMessage) {
		super(errorMessage);
	}
}
