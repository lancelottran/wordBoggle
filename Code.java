String[] wordBoggle(char[][] board, String[] words) {
    if(words == null)
        return new String[0];
    
    TrieNode root = new TrieNode();
    
    for(int i = 0; i < words.length; i++)
        insert(root, words[i]);
    
    List<String> res = find_world(board, root);
    Collections.sort(res);
    
    return res.toArray(new String[0]);
}

List<String> find_world(char[][] board, TrieNode root){
    boolean[][] visit = new boolean[board.length][board[0].length];
    List<String> res = new ArrayList<String>();
    
    TrieNode pChild = root;
    
    String str = "";
    
    for(int i = 0; i < board.length; i++){
        for(int j = 0; j < board[i].length; j++){
            if(pChild.Child[(board[i][j]) - 'A'] != null)
            {
                str = str + board[i][j];
                res = search(pChild.Child[(board[i][j]) - 'A'], board, i, j, visit, str, res);
                str = "";
            }
        }
    }
    
    return res;
}

class TrieNode{
    TrieNode[] Child = new TrieNode[26];
    
    boolean isWord;
    
    public TrieNode(){
        isWord = false;
        
        for(int i = 0; i < 26; i++)
            Child[i] = null;
    }
}

void insert(TrieNode root, String key){
    int len = key.length();
    TrieNode pChild = root;
    
    for(int i = 0; i < len; i++){
        int index = key.charAt(i) - 'A';
        
        if(pChild.Child[index] == null)
            pChild.Child[index] = new TrieNode();
        
        pChild = pChild.Child[index];
    }
    
    pChild.isWord = true;
}

boolean isSafe(int i, int j, boolean[][] visit){    
    return (i >= 0 && i < visit.length && j >= 0 && j < visit[i].length && !visit[i][j]);
}

List<String> search(TrieNode root, char[][] board, int i, int j, boolean[][] visit, String str, List<String> res){   
    if(root.isWord == true){
        if(!res.contains(str))
            res.add(str);
    }
    
    if(isSafe(i, j, visit)){
        visit[i][j] = true;
        
        for(int k = 0; k < 26; k++){
            if(root.Child[k] != null)
            {
                char ch = (char) (k + 'A');
                if(isSafe(i+1, j+1, visit) && board[i+1][j+1] == ch)
                    search(root.Child[k], board, i+1, j+1, visit, str+ch, res);
                if(isSafe(i, j+1, visit) && board[i][j+1] == ch)
                    search(root.Child[k], board, i, j+1, visit, str+ch, res);
                if(isSafe(i+1, j, visit) && board[i+1][j] == ch)
                    search(root.Child[k], board, i+1, j, visit, str+ch, res);
                if(isSafe(i, j-1, visit) && board[i][j-1] == ch)
                    search(root.Child[k], board, i, j-1, visit, str+ch, res);                    
                if(isSafe(i-1, j, visit) && board[i-1][j] == ch)
                    search(root.Child[k], board, i-1, j, visit, str+ch, res);
                if(isSafe(i+1, j-1, visit) && board[i+1][j-1] == ch)
                    search(root.Child[k], board, i+1, j-1, visit, str+ch, res);
                if(isSafe(i-1, j+1, visit) && board[i-1][j+1] == ch)
                    search(root.Child[k], board, i-1, j+1, visit, str+ch, res);
                if(isSafe(i-1, j-1, visit) && board[i-1][j-1] == ch)
                    search(root.Child[k], board, i-1, j-1, visit, str+ch, res);

            }
        }
        
        visit[i][j] = false;
    }
    
    return res;
}
