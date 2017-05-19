package sales;

import static org.junit.Assert.*;

import org.junit.Test;

import errors.ErrorRet;

public class SalesModifierTest
{

    @Test
    public void testSaleModifier()
    {
        SaleModifier sm = new SaleModifier(100, 100);
        assertTrue("Salemodifier error " + sm.error, sm.error != ErrorRet.OK);
        sm = new SaleModifier(1, 100);
        assertTrue("Salemodifier error " + sm.error, sm.error == ErrorRet.OK);
        sm = new SaleModifier(1, 1335.3 * 1000000000);
        assertTrue("Salemodifier error " + sm.error, sm.error != ErrorRet.OK);

    }

    @Test
    public void testGetModifierType()
    {
        SaleModifier sm = new SaleModifier(100, 100);
        assertTrue("Salemodifier getmodifier failed " + sm.error, sm.getModifierType() == 0);
        sm = new SaleModifier(2, 100);
        assertTrue("Salemodifier getmodifier failed " + sm.error, sm.getModifierType() == 2);
        sm = new SaleModifier(-100, 100);
        assertTrue("Salemodifier getmodifier failed " + sm.error, sm.getModifierType() == 0);

    }

    @Test
    public void testSetModifierType()
    {
        SaleModifier sm = new SaleModifier(100, 100);
        sm.setModifierType(100);
        assertTrue("Salemodifier setmodifier failed " + sm.error, sm.getModifierType() == 0);
        sm.setModifierType(2);
        assertTrue("Salemodifier setmodifier failed " + sm.error, sm.getModifierType() == 2);
        sm.setModifierType(-100);
        assertTrue("Salemodifier setmodifier failed " + sm.error, sm.getModifierType() == 2);

    }

    @Test
    public void testGetValue()
    {
        SaleModifier sm = new SaleModifier(100, 100);
        assertTrue("Salemodifier getvalue failed " + sm.error, sm.getValue() != 100);
        sm = new SaleModifier(2, 100);
        assertTrue("Salemodifier getvalue failed " + sm.error, sm.getValue() == 100);
        sm = new SaleModifier(-100, 10000.4 * 1000000000);
        assertTrue("Salemodifier getvalue failed " + sm.error, sm.getValue() == 0);
    }

    @Test
    public void testSetValue()
    {
        SaleModifier sm = new SaleModifier(100, 100);
        sm.setValue(100);
        assertTrue("Salemodifier getvalue failed " + sm.error, sm.getValue() == 100);
        sm.setValue(-1000);
        assertTrue("Salemodifier getvalue failed " + sm.error, sm.getValue() == 100);
        sm.setValue(10000.4 * 1000000000);
        assertTrue("Salemodifier getvalue failed " + sm.error, sm.getValue() == 100);
    }

    

}
