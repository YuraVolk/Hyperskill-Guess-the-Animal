package animals;

import java.util.*;

public class Node {
    public String data = null;
    public Node yes = null;
    public Node no = null;

    public Node(String data, Node yes, Node no) {
        this.data = data;
        this.yes = yes;
        this.no = no;
    }

    public Node(String data) {
        this.data = data;
    }

    public Node() { }

    public String getData() {
        if (data.startsWith("a ")) {
            return data.substring(2);
        } else if (data.startsWith("an ")) {
            return data.substring(3);
        } else {
            return data;
        }
    }

    public void setData(String data) {
        this.data = data;
    }

    public Node getYes() {
        return yes;
    }

    public void setYes(Node yes) {
        this.yes = yes;
    }

    public Node getNo() {
        return no;
    }

    public List<Node> getAllLeafs() {
        List<Node> leafs = new ArrayList<>();
        if (this.yes == null && this.no == null) {
            leafs.add(this);
        } else {
            leafs.addAll(this.no.getAllLeafs());
            leafs.addAll(this.yes.getAllLeafs());
        }
        return leafs;
    }

    public int getMaxDepth(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + getMaxDepth(node.getYes()) + getMaxDepth(node.getNo());
    }

    public void getLevel(Node node, List<Integer> depths) {
        if (node == null) {
            return;
        }

        Queue<Pair> queue = new LinkedList<Pair>();
        queue.add(new Pair(node, 1));
        Pair pair;

        while (!queue.isEmpty()) {
            pair = queue.peek();
            queue.remove();
            if (pair.n.getNo() == null) {
                depths.add(pair.i);
            } else {
                depths.add(pair.i);
                depths.add(pair.i);
                depths.add(pair.i);
                queue.add(new Pair(pair.n.getYes(), pair.i + 1));
                queue.add(new Pair(pair.n.getNo(), pair.i + 1));
            }
        }
    }


    @Override
    public String toString() {
        return "Node{" +
                "data='" + data + '\'' +
                ", yes=" + yes +
                ", no=" + no +
                '}';
    }
}