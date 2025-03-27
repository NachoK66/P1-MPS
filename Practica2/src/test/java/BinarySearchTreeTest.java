import com.uma.tree.BinarySearchTree;
import com.uma.tree.BinarySearchTreeException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Binary Search Tree Test")
class BinarySearchTreeTest {
	BinarySearchTree<Integer> tree;

	@Test
	@DisplayName("Falla al crear un árbol con un comparador nulo")
	void testConstructorConComparadorNulo() {
		assertThrows(BinarySearchTreeException.class, () -> new BinarySearchTree<>(null));
	}

	@Nested
	@DisplayName("Al usar un árbol vacío")
	class testArbolVacio {

		@BeforeEach
		void setUp() {
			tree = new BinarySearchTree<>(Integer::compareTo);
		}

		@Test
		@DisplayName("se muestra como cadena vacía")
		void render_arbolvacio_devuelveCadenaVacia() {
			assertEquals("", tree.render());
		}

		@Test
		@DisplayName("se puede insertar un valor")
		void insert_insertarValorEnArbolVacio_insertaValor() {
			tree.insert(5);

			assertEquals("5", tree.render());
		}

		@Test
		@DisplayName("lanza excepcion al intentar insertar un valor nulo")
		void insert_valorNuloEnArbolVacio_LanzaExcepcion() {
			assertThrows(BinarySearchTreeException.class, () -> tree.insert(null));
		}

		@Test
		@DisplayName("lanza excepcion al ver si es hoja")
		void isLeaf_arbolvacio_lanzaExcepcion() {
			assertThrows(BinarySearchTreeException.class, () -> tree.isLeaf());
		}


	}

}
