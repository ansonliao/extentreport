package Utility.Thread;

/**
 * Created by AnsonLiao on 17/11/15.
 * @author AnsonLiao
 */
public class ThreadUtil {

    public synchronized static Long getCurrentThreadID() {
        return (Long) Thread.currentThread().getId();
    }
}
