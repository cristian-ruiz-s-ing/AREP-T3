# Taller 3 - Micoframewoks
### Cristian Camilo Ruiz Santa

Servidor web construido para proporcionar funcionalidades como el manejo de solicitudes GET y POST, la entrega de archivos estáticos y la configuración de directorios de archivos estáticos.

## Funcionamiento Básico

El servidor funciona como una aplicación autónoma y utiliza la clase `HttpServer` de la API `java.net` para recibir solicitudes HTTP entrantes en un puerto específico (En este caso el puerto 35000). A continuación, se describe un su funcionamiento básico:

1. **Configuración Inicial**: El servidor se configura en el puerto especificado y se establece para escuchar las solicitudes entrantes.

2. **Registro de Rutas**: El servidor permite registrar rutas para los métodos GET y POST utilizando funciones lambda. Por ejemplo, se pueden registrar rutas como `/hello` y `/echo`. Cuando se recibe una solicitud que coincide con una de estas rutas, se ejecuta la función lambda correspondiente.

3. **Gestión de Solicitudes**: Cuando llega una solicitud HTTP al servidor, se llama al método `handleRequest`. Este método determina si la solicitud es un GET o POST y si coincide con alguna de las rutas registradas. En función de esto, se ejecuta la función lambda asociada o se sirve un archivo estático.

4. **Archivos Estáticos**: El servidor puede servir archivos estáticos como páginas HTML, CSS, JavaScript e imágenes. Estos archivos se almacenan en un directorio específico que se puede configurar usando el método `setStaticFilesDirectory`.

5. **Respuestas Personalizadas**: El servidor puede responder en diferentes formatos. Si se establece la respuesta como JSON, se configurará el encabezado `Content-Type` para indicar que el contenido es de tipo JSON.



## Uso del Servidor

Para utilizar el servidor se puede ejecutar a través del navegador o mediante herramientas de cliente HTTP, como curl o Postman. A continuación, se explica cómo ejecutar el servidor y cómo acceder a archivos estáticos usando el servidor.

### Realizar Solicitudes GET

1. **Abrir el Navegador Web**: Se puede usar cualquier navegador web para realizar solicitudes al servidor.

2. **Especificar la URL**: Ingresar la URL del servidor seguida de la ruta a la que se desea acceder. Por ejemplo:

   ```
   http://localhost:35000/hello
   ```

## Realizar Solicitudes POST

1. **Utilizar una Herramienta de Cliente HTTP**: Para realizar solicitudes POST al servidor, se puede utilizar una herramienta de cliente HTTP como curl o Postman.

2. **Ejecutar la Solicitud**: Se debe ejecutar un comando curl o configurar una solicitud POST en Postman. Por ejemplo:

    - Con curl (en la línea de comandos):
      ```shell
      curl -X POST -d "Mi mensaje" http://localhost:35000/echo
      ```

    - Con Postman: Se configura una solicitud POST a `http://localhost:35000/echo` y se deben definir los datos en el cuerpo de la solicitud.

## Acceder a Archivos Estáticos

El servidor también puede entregar archivos estáticos, como páginas HTML, CSS, JavaScript e imágenes. Para acceder a estos archivos:

- **Especificar la URL del Archivo Estático**: En la barra de búsqueda del navegador, ingresar la URL del servidor seguida de la ruta al archivo estático al que se desea acceder. Por ejemplo:

   ```
   http://localhost:35000/static/index.html
   ```
