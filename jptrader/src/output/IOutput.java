package output;

public interface IOutput 
{
	void println (String name);
	void println (String name, String qty);
	void println (String name, String qty, String value);
	void println (String name, String qty, String value, String totalValue);
	void printlnAdj(String adjustmentTypeString, String valueString);
	void printlnAdj(String adjustmentTypeString, String valueString, String name);
	void println ();
	void printlnAdj ();
	
}
