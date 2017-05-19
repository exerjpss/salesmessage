/**
 * 
 */
package errors;

/**
 * Class to hold error codes
 *
 */
public class ErrorRet
{
    public final static int OK = 0;
    public final static int TransGeneric = -1;
    public final static int TransNoData = -2;
    public final static int TransDataTooLong = -3;
    public final static int TransInvalidFormat = -4;
    public final static int TransUnknown = -5;
    public final static int TransInvalidParams = -6;
    public static final int TransValueOutOfRange = -7;
    public static final int TransQtyOutOfRange = -8;
    public static final int TransAdjTypeOutOfRange = -9;
    
}
