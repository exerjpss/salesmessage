package core.messages;

import java.util.LinkedList;

public class MessageItemList 
{

	private LinkedList<MessageItem> messages = new LinkedList<>();
	
	public void add(MessageItem msg) 
	{
		messages.add(msg);
		
	}
	public int getTotalMessages ()
	{
		return messages.size();
	}

}
