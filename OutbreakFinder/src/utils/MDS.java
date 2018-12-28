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
public class MDS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
//        new MDS().simulation1();
//        new MDS().genMDSImage("E:\\tmp_for_spid\\workdir\\tmp\\mds_log.map","E:\\tmp_for_spid\\workdir\\tmp\\test.png");
//        new MDS().genMDSImageWithLegend("E:\\tmp_for_spid\\workdir\\tmp\\mds_log.map","E:\\tmp_for_spid\\workdir\\tmp\\test.png", "E:\\tmp_for_spid\\workdir\\tmp\\color_def.map");
        //16S
//        new MDS().genMDSImageWithLegend("E:\\tmp_for_spid\\workdir\\tmp\\mds_16_log.map","E:\\tmp_for_spid\\workdir\\tmp\\test.png", "E:\\tmp_for_spid\\workdir\\tmp\\color_def.map");
//        new MDS().genMDSImageWithLegend("E:\\tmp_for_spid\\workdir\\result_that\\statistics\\mds.coods","E:\\tmp_for_spid\\workdir\\result_that\\statistics\\test.png", "E:\\tmp_for_spid\\workdir\\tmp\\color_def.map");
//        new MDS().genMDSImageWithLegend("E:\\tmp_for_spid\\workdir\\result_that\\dbln30mb15t11k4_step_1\\mds.coods","E:\\tmp_for_spid\\workdir\\result_that\\dbln30mb15t11k4_step_1\\test.png", "E:\\tmp_for_spid\\workdir\\result_that\\dbln30mb15t11k4_step_1\\color_def.map");
//        new utils.MDS().genMDSImageDistMatrixFile("E:\\tmp\\tmp_for_delete\\test.matrix", "E:\\tmp\\tmp_for_delete\\test.png", "E:\\tmp\\tmp_for_delete\\name_color.map");
//        new utils.MDS().genMDSImageDistMatrixFile("E:\\tmp_for_spid\\workdir\\result_from_server\\test1_list\\test.matrix", "E:\\tmp_for_spid\\workdir\\result_from_server\\test1_list\\test.png", "E:\\tmp_for_spid\\workdir\\result_from_server\\test1_list\\name_color.map");
//        new utils.MDS().genMDSImageWithLegend();
    }
    
    public void simulation1() throws Exception {
        double[][] obdist = genSimulationData();
        double[][] coords = calculateMDS_2D(obdist);
        String dest = "E:\\tmp_for_spid\\workdir\\tmp\\test.png";
        drawImage(coords, dest);
    }
    
    public void genMDSImage(String src, String dist) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line;
        java.util.ArrayList<double[]> coorArr = new java.util.ArrayList<>();
        java.util.ArrayList<java.awt.Color> colorArr = new java.util.ArrayList<>();
        while((line=br.readLine())!=null){
            String tk[] = line.split("\t");
            if(tk.length!=3){
                continue;
            }
            coorArr.add(new double[]{Double.valueOf(tk[0]), Double.valueOf(tk[1])});
            System.out.println(tk[2]);
            String tk2[] = tk[2].split(",");
            colorArr.add(new java.awt.Color(Integer.valueOf(tk2[0]), Integer.valueOf(tk2[1]), Integer.valueOf(tk2[2])));
        }
        br.close();
        drawImage(coorArr, colorArr, dist);
    }
    
    public void genMDSImageDistMatrixFile(String src, String dist, String colorMap) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(src));
        String line=br.readLine();
        String nameList[] = line.split("\t");
        java.util.ArrayList<String> nameArr = new java.util.ArrayList<>();
        for(int i=1; i<nameList.length; i++){
            nameArr.add(nameList[i]);
        }
        double[][] obdist = new double[nameArr.size()][nameArr.size()];
        int idx=0;
        while((line=br.readLine())!=null){
            String tk[] = line.split("\t");
            if(tk.length<nameArr.size()-1){
                continue;
            }
            for(int i=1; i<tk.length; i++){
//                System.out.println(tk[i]);
                obdist[idx][i-1] = obdist[i-1][idx] = Double.valueOf(tk[i]);
            }
            idx++;
        }
        double[][] coords = calculateMDS_2D(obdist);
//        drawImageByLabel(double coords[][], String dest, java.util.ArrayList<String> nameList) 
        drawImageByLabel(coords, dist, nameArr, colorMap);
    }
    
    public void genMDSImageWithLegend(String src, String dist, String legend) throws Exception {
        String line;
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(legend));
        java.util.HashMap<String, java.awt.Color> nameColorMap = new java.util.HashMap<>();
        while((line=br.readLine())!=null){
            String tk[] = line.split("\t");
            String tk2[] = tk[1].split(",");
            nameColorMap.put(tk[0], new java.awt.Color(Integer.valueOf(tk2[0]), Integer.valueOf(tk2[1]), Integer.valueOf(tk2[2])));
        }
        br.close();
        br = new java.io.BufferedReader(new java.io.FileReader(src));
        java.util.ArrayList<double[]> coorArr = new java.util.ArrayList<>();
        java.util.ArrayList<java.awt.Color> colorArr = new java.util.ArrayList<>();
        while((line=br.readLine())!=null){
            String tk[] = line.split("\t");
            if(tk.length!=3){
                continue;
            }
            coorArr.add(new double[]{Double.valueOf(tk[0]), Double.valueOf(tk[1])});
            System.out.println(tk[2]);
            if(!tk[2].matches("[0-9]+,[0-9]+,[0-9]+")){
                colorArr.add(new java.awt.Color(100, 100, 100));
            }
            else{
                String tk2[] = tk[2].split(",");
                colorArr.add(new java.awt.Color(Integer.valueOf(tk2[0]), Integer.valueOf(tk2[1]), Integer.valueOf(tk2[2])));
            }
            
        }
        br.close();
        drawImage(coorArr, colorArr, nameColorMap, dist);
    }
    
    public void genMDSImageWithLegend(String coods, String colorMap, String dist, String legend) throws Exception {
//        String coods = "E:\\tmp_for_mds\\result\\example_for_clustero\\multiAlign2.png.coods";
//        String colorMap = "E:\\tmp_for_mds\\result\\example_for_clustero\\multiAlign2.color.map";
//        String dist = "E:\\tmp_for_mds\\result\\example_for_clustero\\multiAlign2_new.png";
//        String legend = "E:\\tmp_for_mds\\result\\example_for_clustero\\multiAlign2.color.map.legend";
        String line;
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(legend));
        java.util.HashMap<String, java.awt.Color> nameColorMap = new java.util.HashMap<>();
        while((line=br.readLine())!=null){
            String tk[] = line.split("\t");//java.awt.Color.decode(cd)
            nameColorMap.put(tk[0], java.awt.Color.decode(tk[1]));
//            String tk2[] = tk[1].split(",");
//            nameColorMap.put(tk[0], new java.awt.Color(Integer.valueOf(tk2[0]), Integer.valueOf(tk2[1]), Integer.valueOf(tk2[2])));
        }
        br.close();
        utils.TextTabFileReader txtReader = new utils.TextTabFileReader();
        txtReader.open(colorMap);
//        java.util.HashMap<String, java.awt.Color> colorMap = new java.util.HashMap<>();
        java.util.ArrayList<java.awt.Color> colorArr = new java.util.ArrayList<>();
        while((line=txtReader.nextLine())!=null){
            String tk[] = line.split("\t");
            colorArr.add(java.awt.Color.decode(tk[1]));
//            colorMap.put(tk[0], java.awt.Color.decode(tk[1]));
        }
        txtReader.close();
//        br = new java.io.BufferedReader(new java.io.FileReader(src));
        txtReader.open(coods);
        java.util.ArrayList<double[]> coorArr = new java.util.ArrayList<>();
        while((line=txtReader.nextLine())!=null){
            if(line.trim().isEmpty()){
                continue;
            }
            String tk[] = line.split("\t");
            coorArr.add(new double[]{Double.valueOf(tk[1]), Double.valueOf(tk[2])});
            System.out.println(tk[2]);
//            if(!tk[2].matches("[0-9]+,[0-9]+,[0-9]+")){
//                colorArr.add(new java.awt.Color(100, 100, 100));
//            }
//            else{
//                String tk2[] = tk[2].split(",");
//                colorArr.add(new java.awt.Color(Integer.valueOf(tk2[0]), Integer.valueOf(tk2[1]), Integer.valueOf(tk2[2])));
//            }
            
        }
        txtReader.close();
        drawImage(coorArr, colorArr, nameColorMap, dist);
    }
    
    public double[][] genSimulationData() throws Exception {
        int size = 200;
        int md = 20;
        double[][] simCoods = new double[size][md];
        for(int i=0; i<size; i++){
            for(int j=0; j<md; j++){
                double cx = Math.random()*3;
                simCoods[i][j]=cx;
            }
            int d = i/10;
            simCoods[i][d]+=14;
        }
        double[][] obdist = new double[size][size];
        for(int i=0; i<size; i++){
            for(int j=i; j<size; j++){
                double sum = 0;
                for(int d=0; d<md; d++){
                    sum += Math.pow(simCoods[i][d]-simCoods[j][d], 2);
                }
                double dst = Math.pow(sum, 0.5);
                obdist[i][j] = dst;
                obdist[j][i] = dst;
            }
        }
        return obdist;
    }
    
    public double[][] calculateMDS_2D(double[][] obdist) throws Exception {
        int size = obdist.length;
//        double sizeRate = 1.0;
        java.util.Random rd = new java.util.Random();
        double coords[][] = new double[size][2];
        for(int i=0; i<size; i++){
            coords[i][0] = rd.nextDouble();
            coords[i][1] = rd.nextDouble();
        }
        double lastError = Long.MAX_VALUE;
//        double delta = 0.0001;
        for(int it=0; it<50; it++){ // iteration
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
            double error = errorFunction(coords, obdist);
            System.out.println(error);
//            if(lastError - error < delta){
//                break;
//            }
//            lastError = error;
        }
        return coords;
    }
    
    public double[][] calculateMDS_2D_NonMetric(double[][] obdist, double threashold) throws Exception {
        int size = obdist.length;
        java.util.Random rd = new java.util.Random();
        double coords[][] = new double[size][2];
        for(int i=0; i<size; i++){
            coords[i][0] = rd.nextDouble();
            coords[i][1] = rd.nextDouble();
        }
        for(int it=0; it<240; it++){ // iteration
            for(int i=0; i<size; i++){
                double dx=0;
                double dy=0;
                int rs=1;
                for(int j=0; j<size; j++){
                    double absx = coords[j][0]-coords[i][0];
                    double absy = coords[j][1]-coords[i][1];
                    double d = Math.pow(absx*absx+absy*absy, 0.5);
                    if(obdist[i][j]>threashold && d>threashold){
                        continue;
                    }
                    rs++;
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
                coords[i][0] -= dx/rs;
                coords[i][1] -= dy/rs;
            }
            System.out.println(errorFunction(coords, obdist));
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
        java.io.File output = new java.io.File(dest);
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(fWidth, fWidth, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g = img.createGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, fWidth, fWidth);
        g.setColor(java.awt.Color.RED);
        g.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 12));
        double rate = fWidth/mapWidth*0.9;
        int iSize = 10;
        for (int i=0; i< coords.length; i++) {
            double[] coord = coords[i];
            double x = (coord[0]-minX)*rate+100;
            double y = (coord[1]-minY)*rate+100;
            g.setColor(java.awt.Color.RED);
            g.fillOval((int)x, (int)y, iSize, iSize);
//            g.drawLine((int)x, (int)y-iSize, (int)x, (int)y+iSize);
//            g.drawLine((int)x-iSize, (int)y, (int)x+iSize, (int)y);
        }
        javax.imageio.ImageIO.write(img, "png", output);
    }
    
    
    public void drawImage( java.util.ArrayList<double[]> coords, 
                           java.util.ArrayList<java.awt.Color> colorArr,
                           String dest) throws Exception {
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
        int iSize = 10;
        for (int i=0; i< coords.size(); i++) {
            double[] coord = coords.get(i);
            double x = (coord[0]-minX)*rate+100;
            double y = (coord[1]-minY)*rate+100;
//            colorArr.get(i);
            g.setColor(colorArr.get(i));
//            g.fillOval((int)x, (int)y, iSize, iSize);
            g.drawLine((int)x, (int)y-iSize, (int)x, (int)y+iSize);
            g.drawLine((int)x-iSize, (int)y, (int)x+iSize, (int)y);
        }
        javax.imageio.ImageIO.write(img, "png", output);
    }
    
    public void drawImage( java.util.ArrayList<double[]> coords, 
                           java.util.ArrayList<java.awt.Color> colorArr,
                           java.util.HashMap<String, java.awt.Color> nameColorMap,
                           String dest) throws Exception {
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
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(fWidth+500, fWidth, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g = img.createGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, fWidth+500, fWidth);
        g.setColor(java.awt.Color.RED);
        g.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 40));
        double rate = fWidth/mapWidth*0.95;
        int iSize = 10;
        int step=50;
        java.util.TreeMap<Integer, String> sortMap = new java.util.TreeMap<>();
        for (String S:nameColorMap.keySet()) {
            sortMap.put(nameColorMap.get(S).getRGB(), S);
        }
        for (Integer S:sortMap.keySet()) {
            String n = sortMap.get(S);
            g.setColor(nameColorMap.get(n));
            g.fillOval(10, 10+step, iSize, iSize);
            g.drawString(n, 25, 25+step);
            step+=40;
        }
        for (int i=0; i< coords.size(); i++) {
            double[] coord = coords.get(i);
            double x = (coord[0]-minX)*rate+400;
            double y = (coord[1]-minY)*rate+50;
            g.setColor(colorArr.get(i));
//            g.drawLine((int)x-iSize, (int)y, (int)x+iSize, (int)y);
//            g.drawLine((int)x, (int)y-iSize, (int)x, (int)y+iSize);
            g.fillOval((int)x, (int)y, iSize, iSize);
        }
        javax.imageio.ImageIO.write(img, "png", output);
    }
    
    public void drawImageByLabel(double coords[][], String dest, java.util.ArrayList<String> nameList, String colorMap) throws Exception {
        java.util.HashMap<String, String> nameColorMap = utils.TextTabFileReader.getMapFromTabTextFile(colorMap);
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
        int iSize = 10;
        int fSize = 30;
        java.util.ArrayList<int[]> coodrArr = new java.util.ArrayList<>();
        java.util.TreeMap<Integer, java.util.ArrayList<Integer>> coordIdMap = new java.util.TreeMap<>();
        for (int i=0; i< coords.length; i++) {
            double[] coord = coords[i];
            double x = (coord[0]-minX)*rate+100;
            double y = (coord[1]-minY)*rate+100;
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
                g.drawLine((int)(coord[0]+iSize/2.0), (int)(coord[1]+iSize/2.0), tmpCoord[0], tmpCoord[3]);
                g.setColor(java.awt.Color.BLACK);
                String color = nameColorMap.get(nameList.get(i));
                if(color != null){
                    java.lang.reflect.Field f = java.awt.Color.class.getField(color);
                    g.setColor((java.awt.Color)f.get(null));
                }
                g.drawString(String.valueOf(i), tmpCoord[0], tmpCoord[3]);
                regSite(tmpCoord[0], coodrArr.size(), coordIdMap);
                regSite(tmpCoord[1], coodrArr.size(), coordIdMap);
                coodrArr.add(tmpCoord);
            }
        }
//        java.lang.reflect.Field f = java.awt.Color.class.getField("blue");
//        g.setColor((java.awt.Color)f.get(null));
//        g.setColor(java.awt.Color.RED);
        for (int i=0; i< nodeArr.size(); i++) {
            String color = nameColorMap.get(nameList.get(i));
            g.setColor(java.awt.Color.BLACK);
            if(color != null){
                java.lang.reflect.Field f = java.awt.Color.class.getField(color);
                g.setColor((java.awt.Color)f.get(null));
            }
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
//                g.fillOval((int)x, (int)y, iSize, iSize);
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
