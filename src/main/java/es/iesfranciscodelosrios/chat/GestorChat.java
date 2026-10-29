package es.iesfranciscodelosrios.chat;

import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class GestorChat {
    private final GestorXML gestorXML = new GestorXML();

    public void registrarUsuario(String nombre) throws JAXBException {
        ListaUsuarios lista = gestorXML.leerUsuarios();

        for(Usuario usuario : lista.getUsuarios()){
            if(usuario.getNombre().equals(nombre)){
                System.out.println("Error: El usuario " + nombre + " ya existe.");
                return;
            }
        }

        lista.getUsuarios().add(new Usuario(nombre));
        gestorXML.guardarUsuarios(lista);
        System.out.println("Usuario " + nombre + " registrado correctamente.");
    }

    public void enviarMensaje(String emisor, String destinatario, String contenido) throws JAXBException {
        ListaUsuarios listaUsuarios = gestorXML.leerUsuarios();
        boolean emisorValido = false;
        boolean destinatarioValido = false;

        for(Usuario usuario : listaUsuarios.getUsuarios()){
            if(usuario.getNombre().equalsIgnoreCase(emisor)){
                emisorValido = true;
            }
            if(usuario.getNombre().equalsIgnoreCase(destinatario)){
                destinatarioValido = true;
            }
        }

        if(!emisorValido || !destinatarioValido){
            System.out.println("Error: El emisor o el destinatario no existen.");
            return;
        }

        String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Mensaje nuevoMensaje = new Mensaje(emisor, destinatario, contenido, fechaActual);

        ListaMensajes listaMensajes = gestorXML.leerMensajes();
        listaMensajes.getMensajes().add(nuevoMensaje);
        gestorXML.guardarMensajes(listaMensajes);

        System.out.println("Mensaje enviado de " + emisor + " a " + destinatario + " correctamente.");
    }

    public List<Mensaje> consultarConversacion(String usuarioA, String usuarioB) throws JAXBException {
        ListaMensajes listaCompleta = gestorXML.leerMensajes();

        List<Mensaje> conversacionFiltrada = new ArrayList<>();

        for(Mensaje mensaje : listaCompleta.getMensajes()){
            boolean deAparaB = mensaje.getEmisor().equalsIgnoreCase(usuarioA) && mensaje.getDestinatario().equalsIgnoreCase(usuarioB);
            boolean deBparaA = mensaje.getEmisor().equalsIgnoreCase(usuarioB) && mensaje.getDestinatario().equalsIgnoreCase(usuarioA);

            if (deAparaB || deBparaA){
                conversacionFiltrada.add(mensaje);
            }
        }
        return conversacionFiltrada;
    }


    public Map<String, Long> estadisticas(String usuarioA, String usuarioB) throws JAXBException {
        List <Mensaje> conversacion = consultarConversacion(usuarioA, usuarioB);

        return conversacion.stream().collect(Collectors.groupingBy(Mensaje::getEmisor, Collectors.counting()));
    }

    public void exportarConversacion(String usuarioA, String usuarioB, Path destino)
            throws JAXBException, IOException {
       List<Mensaje> conversacion = consultarConversacion(usuarioA, usuarioB);

       try(BufferedWriter bw = new BufferedWriter(new FileWriter(destino.toFile()))){
           bw.write("--- Historial de conversación entre " + usuarioA + " y " + usuarioB + " ---");
           bw.newLine();

           for(Mensaje mensaje : conversacion){
               bw.write("-" +  mensaje.getFecha() + ": " + mensaje.getEmisor() + ": " + mensaje.getContenido());
               bw.newLine();
           }
       }
        System.out.println("Mensaje exportado correctamente a " + destino.toString());
    }

    public void adjuntarArchivo(Mensaje mensaje, Path archivoOrigen) throws IOException, JAXBException {
        File origen = archivoOrigen.toFile();

        if(!origen.exists()){
            System.out.println("Error: El archivo no existe.");
            return;
        }
        if(origen.length() > 10 * 1024 * 1024){
            System.out.println("Error: El archivo supera el tamaño máximo permitido (10 MB).");
            return;
        }

        File carpetaMedia = new File("media");
        if(!carpetaMedia.exists()){
            carpetaMedia.mkdir();
        }

        File destino = new File(carpetaMedia, origen.getName());
        try(FileInputStream fis = new FileInputStream(origen);
            FileOutputStream fos = new FileOutputStream(destino)){

            byte[] buffer = new byte[1024];
            int bytesLeidos;
            while((bytesLeidos = fis.read(buffer)) != -1){
                fos.write(buffer, 0, bytesLeidos);
            }
        }

        mensaje.setAdjuntoNombre(origen.getName());
        mensaje.setAdjuntoRuta(destino.getPath());
        mensaje.setAdjuntoTamano(origen.length());

        String nombreArchivo = origen.getName();
        String extension =  nombreArchivo.substring(nombreArchivo.lastIndexOf('.') + 1);
        mensaje.setAdjuntoTipo(extension);

        ListaMensajes listaMensajes = gestorXML.leerMensajes();
        for(Mensaje m : listaMensajes.getMensajes()){
            if(m.getFecha().equals(mensaje.getFecha()) && m.getEmisor().equals(mensaje.getEmisor())){
                m.setAdjuntoNombre(mensaje.getAdjuntoNombre());
                m.setAdjuntoRuta(mensaje.getAdjuntoRuta());
                m.setAdjuntoTamano(mensaje.getAdjuntoTamano());
                m.setAdjuntoTipo(mensaje.getAdjuntoTipo());
                break;
            }
        }
        gestorXML.guardarMensajes(listaMensajes);
        System.out.println("Archivo adjuntado y guardado correctamente.");
    }
}
