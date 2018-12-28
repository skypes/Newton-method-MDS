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
public class ProcessForLyveSet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
//        new ProcessForLyveSet().step_1_createDistanceMatrixFromLyveSetMSA();
//        new ProcessForLyveSet().step_2_drawMDS();
//        new ProcessForLyveSet().step_3_drawMDSbyLyveSetPairwiseMatrix();
    }
    
    public void step_1_createDistanceMatrixFromLyveSetMSA() throws Exception {
        String fn = "E:\\tmp_for_mds\\data\\F792F00E766060470FABB816B5456C77\\result\\setTest\\msa\\out.aln.fasta";
        String idNameMap = "E:\\tmp_for_mds\\result\\example_for_lyve_set\\lyveset_id_name.map";
        String distMatrixMap = "E:\\tmp_for_mds\\result\\example_for_lyve_set\\lyveset_id_name.matrix.csv";
        new utils.CalculateDistanceFromMultiAlignmentSequence().calculateDistanceMatrixByClustalo(fn, idNameMap, distMatrixMap);
    }
    
    public void step_2_drawMDS() throws Exception {
        String distMatrixMap = "E:\\tmp_for_mds\\result\\example_for_lyve_set\\lyveset_id_name.matrix.csv";
        String distMatrixMapPng = "E:\\tmp_for_mds\\result\\example_for_lyve_set\\lyveset_id_name.matrix.png";
        String idColorMap = "E:\\tmp_for_mds\\result\\example_for_lyve_set\\lyveset_id_name.color.map";
//        new utils.AdvancedMDS().createMDSImageByIDTmatrixCsv(distMatrixMap, distMatrixMapPng);
//        new utils.AdvancedMDS().createMDSImageByIDTmatrixCsvAndColor(distMatrixMap, idColorMap, distMatrixMapPng);
        new utils.AdvancedMDS().createMDSLableImageByDistanceMatrixCsvAndColor(distMatrixMap, idColorMap, distMatrixMapPng, null, true);
    }
    
    public void step_3_drawMDSbyLyveSetPairwiseMatrix() throws Exception {
        String alleleFile = "E:\\tmp_for_mds\\data\\F792F00E766060470FABB816B5456C77\\result\\setTest\\msa\\out.pairwiseMatrix.tsv";
        String dist="E:\\tmp_for_mds\\result\\example_for_lyve_set";
        String matrixCSVFile = dist+"/lyve_set.matrix.csv";
        String listMapFile = dist+"/lyve_set.list.map";
        String pngFile = dist+"/lyve_set.png";
        String prefix="lyve_set";
        createMatrixCsvByLyveSet(alleleFile, matrixCSVFile, listMapFile);
        String idColorMap = "E:\\tmp_for_mds\\result\\example_for_lyve_set\\lyveset_id_name.color.map";
//        new utils.AdvancedMDS().createMDSImageByDistanceMatrixCsvAndColor(matrixCSVFile, idColorMap, pngFile);
        new utils.AdvancedMDS().createMDSLableImageByDistanceMatrixCsvAndColor(matrixCSVFile, idColorMap, pngFile, null, false);
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
    
}
