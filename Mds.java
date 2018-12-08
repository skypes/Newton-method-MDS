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
public class Mds {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        if(args.length==0){
            // a simple case
            /*
            args = new String[]{"matrix=E:\\tmp_for_mds\\result\\example_for_simple\\simple_matrix.csv",
                                "dist=E:\\tmp_for_mds\\result\\example_for_simple",
                                "color=E:\\tmp_for_mds\\result\\example_for_simple\\color.map",
                                "-label",
                                "prefix=test"
                               };
            */
            // multiple alignment
            /*
            */
            args = new String[]{"msa=E:\\tmp_for_mds\\data\\rrnDB_16S_rRNA_1450_1600.ma",
                                "mode=multiAlignment",
                                "dist=E:\\tmp_for_mds\\result\\example_for_clustero",
                                "color=E:\\tmp_for_mds\\result\\example_for_clustero\\rrnDB_16S_rRNA.color.map",
                                "prefix=multiAlign2"
                               };
            // lyve-set
            /*
            args = new String[]{"pairwiseMatrix=E:\\tmp_for_mds\\data\\F792F00E766060470FABB816B5456C77\\result\\setTest\\msa\\out.pairwiseMatrix.tsv",
                                "mode=lyve-set",
                                "dist=E:\\tmp_for_mds\\result\\example_for_lyve_set",
//                                "color=E:\\tmp_for_mds\\result\\example_for_lyve_set\\lyveset_id_name.color.map",
                                "prefix=lyve_set"
                               };
            */
            // parsnp
            /*
            args = new String[]{"xmfa=E:\\tmp_for_mds\\data\\7340E12653A93B3912AE3755EF02E995\\result\\out\\parsnp.xmfa",
                                "mode=parsnp",
                                "dist=E:\\tmp_for_mds\\result\\example_for_parsnp",
                                "color=E:\\tmp_for_mds\\result\\example_for_parsnp\\out.list.color.map",
                                "prefix=parsnp"
                               };
            */
        }
        
        java.util.HashMap<String, String> para = new java.util.HashMap<>();
        for(String arg:args){
            String tk[]=arg.split("=");
            if(tk.length==2){
                para.put(tk[0], tk[1]);
            }
            else{
                para.put(tk[0], tk[0]);
            }
        }
        String prefix = para.get("prefix");
        if(prefix == null){
            prefix = "mds";
        }
        String dist = para.get("dist");
        if(dist == null){
            dist = ".";
        }
        String figure = dist+"/"+prefix+".png";
        //
        String mode = para.get("mode");
        if("multiAlignment".equals(mode)){
            String msa = para.get("msa");
            if(msa == null){
                System.out.println("parameter msa (Multiple Sequence Alignment) not found!!");
                return;
            }
            String idNameMap = dist+"/"+prefix+".map";
            String distMatrixMap = dist+"/"+prefix+".matrix.csv";
            new utils.CalculateDistanceFromMultiAlignmentSequence().calculateDistanceMatrixByClustalo(msa, idNameMap, distMatrixMap);
            String color = para.get("color");
            new utils.AdvancedMDS().createMDSImageByIDTmatrixCsvAndColor(distMatrixMap, color, figure);
//            if(color == null){
//                new utils.AdvancedMDS().createMDSImageByIDTmatrixCsv(distMatrixMap, figure);
//            }
//            else{
//                new utils.AdvancedMDS().createMDSImageByIDTmatrixCsvAndColor(distMatrixMap, color, figure);
//            }
            return;
        }
        else if("lyve-set".equals(mode)){
            String pairwiseMatrix = para.get("pairwiseMatrix");
            if(pairwiseMatrix == null){
                System.out.println("parameter pairwiseMatrix (SNP distance matrix file) not found!!");
                return;
            }
            String matrixCSVFile = dist+"/"+prefix+".matrix.csv";
            String listMapFile = dist+"/"+prefix+".list.map";
            new Mds().createMatrixCsvByLyveSet(pairwiseMatrix, matrixCSVFile, listMapFile);
            String color = para.get("color");
            new utils.AdvancedMDS().createMDSLableImageByDistanceMatrixCsvAndColor(matrixCSVFile, color, figure);
            return;
        }
        else if("parsnp".equals(mode)){
            String xmfa = para.get("xmfa");
            if(xmfa == null){
                System.out.println("parameter xmfa (multiple alignment file from parsnp) not found!!");
                return;
            }
            String listMapFile=dist+"/"+prefix+".list.map";
            String matrixCSVFile=dist+"/"+prefix+".csv";
            new Mds().process(xmfa, listMapFile, matrixCSVFile);
            String color = para.get("color");
            new utils.AdvancedMDS().createMDSLableImageByDistanceMatrixCsvAndColor(matrixCSVFile, color, figure);
            return;
        }
        
        String matrix = para.get("matrix");
        if(matrix == null){
            System.out.println("parameter matrix not found!!");
            return;
        }
        
        String color = para.get("color");
        if(para.containsKey("-label")){
            new utils.AdvancedMDS().createMDSLableImageByDistanceMatrixCsvAndColor(matrix, color, figure);
            return;
        }
        else{
            if(color == null){
                new utils.AdvancedMDS().createMDSImageByDistanceMatrixCsv(matrix, figure);
                return;
            }
            else{
                new utils.AdvancedMDS().createMDSImageByDistanceMatrixCsvAndColor(matrix, color, figure);
                return;
            }
        }
    }
    
    public void createMatrixCsvByLyveSet(String alleleFile, String matrixCSVFile, String listMapFile) throws Exception {
        java.io.BufferedReader in = new java.io.BufferedReader(new java.io.FileReader(alleleFile));
        String line=in.readLine();
        String header[] = line.split("\t");
        int size = header.length-1;
        double[][] tmpObdist = new double[size][size];
        int jj=0;
        while((line=in.readLine())!=null){
            String tk[] = line.split("\t");
            for(int i=1; i<tk.length; i++){
                int d=0;
                if(tk[i].matches("[0-9]+")){
                    d=Integer.valueOf(tk[i]);
                }
                tmpObdist[i-1][jj]=d;
                tmpObdist[jj][i-1]=d;
            }
            jj++;
        }
        in.close();

//        String matrixCSVFile = dist+"/"+prefix+".matrix.csv";
        java.io.FileWriter CSVout = new java.io.FileWriter(matrixCSVFile);
        for(int i=0; i<size; i++){
            CSVout.write(","+i);
        }
        CSVout.write("\n");
        for(int i=0; i<size; i++){
            CSVout.write(String.valueOf(i));
            for(int j=0; j<size; j++){
                CSVout.write(","+tmpObdist[i][j]);
            }
            CSVout.write("\n");
        }
        CSVout.close();
        
//        String listMapFile = dist+"/"+prefix+".list.map";
        java.io.FileWriter out = new java.io.FileWriter(listMapFile);
        for(int i=0; i<size; i++){
            out.write(i+"\t"+header[i+1]+"\n");
        }
        out.close();
    }
    
    public void process(String src, String dist, String dist2) throws Exception {
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
        
        for(Integer tmpId : idSeqMap.keySet()){
            StringBuilder tmpbf = idSeqMap.get(tmpId);
            if(tmpId == 1){
                continue;
            }
            for(Integer IPOS : orderedSet){
                tmpbf.deleteCharAt(IPOS);
            }
            String s1 = tmpbf.toString().toUpperCase();
        }
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
            out2.write(","+i);
        }
        out2.write("\n");
        for(int i=0; i<idList.size(); i++){
            Integer id1 = idList.get(i);
            out2.write(String.valueOf(i));
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
    
}
