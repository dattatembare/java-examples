package examples.bst;

public class BinarySearchTree {

  public Node root;

  public void insert(int value) {
    Node node = new Node<>(value);

    if (root == null) {
      root = node;
      return;
    }

    insertRec(root, node);
  }

  private void insertRec(Node latestRoot, Node node) {

    if (latestRoot.value > node.value) {

      if (latestRoot.left == null) {
        latestRoot.left = node;
        return;
      } else {
        insertRec(latestRoot.left, node);
      }
    } else {
      if (latestRoot.right == null) {
        latestRoot.right = node;
        return;
      } else {
        insertRec(latestRoot.right, node);
      }
    }
  }

  public static void main(String[] args) {
    BinarySearchTree bst = new BinarySearchTree();
    bst.insert(10);
    bst.insert(12);
    bst.insert(14);
    bst.insert(8);
    bst.insert(9);
  }

}

