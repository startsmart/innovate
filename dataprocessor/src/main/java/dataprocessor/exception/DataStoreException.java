package dataprocessor.exception;

/**
 * @author 527395
 *
 */
public class DataStoreException extends Exception
{

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 7869209472009920870L;

    public DataStoreException()
    {
        super();
    }

    public DataStoreException(String message)
    {
        super(message);
    }

    public DataStoreException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DataStoreException(Throwable cause)
    {
        super(cause);
    }

    protected DataStoreException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
