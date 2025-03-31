/* Realizado por Ignacio Morillas Rosell */

import com.uma.tree.BinarySearchTree;
import com.uma.tree.BinarySearchTreeException;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/* TESTS
 * 
 * 1. Constructor con comparador nulo
 * 2. Constructor con comparador correcto
 * 
 * 3. Render del árbol
 * 
 * 4. Insertar un valor correcto
 * 5. Insertar un valor nulo
 * 6. Insertar un valor ya existente
 * 
 * 7. Preguntar si es hoja
 * 
 * 8. Preguntar si contiene un valor existente
 * 9. Preguntar si contiene un valor no existente
 * 10. Preguntar si contiene un valor nulo
 * 
 * 11. Obtener el valor del nodo mínimo
 * 12. Obtener el valor del nodo máximo
 * 
 * 13. Borrar una rama existente
 * 14. Borrar una rama no existente
 * 15. Borrar un valor nulo
 * 16. Borrar un valor existente hoja
 * 17. Borrar un valor existente con un hijo
 * 18. Borrar un valor existente con dos hijos
 * 19. Borrar un valor no existente
 * 
 * 20. Preguntar por el tamaño
 * 21. Preguntar por la profundidad
 * 
 * 22. Obtener una lista con los elementos del árbol en orden
 * 
 * 23. Balancear el árbol
 * 
 */

@DisplayName("Árbol binario de búsqueda")
class BinarySearchTreeTest {
	BinarySearchTree<Integer> tree;

	@Test
	@DisplayName("lanza una excepción al crear un árbol con un comparador nulo")
	void binarySearchTree_conComparadorNulo_devuelveExcepcion() {
		assertThrows(BinarySearchTreeException.class, () -> new BinarySearchTree<>(null));
	}

	@Test
	@DisplayName("se instancia correctamente al usar un comparador usando new BinarySearchTree<>(Comparator)")
	void testConstructorConComparador() {
		assertNotNull(new BinarySearchTree<>(Integer::compareTo));
	}

	@Nested
	@DisplayName("cuando está vacío")
	class testArbolVacio {

		@BeforeEach
		void setUp() {
			tree = new BinarySearchTree<>(Integer::compareTo);
		}

		@Test
		@DisplayName("se muestra como cadena vacía")
		void render_arbolVacio_devuelveCadenaVacia() {
			assertEquals("", tree.render());
		}

		@Nested
		@DisplayName("al insertar")
		class insertarEnVacio {
			@Test
			@DisplayName("se puede insertar un valor si es correcto")
			void insert_valorEnArbolVacio_insertaValor() {
				tree.insert(5);

				assertEquals("5", tree.render());
			}

			@Test
			@DisplayName("lanza excepcion al intentar insertar un valor nulo")
			void insert_valorNuloEnArbolVacio_LanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.insert(null));
			}
		}

		@Test
		@DisplayName("no es hoja")
		void isLeaf_arbolVacio_devuelveFalso() {
			assertFalse(tree.isLeaf());
		}

		@Nested
		@DisplayName("al preguntar si contiene un valor")
		class containsVacio {
			@Test
			@DisplayName("devuelve falso para cualquier valor")
			void contains_arbolVacio_devuelveFalso() {
				assertFalse(tree.contains(5));
			}

			@Test
			@DisplayName("lanza excepción si el valor es nulo")
			void contains_elementoNuloEnArbolVacio_lanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.contains(null));
			}
		}

		@Test
		@DisplayName("lanza excepción al preguntar por el mínimo")
		void minimum_arbolVacio_lanzaExcepcion() {
			assertThrows(BinarySearchTreeException.class, () -> tree.minimum());
		}

		@Test
		@DisplayName("lanza excepción al preguntar por el máximo")
		void maximum_arbolVacio_lanzaExcepcion() {
			assertThrows(BinarySearchTreeException.class, () -> tree.maximum());
		}

		@Nested
		@DisplayName("al borrar")
		class borrarVacio {
			@Test
			@DisplayName("una rama, se mantiene igual")
			void removeBranch_arbolVacio_seMantieneIgual() {
				String renderPrevio = tree.render();

				tree.removeBranch(5);

				assertEquals(renderPrevio, tree.render());
			}

			@Test
			@DisplayName("un valor nulo, lanza excepción")
			void removeValue_valorNulo_lanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.removeValue(null));
			}

			@Test
			@DisplayName("un valor, no hace nada")
			void removeValue_arbolVacio_noHaceNada() {
				tree.removeValue(5);

				assertEquals("", tree.render());
			}
		}
		
		@Test
		@DisplayName("es de tamaño 0")
		void size_arbolVacio_devuelveCero() {
			int size = tree.size();

			assertEquals(0, size);
		}

		@Test
		@DisplayName("es de profundidad 0")
		void depth_arbolVacio_devuelveCero() {
			int depth = tree.depth();

			assertEquals(0, depth);
		}

		@Test
		@DisplayName("devuelve una lista vacía")
		void inOrder_arbolVacio_devuelveListaVacia() {
			List<Integer> listaEsperada = new ArrayList<>();

			assertEquals(listaEsperada, tree.inOrder());
		}

		@Test
		@DisplayName("al balancear no hace nada")
		void balance_arbolVacio_noHaceNada() {
			String renderPrevio = tree.render();

			tree.balance();

			assertEquals(renderPrevio, tree.render());
		}

	}

	@Nested
	@DisplayName("cuando tiene un elemento")
	class testArbolHoja {
		@BeforeEach
		void setUp() {
			tree = new BinarySearchTree<>(Integer::compareTo);

			tree.insert(5);
		}

		@Test
		@DisplayName("se muestra correctamente")
		void render_arbolHoja_devuelveCadenaConElemento() {
			assertEquals("5", tree.render());
		}

		@Nested
		@DisplayName("al insertar")
		class insertar {
			@Test
			@DisplayName("se puede insertar un valor si es correcto")
			void insert_valorCorrectoEnArbolHoja_insertaValor() {
				tree.insert(3);

				assertEquals("5(3,)", tree.render());
			}

			@Test
			@DisplayName("al insertar un valor ya existente no hace nada")
			void insert_valorYaExistente_noHaceNada() {
				String renderPrevio = tree.render();

				tree.insert(5);

				assertEquals(renderPrevio, tree.render());
			}

			@Test
			@DisplayName("lanza excepcion al intentar insertar un valor nulo")
			void insert_valorNuloEnArbolHoja_LanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.insert(null));
			}
		}

		@Test
		@DisplayName("es hoja")
		void isLeaf_arbolHoja_devuelveVerdadero() {
			assertTrue(tree.isLeaf());
		}

		@Nested
		@DisplayName("al preguntar si contiene un valor")
		class contains {
			@Test
			@DisplayName("devuelve verdadero si el valor existe")
			void contains_elementoEnArbolHoja_devuelveVerdadero() {
				assertTrue(tree.contains(5));
			}

			@Test
			@DisplayName("devuelve falso si el valor no existe")
			void contains_elementoNoEnArbolHoja_devuelveFalso() {
				assertFalse(tree.contains(3));
			}

			@Test
			@DisplayName("lanza excepción si el valor es nulo")
			void contains_elementoNuloEnArbolHoja_lanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.contains(null));
			}
		}

		@Nested
		@DisplayName("al borrar")
		class borrar {
			@Test
			@DisplayName("una rama existente, se queda vacío")
			void removeBranch_existeEnArbolHoja_seQuedaVacio() {
				String renderEsperado = "";

				tree.removeBranch(5);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("una rama que no existe, no cambia nada")
			void removeBranch_noExisteEnArbolHoja_noCambiaNada() {
				String renderEsperado = "5";

				tree.removeBranch(3);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor nulo, lanza excepción")
			void removeValue_valorNulo_lanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.removeValue(null));
			}

			@Test
			@DisplayName("un valor existente, se realiza correctamente")
			void removeValue_arbolHoja_borraValorHoja() {
				tree.removeValue(5);

				assertEquals("", tree.render());
			}
		}

		@Test
		@DisplayName("devuelve el tamaño correcto")
		void size_arbolHoja_devuelveTamano() {
			int sizeEsperado = 1;

			assertEquals(sizeEsperado, tree.size());
		}

		@Test
		@DisplayName("devuelve la profundidad maxima correctamente")
		void depth_arbolHoja_devuelveProfundidadMaxima() {
			int depthEsperada = 1;

			assertEquals(depthEsperada, tree.depth());
		}

		@Test
		@DisplayName("devuelve una lista de un elemento")
		void inOrder_arbolHoja_devuelveListaDeUnElemento() {
			List<Integer> listaEsperada = new ArrayList<>();
			listaEsperada.add(5);

			assertEquals(listaEsperada, tree.inOrder());
		}

		@Test
		@DisplayName("al balancear se queda igual")
		void balance_arbolHoja_quedaIgual() {
			String renderPrevio = tree.render();

			tree.balance();

			assertEquals(renderPrevio, tree.render());
		}

	}

	@Nested
	@DisplayName("cuando tiene varios elementos insertados aleatoriamente")
	class testArbolConVariosElementos {

		@BeforeEach
		void setUp() {
			tree = new BinarySearchTree<>(Integer::compareTo);

			tree.insert(8);
			tree.insert(3);
			tree.insert(6);
			tree.insert(10);
			tree.insert(1);
			tree.insert(14);
			tree.insert(4);
			tree.insert(7);
			tree.insert(13);
			tree.insert(15);
		}

		@Test
		@DisplayName("se muestra correctamente")
		void render_arbolConVariosElementos_devuelveCadenaConElementos() {
			String render = tree.render();
			String renderEsperado = "6(3(1,4),10(8(7,),14(13,15)))";

			System.out.println(render);
			assertEquals(renderEsperado, tree.render());
		}

		@Nested
		@DisplayName("al insertar")
		class insertar {
			@Test
			@DisplayName("se puede insertar un valor si es correcto")
			void insert_valorCorrectoEnArbolConElementos_insertaValor() {
				tree.insert(5);
				String renderEsperado = "6(3(1,4(,5)),10(8(7,),14(13,15)))";

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("al insertar un valor ya existente no hace nada")
			void insert_valorYaExistente_noHaceNada() {
				String renderPrevio = tree.render();

				tree.insert(8);

				assertEquals(renderPrevio, tree.render());
			}

			@Test
			@DisplayName("lanza excepcion al intentar insertar un valor nulo")
			void insert_valorNuloEnArbolConElementos_LanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.insert(null));
			}
		}

		@Test
		@DisplayName("no es hoja")
		void isLeaf_arbolConVariosElementos_devuelveFalso() {
			assertFalse(tree.isLeaf());
		}

		@Nested
		@DisplayName("al preguntar si contiene un valor")
		class contains {
			@Test
			@DisplayName("devuelve verdadero si el valor existe")
			void contains_elementoEnArbolConVariosElementos_devuelveVerdadero() {
				assertTrue(tree.contains(6));
			}

			@Test
			@DisplayName("devuelve falso si el valor no existe")
			void contains_elementoNoEnArbolConVariosElementos_devuelveFalso() {
				assertFalse(tree.contains(9));
			}

			@Test
			@DisplayName("lanza excepción si el valor es nulo")
			void contains_elementoNuloEnArbolConVariosElementos_lanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.contains(null));
			}
		}

		@Test
		@DisplayName("devuelve el mínimo correctamente")
		void minimum_arbolConVariosElementos_devuelveMinimo() {
			int valorEsperado = 1;

			assertEquals(valorEsperado, tree.minimum());
		}

		@Test
		@DisplayName("devuelve el máximo correctamente")
		void maximum_arbolConVariosElementos_devuelveMaximo() {
			int valorEsperado = 15;

			assertEquals(valorEsperado, tree.maximum());
		}

		@Nested
		@DisplayName("al borrar")
		class borrar {

			@Test
			@DisplayName("una rama, se realiza y balancea correctamente")
			void removeBranch_existeEnArbolConVariosElementos_borraRama() {
				String renderEsperado = "8(6(,7),10(,14(13,15)))";

				tree.removeBranch(3);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("una rama que no existe, no cambia nada")
			void removeBranch_noExisteEnArbolConVariosElementos_noCambiaNada() {
				String renderEsperado = "6(3(1,4),10(8(7,),14(13,15)))";

				tree.removeBranch(9);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor nulo, lanza excepción")
			void removeValue_valorNulo_lanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.removeValue(null));
			}

			@Test
			@DisplayName("un valor hoja, se realiza y balancea correctamente")
			void removeValue_arbolConVariosElementos_borraValorHoja() {
				int valorABorrar = 7;
				String renderEsperado = "6(3(1,4),10(8,14(13,15)))";

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor con un hijo, se realiza y balancea correctamente")
			void removeValue_arbolConVariosElementos_borraValorConUnHijo() {
				int valorABorrar = 8;
				String renderEsperado = "6(3(1,4),10(7,14(13,15)))";

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor con dos hijos, se realiza y balancea correctamente")
			void removeValue_arbolConVariosElementos_borraValor() {
				int valorABorrar = 3;
				String renderEsperado = "6(4(1,),10(8(7,),14(13,15)))";

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor que no existe, no hace nada")
			void removeValue_arbolConVariosElementosValorNoExiste_noHaceNada() {
				int valorABorrar = 9;
				String renderEsperado = tree.render();

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}
		}

		@Test
		@DisplayName("devuelve el tamaño correcto")
		void size_arbolConVariosElementos_devuelveTamano() {
			int sizeEsperado = 10;

			assertEquals(sizeEsperado, tree.size());
		}

		@Test
		@DisplayName("devuelve la profundidad maxima correctamente")
		void depth_arbolConVariosElementos_devuelveProfundidadMaxima() {
			int depthEsperada = 4;

			assertEquals(depthEsperada, tree.depth());
		}

		@Test
		@DisplayName("devuelve una lista ordenada")
		void inOrder_arbolConVariosElementos_devuelveListaOrdenada() {
			List<Integer> listaEsperada = new ArrayList<>();
			listaEsperada.add(1);
			listaEsperada.add(3);
			listaEsperada.add(4);
			listaEsperada.add(6);
			listaEsperada.add(7);
			listaEsperada.add(8);
			listaEsperada.add(10);
			listaEsperada.add(13);
			listaEsperada.add(14);
			listaEsperada.add(15);

			assertEquals(listaEsperada, tree.inOrder());
		}

		@Test
		@DisplayName("al balancear se obtiene un arbol balanceado")
		void balance_arbolConVariosElementos_balanceaArbol() {
			String renderEsperado = "6(3(1,4),10(8(7,),14(13,15)))";

			tree.balance();
			String render = tree.render();

			assertEquals(renderEsperado, render);
		}
	}

	@Nested
	@DisplayName("cuando tiene varios elementos insertados en orden ascendente")
	class testArbolConVariosElementosOrdenAscendente {
		@BeforeEach
		void setUp() {
			tree = new BinarySearchTree<>(Integer::compareTo);

			tree.insert(1);
			tree.insert(3);
			tree.insert(4);
			tree.insert(6);
			tree.insert(7);
			tree.insert(8);
			tree.insert(10);
			tree.insert(13);
			tree.insert(14);
			tree.insert(15);
		}

		@Test
		@DisplayName("se muestra balanceado")
		void render_arbolConVariosElementosOrden_devuelveCadenaConElementos() {
			String render = tree.render();
			String renderEsperado = "6(3(1,4),13(8(7,10),14(,15)))";

			System.out.println(render);
			assertEquals(renderEsperado, tree.render());
		}

		@Nested
		@DisplayName("al insertar")
		class insertar {
			@Test
			@DisplayName("se puede insertar un valor si es correcto")
			void insert_insertarValorEnArbolVacio_insertaValor() {
				tree.insert(5);

				assertEquals("6(3(1,4(,5)),13(8(7,10),14(,15)))", tree.render());
			}

			@Test
			@DisplayName("al insertar un valor ya existente no hace nada")
			void insert_insertarValorYaExistente_noHaceNada() {
				String renderPrevio = tree.render();

				tree.insert(6);

				assertEquals(renderPrevio, tree.render());
			}

			@Test
			@DisplayName("lanza excepcion al intentar insertar un valor nulo")
			void insert_valorNuloEnArbolVacio_LanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.insert(null));
			}
		}

		@Test
		@DisplayName("no es hoja")
		void isLeaf_arbolConVariosElementosOrden_devuelveFalso() {
			assertFalse(tree.isLeaf());
		}

		@Nested
		@DisplayName("al preguntar si contiene un valor")
		class contains {
			@Test
			@DisplayName("devuelve verdadero si el valor existe")
			void contains_arbolConVariosElementosOrden_devuelveVerdadero() {
				assertTrue(tree.contains(6));
			}

			@Test
			@DisplayName("devuelve falso si el valor no existe")
			void contains_arbolConVariosElementosOrden_devuelveFalso() {
				assertFalse(tree.contains(9));
			}

			@Test
			@DisplayName("lanza excepción si el valor es nulo")
			void contains_arbolConVariosElementosOrden_lanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.contains(null));
			}
		}

		@Test
		@DisplayName("devuelve el mínimo correctamente")
		void minimum_arbolConVariosElementosOrden_devuelveMinimo() {
			int valorEsperado = 1;

			assertEquals(valorEsperado, tree.minimum());
		}

		@Test
		@DisplayName("devuelve el máximo correctamente")
		void maximum_arbolConVariosElementosOrden_devuelveMaximo() {
			int valorEsperado = 15;

			assertEquals(valorEsperado, tree.maximum());
		}

		@Nested
		@DisplayName("al borrar")
		class borrar {
			@Test
			@DisplayName("una rama, se realiza y rebalancea correctamente")
			void removeBranch_arbolConVariosElementosOrden_borraRama() {
				String renderEsperado = "4(3(1,),6)";

				tree.removeBranch(13);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("una rama que no existe, no cambia nada")
			void removeBranch_arbolConVariosElementosOrden_borraRamaNoExistente() {
				String renderEsperado = "6(3(1,4),13(8(7,10),14(,15)))";

				tree.removeBranch(9);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor nulo, lanza excepción")
			void removeValue_valorNulo_lanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.removeValue(null));
			}

			@Test
			@DisplayName("un valor hoja, se realiza correctamente")
			void removeValue_arbolConVariosElementos_borraValorHoja() {
				int valorABorrar = 15;
				String renderEsperado = "6(3(1,4),13(8(7,10),14))";

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor con un hijo, se realiza correctamente")
			void removeValue_arbolConVariosElementos_borraValorConUnHijo() {
				int valorABorrar = 14;
				String renderEsperado = "6(3(1,4),13(8(7,10),15))";

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor con dos hijos, se realiza correctamente")
			void removeValue_arbolConVariosElementos_borraValorConDosHijos() {
				int valorABorrar = 3;
				String renderEsperado = "6(4(1,),13(8(7,10),14(,15)))";

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor que no existe, no cambia nada")
			void removeValue_arbolConVariosElementosValorNoExiste_noCambiaNada() {
				int valorABorrar = 9;
				String renderEsperado = "6(3(1,4),13(8(7,10),14(,15)))";

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}
		}

		@Test
		@DisplayName("devuelve el tamaño correcto")
		void size_arbolConVariosElementosOrden_devuelveTamano() {
			int sizeEsperado = 10;

			assertEquals(sizeEsperado, tree.size());
		}

		@Test
		@DisplayName("devuelve la profundidad maxima correctamente")
		void depth_arbolConVariosElementosOrden_devuelveProfundidadMaxima() {
			int depthEsperada = 4;

			assertEquals(depthEsperada, tree.depth());
		}

		@Test
		@DisplayName("devuelve una lista ordenada")
		void inOrder_arbolConVariosElementos_devuelveListaOrdenada() {
			List<Integer> listaEsperada = new ArrayList<>();
			listaEsperada.add(1);
			listaEsperada.add(3);
			listaEsperada.add(4);
			listaEsperada.add(6);
			listaEsperada.add(7);
			listaEsperada.add(8);
			listaEsperada.add(10);
			listaEsperada.add(13);
			listaEsperada.add(14);
			listaEsperada.add(15);

			assertEquals(listaEsperada, tree.inOrder());
		}

		@Test
		@DisplayName("al balancear se obtiene un arbol balanceado")
		void balance_arbolConVariosElementos_balanceaArbol() {
			String renderEsperado = "6(3(1,4),13(8(7,10),14(,15)))";

			tree.balance();
			String render = tree.render();

			assertEquals(renderEsperado, render);
		}
	}

	@Nested
	@DisplayName("cuando tiene varios elementos insertados en orden descendente")
	class testArbolConVariosElementosOrdenDescendente {
		@BeforeEach
		void setUp() {
			tree = new BinarySearchTree<>(Integer::compareTo);

			tree.insert(15);
			tree.insert(14);
			tree.insert(13);
			tree.insert(10);
			tree.insert(8);
			tree.insert(7);
			tree.insert(6);
			tree.insert(4);
			tree.insert(3);
			tree.insert(1);
		}

		@Test
		@DisplayName("se muestra balanceado")
		void render_arbolConVariosElementosOrdenDescendente_devuelveCadenaConElementos() {
			String render = tree.render();
			String renderEsperado = "10(4(3(1,),7(6,8)),14(13,15))";

			System.out.println(render);
			assertEquals(renderEsperado, tree.render());
		}

		@Nested
		@DisplayName("al insertar")
		class insertar {
			@Test
			@DisplayName("se puede insertar un valor si es correcto")
			void insert_insertarValorEnArbolOrdenadoDescendente_insertaValorYReordena() {
				String renderEsperado = "7(4(3(1,),6(5,)),10(8,14(13,15)))";

				tree.insert(5);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("al insertar un valor ya existente no hace nada")
			void insert_insertarValorYaExistente_noHaceNada() {
				String renderPrevio = tree.render();

				tree.insert(6);

				assertEquals(renderPrevio, tree.render());
			}

			@Test
			@DisplayName("lanza excepcion al intentar insertar un valor nulo")
			void insert_valorNuloEnArbolOrdenadoDescendente_LanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.insert(null));
			}
		}

		@Test
		@DisplayName("no es hoja")
		void isLeaf_arbolConVariosElementosOrdenDescendente_devuelveFalso() {
			assertFalse(tree.isLeaf());
		}

		@Nested
		@DisplayName("al preguntar si contiene un valor")
		class contains {
			@Test
			@DisplayName("devuelve verdadero si el valor existe")
			void contains_arbolConVariosElementosOrdenDescendente_devuelveVerdadero() {
				assertTrue(tree.contains(6));
			}

			@Test
			@DisplayName("devuelve falso si el valor no existe")
			void contains_arbolConVariosElementosOrdenDescendente_devuelveFalso() {
				assertFalse(tree.contains(9));
			}

			@Test
			@DisplayName("lanza excepción si el valor es nulo")
			void contains_arbolConVariosElementosOrdenDescendente_lanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.contains(null));
			}
		}

		@Test
		@DisplayName("devuelve el mínimo correctamente")
		void minimum_arbolConVariosElementosOrdenDescendente_devuelveMinimo() {
			int valorEsperado = 1;

			assertEquals(valorEsperado, tree.minimum());
		}

		@Test
		@DisplayName("devuelve el máximo correctamente")
		void maximum_arbolConVariosElementosOrdenDescendente_devuelveMaximo() {
			int valorEsperado = 15;

			assertEquals(valorEsperado, tree.maximum());
		}

		@Nested
		@DisplayName("al borrar")
		class borrar {
			@Test
			@DisplayName("una rama, se realiza y rebalancea correctamente")
			void removeBranch_arbolConVariosElementosOrdenDescendente_borraRama() {
				String renderEsperado = "13(10,14(,15))";

				tree.removeBranch(4);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("una rama que no existe, no cambia nada")
			void removeBranch_arbolConVariosElementosOrdenDescendente_borraRamaNoExistente() {
				String renderEsperado = tree.render();

				tree.removeBranch(9);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor nulo, lanza excepción")
			void removeValue_valorNulo_lanzaExcepcion() {
				assertThrows(BinarySearchTreeException.class, () -> tree.removeValue(null));
			}

			@Test
			@DisplayName("un valor hoja, se realiza correctamente")
			void removeValue_arbolConVariosElementos_borraValorHoja() {
				int valorABorrar = 15;
				String renderEsperado = "10(4(3(1,),7(6,8)),14(13,))";

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor con un hijo, se realiza correctamente")
			void removeValue_arbolConVariosElementos_borraValorConUnHijo() {
				int valorABorrar = 3;
				String renderEsperado = "10(4(1,7(6,8)),14(13,15))";

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor con dos hijos, se realiza correctamente")
			void removeValue_arbolConVariosElementos_borraValorConDosHijos() {
				int valorABorrar = 7;
				String renderEsperado = "10(4(3(1,),8(6,)),14(13,15))";

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}

			@Test
			@DisplayName("un valor que no existe, no cambia nada")
			void removeValue_arbolConVariosElementosValorNoExiste_noCambiaNada() {
				int valorABorrar = 9;
				String renderEsperado = tree.render();

				tree.removeValue(valorABorrar);

				assertEquals(renderEsperado, tree.render());
			}
		}

		@Test
		@DisplayName("devuelve el tamaño correcto")
		void size_arbolConVariosElementosOrdenDescendente_devuelveTamano() {
			int sizeEsperado = 10;

			assertEquals(sizeEsperado, tree.size());
		}

		@Test
		@DisplayName("devuelve la profundidad maxima correctamente")
		void depth_arbolConVariosElementosOrdenDescendente_devuelveProfundidadMaxima() {
			int depthEsperada = 4;

			assertEquals(depthEsperada, tree.depth());
		}

		@Test
		@DisplayName("devuelve una lista ordenada")
		void inOrder_arbolConVariosElementos_devuelveListaOrdenada() {
			List<Integer> listaEsperada = new ArrayList<>();
			listaEsperada.add(1);
			listaEsperada.add(3);
			listaEsperada.add(4);
			listaEsperada.add(6);
			listaEsperada.add(7);
			listaEsperada.add(8);
			listaEsperada.add(10);
			listaEsperada.add(13);
			listaEsperada.add(14);
			listaEsperada.add(15);

			assertEquals(listaEsperada, tree.inOrder());
		}

		@Test
		@DisplayName("al balancear se obtiene un arbol balanceado")
		void balance_arbolConVariosElementosOrdenadoDescendente_balanceaArbol() {
			String renderEsperado = "10(4(3(1,),7(6,8)),14(13,15))";

			tree.balance();
			String render = tree.render();

			assertEquals(renderEsperado, render);
		}
	}
}
