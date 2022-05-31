package seminar;

import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        log.info("Please enter the Bulkhead-Strategy: " + Arrays.toString(BulkheadStrategy.values()));
        BulkheadStrategy strategy = BulkheadStrategy.valueOf(scanner.next().toUpperCase());

        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/service1", strategy.wrap(new Service1()));
        server.createContext("/service2", strategy.wrap(new Service2()));
        server.setExecutor(strategy.getServerExecutor());

        log.info(String.format("Starting server: %s, %s", strategy, server.getAddress()));
        server.start();
    }

}
