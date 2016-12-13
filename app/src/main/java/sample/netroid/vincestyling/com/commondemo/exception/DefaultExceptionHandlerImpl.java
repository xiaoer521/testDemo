package sample.netroid.vincestyling.com.commondemo.exception;

/**
 * Created by haipingguo on 16-12-8.
 */
public class DefaultExceptionHandlerImpl implements ExceptionHandler {
    private static DefaultExceptionHandlerImpl sInstance;

    public static synchronized DefaultExceptionHandlerImpl getsInstance() {
        if (sInstance == null) {
            sInstance = new DefaultExceptionHandlerImpl();
        }
        return sInstance;
    }

    @Override
    public void handleException(Exception e, String tag) {

    }

    @Override
    public void handleException(Exception e) {

    }

    @Override
    public void handleError(Error e) {

    }
}
