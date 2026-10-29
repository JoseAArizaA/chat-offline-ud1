package es.iesfranciscodelosrios.chat;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class GestorXML {
    private static final String ARCHIVO_USUARIOS = "usuarios.xml";
    private static final String ARCHIVO_MENSAJES = "mensajes.xml";

    public void guardarUsuarios(ListaUsuarios usuarios) throws JAXBException {
        JAXBContext contexto = JAXBContext.newInstance(ListaUsuarios.class);
        Marshaller marshaller = contexto.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(usuarios, new File(ARCHIVO_USUARIOS));
    }

    public ListaUsuarios leerUsuarios() throws JAXBException {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()){
            return new ListaUsuarios();
        }

        JAXBContext contexto = JAXBContext.newInstance(ListaUsuarios.class);
        Unmarshaller unmarshaller = contexto.createUnmarshaller();
        return (ListaUsuarios) unmarshaller.unmarshal(archivo);
    }


    public void guardarMensajes(ListaMensajes mensajes) throws JAXBException {
        JAXBContext contexto = JAXBContext.newInstance (ListaMensajes.class);
        Marshaller marshaller = contexto.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(mensajes, new File (ARCHIVO_MENSAJES));
    }

    public ListaMensajes leerMensajes() throws JAXBException {
        File archivo = new File(ARCHIVO_MENSAJES);
        if (!archivo.exists()){
            return new ListaMensajes();
        }

        JAXBContext contexto = JAXBContext.newInstance (ListaMensajes.class);
        Unmarshaller unmarshaller = contexto.createUnmarshaller();
        return (ListaMensajes) unmarshaller.unmarshal(archivo);
    }
}
