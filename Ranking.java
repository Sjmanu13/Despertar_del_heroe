import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Ranking {

    private static final String ARCHIVO = "ranking.xml";

    private static final int CAPACIDAD = 10;

    // Arrays paralelos: la posición i de los dos arrays es el mismo jugador
    private String[] nombres;
    private int[] puntos;
    private int total; // cuántas posiciones están ocupadas

    public Ranking() 
    {
        nombres = new String[CAPACIDAD];
        puntos = new int[CAPACIDAD];
        total = 0;
    }


    public void load() 
    {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) 
        {
            System.out.println("No se ha encontrado " + ARCHIVO + ". El ranking empieza vacío.");
            return;
        }

        try 
        {
            // Abrir el XML 
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();

            fabrica.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

            DocumentBuilder constructor = fabrica.newDocumentBuilder();
            Document documento = constructor.parse(archivo);
            documento.getDocumentElement().normalize();

            // Buscar todos los <registro>
            NodeList lista = documento.getElementsByTagName("rank");

            // Copiar a los arrays los que tengan nombre
            total = 0;
            for (int i = 0; i < lista.getLength() && total < CAPACIDAD; i++) 
            {
                Element registro = (Element) lista.item(i);

                String nombre = registro.getElementsByTagName("name")
                        .item(0)
                        .getTextContent();
                String textoPuntos = registro.getElementsByTagName("points")
                        .item(0)
                        .getTextContent();

                if (!nombre.isEmpty()) {
                    nombres[total] = nombre;
                    puntos[total] = Integer.parseInt(textoPuntos);
                    total++;
                }
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Error al leer el ranking: " + e.getMessage());
        }
    }

    /**
     * Guarda el ranking en el archivo XML.
     * Reescribe los 10 registros con el contenido actual de los arrays;
     * las posiciones sin jugador quedan con el nombre vacío y 0 puntos.
     */
    public void save()
    {
        try 
        {
            // Abrir el XML que ya existe
            File archivo = new File(ARCHIVO);
            
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            fabrica.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder constructor = fabrica.newDocumentBuilder();
            Document documento = constructor.parse(archivo);
            documento.getDocumentElement().normalize();

            // Recorrer los 10 <registro> y ponerles lo que hay en los arrays
            NodeList lista = documento.getElementsByTagName("rank");

            for (int i = 0; i < lista.getLength() && i < CAPACIDAD; i++) 
            {
                Element registro = (Element) lista.item(i);
                Element nombre = (Element) registro.getElementsByTagName("name").item(0);
                Element puntosXml = (Element) registro.getElementsByTagName("points").item(0);

                if (i < total) 
                {
                    nombre.setTextContent(nombres[i]);
                    puntosXml.setTextContent(String.valueOf(puntos[i]));
                } 
                else 
                {
                    nombre.setTextContent("");
                    puntosXml.setTextContent("0");
                }
            }

            // Volcar el árbol modificado al mismo archivo
            TransformerFactory transformerFabrica = TransformerFactory.newInstance();
            Transformer transformer = transformerFabrica.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no"); // respeta el formato escrito a mano
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource origen = new DOMSource(documento);
            StreamResult destino = new StreamResult(archivo);
            transformer.transform(origen, destino);

        } 
        catch (Exception e) 
        {
            System.out.println("Error al guardar el ranking: " + e.getMessage());
        }
    }

    /**
    * Añade una partida al ranking manteniendo el orden de mayor a menor.
    * El nuevo se coloca al final y va subiendo mientras el de delante tenga menos puntos.
    * Si el ranking está lleno, solo entra si supera a la última puntuación.
    * En los empates, el registro más antiguo queda delante.
    * @param nombre nombre del héroe
    * @param puntuacion puntos obtenidos en la partida
    */
    public void add(String nombre, int puntuacion) // (hero.getNombre(), totalScore)
    {
        int i;
        if (total < CAPACIDAD)
        {
            i = total;                  
            total++;
        }
        else
        {
            if (puntuacion <= puntos[CAPACIDAD - 1])
            {
                System.out.println("Tu puntuación no ha entrado en el top " + CAPACIDAD + ".");
                return;                 // lleno y no supera al último: no entra
            }
            i = CAPACIDAD - 1;          // lleno: ocupa la posición del último
        }  

        nombres[i] = nombre;
        puntos[i] = puntuacion;

        while (i > 0 && puntos[i - 1] < puntos[i])
        {
            // Intercambiar con el de delante (nombre y puntos)
            String nombreAux = nombres[i];
            nombres[i] = nombres[i - 1];
            nombres[i - 1] = nombreAux;

            int puntosAux = puntos[i];
            puntos[i] = puntos[i - 1];
            puntos[i - 1] = puntosAux;

            i--;
        }

        System.out.println("Enhorabuena! Has entrado en el ranking en la posición " + (i + 1) + ".");


    }

    // Método mostrar ranking
    public void show()
    {
        System.out.println("========== RANKING ==========");
        
        if (total != 0) {

            for (int i = 0; i < total; i++) {
                System.out.println((i+1) + ". " + nombres[i] + "      " + puntos[i] + " puntos");
            }

        }
        else
        {
                System.out.println("Sin heroes registrados");
        }

        System.out.println("=============================");
    }

    private void reorder(String[] nombres, int[] puntos)
    {
        
    }
}