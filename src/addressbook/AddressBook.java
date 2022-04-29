/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package addressbook;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

/**
 *
 * @author Intel
 */
public class AddressBook {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, String> AgendaContactos = new HashMap<>();
        
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int decision = 0;
        load(AgendaContactos);
        while(!salir){
            System.out.println("Selecciona una opción");
            System.out.println("1. Ver Agenda \n2. Crear Contacto \n3. Eliminar Contacto \n4. Guardar Agenda \n5. Cargar Agenda \n" +
                    "0. Salir");
            
            try{
                decision = sn.nextInt();
                
                switch(decision){
                    case 1: 
                        list(AgendaContactos);
                        break;
                        
                    case 2:
                        create(AgendaContactos);
                        break;
                        
                    case 3: 
                        delete(AgendaContactos);
                        break;

                    case 4:
                        save(AgendaContactos);
                        break;

                    case 5:
                        load(AgendaContactos);
                        break;

                    case 0:
                        salir = true;
                        break;
                        
                    default:
                        System.out.println("opcion no valida");

                }
            }catch (InputMismatchException e){
                System.out.println("Debes inserta un numero dentro del 0 al 5");
                sn.nextInt();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    //Método List: Muestra la lista de contactos.
    public static void list(HashMap AgendaContactos){
        System.out.println("Agenda");
        for(Iterator<Entry<String, String>> entries = AgendaContactos.entrySet().iterator(); entries.hasNext();){
            Map.Entry<String, String> entry = entries.next();
            String output = String.format("%s:%s", entry.getKey(), entry.getValue());
            System.out.println(output);
        }
    }
    //Método Create: Crear un nuevo contacto en la Agenda.
    public static void create(HashMap AgendaContactos){
        BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
        String phone = null;
        String name = null;
        System.out.println("Ingrese el numero de telefono: ");
        
        try{
           phone = leer.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        
        System.out.println("Ingrese el nombre del contacto: ");
        try{
            name = leer.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        
        if(phone != null && name != null)
            AgendaContactos.put(name, phone);
    }
    //Método Delete. Elimina un contacto de la agenda.
    public static void delete(HashMap AgendaContactos){
        BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
        String phone = null;
        System.out.println("Ingrese el numero de telefono: ");
        
        try{
            phone = leer.readLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
        AgendaContactos.remove(phone);
    }
    
    //Método Save: Salva los contactos en un archivo.
    public static void save(List<Agenda> AgendaContactos) {
        String salidaArchivo = "Agenda.csv"; // Nombre del archivo
        boolean existe = new File(salidaArchivo).exists(); // Verifica si existe

        // Si existe un archivo llamado asi lo borra
        if(existe) {
            File AgendaContactos = new File(AgendaContactos);
            AgendaContactos.delete();
        }

        try {
            // Crea el archivo
            CsvWriter salidaCSV = new CsvWriter(new FileWriter(salidaArchivo, true), ',');

            // Datos para identificar las columnas
            salidaCSV.write("Nombre");
            salidaCSV.write("Telefono");


            salidaCSV.endRecord(); // Deja de escribir en el archivo

            // Recorremos la lista y lo insertamos en el archivo
            for(AgendaContactos user :AgendaContactos) {
                salidaCSV.write(user.getNombre());
                salidaCSV.write(user.getTelefono());

                salidaCSV.endRecord(); // Deja de escribir en el archivo
            }

            salidaCSV.close(); // Cierra el archivo

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    //Método Load: Importar los contactos de un txt.
    public static void load(HashMap AgendaContactos) throws FileNotFoundException{
        String inputFilename = "agenda.csv";
        BufferedReader bufferedReader = null;
        String phone = " ";
        String name = " ";
        try{
            bufferedReader = new BufferedReader(new FileReader(inputFilename));
            String line;

            while((line = bufferedReader.readLine()) != null){
                int coma = line.indexOf(',');
                phone = line.substring(0, coma);
                name = line.substring(coma+1, line.length());
                AgendaContactos.put(phone, name);
            }
        }catch(IOException e){
            System.out.println("IOException catched while reading: " + e.getMessage());
        }finally {
            try{
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            }catch(IOException e){
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }
}
