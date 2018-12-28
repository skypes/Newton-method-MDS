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
public class ProcessForClusterOmega {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
//        new ProcessForClusterOmega().step_1_createDistanceMatrixFromClusterOmega();
//        new ProcessForClusterOmega().step_2_createColorMap();
//        new ProcessForClusterOmega().step_3_drawMDS();
//        new ProcessForClusterOmega().step_4_drawMDSbyColor();
//        new ProcessForClusterOmega().createColorMap("E:\\tmp_for_mds\\result\\cluster16S\\rrnDB_16S_rRNA.map", "E:\\tmp_for_mds\\result\\cluster16S\\multiAlign2.color.map");
        
        String coods = "E:\\tmp_for_mds\\result\\cluster16S\\multiAlign2.matrix.png.coods";
        String colorMap = "E:\\tmp_for_mds\\result\\cluster16S\\multiAlign2.color.map";
        String dist = "E:\\tmp_for_mds\\result\\cluster16S\\multiAlign2_new.png";
        String legend = "E:\\tmp_for_mds\\result\\cluster16S\\multiAlign2.color.map.legend";
        new utils.MDS().genMDSImageWithLegend(coods, colorMap, dist, legend);
    }
    
    public void step_1_createDistanceMatrixFromClusterOmega() throws Exception {
//        String fn = "E:\\tmp_for_mds\\data\\rrnDB_16S_rRNA.ma";
//        String idNameMap = "E:\\tmp_for_mds\\result\\example_for_clustero\\rrnDB_16S_rRNA.map";
//        String distMatrixMap = "E:\\tmp_for_mds\\result\\example_for_clustero\\rrnDB_16S_rRNA.matrix.csv";
        String fn = "E:\\tmp_for_mds\\result\\cluster16S\\rrnDB_mafft_auto_no_null.ma";
        String idNameMap = "E:\\tmp_for_mds\\result\\cluster16S\\rrnDB_16S_rRNA.map";
        String distMatrixMap = "E:\\tmp_for_mds\\result\\cluster16S\\rrnDB_16S_rRNA.matrix.csv";
        new utils.CalculateDistanceFromMultiAlignmentSequence().calculateDistanceMatrixByClustalo(fn, idNameMap, distMatrixMap);
    }
    
    public void step_2_createColorMap() throws Exception {
        String idNameMapFn = "E:\\tmp_for_mds\\result\\cluster16S\\rrnDB_16S_rRNA.map";
        String idColorMap = "E:\\tmp_for_mds\\result\\cluster16S\\multiAlign2.color.map";
        String prokList = "E:\\tmp_for_spid\\data\\genomes_proks_Bacteria.txt";
        String fungikList = "E:\\tmp_for_spid\\data\\genomes_euks_Fungi.txt";
        String archaeakList = "E:\\tmp_for_spid\\data\\genomes_proks_Archaea.txt";
        new utils.CalculateDistanceFromMultiAlignmentSequence().createIdColorMap(idNameMapFn, idColorMap, prokList, fungikList, archaeakList);
    }
    
    public void step_3_drawMDS() throws Exception {
        String distMatrixMap = "E:\\tmp_for_mds\\result\\example_for_clustero\\rrnDB_16S_rRNA.matrix.csv";
        String distMatrixMapPng = "E:\\tmp_for_mds\\result\\example_for_clustero\\rrnDB_16S_rRNA.matrix.png";
//        new utils.AdvancedMDS().createMDSImageByIDTmatrixCsv(distMatrixMap, distMatrixMapPng);
//        new utils.AdvancedMDS().createMDSImageByIDTmatrixCsvAndColor(distMatrixMap, null, distMatrixMapPng);
        new utils.AdvancedMDS().createMDSLableImageByDistanceMatrixCsvAndColor(distMatrixMap, null, distMatrixMapPng, null, true);
    }
    
    public void step_4_drawMDSbyColor() throws Exception {
        String distMatrixMap = "E:\\tmp_for_mds\\result\\cluster16S\\rrnDB_16S_rRNA.matrix.csv";
        String idColorMap = "E:\\tmp_for_mds\\result\\cluster16S\\multiAlign2.color.map";
        String distMatrixMapPng = "E:\\tmp_for_mds\\result\\cluster16S\\multiAlign2.matrix.png";
//        new utils.AdvancedMDS().createMDSImageByIDTmatrixCsvAndColor(distMatrixMap, idColorMap, distMatrixMapPng);
        new utils.AdvancedMDS().createMDSLableImageByDistanceMatrixCsvAndColor(distMatrixMap, idColorMap, distMatrixMapPng, null, true);
    }
    
//    public void createColorMap() throws Exception {
//        String idNameMap = "E:\\tmp_for_mds\\result\\example_for_clustero\\multiAlign2.map";
//        String idColorMap = "E:\\tmp_for_mds\\result\\example_for_clustero\\multiAlign2.color.map";
//        String distMatrixMapPng = "E:\\tmp_for_mds\\result\\example_for_clustero\\multiAlign2.matrix.png";
//        new utils.AdvancedMDS().createMDSImageByIDTmatrixCsvAndColor(distMatrixMap, idColorMap, distMatrixMapPng);
//    }
    
    
    public void createColorMap(String list, String dist) throws Exception {
        String prokList = "E:\\tmp_for_spid\\data\\genomes_proks_Bacteria.txt";
        String fungikList = "E:\\tmp_for_spid\\data\\genomes_euks_Fungi.txt";
        String archaeakList = "E:\\tmp_for_spid\\data\\genomes_proks_Archaea.txt";
//        java.util.HashMap<String, String> AMap = getGenusSet(archaeakList);
//        java.util.HashMap<String, String> FMap = getGenusSet(fungikList);
//        java.util.HashMap<String, String> BMap = getGenusSet(prokList);
//        java.util.HashMap<String, String> AMap = getGenusPhylumMapFromBac(archaeakList);
        java.util.HashMap<String, String> BMap = getGenusPhylumMapFromBac(prokList);
//        java.util.HashMap<String, String> FMap = getGenusPhylumMapFromFungi(fungikList);
        java.util.HashMap<String, String> GenusPhylumMap = new java.util.HashMap<>();
//        GenusPhylumMap.putAll(FMap);
        GenusPhylumMap.putAll(BMap);
//        GenusPhylumMap.putAll(AMap);
        java.util.TreeMap<Integer, String> sortColorMap = new java.util.TreeMap<>();
        for(int i=0;i<256;i++){
            String ibx = Integer.toHexString(i).toUpperCase();
            if(ibx.length()==1){
                ibx = "0"+ibx;
            }
            String ibx2 = Integer.toHexString(255-i).toUpperCase();
            if(ibx2.length()==1){
                ibx2 = "0"+ibx2;
            }
            sortColorMap.put(i, "#FF00"+ ibx );
            sortColorMap.put(255+i, "#"+ibx2+"00FF" );
            sortColorMap.put(255+255+i, "#00"+ ibx +"FF" );
            sortColorMap.put(255+255+255+i, "#00FF"+ibx2 );
            sortColorMap.put(255+255+255+255+i, "#"+ibx+"FF00" );
        }
        java.util.HashMap<String, String> colorMap = new java.util.HashMap<>();
        int maxCode = sortColorMap.size();
        java.util.HashMap<String, String> phMap = new java.util.HashMap<>();
        GenusPhylumMap.keySet().forEach((S) -> { phMap.put(GenusPhylumMap.get(S), ""); });
        int stepCode = maxCode/(phMap.size());
        int base = 0;
        
        java.io.FileWriter out2 = new java.io.FileWriter(dist+".legend");
        for(String S:phMap.keySet()){
            out2.write(S+"\t"+sortColorMap.get(base)+"\n");
            phMap.put(S, sortColorMap.get(base));
            base+=stepCode;
        }
        out2.write("Archaea\t#999999\n");
        out2.close();
        for(String S:GenusPhylumMap.keySet()){
            colorMap.put(S, phMap.get(GenusPhylumMap.get(S)));
        }
        java.io.FileWriter out = new java.io.FileWriter(dist);
        utils.TextTabFileReader txtReader = new utils.TextTabFileReader();
        txtReader.open(list);
        String line;
        while((line=txtReader.nextLine())!=null){
            String tk[] = line.replaceAll("[\\[\\]]", "").split("\\s+");
            if(colorMap.containsKey(tk[1])){
                out.write(tk[0]+"\t"+colorMap.get(tk[1])+"\n");
            }
            else{
                out.write(tk[0]+"\t#999999\n");
            }
        }
        txtReader.close();
        out.close();
//        return colorMap;
    }
    
    public java.util.HashMap<String, String> getGenusPhylumMapFromBac(String fn) throws Exception {
        java.util.HashMap<String, String> aMap = new java.util.HashMap<>();
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fn));
        String line=br.readLine();
        while((line=br.readLine())!=null){
            String tk[] = line.split("\t");
            String gp = tk[5];
            if(gp.contains("unclassified")){
                continue;
            }
            if(gp.contains("group")){ //Bacteroidetes/Chlorobi group
                gp = tk[6];
                if(gp.contains("/")){
                    gp = tk[6].split("/")[0];
                }
                if(gp.contains("Candidatus") || gp.contains("group")){
                    continue;
                }
            }
            String gbln[] = tk[0].replaceAll("[\\[\\]]", "").split("\\s+");
            String gbfn = gbln[0];
            aMap.put(gbfn, gp);
        }
        br.close();
        return aMap;
    }
    
    public java.util.HashMap<String, String> getGenusPhylumMapFromFungi(String fn) throws Exception {
        java.util.HashMap<String, String> aMap = new java.util.HashMap<>();
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fn));
        String line=br.readLine();
        while((line=br.readLine())!=null){
            String tk[] = line.split("\t");
            String gp = tk[5];
            String gbln[] = tk[0].replaceAll("[\\[\\]]", "").split("\\s+");
            String gbfn = gbln[0];
            aMap.put(gbfn, gp);
        }
        br.close();
        return aMap;
    }
    
}
