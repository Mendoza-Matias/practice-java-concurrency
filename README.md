# practice-java-concurrency

## 🧪 Resultados de ejecución

### 🔢 Contadores

| Implementación | Tiempo  |
|----------------|---------|
| AtomicInteger  | 4.63 ms |
| Synchronized   | 4.71 ms |
| ReentrantLock  | 4.38 ms |

### 📌 Conclusiones

- **AtomicInteger**  
  Ideal para contadores simples con alta concurrencia. Es la opción más eficiente y simple en este caso.

- **Synchronized**  
  Útil cuando necesitás proteger bloques de código más complejos o críticos.

- **ReentrantLock**  
  Recomendado cuando necesitás mayor control del locking:
    - `tryLock`
    - fairness (orden de acceso)
    - manejo más fino del lock

---

## ⚙️ ExecutorService

### ⏱️ Tiempos de ejecución

| Configuración | Tiempo   |
|---------------|----------|
| 1 hilo        | 36.51 ms |
| 5 hilos       | 7.45 ms  |

### 📊 Análisis

- Con **1 hilo**:
    - Procesamiento secuencial
    - `1 * 20 tareas * 100 ms = 2000 ms`

- Con **5 hilos**:
    - Ejecución en paralelo
    - `20 tareas / 5 hilos = 4 tareas por hilo`
    - `4 * 100 ms = 400 ms`

### 📌 Conclusiones

- A mayor cantidad de hilos, mejor aprovechamiento del paralelismo (hasta cierto punto).
- El **thread pool** distribuye automáticamente las tareas entre los hilos disponibles.
- Mejora notable en tareas independientes (sin bloqueo).

---

## 🚀 Ideas clave

- Usá **AtomicInteger** para operaciones atómicas simples.
- Evitá `synchronized` en escenarios con alta contención.
- Preferí **ExecutorService** en lugar de manejar hilos manualmente.
- Ajustá el tamaño del pool según el tipo de tarea:
    - **CPU-bound** → número de cores
    - **IO-bound** → más hilos que cores

---

## 🔄 CompletableFuture

- Permite ejecución **asíncrona no bloqueante**.
- El hilo principal puede continuar mientras la tarea se procesa.
- El resultado se maneja cuando la operación finaliza (callbacks / chaining).

---

## 🧵 Virtual Threads vs Thread Pool

- Los **Virtual Threads** permiten crear una gran cantidad de hilos livianos.
- En pruebas con pocos hilos (ej: 20), la mejora es visible.
- Sin embargo:
    - Crear más hilos de los necesarios no siempre mejora el rendimiento.
    - Puede generar **overhead innecesario** si no se aprovechan correctamente.

📌 Conclusión:  
Elegir entre virtual threads o thread pools depende del tipo de carga (IO vs CPU) y del nivel de concurrencia requerido.

---

## ⚠️ Deadlock

### 🧩 Problema

Ocurre cuando:

- Hilo 1 adquiere **Lock 1** y espera **Lock 2**
- Hilo 2 adquiere **Lock 2** y espera **Lock 1**

➡️ Se genera un ciclo de espera infinita.

### ✅ Solución

Evitar el **orden inconsistente de adquisición de locks**.

### ️ Implementación

- Ambos hilos deben adquirir los locks en el mismo orden:
