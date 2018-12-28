/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

//import java.awt.Color;

/**
 *
 * @author shining
 */
public class AdvancedMDS {

    int iSize = 10;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
//        new AdvancedMDS().genMDSImageDistMatrixFile("E:\\tmp\\tmp_for_delete\\test.matrix", "E:\\tmp\\tmp_for_delete\\test.png", "E:\\tmp\\tmp_for_delete\\name_color.map");
//        new AdvancedMDS().genMDSImageDistMatrixFile("E:\\tmp_for_spid\\workdir\\result_from_server\\test1_list\\test.matrix", "E:\\tmp_for_spid\\workdir\\result_from_server\\test1_list\\test.png", "E:\\tmp_for_spid\\workdir\\result_from_server\\test1_list\\name_color.map");
//        new AdvancedMDS().genMDSImageDistMatrixFile("E:\\tmp\\YY\\dist.txt", "E:\\tmp\\YY\\test.png");
        new AdvancedMDS().plotMDSbyCoordsFile("E:\\tmp_for_mds\\result\\example_for_simple\\AP_example.coods", "E:\\tmp_for_mds\\result\\example_for_simple\\AP_example.png", false);
    }
    
    public void plotMDSbyCoordsFile(String coordsFile, String dist, boolean isLabled) throws Exception {
        java.util.ArrayList<String> lines = utils.TextTabFileReader.getLineArrFromFile(coordsFile);
        double[][] coords = new double[lines.size()][2];
        java.util.HashMap<String, String> idNameMap = new java.util.HashMap<>(); 
        for(int i=0; i<lines.size(); i++){
            String line = lines.get(i);
            String tk[] = line.split("\t");
            coords[i][0] = Double.valueOf(tk[1]);
            coords[i][1] = Double.valueOf(tk[2]);
            idNameMap.put(tk[0], tk[3]);
        }
        if(isLabled){
            drawImageByLabel(coords, dist, idNameMap);
        }
        else{
            drawImageByColor(coords, dist, idNameMap);
        }
        
    }
    
//    public void createMDSImageByIDTmatrixCsv(String src, String dist) throws Exception {
//        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
//        String line=br.readLine();
//        String nameList[] = line.split(",");
//        int size = Integer.valueOf(nameList[nameList.length-1])+1;
//        double[][] obdist = new double[size][size];
//        while((line=br.readLine())!=null){
//            nameList = line.split(",");
//            if(nameList.length < size){
//                continue;
//            }
//            int id1 = Integer.valueOf(nameList[0]);
//            for(int j=1; j<nameList.length; j++){
//                int id2 = j-1;
//                int val = Integer.valueOf(nameList[j]);
//                if(id1 == id2){
//                    obdist[id1][id2] = 0;
//                }
//                else{
//                    obdist[id1][id2] = 1.0/(double)val;
//                }
//            }
//        }
//        
//        utils.MDS mds = new utils.MDS();
//        double[][] coords = mds.calculateMDS_2D(obdist);
//        drawImage(coords, dist);
//    }
    
    public void createMDSImageByDistanceMatrixCsv(String src, String dist) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line=br.readLine();
        String nameList[] = line.split(",");
        int size = Integer.valueOf(nameList[nameList.length-1])+1;
        double[][] obdist = new double[size][size];
        while((line=br.readLine())!=null){
            nameList = line.split(",");
            if(nameList.length < size){
                continue;
            }
            int id1 = Integer.valueOf(nameList[0]);
            for(int j=1; j<nameList.length; j++){
                int id2 = j-1;
                double val = Double.valueOf(nameList[j]);
                obdist[id1][id2] = val;
                if(id1 == id2){
                    obdist[id1][id2] = 0;
                }
            }
        }
        
/////////////////////////////////////////////////////////

        utils.MDS mds = new utils.MDS();
        double[][] coords = mds.calculateMDS_2D(obdist);
        drawImage(coords, dist);
    }
    
    public void createMDSImageByIDTmatrixCsvAndColor(String src, String colorMapFn, String dist, String mod) throws Exception {
        double[][] obdist;
        if("log".equals(mod)){
            obdist = loadIdtMatrixByLog( src);
        }
        else if("pow".equals(mod)){
            obdist = loadIdtMatrixByPow( src);
        }
        else{
            obdist = loadIdtMatrix( src);
        }
        if(colorMapFn == null){
            
            utils.MDS mds = new utils.MDS();
            double[][] coords = mds.calculateMDS_2D(obdist);
            ////////////////////////////////////
            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
//            java.io.FileWriter coodsOut = new java.io.FileWriter(dist+"tmp.coods");
//            for(int i=0; i<coords.length; i++){
//                coodsOut.write(i+"\t"+coords[i][0]+"\t"+coords[i][1]+"\t#000000\n");
//            }
//            coodsOut.close();
            
            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
            ////////////////////////////////////
//            String coodsDist = dist+".coods";
//            utils.AffinityPropagation afp = new utils.AffinityPropagation();
//            afp.runAffinityPropagation(coords, coodsDist);
//            plotMDSbyCoordsFile(coodsDist, dist, false);
            
//            drawImageByColor(coords, dist, colorMapFn);
            drawImage(coords, dist);
            java.io.FileWriter coodsOut = new java.io.FileWriter(dist+".coods");
            for(int i=0; i<coords.length; i++){
                coodsOut.write(i+"\t"+coords[i][0]+"\t"+coords[i][1]+"\t#000000\n");
            }
            coodsOut.close();
        }
        else{
            java.util.HashMap<String, String> idNameMap = utils.TextTabFileReader.getMapFromTabTextFile(colorMapFn); 
            utils.MDS mds = new utils.MDS();
            double[][] coords = mds.calculateMDS_2D(obdist);
            drawImageByColor(coords, dist, idNameMap);
            java.io.FileWriter coodsOut = new java.io.FileWriter(dist+".coods");
            for(int i=0; i<coords.length; i++){
                coodsOut.write(i+"\t"+coords[i][0]+"\t"+coords[i][1]+"\t"+idNameMap.get(String.valueOf(i))+"\t"+"\n");
            }
            coodsOut.close();
        }
        
    }
    
    public void createMDSImageByDistanceMatrixCsvAndColor(String src, String colorMapFn, String dist, String mod, boolean isIdentity) throws Exception {
        double[][] obdist;
        if(isIdentity){
            if("log".equals(mod)){
                obdist = loadIdtMatrixByLog( src);
            }
            else if("pow".equals(mod)){
                obdist = loadIdtMatrixByPow( src);
            }
            else{
                obdist = loadIdtMatrix( src);
            }
//            obdist = loadIdtMatrix( src);
        }
        else{
            if("log".equals(mod)){
                obdist = loadMatrixByLog( src);
            }
            else if("pow".equals(mod)){
                obdist = loadMatrixByPow( src);
            }
            else{
                obdist = loadMatrix( src);
            }
        }
        utils.MDS mds = new utils.MDS();
        double[][] coords = mds.calculateMDS_2D(obdist);
        
        if(colorMapFn == null){
//            String coodsDist = dist+".coods";
//            utils.AffinityPropagation afp = new utils.AffinityPropagation();
//            afp.runAffinityPropagation(coords, coodsDist);
//            plotMDSbyCoordsFile(coodsDist, dist, false);
            
            drawImage(coords, dist);
            java.io.FileWriter coodsOut = new java.io.FileWriter(dist+".coods");
            for(int i=0; i<coords.length; i++){
                coodsOut.write(i+"\t"+coords[i][0]+"\t"+coords[i][1]+"\t#000000\n");
            }
            coodsOut.close();
        }
        else{
            java.util.HashMap<String, String> idNameMap = utils.TextTabFileReader.getMapFromTabTextFile(colorMapFn); //getGenusSet(fungikList);
            drawImageByColor(coords, dist, idNameMap);
            java.io.FileWriter coodsOut = new java.io.FileWriter(dist+".coods");
            for(int i=0; i<coords.length; i++){
                coodsOut.write(i+"\t"+coords[i][0]+"\t"+coords[i][1]+"\t"+idNameMap.get(String.valueOf(i))+"\n");
            }
            coodsOut.close();
        }
        
//        System.out.println(idNameMap);
/////////////////////////////////////////////////////////

        
    }
    
    public void createMDSLableImageByDistanceMatrixCsvAndColor(String src, String colorMapFn, String dist, String mod, boolean isIdentity) throws Exception {
        double[][] obdist;
        if(isIdentity){
            if("log".equals(mod)){
                obdist = loadIdtMatrixByLog( src);
            }
            else if("pow".equals(mod)){
                obdist = loadIdtMatrixByPow( src);
            }
            else{
                obdist = loadIdtMatrix( src);
            }
//            obdist = loadIdtMatrix( src);
        }
        else{
            if("log".equals(mod)){
                obdist = loadMatrixByLog( src);
            }
            else if("pow".equals(mod)){
                obdist = loadMatrixByPow( src);
            }
            else{
                obdist = loadMatrix( src);
            }
        }
        java.util.HashMap<String, String> idNameMap = null;
        if(colorMapFn != null){
            idNameMap = utils.TextTabFileReader.getMapFromTabTextFile(colorMapFn); //getGenusSet(fungikList);
//            System.out.println(idNameMap);
            double[][] coords = calculateMDSOptimise(obdist);
            drawImageByLabel(coords, dist, idNameMap);
            java.io.FileWriter coodsOut = new java.io.FileWriter(dist+".coods");
            for(int i=0; i<coords.length; i++){
                coodsOut.write(i+"\t"+coords[i][0]+"\t"+coords[i][1]+"\t"+idNameMap.get(String.valueOf(i))+"\t"+"\n");
            }
            coodsOut.close();
        }
        else{
            String coodsDist = dist+".coods";
            double[][] coords = calculateMDSOptimise(obdist);
            utils.AffinityPropagation afp = new utils.AffinityPropagation();
            afp.runAffinityPropagation(coords, coodsDist);
            plotMDSbyCoordsFile(coodsDist, dist,true);
//            drawImageByLabel(coords, dist, idNameMap);
//            java.io.FileWriter coodsOut = new java.io.FileWriter(dist+".coods");
//            for(int i=0; i<coords.length; i++){
//                coodsOut.write(i+"\t"+coords[i][0]+"\t"+coords[i][1]+"\t"+"\n");
//            }
//            coodsOut.close();
        }
    }
    
    public double[][] loadIdtMatrix(String src) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line=br.readLine();
        String nameList[] = line.split(",");
        int size = Integer.valueOf(nameList[nameList.length-1])+1;
        double[][] obdist = new double[size][size];
        while((line=br.readLine())!=null){
            nameList = line.split(",");
            if(nameList.length < size){
                continue;
            }
            int id1 = Integer.valueOf(nameList[0]);
            for(int j=1; j<nameList.length; j++){
                int id2 = j-1;
                int val = Integer.valueOf(nameList[j]);
                if(id1 == id2){
                    obdist[id1][id2] = 0;
                }
                else{
//                    obdist[id1][id2] = Math.pow(1.0/(double)val, 2.0);
                    obdist[id1][id2] = 1.0/(double)(val);
                }
            }
        }
        return obdist;
    }
    
    public double[][] loadIdtMatrixByLog(String src) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line=br.readLine();
        String nameList[] = line.split(",");
        int size = Integer.valueOf(nameList[nameList.length-1])+1;
        double[][] obdist = new double[size][size];
        while((line=br.readLine())!=null){
            nameList = line.split(",");
            if(nameList.length < size){
                continue;
            }
            int id1 = Integer.valueOf(nameList[0]);
            for(int j=1; j<nameList.length; j++){
                int id2 = j-1;
                int val = Integer.valueOf(nameList[j]);
                if(id1 == id2){
                    obdist[id1][id2] = 0;
                }
                else{
                    obdist[id1][id2] = Math.log(1.0/(double)val);
//                    obdist[id1][id2] = 1.0/(double)(val);
                }
            }
        }
        return obdist;
    }
    
    public double[][] loadIdtMatrixByPow(String src) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line=br.readLine();
        String nameList[] = line.split(",");
        int size = Integer.valueOf(nameList[nameList.length-1])+1;
        double[][] obdist = new double[size][size];
        while((line=br.readLine())!=null){
            nameList = line.split(",");
            if(nameList.length < size){
                continue;
            }
            int id1 = Integer.valueOf(nameList[0]);
            for(int j=1; j<nameList.length; j++){
                int id2 = j-1;
                int val = Integer.valueOf(nameList[j]);
                if(id1 == id2){
                    obdist[id1][id2] = 0;
                }
                else{
                    obdist[id1][id2] = Math.pow(1.0/(double)val, 2.0);
//                    obdist[id1][id2] = 1.0/(double)(val);
                }
            }
        }
        return obdist;
    }
    
    public double[][] loadMatrix(String src) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line=br.readLine();
        String nameList[] = line.split(",");
        int size = Integer.valueOf(nameList[nameList.length-1])+1;
        double[][] obdist = new double[size][size];
        while((line=br.readLine())!=null){
            nameList = line.split(",");
            if(nameList.length < size){
                continue;
            }
            int id1 = Integer.valueOf(nameList[0]);
            for(int j=1; j<nameList.length; j++){
                int id2 = j-1;
                double val = Double.valueOf(nameList[j]);
                obdist[id1][id2] = val;
                if(id1 == id2){
                    obdist[id1][id2] = 0;
                }
            }
        }
        br.close();
        return obdist;
    }
    
    public double[][] loadMatrixByLog(String src) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line=br.readLine();
        String nameList[] = line.split(",");
        int size = Integer.valueOf(nameList[nameList.length-1])+1;
        double[][] obdist = new double[size][size];
        while((line=br.readLine())!=null){
            nameList = line.split(",");
            if(nameList.length < size){
                continue;
            }
            int id1 = Integer.valueOf(nameList[0]);
            for(int j=1; j<nameList.length; j++){
                int id2 = j-1;
                double val = Double.valueOf(nameList[j]);
                obdist[id1][id2] = Math.log(val+1);
                if(id1 == id2){
                    obdist[id1][id2] = 0;
                }
            }
        }
        br.close();
        return obdist;
    }
    
    public double[][] loadMatrixByPow(String src) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line=br.readLine();
        String nameList[] = line.split(",");
        int size = Integer.valueOf(nameList[nameList.length-1])+1;
        double[][] obdist = new double[size][size];
        while((line=br.readLine())!=null){
            nameList = line.split(",");
            if(nameList.length < size){
                continue;
            }
            int id1 = Integer.valueOf(nameList[0]);
            for(int j=1; j<nameList.length; j++){
                int id2 = j-1;
                double val = Double.valueOf(nameList[j]);
                obdist[id1][id2] = Math.pow(val, 2);
                if(id1 == id2){
                    obdist[id1][id2] = 0;
                }
            }
        }
        br.close();
        return obdist;
    }
    
//    public void createMDSLableImageByDistanceMatrixCsvAndColor(String src, String colorMapFn, String dist) throws Exception {
//        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
//        String line=br.readLine();
//        String nameList[] = line.split(",");
//        int size = Integer.valueOf(nameList[nameList.length-1])+1;
//        double[][] obdist = new double[size][size];
//        while((line=br.readLine())!=null){
//            nameList = line.split(",");
//            if(nameList.length < size){
//                continue;
//            }
//            int id1 = Integer.valueOf(nameList[0]);
//            for(int j=1; j<nameList.length; j++){
//                int id2 = j-1;
//                double val = Double.valueOf(nameList[j]);
//                obdist[id1][id2] = val;
//                if(id1 == id2){
//                    obdist[id1][id2] = 0;
//                }
//            }
//        }
//        
//        java.util.HashMap<String, String> idNameMap = null;
//        if(colorMapFn != null){
//            idNameMap = utils.TextTabFileReader.getMapFromTabTextFile(colorMapFn); //getGenusSet(fungikList);
////            System.out.println(idNameMap);
//            double[][] coords = calculateMDSOptimise(obdist);
//            drawImageByLabel(coords, dist, idNameMap);
//            java.io.FileWriter coodsOut = new java.io.FileWriter(dist+".coods");
//            for(int i=0; i<coords.length; i++){
//                coodsOut.write(i+"\t"+coords[i][0]+"\t"+coords[i][1]+"\t"+idNameMap.get(String.valueOf(i))+"\t"+"\n");
//            }
//            coodsOut.close();
//        }
//        else{
//            double[][] coords = calculateMDSOptimise(obdist);
//            drawImageByLabel(coords, dist, idNameMap);
//            java.io.FileWriter coodsOut = new java.io.FileWriter(dist+".coods");
//            for(int i=0; i<coords.length; i++){
//                coodsOut.write(i+"\t"+coords[i][0]+"\t"+coords[i][1]+"\t"+"\n");
//            }
//            coodsOut.close();
//        }
//        
//    }
    
    
    
    public void convertDistancePairToMatrix(String dsPair, String dist) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(dsPair));
        java.util.HashMap<String, String> map = new java.util.HashMap<>();
        String line;
        int max=0;
        while((line=br.readLine())!=null){
            String tk[] = line.split("=");
            String tk2[] = tk[0].split(",");
            map.put(tk[0], tk[1]);
            int n1 = Integer.valueOf(tk2[1]);
            int n0 = Integer.valueOf(tk2[0]);
            max = Math.max(max, Math.max(n1, n0));
        }
        br.close();
        double[][] obsMatrix = new double[max+1][max+1];
        for(String Pr:map.keySet()){
            String tk2[] = Pr.split(",");
            double d = Double.valueOf(map.get(Pr));
            int n1 = Integer.valueOf(tk2[1]);
            int n0 = Integer.valueOf(tk2[0]);
            if(d != 0){
                d = 1.0/d;
//                d = 1.0-d;
            }
            obsMatrix[n0][n1] = d;
            obsMatrix[n1][n0] = d;
        }
        java.io.FileWriter out = new java.io.FileWriter(dist);
        for(int i=0; i<=max; i++){
            out.write("\t"+i);
        }
        out.write("\n");
        for(int i=0; i<=max; i++){
            out.write(String.valueOf(i));
            for(int j=0; j<=max; j++){
                out.write("\t"+obsMatrix[i][j]);
            }
            out.write("\n");
        }
        out.close();
    }
    
    public void convertColorMap(String list, String dir) throws Exception {
        String dist = dir+"/color.map";
        String idColorMap = dir+"/id_color.map";
        java.io.FileWriter out = new java.io.FileWriter(dist);
        java.util.HashMap<String, String> map = utils.TextTabFileReader.getMapFromTabTextFile(list, 0, 2);

        java.util.TreeMap<Integer, String> sortColorMap = new java.util.TreeMap<>();
        for(int i=0;i<256;i++){
            sortColorMap.put(i, "255,0,"+ i );
            sortColorMap.put(255+i, (255-i)+",0,255" );
            sortColorMap.put(255+255+i, "0,"+ i +",255" );
            sortColorMap.put(255+255+255+i, "0,255,"+(255-i) );
            sortColorMap.put(255+255+255+255+i, i+",255,0" );
        }
        java.util.HashMap<String, String> colorMap = new java.util.HashMap<>();
        int maxCode = sortColorMap.size();
        java.util.HashSet<String> aSet = new java.util.HashSet<>();
        for(String S:map.keySet()){
            aSet.add(map.get(S));
        }
        int stepCode = maxCode/(aSet.size());
        int base = 0;
        
        java.util.HashMap<String, String> cmap = new java.util.HashMap<>();
        for(String S:aSet){
            out.write(S+"\t"+sortColorMap.get(base)+"\n");
            cmap.put(S, sortColorMap.get(base));
            base+=stepCode;
        }
        out.close();
        java.io.FileWriter out2 = new java.io.FileWriter(idColorMap);
        for(String S:map.keySet()){
            out2.write(S+"\t"+cmap.get(map.get(S))+"\n");
        }
        out2.close();
    }
    
    public double[][] calculateMDSOptimise(double[][] obdist) throws Exception {
        double[][] minCoords = calculateMDS_2D(obdist);
        double minErr = errorFunction(minCoords, obdist);
        System.out.println(minErr);
        double[][] tmpCoords = duplicateCoords(minCoords);
        for(int i=0; i<20; i++){
            int[] maxIdxs = findMaxError(tmpCoords, obdist);
            double[][] rCoords = duplicateCoords(tmpCoords);
            rCoords[maxIdxs[0]][0] = rCoords[maxIdxs[1]][0];
            rCoords[maxIdxs[0]][1] = rCoords[maxIdxs[1]][1];
            double[][] lCoords = duplicateCoords(tmpCoords);
            lCoords[maxIdxs[1]][0] = lCoords[maxIdxs[0]][0];
            lCoords[maxIdxs[1]][1] = lCoords[maxIdxs[0]][1];
            double rErr = errorFunction(rCoords, obdist);
            double lErr = errorFunction(lCoords, obdist);
            
            if(rErr>lErr){
                tmpCoords = lCoords;
            }
            else{
                tmpCoords = rCoords;
            }
            calculateMDS_2D(tmpCoords, obdist);
            double tmpErr = errorFunction(tmpCoords, obdist);
            if(minErr>tmpErr){
                minErr = tmpErr;
                minCoords = tmpCoords;
                System.out.println(minErr);
            }
        }
//        System.out.println(errorFunction(minCoords, obdist));
        int[] maxIdxs = findMaxError(minCoords, obdist);
        System.out.println(maxIdxs[0]+" -> "+maxIdxs[1]);
        return minCoords;
    }
    
    public double[][] duplicateCoords(double[][] coords) throws Exception {
        double[][] tmpCoords = new double[coords.length][coords[0].length];
        for(int i=0; i<coords.length; i++ ){
            for(int j=0; j<coords[i].length; j++ ){
                tmpCoords[i][j] = coords[i][j];
            }
        }
        return tmpCoords;
    }
    
    public double[][] calculateMDS_2D(double[][] obdist) throws Exception {
        int size = obdist.length;
        java.util.Random rd = new java.util.Random();
        double coords[][] = new double[size][2];
        for(int i=0; i<size; i++){
            coords[i][0] = rd.nextDouble();
            coords[i][1] = rd.nextDouble();
        }
        for(int it=0; it<500; it++){ // iteration
            for(int i=0; i<size; i++){
                double dx=0;
                double dy=0;
                for(int j=0; j<size; j++){
                    double absx = coords[j][0]-coords[i][0];
                    double absy = coords[j][1]-coords[i][1];
                    double d = Math.pow(absx*absx+absy*absy, 0.5);
                    if(d==0){
                        dx += obdist[i][j]/2;
                        dy += obdist[i][j]/2;
                    }
                    else{
                        double rt = obdist[i][j]/d;
                        dx += (absx*rt-absx);
                        dy += (absy*rt-absy);
                    }
                }
                coords[i][0] -= dx/size;
                coords[i][1] -= dy/size;
            }
        }
        return coords;
    }
    
    public double[][] calculateMDS_2D(double[][] coords, double[][] obdist) throws Exception {
        int size = obdist.length;
        for(int it=0; it<500; it++){ // iteration
            for(int i=0; i<size; i++){
                double dx=0;
                double dy=0;
                for(int j=0; j<size; j++){
                    double absx = coords[j][0]-coords[i][0];
                    double absy = coords[j][1]-coords[i][1];
                    double d = Math.pow(absx*absx+absy*absy, 0.5);
                    if(d==0){
                        dx += obdist[i][j]/2;
                        dy += obdist[i][j]/2;
                    }
                    else{
                        double rt = obdist[i][j]/d;
                        dx += (absx*rt-absx);
                        dy += (absy*rt-absy);
                    }
                }
                coords[i][0] -= dx/size;
                coords[i][1] -= dy/size;
            }
        }
        return coords;
    }
    
    public double errorFunction(double coords[][], double[][] obdist){
        int size = obdist.length;
        double err=0;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                double absx = coords[j][0]-coords[i][0];
                double absy = coords[j][1]-coords[i][1];
                double d = Math.pow(absx*absx+absy*absy, 0.5);
                err += Math.pow(obdist[i][j]-d, 2);
            }
        }
        err = Math.pow(err, 0.5);
        return err;
    }
    
    public int[] findMaxError(double coords[][], double[][] obdist){
        int size = obdist.length;
        double maxErr = 0;
        int maxIdx[] = new int[]{0,0};
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                double absx = coords[j][0]-coords[i][0];
                double absy = coords[j][1]-coords[i][1];
                double d = Math.pow(absx*absx+absy*absy, 0.5);
                double err = (d+1)/(obdist[i][j]+1);
                if(maxErr < err){
                    maxErr = err;
                    maxIdx[0] = i;
                    maxIdx[1] = j;
                }
            }
        }
        return maxIdx;
    }
    
    public void drawImageByColor(double coords[][], String dest, java.util.HashMap<String, String> nameColorMap) throws Exception {
        double maxX = Long.MIN_VALUE;
        double minX = Long.MAX_VALUE;
        double maxY = Long.MIN_VALUE;
        double minY = Long.MAX_VALUE;
        for (double[] coord : coords) {
            maxX = Math.max(maxX, coord[0]);
            maxY = Math.max(maxY, coord[1]);
            minX = Math.min(minX, coord[0]);
            minY = Math.min(minY, coord[1]);
        }
        double mapWidth = Math.max(maxX-minX, maxY-minY);
        int fWidth = 2000;
        java.io.File output = new java.io.File(dest);
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(fWidth, fWidth, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g = img.createGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, fWidth, fWidth);
        g.setColor(java.awt.Color.RED);
        g.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 12));
        double rate = fWidth/mapWidth*0.9;
        java.util.ArrayList<double[]> nodeArr = new java.util.ArrayList<>();
//        int iSize = 10;
//        int fSize = 30;
        for (int i=0; i< coords.length; i++) {
            double[] coord = coords[i];
            double x = (coord[0]-minX)*rate+100;
            double y = (coord[1]-minY)*rate+100;
            String cd = nameColorMap.get(String.valueOf(i));
            
            if(cd != null){
               g.setColor(java.awt.Color.decode(cd));
            }
            else{
               g.setColor(java.awt.Color.BLACK);
            }
            g.fillOval((int)x, (int)y, iSize, iSize);
        }
        javax.imageio.ImageIO.write(img, "png", output);
    }
    
    public void drawImageByLabel(double coords[][], String dest) throws Exception {
        drawImageByLabel(coords, dest, null);
    }
    
    public void drawImageByLabel(double coords[][], String dest, java.util.HashMap<String, String> nameColorMap) throws Exception {
        double maxX = Long.MIN_VALUE;
        double minX = Long.MAX_VALUE;
        double maxY = Long.MIN_VALUE;
        double minY = Long.MAX_VALUE;
        for (double[] coord : coords) {
            maxX = Math.max(maxX, coord[0]);
            maxY = Math.max(maxY, coord[1]);
            minX = Math.min(minX, coord[0]);
            minY = Math.min(minY, coord[1]);
        }
        double mapWidth = Math.max(maxX-minX, maxY-minY);
        int fWidth = 2000;
        int bounber = 200;
        java.io.File output = new java.io.File(dest);
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(fWidth, fWidth, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g = img.createGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, fWidth, fWidth);
        g.setColor(java.awt.Color.RED);
        g.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 12));
        double rate = fWidth/mapWidth*0.7;
        java.util.ArrayList<double[]> nodeArr = new java.util.ArrayList<>();
        int iSize = 15;
        int fSize = 35;
        java.util.ArrayList<int[]> coodrArr = new java.util.ArrayList<>();
        java.util.TreeMap<Integer, java.util.ArrayList<Integer>> coordIdMap = new java.util.TreeMap<>();
        for (int i=0; i< coords.length; i++) {
            double[] coord = coords[i];
            double x = (coord[0]-minX)*rate+bounber;
            double y = (coord[1]-minY)*rate+bounber;
            regSite((int)x, coodrArr.size(), coordIdMap);
            regSite((int)(x+iSize), coodrArr.size(), coordIdMap);
            coodrArr.add(new int[]{(int)x, (int)(x+iSize), (int)y, (int)(y+iSize)});
            nodeArr.add(new double[]{x, y});
        }
        g.setFont(new java.awt.Font(java.awt.Font.SERIF, java.awt.Font.BOLD, fSize));
        for (int i=0; i< nodeArr.size(); i++) {
            double[] coord = nodeArr.get(i);
            int[] tmpCoord = getSite((int)coord[0], (int)coord[1], fSize, coordIdMap, coodrArr);
            if(tmpCoord != null){
                g.setColor(java.awt.Color.LIGHT_GRAY);
                // Drae line
                g.drawLine((int)(coord[0]+iSize/2.0), (int)(coord[1]+iSize/2.0), tmpCoord[0], tmpCoord[3]);
                g.setColor(java.awt.Color.BLACK);
                // Drae label
                g.drawString(String.valueOf(i), tmpCoord[0], tmpCoord[3]);
                regSite(tmpCoord[0], coodrArr.size(), coordIdMap);
                regSite(tmpCoord[1], coodrArr.size(), coordIdMap);
                coodrArr.add(tmpCoord);
            }
        }
        if(nameColorMap == null){
            g.setColor(java.awt.Color.RED);
            for (int i=0; i< nodeArr.size(); i++) {
                double[] coord = nodeArr.get(i);
                g.fillOval((int)coord[0], (int)coord[1], iSize, iSize);
            }
        }
        else{
            for (int i=0; i< nodeArr.size(); i++) {
                String cd = nameColorMap.get(String.valueOf(i));
                if(cd != null){
                   g.setColor(java.awt.Color.decode(cd));
                }
                else{
                   g.setColor(java.awt.Color.BLACK);
                }
                double[] coord = nodeArr.get(i);
                g.fillOval((int)coord[0], (int)coord[1], iSize, iSize);
            }
        }
        
        javax.imageio.ImageIO.write(img, "png", output);
    }
    
    public void drawImage(double coords[][], String dest) throws Exception {
        double maxX = Long.MIN_VALUE;
        double minX = Long.MAX_VALUE;
        double maxY = Long.MIN_VALUE;
        double minY = Long.MAX_VALUE;
        for (double[] coord : coords) {
            maxX = Math.max(maxX, coord[0]);
            maxY = Math.max(maxY, coord[1]);
            minX = Math.min(minX, coord[0]);
            minY = Math.min(minY, coord[1]);
        }
        double mapWidth = Math.max(maxX-minX, maxY-minY);
        int fWidth = 2000;
        int xSeft = 200;
        java.io.File output = new java.io.File(dest);
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(fWidth+xSeft, fWidth, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g = img.createGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, fWidth+xSeft, fWidth);
        g.setColor(java.awt.Color.RED);
        double rate = fWidth/mapWidth*0.9;
        java.util.ArrayList<double[]> nodeArr = new java.util.ArrayList<>();
        int iSize = 20;
        for (int i=0; i< coords.length; i++) {
            double[] coord = coords[i];
            double x = (coord[0]-minX)*rate+200;
            double y = (coord[1]-minY)*rate+100;
            nodeArr.add(new double[]{x, y});
        }
        g.setColor(java.awt.Color.BLACK);
        for (int i=0; i< nodeArr.size(); i++) {
            double[] coord = nodeArr.get(i);
            g.fillOval((int)coord[0], (int)coord[1], iSize, iSize);
        }
        javax.imageio.ImageIO.write(img, "png", output);
    }
    
    public void regSite(int x, int idx, java.util.TreeMap<Integer, java.util.ArrayList<Integer>> coordIdMap){
        java.util.ArrayList<Integer> X = coordIdMap.get(x);
        if(X==null){
            X = new java.util.ArrayList<>();
            coordIdMap.put(x, X);
        }
        X.add(idx);
    }
    
    public int[] getSite(int x1, int y1, int fSize, java.util.TreeMap<Integer, java.util.ArrayList<Integer>> coordIdMap, java.util.ArrayList<int[]> coodrArr){
        for(int r=1; r<100; r++){
            double rl = 2*Math.PI*fSize*r;
            double an = (1500/rl)/(2*Math.PI);
            for(double d=0; d<2*Math.PI; d+=an){
                double x = Math.cos(d-Math.PI/4.0)*fSize*r+x1;
                double y = Math.sin(d-Math.PI/4.0)*fSize*r+y1;
                if(!isCollision((int)x, (int)(x+2*fSize), (int)y, (int)(y + fSize), coordIdMap, coodrArr)){
                    return new int[]{(int)x, (int)(x+2*fSize), (int)y, (int)(y + fSize)};
                }
            }
        }
        return null;
    }
    
    public boolean isCollision(int x1, int x2, int y1, int y2, java.util.TreeMap<Integer, java.util.ArrayList<Integer>> coordIdMap, java.util.ArrayList<int[]> coodrArr){
        java.util.SortedMap<Integer, java.util.ArrayList<Integer>> submap = coordIdMap.subMap(x1, x2);
        if(submap.isEmpty()){
            return false;
        }
        java.util.TreeSet<Integer> ySet = new java.util.TreeSet<>();
        for(Integer I:submap.keySet()){
            java.util.ArrayList<Integer> tmpArr = submap.get(I);
            for(Integer Id:tmpArr){
                int[] coords = coodrArr.get(Id);
                ySet.add(coords[2]);
                ySet.add(coords[3]);
            }
        }
        java.util.SortedSet<Integer> sSet = ySet.subSet(y1, y2);
        if(sSet.isEmpty()){
            return false;
        }
        return true;
    }
    
}
