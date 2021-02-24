# Riyadh Metro Transit Network Services
Riyadh Metro Network Service (RMNS) is an open source web application/ REST API service, that can be used by developers and users. It supports two languages (Arabic and English). The application can determine the shortest route for your current location to a designated metro station. it can help to reduce the time in searching about routes of the metro by several activities that can be done by it. The application includes details of all metro routes, stations, vertices in Riyadh (Where the application displays the map of the Riyadh metro system). Users can specify the departure station and the destination station. This is done in one of the following ways: Selection from a drop-down list containing the names of the main and secondary stations or by specifying the station on the map. The search process shows the available routes so that it is the fastest way to reach your destination. It also shows the stations and trains schedules.

- ## Solving The Shortest Path Problem With Dijkstra
[![](/docs/initial-graph.webp)](/docs/RMNS-Software-Impl.pdf)

- ## Calculate distance between two points with Haversine formula
[![](/docs/RMNS-network.png)](/docs/RMNS-network.png)

- ## In-Memory DataBase for metro network data
[![](/docs/db.png)](/docs/db.png)

- ## Real-Time calculations
[![](/docs/search.png)](/docs/search.png)


### Quick Start

Use any of these methods to get started.

### Install Prebuild Docker image
* This is the easy and fastest way to run the application server on your environment.
```
docker pull bugyboo/rmns-jdk11:latest

docker run -dp 8081:8081 --name rmns bugyboo/rmns-jdk11 
```
* open internet browser `http://localhost:8081`
* login with username `admin` & password `admin123`

### Build Docker Image Localy 
* clone from GitHub
```
git clone https://github.com/bugyboo/riyadh-metro-network-services
```
* CD into folder springboot-backend.
* Run `mvn clean install`
* Run `docker build -t rmns-jdk11:latest .`
* Run `docker run -dp 8081:8081 --name rmns rmns-jdk11`


### Setup local Development build
* clone from GitHub
```
git clone https://github.com/bugyboo/riyadh-metro-network-services
```
Read more here [springboot-backend](/springboot-backend/README.md) about how to build the server project.

