设计并实现最不经常使用（LFU）缓存的数据结构。它应该支持以下操作：get 和 put。

get(key) - 如果键存在于缓存中，则获取键的值（总是正数），否则返回 -1。
put(key, value) - 如果键不存在，请设置或插入值。当缓存达到其容量时，它应该在插入新项目之前，使最不经常使用的项目无效。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，最近最少使用的键将被去除。

一个项目的使用次数就是该项目被插入后对其调用 get 和 put 函数的次数之和。使用次数会在对应项目被移除后置为 0。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/lfu-cache
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

class LFUCache {
    Map<Integer,Node> map;
    Map<Integer,LinkedList<Node>> feqMap;
    private int capacity;
    private int min=1;
    public LFUCache(int capacity) {
        map=new HashMap<>(capacity);
        feqMap=new HashMap<>();
        this.capacity=capacity;
    }
    
    public int get(int key) {
        Node node=map.get(key);
        if(node==null){
            return -1;
        }
        freqInc(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if(capacity==0) return;
        Node node=map.get(key);
        if(node!=null){
            node.val=value;
            freqInc(node);
        }else{
            if(map.size()==capacity){
                LinkedList<Node> list=feqMap.get(min);
                Node cur=list.removeLast();
                map.remove(cur.key);
            }
            Node newNode=new Node(key,value);
            map.put(key,newNode);
            LinkedList<Node> list=feqMap.get(1);
            if(list==null){
                list=new LinkedList<>();
                feqMap.put(1,list);
            }
            list.addFirst(newNode);
            min=1;
        }
    }

    private void freqInc(Node node){
        int fre=node.freq;
        LinkedList<Node> list1=feqMap.get(fre);
        list1.remove(node);
        if(fre==min&&list1.size()==0){
            min=fre+1;
        }
        node.freq++;
        LinkedList<Node> list2=feqMap.get(fre+1);
        if(list2==null){
            list2=new LinkedList<>();
            feqMap.put(fre+1,list2);
        }
        list2.addFirst(node);
    }
}

class Node{
    public int key;
    public int val;
    public int freq=1;
    public Node(){

    }
    public Node(int key,int val){
        this.key=key;
        this.val=val;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
 
 
 