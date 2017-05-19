/**
 * 
 */
package messagetest;

import static org.junit.Assert.*;

import message.MessageProcess;

import org.junit.Test;

import errors.ErrorRet;

/**
 *
 *
 */
public class MessageProcessTest {

	/**
	 * Test method for {@link message.MessageProcess#checkformat(java.lang.String)}.
	 */
	@Test
	public void testCheckformat() 
	{
		MessageProcess mp = new MessageProcess();
		assertTrue("short string", mp.processMessage("dd").getError() != ErrorRet.OK);
		assertTrue("valid message, no data", mp.processMessage("#####").getError() != ErrorRet.OK);
		assertTrue("valid message too long",  mp.processMessage("0#1#3#4#5#0#1#3#4#5").getError()!= ErrorRet.OK);
		
        assertEquals("valid message sale", ErrorRet.OK, mp.processMessage("MESSAGEID=0#PRODUCTID=1#SALEVALUE=3#SALEQTY=4").getError());
        assertEquals("valid message adjust", ErrorRet.OK, mp.processMessage("MESSAGEID=1#PRODUCTID=3#SALEVALUE=2#ADJUSTID=1#ADJUSTVALUE=4").getError());
        assertEquals("valid message singlesale", ErrorRet.OK, mp.processMessage("MESSAGEID=2#SALEVALUE=1#PRODUCTID=3").getError());
	}

}
