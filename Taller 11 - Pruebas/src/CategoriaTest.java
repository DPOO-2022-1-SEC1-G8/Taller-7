import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uniandes.cupi2.almacen.mundo.AlmacenException;
import uniandes.cupi2.almacen.mundo.Categoria;
import uniandes.cupi2.almacen.mundo.NodoAlmacen;

public class CategoriaTest {
	
	private final static String RUTA_GENRAL = System.getProperty("user.dir");;
	
	private BufferedReader generarArchivoCorrecto() throws FileNotFoundException {
		String rutaArchivoDatos = RUTA_GENRAL + "\\data\\datos.txt";
		File archivoDatos = new File(rutaArchivoDatos);
		BufferedReader in = new BufferedReader(new FileReader(archivoDatos));
	    return in;
	}
	
	private BufferedReader generarArchivoVacio() throws FileNotFoundException {
		String rutaArchivoDatos = RUTA_GENRAL + "\\data\\datos_cortos.txt";
		File archivoDatos = new File(rutaArchivoDatos);
		BufferedReader in = new BufferedReader(new FileReader(archivoDatos));
	    return in;
	}
	
	private BufferedReader generarArchivoIncorrecto() throws FileNotFoundException {
		String rutaArchivoDatos = RUTA_GENRAL + "\\data\\datos_incorrectos.txt";
		File archivoDatos = new File(rutaArchivoDatos);
		BufferedReader in = new BufferedReader(new FileReader(archivoDatos));
	    return in;
	}

	@Test
	public void ConstructorCategoriaCargaExiste() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertNotNull(categoria);
	}
	
	@Test
	public void ConstructorCategoriaCargaVacio() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoVacio();
		assertThrows(NullPointerException.class,() -> new Categoria(in.readLine( ),in));
	}
	
	@Test
	public void ConstructorCategoriaCargaIncorrecta() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoIncorrecto();
		assertThrows(AlmacenException.class,() -> new Categoria(in.readLine( ),in));
	}
	
	@Test
	public void ConstructorCategoriaNoCargaIncorrecta1() {
		Categoria categoria = new Categoria("pIdentificador","pNombre");
		assertNotNull(categoria);
	}
	
	@Test
	public void darNodosCorrectoNoCarga() {
		Categoria categoria = new Categoria("pIdentificador","pNombre");
		List<NodoAlmacen> nodosHijos = new ArrayList<>( );
		assertEquals(nodosHijos, categoria.darNodos());
	}
	
	@Test
	public void darNodosCorrectoCarga() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertTrue(categoria.darNodos().size() != 0);
	}
	
	@Test
	public void buscarNodoExiste() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertNotNull(categoria.buscarNodo("1"));
	}
	
	@Test
	public void buscarNodoArregloVacio() throws AlmacenException, IOException {
		Categoria categoria = new Categoria("pIdentificador","pNombre");
		assertNull(categoria.buscarNodo("1"));
	}
	
	@Test
	public void buscarNodoNoExiste() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertNull(categoria.buscarNodo("2"));
	}
	
	@Test
	public void buscarNodoNull() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertThrows(NullPointerException.class,() -> categoria.buscarNodo(null));
	}
	
	@Test
	public void buscarNodoVacio() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertNull(categoria.buscarNodo(""));
	}

	@Test
	public void agregarNodoCorrecto() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		categoria.agregarNodo("1","Test","2","Prueba");
		assertTrue(categoria.buscarNodo("2").darNombre() == "Prueba");
	}
	
	@Test
	public void agregarNodoIncorrecto1() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		categoria.agregarNodo("","Test","2","Prueba");
		assertNull(categoria.buscarNodo("2"));
	}
	
	@Test
	public void agregarNodoIncorrecto2() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		categoria.agregarNodo("1","Test","","Prueba");
		assertTrue(categoria.buscarNodo("").darNombre() == "Prueba");
	}
	
	@Test
	public void agregarNodoIncorrecto3() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		categoria.agregarNodo(null,"Test","2","Prueba");
		assertNull(categoria.buscarNodo("2"));
	}
	
	@Test
	public void buscaPadreExiste() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		categoria.agregarNodo("1","Test","2","Prueba");
		assertEquals(categoria.buscarNodo("1"),categoria.buscarPadre("2"));
	}

	@Test
	public void buscaPadreNoExiste() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		categoria.agregarNodo(null,"Test","2","Prueba");
		assertNull(categoria.buscarPadre("2"));
	}
	
	@Test
	public void eliminarNodoCorrecto() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		categoria.agregarNodo("1","Test","2","Prueba");
		NodoAlmacen nodoEliminado = categoria.buscarNodo("2");
		assertTrue(categoria.eliminarNodo("2") == nodoEliminado);
	}
	
	@Test
	public void eliminarNodoNoExiste() {
		Categoria categoria = new Categoria("pIdentificador","pNombre");
		assertNull(categoria.eliminarNodo("1"));
	}
	
	@Test
	public void eliminarNodoNull() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertNull(categoria.eliminarNodo(null));
	}
	
	@Test
	public void eliminarNodoVacio() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertNull(categoria.eliminarNodo(""));
	}
	
	@Test
	public void buscarProductoExiste() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertNotNull(categoria.buscarProducto("24881271"));
	}
	
	@Test
	public void buscarProductoNoExiste() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertNull(categoria.buscarProducto("24883241"));
	}
	
	@Test
	public void buscarProductoNull() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertThrows(NullPointerException.class,() -> categoria.buscarProducto(null));
	}
	
	@Test
	public void buscarProductoVacio() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertNull(categoria.buscarProducto(""));
	}
	
	@Test
	public void darMarcasNoVacio() throws AlmacenException, IOException {
		BufferedReader in = generarArchivoCorrecto();
		Categoria categoria = new Categoria(in.readLine( ),in);
		assertTrue(categoria.darMarcas().size() != 11);
	}
	
	@Test
	public void darMarcasVacio() {
		Categoria categoria = new Categoria("pIdentificador","pNombre");
		System.out.println(categoria.darMarcas().size());
		assertTrue(categoria.darMarcas().size() == 0);
	}

}
