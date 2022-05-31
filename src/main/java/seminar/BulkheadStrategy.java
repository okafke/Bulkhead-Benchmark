package seminar;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.concurrent.Executor;

@Getter
@RequiredArgsConstructor
public enum BulkheadStrategy {
    SINGLE_THREAD(null),
    GLOBAL_THREAD_POOL(HasThreadPool.GLOBAL_THREAD_POOL),
    BULKHEAD(null) {
        @Override
        public void handle(HttpExchange exchange, HttpHandler handler) {
            if (handler instanceof HasThreadPool) {
                ((HasThreadPool) handler).getThreadPool().execute(() -> {
                    try {
                        handler.handle(exchange);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
            }
        }
    };

    private final Executor serverExecutor;

    public void handle(HttpExchange exchange, HttpHandler handler) throws IOException {
        handler.handle(exchange);
    }

    public HttpHandler wrap(HttpHandler handler) {
        return exchange -> this.handle(exchange, handler);
    }

}
