package core.product;

/*
 * This is the base class for sale and adjustment classes
 * Moving common code here instead of repeating it later
 * 
 */

public class ProductItem 
{
	private int product = 0;

	protected boolean productset = false;

	public int getProduct() {return product;}
	
	protected void setProduct (int value) 
	{
		if (value>0) 
			{ 
			  product=value;
			  productset=true;
			}
	}
	
	public void setProductInfo (String value)
	{
		ProductItemList productlist = ProductItemList.getInstance();
		int val = productlist.getOrAdd(value);
		setProduct(val);
	}
	public String getProductInfo ()
	{
		ProductItemList productlist = ProductItemList.getInstance();
		String val = productlist.getName(getProduct());
		return val;
	}
	
}
