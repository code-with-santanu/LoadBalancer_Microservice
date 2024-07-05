<h1 align="center">LoadBalancer Microservice</h1>

  <p align="center">
   A server-side load-balancer for distributing the trafic among the web servers.
  </p>
</p>

---

## About The Project

A server-side load-balancer for distributing the trafic among the web servers based on the selected server by using dynamic load balancing algorithm(Weighted Least Connections).<br>
For details implementation see 👉 [documentation](README)<br>

### Built With

- `Java` `Spring` `Spring Boot` `Hibernate`

---

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contribution](#contribution)
- [License](#license)

<!-- FEATURES -->

## Features

- Dynamic load balancing algorithm for better efficiency .
- Secured endpoints to manage server-cache (add, update, delete server info) .
- Continuously monitored servers' health .
- Supported multithreading and concurrency for
  handling incoming web requests .
- Achieved containerization for scalability and
  portability .
- Deployed on cloud for real-time use .

<!-- INSTALLATION -->

## Installation

1. Clone the repository:

```bash
 git clone https://github.com/code-with-santanu/LoadBalancer_Microservice.git
```

2. Install dependencies:

```bash

```

3.  To run the project, use the following command:

```bash

```

<!-- USAGE -->

## Usage

The load balancer provides various endpoints for managing server cache and balancing web requests.

- The base URL for sending requests to the load balancer is:

```bash
 https://loadbalancer-microservice.onrender.com/lb
```

### Endpoints

1. Retrieve All Servers:
   _GET_ `/api/servers`

- Returns a list of all servers in the cache.
- For terminal usage, to make a _GET_ request :

```bash
 curl -X GET "https://loadbalancer-microservice.onrender.com/api/servers"
```

2. Add a Server:
   _POST_ `/api/servers`

- Add a new server to the cache.
- For terminal usage, to make a _POST_ request :

```bash
 curl -X POST "https://loadbalancer-microservice.onrender.com/api/servers" -H "Content-Type: application/json" -d '{
  "serverIp": "192.168.136.2",
  "port" : 8080,
  "maxWeight": 10,
  "serverUrl": "https://myserver.example.com"
}'
```

`NOTE:` Ensure to replace `{serverId}` with the actual server ID when making requests to the PUT and DELETE endpoints.

3. Update a Server:
   _PUT_ `/api/servers/{serverId}`

- Updates an existing server's information in the cache.
- For terminal usage, to make a _PUT_ request :

```bash
 curl -X PUT "https://loadbalancer-microservice.onrender.com/api/servers/server3" -H "Content-Type: application/json" -d '{
  "port" : 5050,
  "weight": 15
}'
```

4. Delete a Server:
   _DELETE_ `/api/servers/{serverId}`

- Deletes an existing server's information in the cache.
- For terminal usage, to make a _DELETE_ request :

```bash
 curl -X DELETE "https://loadbalancer-microservice.onrender.com/api/servers/{serverId}"

```

<!-- CONTRIBUTION -->

## Contribution

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch
3. Commit your Changes
4. Push to the Branch
5. Open a Pull Request

<!-- LICENSE -->

## License

This project is licensed under the [MIT LICENSE](LICENSE)

<br>

---

<!-- SUPPORT -->

## ☺️ Support

💙 If you like this project, give it a ⭐ and share it with friends!<br>

<!-- CONTACT -->

## 🤝 Contact

More Projects
GitHub: [Santanu Pal](https://github.com/code-with-santanu)

<!-- ACKNOWLEDGEMENTS -->

## 💻 Acknowledgements

Made with ❤️ by Santanu <br><br>
