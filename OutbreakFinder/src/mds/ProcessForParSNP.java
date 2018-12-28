/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mds;

/**
 *
 * @author shining
 */
public class ProcessForParSNP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
//        new ProcessForParSNP().step_1_createDistanceMatrixFromParSNP();
        new ProcessForParSNP().step_2_drawMDSbyParSNP();
//        new ProcessForParSNP().test();
    }
    
    public void test() throws Exception {
        String matrixCSVFile="E:\\tmp_for_mds\\result\\example_for_parsnp\\distmatrix.xls";
        utils.TextTabFileReader ttfr= new utils.TextTabFileReader();
        ttfr.open(matrixCSVFile);
        String line = ttfr.nextLine();
        String tk[] = line.split("\t");
        int size = tk.length-1;
        double distMatrix[][] = new double[size][size];
        int idx=0;
        while((line = ttfr.nextLine())!=null){
            tk = line.split("\t");
            for(int i=1; i<tk.length; i++){
                if("-".equals(tk[i])){
                    distMatrix[idx][i-1] =0;
                }
                else{
                    distMatrix[idx][i-1] = Double.valueOf(tk[i]);
                }
            }
            idx++;
        }
        ttfr.close();
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                System.out.print(distMatrix[i][j]+"\t");
            }
            System.out.print("\n");
        }
        String pngFile = "E:\\tmp_for_mds\\result\\example_for_parsnp\\parsnp_2.png";
        double[][] coords = new utils.AdvancedMDS().calculateMDSOptimise(distMatrix);
        new utils.AdvancedMDS().drawImageByLabel(coords, pngFile, null);
//        new utils.AdvancedMDS().createMDSImageByDistanceMatrixCsvAndColor(matrixCSVFile, null, pngFile);
        
    }
    
    public void step_1_createDistanceMatrixFromParSNP() throws Exception {
        String src="E:\\tmp_for_mds\\data\\7340E12653A93B3912AE3755EF02E995\\result\\out\\parsnp.xmfa";
        String listMapFile="E:\\tmp_for_mds\\result\\example_for_parsnp\\out.list.map";
        String matrixCSVFile="E:\\tmp_for_mds\\result\\example_for_parsnp\\distmatrix.csv";
//        createDistanceMatrixFromParSNP(src, listMapFile, matrixCSVFile);
        process(src, listMapFile, matrixCSVFile);
    }
    
    public void step_2_drawMDSbyParSNP() throws Exception {
//        String alleleFile = "E:\\tmp_for_mds\\data\\F792F00E766060470FABB816B5456C77\\result\\setTest\\msa\\out.pairwiseMatrix.tsv";
//        String dist="E:\\tmp_for_mds\\result\\example_for_lyve_set";
//        String listMapFile="E:\\tmp_for_mds\\result\\example_for_parsnp\\out.list.map";
        String matrixCSVFile="E:\\tmp_for_mds\\result\\example_for_parsnp\\distmatrix.csv";
//        String matrixCSVFile = dist+"/lyve_set.matrix.csv";
//        String listMapFile = dist+"/lyve_set.list.map";
        String pngFile = "E:\\tmp_for_mds\\result\\example_for_parsnp\\parsnp.png";
        String colorMap = "E:\\tmp_for_mds\\result\\example_for_parsnp\\out.list.color.map";
//        String prefix="lyve_set";
//        createMatrixCsvByLyveSet(alleleFile, matrixCSVFile, listMapFile);
//        String idColorMap = "E:\\tmp_for_mds\\result\\example_for_lyve_set\\lyveset_id_name.color.map";
//        new utils.AdvancedMDS().createMDSImageByDistanceMatrixCsvAndColor(matrixCSVFile, idColorMap, pngFile);
        new utils.AdvancedMDS().createMDSLableImageByDistanceMatrixCsvAndColor(matrixCSVFile, colorMap, pngFile, null, false);
    }
    
    public void createDistanceMatrixFromParSNP(String src, String dist1, String dist2) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line;
        Integer ID=null;
        java.util.HashMap<Integer, StringBuilder> idSeqMap = new java.util.HashMap<>();
        java.util.HashMap<Integer, String> idNameMap = new java.util.HashMap<>();
        StringBuilder strbf = null;
        while((line=br.readLine())!=null){
            if(line.startsWith("##SequenceIndex")){
                ID = Integer.valueOf(line.substring(16).trim());
            }
            else if(line.startsWith("##SequenceFile")){
                String fn = line.substring(15);
                idNameMap.put(ID, fn);
            }
            else if(line.startsWith(">")){
                int idx = line.indexOf(":");
                ID = Integer.valueOf(line.substring(1,idx));
                strbf=idSeqMap.get(ID);
                if(strbf==null){
                    strbf = new StringBuilder();
                    idSeqMap.put(ID, strbf);
                }
                break;
            }
        }
        while((line=br.readLine())!=null){
            if(line.startsWith(">")){
                int idx = line.indexOf(":");
                ID = Integer.valueOf(line.substring(1,idx));
                strbf=idSeqMap.get(ID);
                if(strbf==null){
                    strbf = new StringBuilder();
                    idSeqMap.put(ID, strbf);
                }
            }
            else if(line.startsWith("=")){
            }
            else{
                strbf.append(line);
            }
        }
        java.util.HashSet<Integer> nPosSet = new java.util.HashSet<>();
        for(Integer tmpId : idSeqMap.keySet()){
            String s1 = idSeqMap.get(tmpId).toString();
            for(int i=0; i<s1.length(); i++){
                if(s1.charAt(i)=='N' || s1.charAt(i)=='n'){
                    nPosSet.add(i);
                }
            }
        }
        
        java.util.TreeSet<Integer> orderedSet = new java.util.TreeSet<>(java.util.Collections.reverseOrder());
        orderedSet.addAll(nPosSet);
        
//        java.io.FileWriter out = new java.io.FileWriter(dist);
//        for(Integer tmpId : idSeqMap.keySet()){
//            StringBuilder tmpbf = idSeqMap.get(tmpId);
//            if(tmpId == 1){
//                continue;
//            }
//            for(Integer IPOS : orderedSet){
//                tmpbf.deleteCharAt(IPOS);
//            }
//            String s1 = tmpbf.toString().toUpperCase();
//            out.write(">"+idNameMap.get(tmpId)+"\n"+s1+"\n");
//        }
//        out.close();
        java.util.HashMap<Integer, java.util.HashMap<Integer, Integer>> idIdDistMap = new java.util.HashMap<>();
        for(Integer tmpId : idSeqMap.keySet()){
            if(tmpId == 1){
                continue;
            }
            StringBuilder tmpbf = idSeqMap.get(tmpId);
            for(Integer tmpId2 : idSeqMap.keySet()){
                if(tmpId2 == 1 || tmpId >= tmpId2){
                    if((int)tmpId == (int)tmpId2){
                        java.util.HashMap<Integer, Integer> tmpMap = idIdDistMap.get(tmpId);
                        if(tmpMap==null){
                            tmpMap = new java.util.HashMap<>();
                            idIdDistMap.put(tmpId, tmpMap);
                        }
                        tmpMap.put(tmpId2, 0);
                    }
                    continue;
                }
                StringBuilder tmpbf2 = idSeqMap.get(tmpId2);
                int dst=0;
                for(int i=0; i<tmpbf.length(); i++){
                    if(tmpbf.charAt(i) != tmpbf2.charAt(i)){
                        dst++;
                    }
                }
                java.util.HashMap<Integer, Integer> tmpMap = idIdDistMap.get(tmpId);
                if(tmpMap==null){
                    tmpMap = new java.util.HashMap<>();
                    idIdDistMap.put(tmpId, tmpMap);
                }
                tmpMap.put(tmpId2, dst);
            }
        }
        
        java.io.FileWriter out1 = new java.io.FileWriter(dist1);
        java.io.FileWriter out2 = new java.io.FileWriter(dist2);
        java.util.ArrayList<Integer> idList = new java.util.ArrayList<>();
        idList.addAll(idIdDistMap.keySet());
//        out2.write(".");
        for(int i=0; i<idList.size(); i++){
//            out2.write(","+idNameMap.get(idList.get(i)));
            out2.write(","+i);
            out1.write(i+"\t"+idNameMap.get(idList.get(i))+"\n");
        }
        out1.close();
        out2.write("\n");
        for(int i=0; i<idList.size(); i++){
            Integer id1 = idList.get(i);
//            out2.write(idNameMap.get(id1));
            out2.write(String.valueOf(String.valueOf(i)));
            for(int j=0; j<idList.size(); j++){
                Integer id2 = idList.get(j);
                if(id1.compareTo(id2) == 0){
                    out2.write(",0");
                }
                else if(id1.compareTo(id2) > 0){
                    java.util.HashMap<Integer, Integer> tmpMap = idIdDistMap.get(id2);
                    out2.write(","+tmpMap.get(id1));
                }
                else{
                    java.util.HashMap<Integer, Integer> tmpMap = idIdDistMap.get(id1);
                    out2.write(","+tmpMap.get(id2));
                }
            }
            out2.write("\n");
        }
        out2.close();
        
    }
    
    public void process(String src, String dist, String dist2) throws Exception {
//        String src="C:\\tmp3\\test3\\parsnp.xmfa";
//        String dist="C:\\tmp2\\out.aln.fasta";
//        String dist2="C:\\tmp2\\distmatrix.xls";
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line;
        Integer ID=null;
        java.util.HashMap<Integer, StringBuilder> idSeqMap = new java.util.HashMap<>();
        java.util.HashMap<Integer, String> idNameMap = new java.util.HashMap<>();
        StringBuilder strbf = null;
        while((line=br.readLine())!=null){
            if(line.startsWith("##SequenceIndex")){
                ID = Integer.valueOf(line.substring(16).trim());
            }
            else if(line.startsWith("##SequenceFile")){
                String fn = line.substring(15);
                idNameMap.put(ID, fn);
            }
            else if(line.startsWith(">")){
                int idx = line.indexOf(":");
                ID = Integer.valueOf(line.substring(1,idx));
//                Integer ID = Integer.valueOf(id);
                strbf=idSeqMap.get(ID);
                if(strbf==null){
                    strbf = new StringBuilder();
                    idSeqMap.put(ID, strbf);
                }
                break;
            }
        }
        while((line=br.readLine())!=null){
            if(line.startsWith(">")){
                int idx = line.indexOf(":");
                ID = Integer.valueOf(line.substring(1,idx));
//                Integer ID = Integer.valueOf(id);
                strbf=idSeqMap.get(ID);
                if(strbf==null){
                    strbf = new StringBuilder();
                    idSeqMap.put(ID, strbf);
                }
            }
            else if(line.startsWith("=")){
            }
            else{
                strbf.append(line);
            }
        }
        java.util.HashSet<Integer> nPosSet = new java.util.HashSet<>();
        for(Integer tmpId : idSeqMap.keySet()){
            String s1 = idSeqMap.get(tmpId).toString();
            for(int i=0; i<s1.length(); i++){
                if(s1.charAt(i)=='N' || s1.charAt(i)=='n'){
                    nPosSet.add(i);
                }
            }
        }
        
        java.util.TreeSet<Integer> orderedSet = new java.util.TreeSet<>(java.util.Collections.reverseOrder());
        orderedSet.addAll(nPosSet);
        
//        System.out.println(orderedSet);
//        System.out.println(orderedSet.size());
//        java.io.FileWriter out = new java.io.FileWriter(dist);
        for(Integer tmpId : idSeqMap.keySet()){
            StringBuilder tmpbf = idSeqMap.get(tmpId);
            if(tmpId == 1){
                continue;
            }
//            System.out.println(idNameMap.get(tmpId)+"\t"+tmpbf.length());
            for(Integer IPOS : orderedSet){
//                System.out.println("delete "+IPOS);
                tmpbf.deleteCharAt(IPOS);
            }
            String s1 = tmpbf.toString().toUpperCase();
//            out.write(">"+idNameMap.get(tmpId)+"\n"+s1+"\n");
        }
//        out.close();
//        System.out.println("idSeqMap.size()="+idSeqMap.size()+"\n");
        java.util.HashMap<Integer, java.util.HashMap<Integer, Integer>> idIdDistMap = new java.util.HashMap<>();
        for(Integer tmpId : idSeqMap.keySet()){
//            System.out.println("->"+tmpId+"<-");
            if(tmpId == 1){
                continue;
            }
            StringBuilder tmpbf = idSeqMap.get(tmpId);
            for(Integer tmpId2 : idSeqMap.keySet()){
//                System.out.println(tmpId+"@->"+tmpId2+"<-");
                if(tmpId2 == 1 || tmpId >= tmpId2){
                    if((int)tmpId == (int)tmpId2){
                        java.util.HashMap<Integer, Integer> tmpMap = idIdDistMap.get(tmpId);
                        if(tmpMap==null){
                            tmpMap = new java.util.HashMap<>();
                            idIdDistMap.put(tmpId, tmpMap);
                        }
                        tmpMap.put(tmpId2, 0);
                    }
                    continue;
                }
//                System.out.println(tmpId+"#->"+tmpId2+"<-");
                StringBuilder tmpbf2 = idSeqMap.get(tmpId2);
                int dst=0;
                for(int i=0; i<tmpbf.length(); i++){
                    if(tmpbf.charAt(i) != tmpbf2.charAt(i)){
                        dst++;
                    }
                }
                java.util.HashMap<Integer, Integer> tmpMap = idIdDistMap.get(tmpId);
                if(tmpMap==null){
                    tmpMap = new java.util.HashMap<>();
                    idIdDistMap.put(tmpId, tmpMap);
                }
                tmpMap.put(tmpId2, dst);
            }
        }
        
//        System.out.println("idIdDistMap.size()="+idIdDistMap.size()+"\n");
//        for(Integer tmpId : idSeqMap.keySet()){
//            if(!idIdDistMap.containsKey(tmpId)){
//                System.out.println("XXX="+tmpId+"\n");
//            }
//        }
        java.util.ArrayList<Integer> idList = new java.util.ArrayList<>();
        idList.addAll(idIdDistMap.keySet());
        java.io.FileWriter out = new java.io.FileWriter(dist);
        for(int i=0; i<idList.size(); i++){
            out.write(i+"\t"+idNameMap.get(idList.get(i))+"\n");
        }
        out.close();
        java.io.FileWriter out2 = new java.io.FileWriter(dist2);
        out2.write(".");
        for(int i=0; i<idList.size(); i++){
//            out2.write("\t"+idNameMap.get(idList.get(i)));
            out2.write(","+i);
        }
        out2.write("\n");
        for(int i=0; i<idList.size(); i++){
            Integer id1 = idList.get(i);
//            out2.write(idNameMap.get(id1));
            out2.write(String.valueOf(i));
            for(int j=0; j<idList.size(); j++){
                Integer id2 = idList.get(j);
                if(id1.compareTo(id2) == 0){
//                    out2.write("\t-");
                    out2.write(",0");
                }
                else if(id1.compareTo(id2) > 0){
                    java.util.HashMap<Integer, Integer> tmpMap = idIdDistMap.get(id2);
//                    out2.write("\t"+tmpMap.get(id1));
                    out2.write(","+tmpMap.get(id1));
                }
                else{
                    java.util.HashMap<Integer, Integer> tmpMap = idIdDistMap.get(id1);
//                    out2.write("\t"+tmpMap.get(id2));
                    out2.write(","+tmpMap.get(id2));
                }
            }
            out2.write("\n");
        }
        out2.close();
        
//        java.io.FileWriter out2 = new java.io.FileWriter(dist2);
//        for(int i=0; i<mtx.length; i++){
//            for(int j=0; j<mtx[i].length; j++){
//                out2.write(mtx[i][j]+"\t");
//            }
//            out2.write("\n");
//        }
//        out2.close();
    }
    
}
