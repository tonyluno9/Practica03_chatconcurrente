Chat Concurrente en Java

Autor: Angel Antonio Perez Reyes
Materia: Programacion Concurrente y Paralela
Año: 2025

Descripcion:
Proyecto de chat concurrente cliente-servidor desarrollado en Java usando sockets TCP y hilos (Thread).
Permite que varios usuarios se conecten al servidor, cambien su nombre de usuario y envien mensajes privados o globales.

Funcionalidades:

* start-connection: establece conexion entre cliente y servidor (IP y puerto 8080).
* change-userName: cambia el nombre del usuario conectado.
* send-msg: envia mensajes privados a otros usuarios.
* global-msg: envia mensajes a todos los usuarios conectados.
* Funcion extra: /users muestra la lista de usuarios activos.

Concurrencia:
El servidor crea un hilo independiente por cada cliente conectado (ClientHandler), permitiendo multiples conexiones al mismo tiempo.
El cliente usa dos hilos: uno para enviar y otro para recibir mensajes simultaneamente.

Ejecucion:

1. Ejecutar el servidor:
   java ServerMain
2. Ejecutar el cliente:
   java Handlers.Client
3. Comandos disponibles:
   /name nuevoNombre
   /msg usuario mensaje
   /global mensaje
   /users
   /exit
