package testcore.messages;

import static org.junit.Assert.*;

import org.junit.Test;

import core.messages.MessageItem;
import core.messages.MessageItemList;

public class TestMessageItemList 
{

	/*
	 * Create a message list object
	 * add messages 
	 * check size of message list
	 */
	@Test
	public void testBasicMessageList() 
	{
		MessageItemList msglist = new MessageItemList();
		
		MessageItem msg = null;
		
		msg = new MessageItem();
		msg.setMessage("This doesn't matter");
		msglist.add (msg);
		
		msg = new MessageItem();
		msg.setMessage("TYPE=SALE");
		msglist.add (msg);

		msg = new MessageItem();
		msg.setMessage("TYPE=ADJUSTMENT#PRODUCT=ELEPHANTS");
		msglist.add (msg);
		
		assertTrue ("testBasicMessageList items= " + msglist.getTotalMessages(),msglist.getTotalMessages()==3);
		
}

}
