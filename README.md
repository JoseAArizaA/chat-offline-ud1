# Chat Offline - Proyecto UD1 (Acceso a Datos)

Aplicación de chat desarrollada en Java para la gestión offline de usuarios y conversaciones mediante persistencia XML y procesamiento de datos con la Stream API.

## 🚀 Funcionalidades Principales

- **Registro de usuarios:** Sistema de alta con validación de duplicados y persistencia en `usuarios.xml`.
- **Envío de mensajes:** Intercambio de mensajes entre usuarios registrados guardados en `mensajes.xml`.
- **Consulta de conversaciones:** Historial completo filtrado de forma bidireccional entre dos usuarios.
- **Estadísticas con Streams:** Generación de informes analíticos utilizando la API de Streams de Java y `Collectors.groupingBy`.
- **Exportación a texto plano:** Volcado de historiales a ficheros `.txt` mediante `BufferedWriter`.
- **Gestión de archivos adjuntos:** Copia de seguridad física de ficheros hacia la carpeta `media/` empleando flujos binarios (`InputStream`/`OutputStream`) con control de metadatos en el XML.

## 🛠️ Tecnologías y Requisitos Técnicos

- **Java** (JDK 17+)
- **JAXB** (Java Architecture for XML Binding) para el mapeo y serialización de objetos.
- **Java Stream API** para operaciones de filtrado y conteo.
- **Java I/O / NIO** para la manipulación de flujos de texto y binarios.

## ▶️ Cómo ejecutar la aplicación

1. Abre el proyecto en **IntelliJ IDEA**.
2. Navega hasta la clase `Main.java` (en el paquete `es.iesfranciscodelosrios.chat`).
3. Haz clic en el botón verde de **Run** situado en el margen izquierdo o pulsa el acceso rápido para iniciar el menú interactivo por consola.

## 👨‍💻 Autor

**Jose Antonio Ariza Aguilera**
