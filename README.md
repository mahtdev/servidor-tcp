# Objetivo

Aplicacion que permite abrir abrir un canal de comunicacion TCP/IP como servidor y puede mandar la informacion que contiene un archivo

# VM Options

La aplicacion usa las siguientes variables

|    VM Option    | Descripcion                                                                  | Tipo de dato | valor por defecto |
|:---------------:|:-----------------------------------------------------------------------------|:------------:|:-----------------:|
|       ip        | Direccion IP a donde se realizara la conexion TCP                            |    String    |     localhost     |
|      port       | Puerto de conexion                                                           |   Integer    |       9090        |
|      file       | Archivo con los mensajes que se van a enviar una vez establecida la conexion |    String    |       NULL        |
|    recursive    | Indicador si el archivo se va a leer indefinidamente                         |   Boolean    |       false       |
|      time       | El tiempo en mseg que se va a esperar en mandar cada mensaje del archivo     |   Integer    |       1000        |
| connectionType  | El tipo de conexion a realizar, ya sea "client" o "server"                   |    String    |      server       |

# Iniciar componente (ejemplos)

### Levantar puerto 9090 

    java -jar connection-1.2.0.0.jar

### Levantar un socket server en el puerto 5000

    java -Dport=5000 -jar connection-1.2.0.0.jar