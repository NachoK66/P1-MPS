package com.uma.tree;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class BinarySearchTree<T> implements BinarySearchTreeStructure<T> {
    private Comparator<T> comparator;
    private T value;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    public String render(){
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
        if(comparator == null)
            throw new BinarySearchTreeException("El comparador no puede ser nulo");
        this.comparator = comparator;
        value = null;
        left = null;
        right = null;
    }

    @Override
    public void insert(T value) {
        if(value == null)
            throw new BinarySearchTreeException("El valor no puede ser nulo");
        if(this.value == value)
            throw new BinarySearchTreeException("El valor ya existe en el árbol");
        if(this.value == null || isLeaf())
            this.value = value;
        else if(comparator.compare(value, this.value) < 0){
            if(left == null)
                left = new BinarySearchTree<>(comparator);
            left.insert(value);
        } else {
            if(right == null)
                right = new BinarySearchTree<>(comparator);
            right.insert(value);
        }
    }

    @Override
    public boolean isLeaf() {
        if (value == null)
            throw new BinarySearchTreeException("Arbol o rama vacíos");
        return left == null && right == null;
    }

    @Override
    public boolean contains(T value) throws BinarySearchTreeException {
        if (this.value == null)
            throw new BinarySearchTreeException("Arbol o rama vacíos");
        return value.equals(this.value) ||
            (left != null && left.contains(value)) || (right != null && right.contains(value));
    }

    @Override
    public T minimum() throws BinarySearchTreeException {
        if (value == null)
            throw new BinarySearchTreeException("Arbol o rama vacíos");
        return left == null ? value : left.minimum();
    }

    @Override
    public T maximum() {
        if (value == null)
            throw new BinarySearchTreeException("Arbol o rama vacíos");
        return right == null ? value : right.maximum();
    }

    @Override
    public void removeBranch(T value){
        if(this.value == null)
            throw new BinarySearchTreeException("Arbol o rama vacíos");
        if(this.value.equals(value)){
            this.value = null;
            this.left = null;
            this.right = null;
        } else {
            if(left != null) {
                if(left.value.equals(value)) {
                    left.removeBranch(value);
                    left = null;
                } else {
                    left.removeBranch(value);
                }
            }
            if(right != null) {
                if(right.value.equals(value)) {
                    right.removeBranch(value);
                    right = null;
                } else {
                    right.removeBranch(value);
                }
            }
        }
    }

    @Override
    public int size() {
        if(this.value == null)
            return 0;
        return 1 + (left != null ? left.size() : 0) + (right != null ? right.size() : 0);
    }

    @Override
    public int depth() {
        if(this.value == null)
            return 0;
        return 1 + Math.max(left != null ? left.depth() : 0, right != null ? right.depth() : 0);
    }

    // Complex operations
    // (Estas operaciones se incluirán más adelante para ser realizadas en la segunda
    // sesión de laboratorio de esta práctica)
}
