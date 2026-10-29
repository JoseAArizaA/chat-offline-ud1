package es.iesfranciscodelosrios.chat;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        GestorChat chat = new GestorChat();
        boolean salir = false;

        System.out.println("Chat Offline - Proyecto UD1");
        while(!salir){
            System.out.println("\n--------------------");
            System.out.println("1.- Registrar usuario");
            System.out.println("2.- Enviar mensaje");
            System.out.println("3.- Consultar conversación");
            System.out.println("4.- Estadísticas");
            System.out.println("5.- Exportar conversación");
            System.out.println("6.- Adjuntar archivo");
            System.out.println("0.- Salir");
            System.out.println("Seleccione una opción: ");

            String opcion = teclado.nextLine();

            try{
                switch (opcion){
                    case "1":
                        System.out.println("Ingrese el nombre del usuario que quiere registrar: ");
                        String nombre = teclado.nextLine();
                        chat.registrarUsuario(nombre);
                        break;

                    case "2":
                        System.out.println("Ingrese el nombre del emisor: ");
                        String emisor = teclado.nextLine();

                        System.out.println("Ingrese el nombre del destinatario: ");
                        String destinatario = teclado.nextLine();

                        System.out.println("Ingrese el mensaje: ");
                        String mensaje = teclado.nextLine();

                        chat.enviarMensaje(emisor, destinatario, mensaje);
                        break;

                    case "3":
                        System.out.println("Ingrese el nombre del usuario A: ");
                        String usuarioA = teclado.nextLine();

                        System.out.println("Ingrese el nombre del usuario B: ");
                        String usuarioB = teclado.nextLine();

                        List<Mensaje> conversacion = chat.consultarConversacion(usuarioA, usuarioB);
                        System.out.println("<----- Conversación entre " + usuarioA + " y " + usuarioB + " ----->");
                        if(conversacion.isEmpty()){
                            System.out.println("No hay mensaje entre " + usuarioA + " y " + usuarioB);
                        }else{
                            for (Mensaje m : conversacion) {
                                String adjInfo = (m.getAdjuntoNombre() != null) ? " [Adjunto: " + m.getAdjuntoNombre() + "]" : "";
                                System.out.println("[" + m.getFecha() + "] " + m.getEmisor() + ": " + m.getContenido() + adjInfo);
                            }
                        }
                        break;

                    case "4":
                        System.out.println("Ingrese el nombre del usuario A: ");
                        String userA = teclado.nextLine();

                        System.out.println("Ingrese el nombre del usuario B: ");
                        String userB = teclado.nextLine();

                        Map<String, Long> estadisticas = chat.estadisticas(userA, userB);

                        System.out.println("<----- Estadísticas de mensajes entre " + userA + " y " + userB + " ----->");
                        if(estadisticas.isEmpty()){
                            System.out.println("No hay mensajes entre " + userA + " y " + userB);
                        }else{
                            estadisticas.forEach((usuario, total) ->System.out.println("Usuario: " + usuario + ", Total mensajes: " + total));
                        }
                        break;

                    case "5":
                        System.out.println("Ingrese el nombre del usuario A: ");
                        String uA = teclado.nextLine();

                        System.out.println("Ingrese el nombre del usuario B: ");
                        String uB = teclado.nextLine();

                        System.out.println("Ingrese la ruta del archivo: ");
                        String rutaArchivo = teclado.nextLine();

                        chat.exportarConversacion(uA, uB, Paths.get(rutaArchivo));
                        break;

                    case "6":
                        System.out.println("Ingrese el nombre del emisor: ");
                        String emisorAdj = teclado.nextLine();

                        System.out.println("Ingrese el nombre del destinatario: ");
                        String destinatarioAdj = teclado.nextLine();

                        System.out.println("Ingrese el mensaje: ");
                        String mensajeAdj = teclado.nextLine();

                        System.out.println("Ingrese la ruta del archivo a adjuntar: ");
                        String rutaAdjunto = teclado.nextLine();

                        chat.enviarMensaje(emisorAdj, destinatarioAdj, mensajeAdj);

                        List<Mensaje> conversacionAdj = chat.consultarConversacion(emisorAdj, destinatarioAdj);
                        if(!conversacionAdj.isEmpty()) {
                            Mensaje ultimoMensaje = conversacionAdj.get(conversacionAdj.size() - 1);
                            chat.adjuntarArchivo(ultimoMensaje, Paths.get(rutaAdjunto));
                        } else {
                            System.out.println("Error: No se pudo encontrar el mensaje para adjuntar el archivo.");
                        }
                        break;

                    case "0":
                        salir = true;
                        System.out.println("Cerrando la aplicación...");
                        break;

                    default:
                        System.out.println("Opción no válida. Por favor, selecciona un número del 0 al 6.");
                        break;
                }
            }catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        teclado.close();
    }
}
