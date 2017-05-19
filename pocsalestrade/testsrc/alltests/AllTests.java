package alltests;

import message.MessageTest;
import messageTest.MessageTypesTest;
import messagetest.MessageProcessTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import producttest.*;
import sales.SalesModifierTest;
import sales.SalesRecordsTest;

@RunWith(Suite.class)
@SuiteClasses({
    ProductListTest.class,
    MessageTest.class,
    MessageTypesTest.class,
    SalesRecordsTest.class,
    MessageProcessTest.class,
    SalesModifierTest.class
    
    })
public class AllTests
{

}
