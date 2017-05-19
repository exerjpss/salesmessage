package product;

/**
 *
 * 
 *         Class to hold product information, for now the only information will be product type and product name
 *         place holder
 * 
 */
public class Product
{

    private int producttype;
    private String productname;

    public Product(int producttype, String productname)
    {
        this.producttype = producttype;
        this.productname = productname;
    }

    /**
     * @return the producttype
     */
    public int getProducttype()
    {
        return producttype;
    }

    /**
     * @param producttype
     *            the producttype to set
     */
    public void setProducttype(int producttype)
    {
        this.producttype = producttype;
    }

    /**
     * @return the productname
     */
    public String getProductname()
    {
        return productname;
    }

    /**
     * @param productname
     *            the productname to set
     */
    public void setProductname(String productname)
    {
        this.productname = productname;
    }

}
