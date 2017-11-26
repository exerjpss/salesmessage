package testoutput;

import java.util.LinkedList;

import output.IOutput;


public class TestIOutput implements IOutput 
{
	
	
	class Data 
	{
		int type = 0;
		String name;
		int qty;
		int value;
		int totalvalue;
		String adjName;
		int adjamount;
		String adjType;
	
	}
	
	LinkedList<Data> list = new LinkedList<>();
	int counter= 0;
	
	@Override
	public void println(String name) 
	{
		println (name,"");
		

	}

	@Override
	public void println(String name, String qty) 
	{
		println (name,qty,"");

	}

	@Override
	public void println(String name, String qty, String totalValue) 
	{
		println (name,qty,totalValue,"");

	}

	@Override
	public void println(String name, String qty, String totalValue, String value) 
	{
		counter++;
		Data info = new Data();
		
		info.name=name;
		if (!qty.equals("")) info.qty = Integer.valueOf(qty);
		if (!value.equals("")) info.value = Integer.valueOf(value);
		if (!totalValue.equals("")) info.totalvalue = Integer.valueOf(totalValue);
		
		list.add(info);
		
		

	}

	public String getName(int n) {
		return list.get(n).name;
	}

	public int getQty(int n) {
		return  list.get(n).qty;
	}

	public int getValue(int n) {
		return  list.get(n).value;
	}

	public int getTotalvalue(int n) {
		return  list.get(n).totalvalue;
	}

	public int getAdjAmount (int n) {
		return list.get(n).adjamount;
	}
	public String getAdjName (int n) {
		return list.get(n).adjName;
	}
	public String getAdjType (int n) {
		return list.get(n).adjType;
	}
	
	public int getSize () 
	{
		return list.size();
	}
	@Override
	public void printlnAdj(String adjustmentTypeString, String valueString) 
	{
		printlnAdj (adjustmentTypeString, valueString, "");

	}

	@Override
	public void printlnAdj(String adjustmentTypeString, String valueString,	String name) 
	{
		counter++;
		Data info = new Data();
		
		if (!name.equals("")) info.adjName=name;
		info.adjamount = Integer.valueOf(valueString);
		info.adjType = adjustmentTypeString;
		info.type=1;
		list.add(info);
		
	}

	@Override
	public void println() 
	{
		
		
	}

	@Override
	public void printlnAdj() 
	{
		
		
	}

}
