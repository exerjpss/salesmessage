package log;

/*
 * Log class so that output can be handled in a correct method in the future.
 * Currently all output is going via System.out
 */
public class LocalLog
{

    /*
     * debug - for debugging output from the program
     */
    public static void debug(String data)
    {
        System.out.println(data);
    }

    /*
     * Status - for logging output from the program
     */
    public static void status(String data)
    {
        System.out.println(data);
    }

    /*
     * output - for expected output from the program
     */
    public static void output(String data)
    {
        System.out.println(data);
    }

    /*
     * error - for expected errors from the program
     */
    public static void error(String data)
    {
        System.out.println(data);
    }
}
