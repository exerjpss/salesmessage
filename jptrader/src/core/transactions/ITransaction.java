package core.transactions;

import java.util.Comparator;

public interface ITransaction 
{
	//int status = 0;

	abstract void addParameter(String key, String value);
	abstract boolean isValid ();
	
	
}
