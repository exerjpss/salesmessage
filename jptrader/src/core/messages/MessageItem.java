package core.messages;

import java.util.LinkedList;

import core.constants.ConstSet;
import core.constants.ErrorCodes;
import core.transactions.ITransaction;
import core.transactions.TransactionFactory;

/*
 * Class to handle messages
 * Process message breaks down an input string and
 * stores information about the message
 */

public class MessageItem 
{
	String raw = null;
	LinkedList<ITransaction>  transactionList = new LinkedList<>();

	/*
	 * Set the message, but check within message limits
	 */
	public int setMessage(String string) 
	{
		int retval = ErrorCodes.ERR_OK;
		if (string.length() > ConstSet.maxMessageString)
			retval = ErrorCodes.ERR_STRING_TOO_LONG;
		else
			raw = string;
		return retval;
	}

	public String getMessage() 
	{
		
		return raw;
	}

	/*
	 * ProcessMessage
	 * 
	 * we have a string set in raw data
	 * string format is expected # delimited and key=value setting
	 * process each item, and loop through parameters
	 * if parameters are all present and object is created add to the list of object
	 * if not all present or error, then add to list of object with error set
	 * 
	 */
	
	public int processMessage() 
	{
		int retval=ErrorCodes.ERR_OK;
		if (raw==null)
			retval = ErrorCodes.ERR_NULLDATA;
		transactionList = new LinkedList<>();
		String [] data;
		if (retval==ErrorCodes.OK)
		{
			data = raw.split("#");
			// expect format : Type : parameters
			int n=0;
			ITransaction transaction = null;
			while (n < data.length)
			{
				if (data[n].toUpperCase().startsWith(ConstSet.TYPE_HEADER))
				{
					
					String typename = data[n].substring(ConstSet.TYPE_HEADER.length());
					
					transaction = TransactionFactory.generateTransaction(typename);
					transactionList.add (transaction);
				}
				else
				{
					String parameter[] = data[n].split("=");
					
					if (parameter.length==2)
						if (transaction != null)
							transaction.addParameter (parameter[0],parameter[1]);
				}
				n++;
			}
			
			
		}
		return retval;
	}

	public LinkedList<ITransaction> getTransactionList() 
	{
		return transactionList;
	}
	
}
