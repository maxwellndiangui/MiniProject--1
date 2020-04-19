
import java.util.ArrayList;

public class TrieMap implements TrieMapInterface{
    TrieMapNode root;
    public TrieMap(){
        root = new TrieMapNode();
    }

    public void put(String key, String value){
   /* int i=0;
    TrieMapNode curNode = root;
    while(value.length()-key.length()>i){
      curNode= curNode.getChildren().get(value.charAt(i));
      i++;
    }*/
        TrieMapNode curNode= root;
        put(curNode, key, value);
    }

    //Recursive method
    public void put(TrieMapNode current, String curKey, String value){
        if(current.getChildren().containsKey(curKey.charAt(0))){
            if(curKey.length()==1){
                current.getChildren().get(curKey.charAt(0)).setValue(value);
            }
            else
                put(current.getChildren().get(curKey.charAt(0)), curKey.substring(1, curKey.length()), value);
        }
        else{
            TrieMapNode node = new TrieMapNode();
            if(curKey.length()>1){
                current.getChildren().put(curKey.charAt(0), node);
                put(current.getChildren().get(curKey.charAt(0)), curKey.substring(1, curKey.length()), value);
            }else {
                node.setValue(value);
                current.getChildren().put(curKey.charAt(0), node);
            }
        }
    }

    //Indirectly recursive method to meet definition of interface
    public String get(String key){
        TrieMapNode node = root;
        return get(node, key);
    }

    //Recursive method
    public String get(TrieMapNode current, String curKey){
        String value=null;
        if(curKey.length()==1){
            if(current.getChildren().containsKey(curKey.charAt(0))){
                value = current.getChildren().get(curKey.charAt(0)).getValue();
                return value;
            }
        }else {
            if (current.getChildren().containsKey(curKey.charAt(0))) {
                value = get(current.getChildren().get(curKey.charAt(0)), curKey.substring(1));
            }
        }
        return value;
    }

    //Indirectly recursive method to meet definition of interface
    public boolean containsKey(String key){
        TrieMapNode node = root;
        return containsKey(node, key);
    }

    //Recursive method
    public boolean containsKey(TrieMapNode current, String curKey){
        boolean value=false;
        if(curKey.length()==1){
            if(current.getChildren().containsKey(curKey.charAt(0))){
                value = current.getChildren().get(curKey.charAt(0)).getValue() != null;
                return value;
            }
        }else {
            if (current.getChildren().containsKey(curKey.charAt(0))) {
                value = containsKey(current.getChildren().get(curKey.charAt(0)), curKey.substring(1));
            }
        }
        return value;

    }

    //Indirectly recursive method to meet definition of interface
    public ArrayList<String> getKeysForPrefix(String prefix){
        return getKeysForPrefix(root, prefix);
    }
    public ArrayList<String> getKeysForPrefix(TrieMapNode current, String prefix){
        ArrayList<String> prefixList= new ArrayList<String>();

        if(prefix.length()==1){
            if(current.getChildren().containsKey(prefix.charAt(0))){
                if(current.getChildren().get(prefix.charAt(0)).getValue()!=null){
                    prefixList.add(current.getChildren().get(prefix.charAt(0)).getValue());
                }
                current=  current.getChildren().get(prefix.charAt(0));
                for(Character key : current.getChildren().keySet()){
                    prefixList.addAll(getSubtreeKeys(current.getChildren().get(key)));
                }
            }
            return prefixList;
        }
        if(current.getChildren().containsKey(prefix.charAt(0))){
            prefixList.addAll(getKeysForPrefix(current.getChildren().get(prefix.charAt(0)), prefix.substring(1)));
        }
        return prefixList;
    }

    //Recursive helper function to get all keys in a node's subtree
    public ArrayList<String> getSubtreeKeys(TrieMapNode current){
        ArrayList<String> subTreeKeys = new ArrayList<>();
        if(current.getValue()!=null)
            subTreeKeys.add(current.getValue());

        for(Character key : current.getChildren().keySet()){
            subTreeKeys.addAll(getSubtreeKeys(current.getChildren().get(key)));
        }
        return subTreeKeys;
    }

    //Recursive helper function to find node that matches a prefix
    public TrieMapNode findNode(TrieMapNode current, String curKey){
        return null;
    }


    //Indirectly recursive method to meet definition of interface
    public void print(){
        TrieMapNode curNode = root;
        System.out.print("Trie Values: ");
        print(curNode);
    }
    //Recursive method to print values in tree
    public void print(TrieMapNode current){
        if(current.getValue()!=null)
            System.out.print(current.getValue()+", ");

        for(Character key : current.getChildren().keySet()){
            print(current.getChildren().get(key));
        }
    }

    public static void main(String[] args){
        //The TrieMapTester includes a more detailed test
        TrieMap trieMap = new TrieMap();
        trieMap.put("AB","AB");
        trieMap.put("ABC","ABC");
        trieMap.put("ABCDE","ABCDE");

        trieMap.put("ABCD","ABCD");
        trieMap.put("DBAB","DBAB");
        //trieMap.put("ASD","ASD");
        trieMap.print();
        System.out.println("this: " +trieMap.get("ABCD"));
        System.out.println("this: " +trieMap.containsKey("DBAB"));
        for(String x : trieMap.getKeysForPrefix("AB")){
            System.out.print(x+ ", ");
        }
    }
}