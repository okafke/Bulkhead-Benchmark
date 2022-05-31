import matplotlib.pyplot as plt
import os
import csv


def create_graph(file_name, plot_title, png_file):
    service2response = {'/service1': {}, '/service2': {}}
    with open(file_name) as f:
        csv_reader = csv.DictReader(f)
        for row in csv_reader:
            if row['Name'] in service2response:
                service2response[row['Name']][float(row['Requests/s'])] = float(row['Total Average Response Time'])

    for key, value in service2response.items():
        service2response[key] = dict(sorted(value.items()))

    fig, ax = plt.subplots()
    ax.set_ylim([0, 20_000])
    ax.set_xlim([0, min(max(service2response['/service2'].keys()), max(service2response['/service1'].keys()))])

    ax.plot(service2response['/service2'].keys(), service2response['/service2'].values(), label='Service2')
    ax.plot(service2response['/service1'].keys(), service2response['/service1'].values(), label='Service1')

    ax.set(xlabel='Requests/s', ylabel='Response time (ms)', title=plot_title)
    ax.grid()

    plt.legend()
    fig.savefig(png_file)
    plt.show()


if __name__ == '__main__':
    create_graph(os.path.join('results', 'no_bulkhead_stats_history.csv'), 'Global ThreadPool', 'no_bulkhead.png')
    create_graph(os.path.join('results', 'bulkhead_stats_history.csv'), 'Bulkhead', 'bulkhead.png')
