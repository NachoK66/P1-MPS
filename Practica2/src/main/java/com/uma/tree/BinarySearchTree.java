package com.uma.tree;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class BinarySearchTree<T> implements BinarySearchTreeStructure<T> {
    private Comparator<T> comparator;
    private T value;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    public String render() {
        String render = "";

        if (value != null) {
            render += value.toString();
        }

        if (left != null || right != null) {
            render += "(";
            if (left != null) {
                render += left.render();
            }
            render += ",";
            if (right != null) {
                render += right.render();
            }
            render += ")";
        }

        return render;
    }

    public BinarySearchTree(Comparator<T> comparator) {
        if (comparator == null)
            throw new BinarySearchTreeException("El comparador no puede ser nulo");
        this.comparator = comparator;
        value = null;
        left = null;
        right = null;
    }

    // Metodo auxiliar
    private boolean isEmpty() {
        return value == null;
    }

    @Override
    public void insert(T value) throws BinarySearchTreeException {
        if (value == null)
            throw new BinarySearchTreeException("El valor no puede ser nulo");
        if (this.value == value)
            return;
        if (isEmpty()) {
            this.value = value;
        } else if (comparator.compare(value, this.value) < 0) {
            if (left == null)
                left = new BinarySearchTree<>(comparator);
            left.insert(value);
        } else {
            if (right == null)
                right = new BinarySearchTree<>(comparator);
            right.insert(value);
        }
        balance();
    }

    @Override
    public boolean isLeaf() {
        return !isEmpty() && left == null && right == null;
    }

    @Override
    public boolean contains(T value) {
        if (value == null)
            throw new BinarySearchTreeException("El valor no puede ser nulo");
        if (isEmpty())
            return false;
        return value.equals(this.value) ||
                (left != null && left.contains(value)) || (right != null && right.contains(value));
    }

    @Override
    public T minimum() throws BinarySearchTreeException {
        if (isEmpty())
            throw new BinarySearchTreeException("Arbol o rama vacíos");
        return left == null ? value : left.minimum();
    }

    @Override
    public T maximum() throws BinarySearchTreeException {
        if (isEmpty())
            throw new BinarySearchTreeException("Arbol o rama vacíos");
        return right == null ? value : right.maximum();
    }

    @Override
    public void removeBranch(T value) {
        if (!isEmpty()) {
            if (this.value.equals(value)) {
                this.value = null;
                this.left = null;
                this.right = null;
            } else {
                if (left != null) {
                    if (left.value.equals(value)) {
                        left.removeBranch(value);
                        left = null;
                    } else {
                        left.removeBranch(value);
                    }
                }
                if (right != null) {
                    if (right.value.equals(value)) {
                        right.removeBranch(value);
                        right = null;
                    } else {
                        right.removeBranch(value);
                    }
                }
            }
            balance();
        }
    }

    @Override
    public int size() {
        if (isEmpty())
            return 0;
        return 1 + (left != null ? left.size() : 0) + (right != null ? right.size() : 0);
    }

    @Override
    public int depth() {
        if (isEmpty())
            return 0;
        return 1 + Math.max(left != null ? left.depth() : 0, right != null ? right.depth() : 0);
    }

    // Complex operations
    @Override
    public void removeValue(T value) {
        if (value == null)
            throw new BinarySearchTreeException("El valor no puede ser nulo");
        if (!isEmpty()) {
            if (this.value.equals(value)) {
                if (isLeaf()) // Caso hoja
                    this.value = null;
                else if (left != null && right != null) { // Caso con dos hijos
                    this.value = right.minimum();
                    right.removeValue(this.value);
                } else if (left != null) { // Caso con un hijo izquierdo. Como solo tiene 1 hijo (arriba se maneja si
                                           // tiene 2), se puede sustituir por el hijo izquierdo y eliminar esa rama
                    this.value = left.value;
                    left = null;
                } else { // Caso con un hijo derecho
                    this.value = right.value;
                    right = null;
                }
            } else {
                if (comparator.compare(this.value, value) > 0) { // Si el valor buscado es menor que el nodo actual
                    if (left != null) {
                        if (left.value.equals(value) && left.isLeaf()) {
                            left = null;
                        } else
                            left.removeValue(value);
                    }
                } else if (right != null) {
                    if (right.value.equals(value) && right.isLeaf()) {
                        right = null;
                    } else
                        right.removeValue(value);
                }
            }
            balance();
        }
    }

    @Override
    public List<T> inOrder() {
        List<T> list = new ArrayList<>();
        if (!isEmpty()) {
            if (left != null)
                list.addAll(left.inOrder());
            list.add(value);
            if (right != null)
                list.addAll(right.inOrder());
            return list;
        }
        return list;
    }

    // Solo dios sabe ya si hay cosas que sobran, lo revisaré más tarde si me
    // apetece
    @Override
    public void balance() {
        if (left != null)
            left.balance();
        if (right != null)
            right.balance();
        if (!isEmpty() && !isLeaf()) {
            if (left != null && left.depth() > (right == null ? 1 : right.depth() + 1)) { // Si la rama izquierda es más
                                                                                          // profunda en 2 o más niveles
                if (left.left != null &&
                        (left.right == null || left.left.depth() > left.right.depth())) { // Si la rama se balancea a la
                                                                                          // izquierda
                    rotateRight();
                } else { // Si la rama se balancea a la derecha
                    left.rotateLeft();
                    rotateRight();
                }
            } else if (right != null && right.depth() > (left == null ? 1 : left.depth() + 1)) { // Si la rama derecha
                                                                                                 // es más profunda en 2
                                                                                                 // o más niveles
                if (right.right != null &&
                        (right.left == null || right.right.depth() > right.left.depth())) { // Si la rama se balancea a la
                                                                                          // derecha
                    rotateLeft();
                } else { // Si la rama se balancea a la izquierda
                    right.rotateRight();
                    rotateLeft();
                }
            }
        }
    }

    private void rotateRight() {
        BinarySearchTree<T> newRoot = left;
        left = left.right;
        newRoot.right = new BinarySearchTree<>(comparator);
        newRoot.right.insert(this.value);
        newRoot.right.left = left;
        newRoot.right.right = right;
        value = newRoot.value;
        left = newRoot.left;
        right = newRoot.right;
    }

    private void rotateLeft() {
        BinarySearchTree<T> newRoot = right;
        right = right.left;
        newRoot.left = new BinarySearchTree<>(comparator);
        newRoot.left.insert(this.value);
        newRoot.left.left = left;
        newRoot.left.right = right;
        value = newRoot.value;
        left = newRoot.left;
        right = newRoot.right;
    }
}
