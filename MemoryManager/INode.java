import java.util.ArrayList;

public class INode {

    private final int size = 64;
    private final ArrayList<MemoryCell> clusters;
    private INode next;

    public INode() {
        clusters = new ArrayList<>(64);
        next = null;
    }

    public MemoryCell getCluster(int i) {
        return clusters.get(i);
    }

    public void setClusters(MemoryCell[] cells) {
        INode curINode = getLast();
        for (MemoryCell cell : cells) {
            curINode.clusters.add(cell);
            if (curINode.size == clusters.size()) {
                curINode.next = new INode();
                curINode = curINode.next;
            }
        }
    }

    public void setClusters(MemoryCell cells) {
        INode curINode = getLast();
        curINode.clusters.add(cells);
        if (curINode.size == clusters.size()) {
            curINode.next = new INode();
        }
    }

    public INode getNext() {
        return next;
    }

    public void setNext(INode next) {
        this.next = next;
    }

    public INode getLast(){
        INode node = this;
        while (node.next != null)
            node = node.next;
        return node;
    }

    public void setMemoryStatus(MemoryCell.MemoryCellStatus status) {
        for (INode in = this; in.next != null; in = in.next) {
            for (int i = 0; i < clusters.size(); i++) {
                MemoryCell cell = clusters.get(i);
                cell.setStatus(status);
                if (status != MemoryCell.MemoryCellStatus.None) {
                    clusters.add(cell);
                }
            }
        }
    }

    public int getNodeCapacity() {
        return clusters.size();
    }
}
