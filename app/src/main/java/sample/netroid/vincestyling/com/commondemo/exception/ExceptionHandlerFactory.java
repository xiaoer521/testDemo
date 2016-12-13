package sample.netroid.vincestyling.com.commondemo.exception;

/**
 * Created by haipingguo on 16-12-8.
 */
public class ExceptionHandlerFactory {
    public static ExceptionHandler createExceptionHandler() {
        return DefaultExceptionHandlerImpl.getsInstance();
    }
}
