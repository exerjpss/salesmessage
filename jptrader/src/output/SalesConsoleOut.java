package output;


public class SalesConsoleOut implements IOutput
{

	@Override
	public void println(String name) 
	{
		println (name,"","","");

	}

	@Override
	public void println(String name, String qty) 
	{
		println (name,qty,"","");

	}

	@Override
	public void println(String name, String qty, String totalValue) 
	{
		println (name,qty,totalValue,"");

	}

	@Override
	public void println(String name, String qty, String totalValue, String value) 
	{
		System.out.print ("Product =" + name );
		if (!qty.equals("")) System.out.print (" | Qty =" + qty );
		if (!value.equals("")) System.out.print (" | Unit Value =" + value );
		if (!totalValue.equals("")) System.out.print(" | Total value =" + totalValue);
		System.out.println();
		
		
		

	}

	@Override
	public void printlnAdj(String adjustmentTypeString, String valueString) 
	{
		printlnAdj(adjustmentTypeString, valueString, "");
		
	}

	@Override
	public void printlnAdj(String adjustmentTypeString, String valueString,	String name) 
	{
		System.out.print("---> Applied Adjustment : ");
		
		if (!adjustmentTypeString.equals("")) System.out.print(" / Type [" + adjustmentTypeString + "]");
		if (!valueString.equals("")) System.out.print(" / Value [" + valueString + "]");
		if (!name.equals("")) System.out.print(" / Name [" + name + "]");
		System.out.println();
		
		
	}

	@Override
	public void println() 
	{
		System.out.println("...........");
		
	}

	@Override
	public void printlnAdj() 
	{
		System.out.println("-----------");
		
	}

}
