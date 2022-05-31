package seminar;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Executor;

@Slf4j
public class Service2 implements HttpHandler, HasThreadPool {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        log.info("Request to Service2");
        try {
            Thread.sleep(Duration.ofSeconds(2).toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        httpExchange.sendResponseHeaders(200, -1);
    }

    @Override
    public Executor getThreadPool() {
        return HasThreadPool.SERVICE_2_THREAD_POOL;
    }

}
