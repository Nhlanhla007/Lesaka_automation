/* -----------------------------------------------------------------------------
 * Copyright (c) 2008 Plateau Systems, Ltd.
 *
 * This software and documentation is the confidential and proprietary
 * information of Plateau Systems.  Plateau Systems
 * makes no representation or warranties about the suitability of the
 * software, either expressed or implied.  It is subject to change
 * without notice.
 *
 * U.S. and international copyright laws protect this material.  No part
 * of this material may be reproduced, published, disclosed, or
 * transmitted in any form or by any means, in whole or in part, without
 * the prior written permission of Plateau Systems.
 * -----------------------------------------------------------------------------
 */

package utils;

/**
 * Extend this class and override the testCondition() method to pass in a closure into
 * the <code>waitFor()</code> method.  This class method will be executed until it times
 * out or returns true.
 * 
 * @author SHamilton
 */
public interface SyncCondition {
	/**
	 * Perform a test condition, returning true if the condition passed.  The sync loop
	 * will break when this testCondition() returns true or the loop times out.
	 * @return result of test condition
	 */
	public boolean testCondition();
}
