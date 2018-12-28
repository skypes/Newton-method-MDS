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
public class CalculateDistanceFromMultiAlignmentSequence {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
//        new CalculateDistanceFromMultiAlignmentSequence().example_1();
        new CalculateDistanceFromMultiAlignmentSequence().example_2();
    }
    
    public void example_1() throws Exception {
        String fn = "E:\\tmp_for_mds\\data\\rrnDB_16S_rRNA.ma";
        String idNameMap = "E:\\tmp_for_mds\\result\\example_for_clustero\\rrnDB_16S_rRNA.map";
        String distMatrixMap = "E:\\tmp_for_mds\\result\\example_for_clustero\\rrnDB_16S_rRNA.matrix.csv";
        calculateDistanceMatrixByClustalo(fn, idNameMap, distMatrixMap);
    }
    
    public void example_2() throws Exception {
        String idColorMap = "E:\\tmp_for_mds\\result\\cluster16S\\multiAlign2.color.map";
        String idNameMapFn = "E:\\tmp_for_mds\\result\\cluster16S\\rrnDB_16S_rRNA.map";
        String prokList = "E:\\tmp_for_spid\\data\\genomes_proks_Bacteria.txt";
        String fungikList = "E:\\tmp_for_spid\\data\\genomes_euks_Fungi.txt";
        String archaeakList = "E:\\tmp_for_spid\\data\\genomes_proks_Archaea.txt";
        createIdColorMap(idNameMapFn, idColorMap, prokList, fungikList, archaeakList);
    }
    
    public void calculateDistanceMatrixByClustalo(String fn, String idNameMap, String distMatrixMap) throws Exception {
        java.io.FileWriter idNameMapOut = new java.io.FileWriter(idNameMap);
        java.io.FileWriter distMatrixMapOut = new java.io.FileWriter(distMatrixMap);
//        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fn));
        java.util.HashMap<Integer, String> idSeqMap = new java.util.HashMap<>();
        utils.Readers rd = new utils.Readers();
        utils.Readers.FastaReader fr = rd.createFastaReader(fn);
        utils.Readers.Tuple<String, String> tp;
        int idx=0;
        while((tp=fr.getTuple())!=null){
            idNameMapOut.write(idx+"\t"+tp.first+"\n");
            idSeqMap.put(idx, tp.second);
            idx++;
        }
        fr.close();
        int[][] matrix = new int[idx][idx];
        for(int i=0; i<idx; i++){
            String si = idSeqMap.get(i);
            for(int j=i+1; j<idx; j++){
                String sj = idSeqMap.get(j);
                int idt = 0;
                for(int l=0; l<si.length(); l++){
                    if(si.charAt(l) == '-' || 
                       sj.charAt(l) == '-'|| 
                       si.charAt(l) == 'N'|| 
                       sj.charAt(l) == 'N'|| 
                       si.charAt(l) == 'n'|| 
                       sj.charAt(l) == 'n'){
                        continue;
                    }
                    if(si.charAt(l) == sj.charAt(l)){
                        idt++;
                    }
                }
//                if(idt==0){
//                    System.out.println(si);
//                    System.out.println(sj);
//                }
                matrix[j][i] = matrix[i][j] = idt;
//                distMatrixMapOut.write(i+"\t"+j+"\t"+idt+"\n");
            }
        }
        
        for(int i=0; i<idx; i++){
            distMatrixMapOut.write(","+i);
        }
        distMatrixMapOut.write("\n");
        for(int i=0; i<idx; i++){
            distMatrixMapOut.write(String.valueOf(i));
            for(int j=0; j<idx; j++){
                distMatrixMapOut.write(","+matrix[i][j]);
            }
            distMatrixMapOut.write("\n");
        }
        
        System.out.println(idSeqMap.size());
        idNameMapOut.close();
        distMatrixMapOut.close();
    }
    
    
    public void createIdColorMap(String idNameMapFn, String idColorMap, String prokList, String fungikList, String archaeakList) throws Exception {
        java.util.HashSet<String> ASet = utils.TextTabFileReader.getSetFromFile(archaeakList, "\\s+", new int[]{0}); //getGenusSet(archaeakList);
        java.util.HashSet<String> FSet = utils.TextTabFileReader.getSetFromFile(fungikList, "\\s+", new int[]{0}); //getGenusSet(fungikList);
        java.util.HashSet<String> BSet = utils.TextTabFileReader.getSetFromFile(prokList, "\\s+", new int[]{0}); //getGenusSet(fungikList);
        //
//        System.out.println(ASet);
//        System.out.println(FSet);
//        System.out.println(BSet);
        java.util.HashMap<String, String> idNameMap = utils.TextTabFileReader.getMapFromTabTextFile(idNameMapFn); //getGenusSet(fungikList);
        java.io.FileWriter idColorMapOut = new java.io.FileWriter(idColorMap);
        for(int i=0; i<idNameMap.size(); i++){
            String V = idNameMap.get(String.valueOf(i));
            String[] tk = V.split("\\s+");
            if(BSet.contains(tk[0])){
                idColorMapOut.write(i+"\t#FF0000\n");
            }
            else if(ASet.contains(tk[0])){
                idColorMapOut.write(i+"\t#00FF00\n");
            }
            else if(FSet.contains(tk[0])){
                idColorMapOut.write(i+"\t#0000FF\n");
            }
            else{
                idColorMapOut.write(i+"\t#FFFFFF\n");
            }
        }
        idColorMapOut.close();
    }
    
}
