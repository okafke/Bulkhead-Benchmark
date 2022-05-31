package seminar;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class JoinedExecutor implements Executor {
    private final AtomicInteger index = new AtomicInteger();
    private final Executor[] executors;

    public JoinedExecutor(Executor... executors) {
        this.executors = executors;
    }

    @Override
    public void execute(Runnable command) {
        executors[index.incrementAndGet() % executors.length].execute(command);
    }

}
