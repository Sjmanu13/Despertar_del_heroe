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
}
