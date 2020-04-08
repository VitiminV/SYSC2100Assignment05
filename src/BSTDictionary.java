public class BSTDictionary<S1, S2> implements Dictionary {

    private BSTNode<Object, Sortable> root;
    private int depth;
    private int currentDepth = 0;

    public BSTDictionary() {
        root = null;
        depth = 0;
    }

    /**
     *  Recursive function that inserts a BSTNode specified by the 'key' and 'element' parameters into the BSTDictionary.
     *
     *  @param   key   The key by which the items are ordered into the list.
     *  @param element The user readable contents of the node.
     */
    private void insertRecursive(Sortable key, Object element, BSTNode<Object, Sortable> currentNode) {
        //If the supplied key is less than the key of currentNode...
        if(key.compareTo(currentNode.getKey()) > 0) {
            //...check if the left node exists...
            if (currentNode.getLeft() == null) {
                //..if it doesn't, put the current node in that place.
                currentNode.setLeft(new BSTNode<>(key, element, null, null));
                return;
            }
            //...if it does...
            else {
                //...recursively attempt to insert the new node to the left.
                insertRecursive(key, element, currentNode.getLeft());
            }
        }
        //If the supplied key is greater than currentNode.key...
        if(key.compareTo(currentNode.getKey()) < 0) {
            //...check if the right node exists...
            if(currentNode.getRight() == null) {
                //...if it doesn't, put the current node in that place.
                currentNode.setRight(new BSTNode<>(key, element, null, null));
                return;
            }
            // ...if it does...
            else {
                //...recursively try to insert the new node to the right.
                insertRecursive(key, element, currentNode.getRight());
            }
        }
    }



    /**
     *  Recursive function that searches for a BSTNode specified by the 'key' parameter in the BSTDictionary.
     *
     *  @param   key    The key by which the items are ordered into the list.
     *  @return BSTNode The node matching the specified key. If no matching node is found, returns null.
     */
    private BSTNode<Object, Sortable> recursiveSearch(Sortable key, BSTNode<Object, Sortable> currentNode) {
        //If the supplied key is less than the key of currentNode...
        if(key.compareTo(currentNode.getKey()) > 0) {
            //...check if the left node exists...
            if (currentNode.getLeft() == null) {
                //..if it doesn't, return null.
                return null;
            }
            //...if it does...
            else {
                //...recursively search for the the key to the left.
                recursiveSearch(key, currentNode.getLeft());
            }
        }
        //If the supplied key is greater than currentNode.key...
        if(key.compareTo(currentNode.getKey()) < 0) {
            //...check if the right node exists...
            if(currentNode.getRight() == null) {
                //...if it doesn't, return null.
                return null;
            }
            // ...if it does...
            else {
                //...recursively search for the the key to the right.
                recursiveSearch(key, currentNode.getRight());
            }
        }
        //For this return to be reached, currentNode.key must be equal to @param key.
        return currentNode;
    }


    /**
     *  Recursive function that deletes a BSTNode specified by the 'key' parameter from the BSTDictionary.
     *
     *  @param     key     The key by which the items are ordered into the list.
     *  @param currentNode The currently considered node.
     *  @param parentNode  The parent of the currently considered node.
     */
    private void recursiveDelete(Sortable key, BSTNode<Object, Sortable> currentNode, BSTNode<Object, Sortable> parentNode) {
        if(currentNode == null) {return;}
        //If the supplied key is less than the key of currentNode...
        if(key.compareTo(currentNode.getKey()) > 0) {
            //...recur down the tree to the left.
            recursiveDelete(key, currentNode.getLeft(), currentNode);
        }
        //If the supplied key is greater than the key of currentNode...
        if(key.compareTo(currentNode.getKey()) < 0) {
            //...recur down the tree to the right.
            recursiveDelete(key, currentNode.getRight(), currentNode);
        }
        //Otherwise, we must be at the key we mean to delete.
        else {
            BSTNode<Object, Sortable> newNode = recursivePopSmallest(currentNode, parentNode);
            //if the key of currentNode is greater than the key of parentNode...
            if (currentNode.getKey().compareTo(parentNode.getKey()) > 0) {
                //...replace parentNode's right child with newNode, and set newNode's children to currentNode's children.
                parentNode.setRight(newNode);
                newNode.setRight(currentNode.getRight());
                newNode.setLeft(currentNode.getLeft());
            }
        }
    }   

    /**
     * Recursive function that finds the maximum depth of the current tree.
     *
     * @param currentNode The currently considered node.
     * @return   depth    The maximum depth of the tree.
     */
    private int recursiveFindDepth(BSTNode<Object, Sortable> currentNode) {
        //If currentNode is not null, increment.
        if (currentNode != null) {
            currentDepth++;

            //If the current depth is greater than the current maximum depth, record the current depth.
            if (currentDepth > depth) {
                depth = currentDepth;
            }

            //Recursively traverse down the tree.
            recursiveFindDepth(currentNode.getLeft());
            recursiveFindDepth(currentNode.getRight());
        }
        return depth;

    }


    /**
     * Recursive function that finds the smallest Node down the tree from currentNode, removes it from the tree, and returns it.
     *
     * @param currentNode The currently considered Node.
     * @param parentNode  The parent of the currently considered Node.
     * @return  BSTNode   The smallest Node that was found.
     */
    private BSTNode<Object, Sortable> recursivePopSmallest(BSTNode<Object, Sortable> currentNode, BSTNode<Object, Sortable> parentNode) {
        if(currentNode.getLeft() != null) {
            recursivePopSmallest(currentNode.getLeft(), currentNode);
        }
        if(parentNode == null) {
            BSTNode<Object, Sortable> tempNode = currentNode;
            currentNode = null;
            return tempNode;
        }
        else {
            parentNode.setLeft(null);
            return currentNode;
        }
    }

    @Override
    public Object search(Sortable key) {
        return recursiveSearch(key, root);
    }

    @Override
    public void insert(Sortable key, Object element) {
        if(root == null) {
            root = new BSTNode<>(key, element, null, null);
        }
        else {
            insertRecursive(key, element, root);
        }
    }

    @Override
    public void delete(Sortable key) {
        recursiveDelete(key, root, null);
    }

    @Override
    public void printTree() {
        while(root != null) {
            System.out.print(recursivePopSmallest(root, null).getElement());
        }
    }

    @Override
    public int depth() {
        return recursiveFindDepth(root);
    }
}
