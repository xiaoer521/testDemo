package sample.netroid.vincestyling.com.commondemo.mediation;

import java.lang.reflect.Constructor;

/**
 * Created by haipingguo on 16-12-8.
 */
public class CustomEventFactory {
    private static CustomEventFactory sInstance = new CustomEventFactory();

    static CustomEventVideo createVideoInstance(String className) throws Exception {
        return sInstance.createVideo(className);
    }

    private CustomEventVideo createVideo(String className) throws Exception {
        Class<? extends CustomEventVideo> videoClass = Class.forName(className).asSubclass(CustomEventVideo.class);
        Constructor<?> videoConstructor = videoClass.getDeclaredConstructor((Class[]) null);
        videoConstructor.setAccessible(true);
        return (CustomEventVideo) videoConstructor.newInstance();
    }
}
