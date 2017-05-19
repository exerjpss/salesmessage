/**
 * 
 */
package message;



import errors.ErrorRet;

/**
 * 
 * 
 *         MessageProcess takes the input message, which is
 *         defined to be a csv passes it to a message class, which splits the string 
 *         and stores the parameters as strings for use here
 *         
 *         Here will check that the message conforms to basic message size limits and a csv before
 *         having a transaction object process the message contents
 * 
 */
public class MessageProcess
{
   /*
     * Take a string and return a transaction object
     * If there is an error then the transaction object will contain an error field
     */
    public Transaction processMessage(String str)
    {
        Transaction trans = new Transaction();     // create default object
        trans.setError(ErrorRet.TransGeneric);     // set default error message

        if (str != null)                           // check we have some data
        {
            Message msg = new Message (str);
            if (msg.getRetval()==ErrorRet.OK)         // check the message was processed 
            {
                
                int params = msg.getParams();       

                if (params >= 1)                   // require at least one parameter
                {
                    try                            // catch any non integer 
                    {
                        int transaction = Integer.parseInt(msg.getMessageid());
                        
                                                                                // check message is in the list of messages
                        Transaction transtemp = null;                           // and receive back an approp class
                        if ((transtemp=MessageTypes.isValidMessage(transaction))!=null)
                        {
                            if (transtemp.getParams() == params)              // check parameter count for type of message
                            {
                                trans = transtemp.createTransaction(msg);    // try to create a transaction object

                            } else
                                trans.setError(ErrorRet.TransInvalidParams);  // parameter count wrong
                        } else
                            trans.setError(ErrorRet.TransUnknown);            // unknown transaction
                    } catch (Exception e)
                    {
                        trans.setError(ErrorRet.TransInvalidFormat);          // invalid int
                    }

                } else
                    trans.setError(ErrorRet.TransInvalidFormat);              // invalid format

            } else
                trans.setError(ErrorRet.TransDataTooLong);                    // exceeds max length
        } else
            trans.setError(ErrorRet.TransNoData);                             // null data  

        return trans;

    }

    
}
