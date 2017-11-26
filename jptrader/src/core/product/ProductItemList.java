package core.product;

import java.util.HashMap;

/*
 * An object to hold product information
 * 
 * Although the specification states unlimited
 * the source is external and it would be important
 * to have the ability to verify the input
 * against a list
 * 
 * for this exercise the product list will be filled
 * from the external source, and a new one added
 * 
 * The list will be case insensitive
 * 
 * 
 */

public class ProductItemList 
{
	private HashMap<String,Integer> productList = null;
	private HashMap<Integer,String>  reverseproductList = null;
	
	private int keycount = 0;
	private static ProductItemList instance=null;
	private static Object lock = new Object ();
	
	/*
	 * get the value for a string, or
	 * add a new one to the list
	 * adding with Uppercase 
	 * 
	 * If the list is to be restricted then a new function could be added 
	 * to retrieve values only
	 */
	
	
	
	public int getOrAdd(String string) 
	{
		int retval = 0;
		Integer keyval = productList.get(string.toUpperCase());	
		
		if (keyval==null)
		{
		   productList.put(string.toUpperCase(),++keycount);
		   reverseproductList.put (keycount,string.toUpperCase());
		   retval = keycount;
		}
		else
		{
			retval = keyval;
		}
		
		
		return retval;
	}
	
	// place holder
	
	private ProductItemList() 
	{
		productList = new HashMap<>();
		reverseproductList = new HashMap<>();
		keycount = 0;
		
	}
	
	public static int getProductKey (String name)
	{
		ProductItemList productlist = ProductItemList.getInstance();
		int key = productlist.get(name);
		return key;
		
	}
	public static String getProductName (int key)
	{
		ProductItemList productlist = ProductItemList.getInstance();
		String name = productlist.getName(key);
		return name;
		
	}
	
	public static ProductItemList getInstance ()
	{
		synchronized (lock) 
		{
			if (instance==null)
				instance = new ProductItemList();
		}
		return instance;
	}

	public static void Reset ()
	{
		synchronized (lock) 
		{
			instance = null;
		}
	}

	public int get(String string) 
	{
		return getOrAdd (string);
	}
	
	public String getName(int key) 
	{
		//if (key==null) return "";
		String name = reverseproductList.get (key);
		if (name==null) return "";
		return name;
	}
}
