from locust import FastHttpUser, task, between

# Commands for starting the locust server
# locust -f .\locustfile.py --csv=bulkhead  --headless --csv-full-history --host http://localhost
# locust -f .\locustfile.py --csv=no_bulkhead  --headless --csv-full-history --host http://localhost


class TheUser(FastHttpUser):
    wait_time = between(1, 2)

    def on_start(self):
        print("Starting Benchmark...")

    @task
    def service1(self):
        self.client.get("/service1")

    @task
    def service2(self):
        self.client.get("/service2")
