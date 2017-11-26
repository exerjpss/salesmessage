package core.constants;

import java.util.HashMap;

public class ConstSet 
{
	public static final int maxMessageString = 512;
	public static final String TYPE_HEADER = "TYPE=";
	public static final String TYPE_SALE = "SALE";
	public static final String TYPE_ADJUSTMENT = "ADJUSTMENT";
	public static final String PARAM_AMOUNT = "AMOUNT";
	public static final String PARAM_PRODUCT = "PRODUCT";
	public static final String PARAM_QTY = "QTY";
	public static final String PARAM_ADJUSTMENT = "ADJUSTMENTTYPE";
	
	public static final String ADJ_ADD = "ADD";
	public static final String ADJ_SUB = "SUB";
	public static final String ADJ_MUL = "MUL";
	public static final int ADJ_NADD = 1;
	public static final int ADJ_NSUB = 2;
	public static final int ADJ_NMUL = 3;
	
	public static final String[] ADJ_FULLNAME = {"Unknown","Add","Subtract","Multiply"};
	public static final int ADJ_MIN = ADJ_NADD;
	public static final int ADJ_MAX = ADJ_NMUL;
	
	public static HashMap<String, Integer> adjustMap = new HashMap<>(); 
	
	
	

}
