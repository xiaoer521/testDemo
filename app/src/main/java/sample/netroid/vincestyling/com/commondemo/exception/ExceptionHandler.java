package sample.netroid.vincestyling.com.commondemo.exception;

/**
 * Created by haipingguo on 16-12-8.
 */
public interface ExceptionHandler {
    void handleException(Exception e, String tag);

    void handleException(Exception e);

    void handleError(Error e);
}
