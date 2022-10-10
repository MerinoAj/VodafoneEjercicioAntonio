package EjercicioVodafone;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;





//@author ajmerino
public class Metodo2 {

	public static void main(String[] args) {
		try {
			System.out.println("********************************************************");
			System.out.println("      Introduzca el nombre de la cripto  deseada:");
			System.out.println("********************************************************");
			 Scanner entrada=new Scanner(System.in);
			String cripto=entrada.nextLine();
			entrada.close();
			URL url = new URL("https://api.coingecko.com/api/v3/coins/"+cripto);
			HttpsURLConnection conectar = (HttpsURLConnection) url.openConnection();
			conectar.setRequestMethod("GET");
			conectar.connect();

			int Respuesta = conectar.getResponseCode();
			//si la respuesta es 200
			if (Respuesta == 200) {
				StringBuilder informacion = new StringBuilder();
				Scanner scaner = new Scanner(url.openStream());
				while (scaner.hasNext()) {
					informacion.append(scaner.nextLine());
				}
				scaner.close();
				//se encarga de separar los datos que recibo y solo usar los que se encuentran en current_price
				String informacionBuena = informacion.toString();
				String[] parts = informacionBuena.split("current_price"+(char)34+":");
				String part2 = parts[1]; 
				String[] parts2 = part2.split("},\"total_value_locked");
				String parts1 = parts2[0]; 
				/////////////////////////////////////////////////////////////////////////
				//le añado dos ] para que lo pueda convertir en array usando el formato json
				String buenoFin = "["+parts1.toString()+"}]";
				//lo comvierto en un array.
				JSONArray arrayJson = new JSONArray(buenoFin);
				JSONObject obj = arrayJson.getJSONObject(0);
				//imprime el resultado del precio actual de la cripto que manda el usuario usando el usd como parametro para buscarlo en el array.
				System.out.println("El precio actual del "+ cripto+" es: " +obj.getBigDecimal("usd") +" Dolares");
			}
			//si la respuesta es 404
			else if(Respuesta == 404) {
				System.out.println("No existe ninguna criptomoneda con el id: "+cripto);
			}
			//si no es ni 404 ni 202
			else {
				System.out.println("Se ha obtenido una respuesta inesperada.");
			}
		} catch (IOException e) {
			System.out.println("Se ha obtenido una respuesta inesperada.");
		}
	}
}
