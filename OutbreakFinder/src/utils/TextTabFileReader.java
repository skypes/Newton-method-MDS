/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author shining
 */
public class TextTabFileReader {

    java.io.BufferedReader br;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public void open(String fn) throws Exception {
        br = new java.io.BufferedReader(new java.io.FileReader(fn));
    }
    
    public void close() throws Exception {
        br.close();
    }
    
    public String[] next() throws Exception {
        String line = br.readLine();
        if(line == null){
            return null;
        }
        return line.split("\t");
    }
    
    public String[] next(String reg) throws Exception {
        String line = br.readLine();
        if(line == null){
            return null;
        }
        return line.split(reg);
    }
    
    public String nextLine() throws Exception {
        String line = br.readLine();
        if(line == null){
            return null;
        }
        return line;
    }
    
    static public java.util.HashMap<String, String> getMapFromTabTextFile(String fn) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fn));
        java.util.HashMap<String, String> aMap = new java.util.HashMap<>();
        String line;
        while((line=br.readLine())!=null){
            String[] tk = line.split("\t");
            aMap.put(tk[0], tk[1]);
        }
        br.close();
        return aMap;
    }
    
    static public java.util.HashMap<String, String> getMapFromTabTextFile(String fn, String reg) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fn));
        java.util.HashMap<String, String> aMap = new java.util.HashMap<>();
        String line;
        while((line=br.readLine())!=null){
            String[] tk = line.split(reg);
            aMap.put(tk[0], tk[1]);
        }
        br.close();
        return aMap;
    }
    
    static public java.util.HashMap<String, String> getMapFromTabTextFile(String fn, int x, int y) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fn));
        java.util.HashMap<String, String> aMap = new java.util.HashMap<>();
        String line;
        while((line=br.readLine())!=null){
            String[] tk = line.split("\t");
            aMap.put(tk[x], tk[y]);
        }
        br.close();
        return aMap;
    }
    
    static public java.util.HashSet<String> getSetFromFile(String fn) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fn));
        java.util.HashSet<String> aMap = new java.util.HashSet<>();
        String line;
        while((line=br.readLine())!=null){
            aMap.add(line.trim());
        }
        br.close();
        return aMap;
    }
    
    static public java.util.ArrayList<String> getLineArrFromFile(String fn) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fn));
        java.util.ArrayList<String> aMap = new java.util.ArrayList<>();
        String line;
        while((line=br.readLine())!=null){
            String trimed = line.trim();
            if(!trimed.isEmpty()){
                aMap.add(trimed);
            }
        }
        br.close();
        return aMap;
    }
    
    static public java.util.HashSet<String> getSetFromFile(String fn, String reg, int[] arr) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fn));
        java.util.HashSet<String> aMap = new java.util.HashSet<>();
        int maxIdx = arr[arr.length - 1];
        String line;
//        int c=0;
        while((line=br.readLine())!=null){
//            if(10<c++)break;
//            System.out.println(line);
            String tk[] = line.split(reg);
            StringBuilder sb = new StringBuilder();
            if(tk.length > maxIdx){
                sb.append(tk[0]);
                for(int i=1; i<arr.length; i++){
                    sb.append(" ").append(tk[i]);
                }
            }
            aMap.add(sb.toString());
        }
        br.close();
        return aMap;
    }
    
}
