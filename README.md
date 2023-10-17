# Conference Scheduler Service

A simple conference scheduler which schedules the conference day according to given
session list. Returns a schedule which may have couple of tracks. Tracks start from 09.00 am and finished 05.00 pm. 

## Table of Contents

- [Description](#description)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Dockerization](#dockerization)
- [Usage](#usage)
- [Example Request](#example-request)

## Description

A simple conference scheduler which schedules the conference day according to given
session list. Returns a schedule which may have couple of tracks. Tracks starts from 09.00 a.m and finishes at 05.00 pm.
They have the lunch break between 12 p.m and 1 p.m

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Docker
- Or Java Development Kit (JDK) 17 and Maven 3

## Getting Started

1. Run latest build from docker hub:

    ```bash
    docker run --name conference-scheduler-service -p 8081:8081 oguzhanu/conference-scheduler-service:latest
    ```

2. Or clone the repository and manually build the service:

    ```bash
    git clone git@github.com:OguzhanUlucay/Conference-Scheduler.git
    ```

## Dockerization

1. Build the Docker image:

    ```bash
    docker build -t conference-scheduler-service .
    ```

2. Run the Docker container:

    ```bash
    docker run --name conference-scheduler-service -p 8081:8081 conference-scheduler-service
    ```

## Usage

The service only single endpoint to schedule the conference: 

    POST localhost:8081/scheduler-service/schedule

**You can find the postman collection import file under repo!**

## Example request;

```bash
curl --location 'localhost:8081/scheduler-service/schedule' --header 'Content-Type: text/plain' --data 'Architecting Your Codebase 60min
Overdoing it in Python 45min
Flavors of Concurrency in Java 30min
Ruby Errors from Mismatched Gem Versions 45min
JUnit 5 - Shaping the Future of Testing on the JVM 45min
Cloud Native Java lightning
Communicating Over Distance 60min
AWS Technical Essentials 45min
Continuous Delivery 30min
Monitoring Reactive Applications 30min
Pair Programming vs Noise 45min
Rails Magic 60min
Microservices "Just Right" 60min
Clojure Ate Scala (on my project) 45min
Perfect Scalability 30min
Apache Spark 30min
Async Testing on JVM 60min
A World Without HackerNews 30min
User Interface CSS in Apps 30min'
```

Output for the request:
```bash
{
    "tracks": [
        {
            "trackName": "track1",
            "trackSchedule": [
                "09:00 Architecting Your Codebase 60min",
                "10:00 Overdoing it in Python 45min",
                "10:45 Flavors of Concurrency in Java 30min",
                "12:00 lunch",
                "13:00 Ruby Errors from Mismatched Gem Versions 45min",
                "13:45 JUnit 5 - Shaping the Future of Testing on the JVM 45min",
                "14:30 Cloud Native Java lightning",
                "14:35 Communicating Over Distance 60min",
                "15:35 AWS Technical Essentials 45min",
                "16:20 Continuous Delivery 30min",
                "16:50 Monitoring Reactive Applications 30min"
            ]
        },
        {
            "trackName": "track2",
            "trackSchedule": [
                "09:00 Pair Programming vs Noise 45min",
                "09:45 Rails Magic 60min",
                "10:45 Microservices \"Just Right\" 60min",
                "12:00 lunch",
                "13:00 Clojure Ate Scala (on my project) 45min",
                "13:45 Perfect Scalability 30min",
                "14:15 Apache Spark 30min",
                "14:45 Async Testing on JVM 60min",
                "15:45 A World Without HackerNews 30min",
                "16:15 User Interface CSS in Apps 30min"
            ]
        }
    ]
}
```