package sales;

import static org.junit.Assert.*;

import org.junit.Test;

public class SaleRecordTest
{

    @Test
    public void testSetProductid()
    {
        //fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetValue()
    {
        SaleRecord sale = new SaleRecord (1,20000.4 * 1000000000);
        assertTrue ("Sale create exceeds value ", sale.getValue()==0);
        SaleRecord sale2 = new SaleRecord (1,400);
        assertTrue ("Sale create 400 ", sale2.getValue()==400);
        
    }

    @Test
    public void testAdd()
    {
        SaleRecord sale = new SaleRecord (1,200);
        sale.add(300);
        assertTrue("Sale Add test 200 + 300 = " + sale.getValue(), 500 == sale.getValue());
    }

    @Test
    public void testSub()
    {
        SaleRecord sale = new SaleRecord (1,200);
        sale.sub(30);
        assertTrue("Sale Sub test 200 - 30 = " + sale.getValue(), 170 == sale.getValue());
    }

    @Test
    public void testMult()
    {
        SaleRecord sale = new SaleRecord (1,2000);
        sale.mult(38);
        assertTrue("Sale Mult test 2000*38 = " + sale.getValue(), (2000*38) == sale.getValue());
    }

}
