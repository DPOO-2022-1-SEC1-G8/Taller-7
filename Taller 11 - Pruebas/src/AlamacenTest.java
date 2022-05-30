import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import uniandes.cupi2.almacen.mundo.Almacen;
import uniandes.cupi2.almacen.mundo.AlmacenException;

class AlamacenTest {

	private final static String RUTA_GENRAL = System.getProperty("user.dir");;
	
	private File generarArchivoExiste() throws FileNotFoundException {
		String rutaArchivoDatos = RUTA_GENRAL + "\\data\\datos.txt";
		File archivoDatos = new File(rutaArchivoDatos);
	    return archivoDatos;
	}
	
	private File generarArchivoNoExiste() throws FileNotFoundException {
		String rutaArchivoDatos = RUTA_GENRAL + "\\data\\datos_cortos.txt";
		File archivoDatos = new File(rutaArchivoDatos);
	    return archivoDatos;
	}
	
	@Test
	public void ConstructorAlmacenCargaExiste() throws AlmacenException, IOException {
		File archivo = generarArchivoExiste();
		Almacen almacen = new Almacen(archivo);
		assertNotNull(almacen);
	}
	
	@Test
	public void ConstructorAlmacenCargaNoExiste() throws AlmacenException, IOException {
		File archivo = generarArchivoNoExiste();
		assertThrows(AlmacenException.class,() -> new Almacen(archivo));
	}
}