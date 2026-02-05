package PSP_EJERCICIOS.UT_4_FTP;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Scanner;

public class FTPManager {

    private static FTPClient cliente;
    private static Scanner scanner;
    private static String directorioActual = "/"; 

    public static void main(String[] args) {
        cliente = new FTPClient();
        scanner = new Scanner(System.in);

        System.out.println("--- FTPManager ---");

        String server = solicitarDatos("Servidor FTP", "192.168.1.125");
        String user = solicitarDatos("Usuario", ""); 
        String pass = solicitarDatos("Contraseña", ""); 

        try {
            int puerto = 21;
            cliente.connect(server, puerto);

            int replyCode = cliente.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Servidor rechazó la conexión.");
                System.out.println(replyCode);
                return;
            }


            boolean loginExitoso = cliente.login(user, pass);
            if (loginExitoso) {
                System.out.println("Login correcto.");
                
                cliente.enterLocalPassiveMode(); 
                cliente.setFileType(FTP.BINARY_FILE_TYPE);
                
                directorioActual = cliente.printWorkingDirectory();

                procesarComandos();
            } else {
                System.out.println("Credenciales incorrectas.");
                cliente.disconnect();
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void procesarComandos() {
        boolean salir = false;
        while (!salir) {
            try {

                System.out.print("\nFTPManager [" + directorioActual + "]> ");

                String linea = scanner.nextLine();
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.trim().split("\\s+", 3);
                String comando = partes[0].toLowerCase();

                switch (comando) {
                    case "ls":
                        String rutaListar = (partes.length > 1) ? partes[1] : "";
                        listarDirectorio(rutaListar);
                        break;

                    case "cd":
                        if (partes.length < 2) System.out.println("Uso: cd <ruta>");
                        else cambiarDirectorioTrabajo(partes[1]);
                        break;
                    
                    case "mkdir": 
                        if (partes.length < 2) System.out.println("Uso: mkdir <nombre>");
                        else crearDirectorio(partes[1]);
                        break;

                    case "rename": 
                        if (partes.length < 3) System.out.println("Uso: rename <original> <cambio>");
                        else renombrar(partes[1], partes[2]);
                        break;

                    case "put": 
                        if (partes.length < 3) System.out.println("Uso: put <rutaLocal> <rutaRemota");
                        else subirFichero(partes[1],partes[2]);
                        break;

                    case "get": 
                        if (partes.length < 3) System.out.println("Uso: get <nombreRemoto> <rutaLocal>");
                        else descargarFichero(partes[1], partes[2]);
                        break;

                    case "mode":
                        if (partes.length < 2) System.out.println("Uso: mode <passive|active>");
                        else cambiarModo(partes[1]);
                        break;
                    
                    case "quit": 
                    case "exit":
                        salir = true;
                        cliente.logout();
                        cliente.disconnect();
                        System.out.println("Desconectado.");
                        break;

                    case "help":
                        System.out.println("Comandos: ls, cd, mkdir, rename, put, get, mode, quit");
                        break;

                    default:
                        System.out.println("Comando no reconocido. Escribe 'help' para ayuda.");
                }

            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void cambiarDirectorioTrabajo(String ruta) throws IOException {
        boolean exito = cliente.changeWorkingDirectory(ruta);
        
        if (exito) {
            directorioActual = cliente.printWorkingDirectory();
            System.out.println("Cambiado a: " + directorioActual);
        } else {
            System.out.println("Error: No se pudo entrar en '" + ruta + "'. Comprueba que existe.");
        }
    }

    private static void listarDirectorio(String ruta) throws IOException {
        FTPFile[] archivos = (ruta.isEmpty()) ? cliente.listFiles() : cliente.listFiles(ruta);
        
        System.out.println("Contenido de " + (ruta.isEmpty() ? directorioActual : ruta) + ":");
        
        if (archivos != null && archivos.length > 0) {
            for (FTPFile archivo : archivos) {
                String tipo = archivo.isDirectory() ? "[DIR] " : "[FILE]";
                System.out.printf("%-7s %-20s\n", tipo, archivo.getName());
            }
        } else {
            System.out.println("(Vacío)");
        }
    }
    
    private static void crearDirectorio(String ruta) throws IOException {
        if (cliente.makeDirectory(ruta)) System.out.println("Creado: " + ruta);
        else System.out.println("Error al crear directorio.");
    }

    private static void renombrar(String origen, String destino) throws IOException {
        if (cliente.rename(origen, destino)) System.out.println("Renombrado a "+destino);
        else System.out.println("Error al renombrar.");
    }

    private static void subirFichero(String localPath, String remotePath) throws IOException {
        File ficheroLocal = new File(localPath);
        if (!ficheroLocal.exists()) {
            System.out.println("No existe fichero local."); 
            return;
        }
        try (InputStream input = new FileInputStream(ficheroLocal)) {
            if (cliente.storeFile(remotePath, input)) System.out.println("Subido.");
            else System.out.println("Error al subir.");
        }
    }

    private static void descargarFichero(String remotePath, String localPath) throws IOException {
        File ficheroLocal = new File(localPath);

        if (ficheroLocal.exists()) {
            System.out.print("El fichero local ya existe. ¿Sobrescribir (s) o Crear Copia (c)? [s/c]: ");
            String resp = scanner.nextLine().toLowerCase();
            
            if (resp.equals("c")) {
                String nombre = ficheroLocal.getName();
                String path = ficheroLocal.getParent();
                int punto = nombre.lastIndexOf(".");
                if (punto != -1) {
                    nombre = nombre.substring(0, punto) + "_copia" + nombre.substring(punto);
                }
                ficheroLocal = new File(path, nombre);
            } else if (!resp.equals("s")) {
                System.out.println("Operación cancelada.");
                return;
            }
        }

        System.out.println("Descargando fichero...");
        try (OutputStream output = new FileOutputStream(ficheroLocal)) {
            boolean verficacion = cliente.retrieveFile(remotePath, output);
            if (verficacion) {
                System.out.println("Descarga completada: " + ficheroLocal.getAbsolutePath());
            } else {
                System.out.println("Falló la descarga (verifique que el archivo exista).");
                output.close();
            }
        }
    }

    private static void cambiarModo(String modo) {
        if (modo.equalsIgnoreCase("passive")) {
            cliente.enterLocalPassiveMode();
            System.out.println("Cambiado a Modo PASIVO");
        } else if (modo.equalsIgnoreCase("active")) {
            cliente.enterLocalActiveMode();
            System.out.println("Cambiado a Modo ACTIVO");
        } else {
            System.out.println("Modo desconocido. Use 'passive' o 'active'.");
        }
    }

    private static String solicitarDatos(String mensaje, String defecto) {
        System.out.print(mensaje + (defecto.isEmpty() ? "" : " [" + defecto + "]") + ": ");
        String input = scanner.nextLine();
        return input.isEmpty() ? defecto : input;
    }
}