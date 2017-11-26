package testall;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testcore.adjustment.TestAdjustmentItem;
import testcore.messages.*;
import testcore.product.*;
import testcore.adjustment.*;
import testcore.sales.*;
import testcore.transactions.*;
import testoutput.*;

@RunWith(Suite.class)
@SuiteClasses({ TestAdjustmentItem.class,  
				TestProductItem.class, TestProductItemList.class,
				TestMessageItem.class, TestMessageItemList.class,
				TestSaleItem.class, TestSaleList.class,
				TestTransactionFactory.class
				
				
				})
public class AllTests {

}
