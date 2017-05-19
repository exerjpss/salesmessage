package message;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageTest
{

    @Test
    public void testMessage()
    {
        Message msg = new Message ("MESSAGEID=2");
        assertTrue ("MessageID only",msg.hasMessageid());
        
        msg = new Message ("MESSAGEIoD=2");
        assertFalse ("No MessageID only",msg.hasMessageid());
        
        msg = new Message ("MESSAGEID=2#PRODUCTID=3");
        assertEquals("MessageID and ProductID","2",msg.getMessageid());
        assertEquals("MessageID and ProductID","3",msg.getProductid());
        
        msg = new Message ("MESSAGEID=4#PRODUCTID=2#SALEVALUE=8");
        assertEquals("MessageID and ProductID and Salevalue","4",msg.getMessageid());
        assertEquals("MessageID and ProductID and Salevalue","2",msg.getProductid());
        assertEquals("MessageID and ProductID and Salevalue","8",msg.getSalevalue());
        
        msg = new Message ("MESSAGEID=4#PRODUCTID=2#SALEVALUE=12#SALEQTY=9");
        assertEquals("MessageID and ProductID and Salevalue/qty","4",msg.getMessageid());
        assertEquals("MessageID and ProductID and Salevalue/qty","2",msg.getProductid());
        assertEquals("MessageID and ProductID and Salevalue/qty","12",msg.getSalevalue());
        assertEquals("MessageID and ProductID and Salevalue/qty","9",msg.getSalesqty());
        
        msg = new Message ("MESSAGEID=4#PRODUCTID=2#SALEVALUE=12#SALEQTY=9#ADJUSTVALUE=23#ADJUSTID=1");
        assertEquals("MessageID  ProductID  Salevalue/qty Adjustments","4",msg.getMessageid());
        assertEquals("MessageID  ProductID  Salevalue/qty Adjustments","2",msg.getProductid());
        assertEquals("MessageID  ProductID  Salevalue/qty Adjustments","12",msg.getSalevalue());
        assertEquals("MessageID  ProductID  Salevalue/qty Adjustments","9",msg.getSalesqty());
        assertEquals("MessageID  ProductID  Salevalue/qty Adjustments","23",msg.getAdjustmentvalue());
        assertEquals("MessageID  ProductID  Salevalue/qty Adjustments","1",msg.getAdjustmentid());
        
    }

}
