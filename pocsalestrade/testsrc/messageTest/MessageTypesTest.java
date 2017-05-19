package messageTest;

import static org.junit.Assert.*;

import message.MessageTypes;

import org.junit.Test;

public class MessageTypesTest
{

    
    int m;
    
    @Test
    public void test()
    {

        assertNull ("9" , MessageTypes.isValidMessage(9));
        assertNotNull ("0", MessageTypes.isValidMessage(0));
        assertNotNull ("1", MessageTypes.isValidMessage(1));
        assertNotNull ("2", MessageTypes.isValidMessage(2));
        
    }

}
