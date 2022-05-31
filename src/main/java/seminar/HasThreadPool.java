package seminar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface HasThreadPool {
    Executor SERVICE_1_THREAD_POOL = Executors.newFixedThreadPool(4);
    Executor SERVICE_2_THREAD_POOL = Executors.newFixedThreadPool(4);
    Executor GLOBAL_THREAD_POOL = new JoinedExecutor(SERVICE_1_THREAD_POOL, SERVICE_2_THREAD_POOL);

    Executor getThreadPool();

}
