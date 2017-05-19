/**
 * 
 */
package message;

/**
 * 
 * 
 * Although this is an array, messages could be changed, so to get a message type the index
 * should be sought using the message id which is a string.
 * Would have preferred an array, but messages might change or be disabled, so for consistency 
 * using an array that is required to be searched in
 *
 */
public class MessageTypes
{
    public static MessageTypes [] listOfMessages;
    static 
    {
        listOfMessages = new MessageTypes [] 
                { 
                    new MessageTypes (Transaction.sale, new TransactionSale ()),
                    new MessageTypes (Transaction.adjustment, new TransactionAdj()),
                    new MessageTypes (Transaction.salesingle, new TransactionSaleSingle())
                };
    }
    
    /**
     * @return the transactionObj
     */
    public Transaction getTransactionObj()
    {
        return transactionObj;
    }
    /**
     * @param transactionObj the transactionObj to set
     */
    public void setTransactionObj(Transaction transactionObj)
    {
        this.transactionObj = transactionObj;
    }
    public MessageTypes(int messageChar, Transaction trans)
    {
        
        this.messageChar = messageChar;
        
        this.transactionObj = trans;
        
    }
    private int messageChar;
    private String messageName;
    private Transaction transactionObj;

    /**
     * @return the messageChar
     */
    public int getMessageChar()
    {
        return messageChar;
    }
    /**
     * @param messageChar the messageChar to set
     */
    public void setMessageChar(int messageChar)
    {
        this.messageChar = messageChar;
    }
    
    /**
     * @return the messageName
     */
    public String getMessageName()
    {
        return messageName;
    }
    /**
     * @param messageName the messageName to set
     */
    public void setMessageName(String messageName)
    {
        this.messageName = messageName;
    }
    public static Transaction isValidMessage(int transaction)
    {
        for (int i = 0;i<listOfMessages.length;i++)
        {
            if (listOfMessages[i].getMessageChar()==transaction)
                return listOfMessages[i].getTransactionObj();
        }
        return null;
    }
    
    
}
