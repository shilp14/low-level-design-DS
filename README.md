# 🚀 Java Load Balancer (LLD)

A **thread-safe, extensible Load Balancer implementation in Java** supporting multiple routing strategies and background health checks.

This project demonstrates core **Low-Level Design (LLD)** concepts like Strategy Pattern, concurrency handling, and system design fundamentals.

---

## 📌 Features

* ✅ **Round Robin Load Balancing**
* ✅ **Least Connections Strategy**
* ✅ **Pluggable Strategy Pattern**
* ✅ **Background Health Checking**
* ✅ **Thread-safe implementation (ConcurrentHashMap, AtomicInteger)**
* ✅ **Immutable snapshot for lock-free reads**
* ✅ **Graceful shutdown support**

---

## 🏗️ Architecture

```
Client Request
     ↓
LoadBalancerManager
     ↓
LoadBalancerStrategy (RoundRobin / LeastConnection)
     ↓
Healthy Server Pool
     ↓
Selected Server
```

---

## ⚙️ Components

### 1. LoadBalancerManager

* Maintains server registry
* Delegates request routing
* Tracks active connections
* Manages lifecycle (start/stop)

---

### 2. LoadBalancerStrategy

Interface for routing logic:

* `RoundRobinLoadBalancerStrategy`
* `LeastConnectionLoadBalancerStrategy`

---

### 3. HealthCheckerService

* Runs periodically using `ScheduledExecutorService`
* Updates healthy server list
* Removes unhealthy servers from rotation

---

### 4. Server

* Represents backend server
* Tracks:

  * Health status
  * Active connections (AtomicInteger)

---

## 🔄 Load Balancing Strategies

### 🔁 Round Robin

Distributes requests evenly:

```
A → B → C → A → B → C
```

---

### 📉 Least Connections

Routes to server with lowest active load:

```
Min(activeConnections)
```

---

## 🧵 Concurrency Design

* `ConcurrentHashMap` for server registry
* `AtomicInteger` for connection counters
* `volatile` + immutable list for safe reads
* Lock-free read path (high throughput)

---

## ❤️ Health Check Mechanism

* Runs every **5 seconds**
* Simulates `ping()` (can be replaced with real HTTP/TCP checks)
* Updates active server pool dynamically

---

## ▶️ How to Run

### 1. Clone repo

```
git clone https://github.com/<your-username>/load-balancer.git
cd load-balancer
```

### 2. Compile

```
javac Main.java
```

### 3. Run

```
java Main
```

---

## 🧪 Sample Output

```
=== Round Robin ===
Request 1 -> S1
Request 2 -> S2
Request 3 -> S3

=== Least Connection ===
Allocated -> S1
Allocated -> S2
Next Request -> S3
```

---

## 📈 Future Improvements

* 🔹 Weighted Round Robin
* 🔹 Sticky Sessions (Consistent Hashing)
* 🔹 Circuit Breaker Integration
* 🔹 Latency-based routing
* 🔹 Distributed Load Balancer (Redis / ZooKeeper)
* 🔹 Metrics & Monitoring (Prometheus/Grafana)

---

## 🧠 Learning Outcomes

* Strategy Pattern in real systems
* Concurrency in Java
* Designing scalable backend components
* Trade-offs between correctness vs performance

---

## 🏆 Interview Relevance

This project is a great example of:

* System Design (LLD)
* Backend Engineering
* Concurrency handling
* Real-world problem solving

---

## 👨‍💻 Author

**Shilpi Kumari**

---

## ⭐ If you found this useful, give it a star!
