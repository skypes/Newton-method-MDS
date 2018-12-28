/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

//import java.awt.Color;

/**
 *
 * @author shining
 */
public class PlotMDSwithRange {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        new PlotMDSwithRange().plotMDSbyCoodsAndRange("E:\\tmp_for_mds\\result\\cluster16S\\16S.png.coods", 
                                                      "E:\\tmp_for_mds\\result\\cluster16S\\16S.map", 
                                                      "E:\\tmp_for_mds\\result\\cluster16S\\16S.png.coods.png");
    }
    
    public void plotMDSbyCoodsAndRange(String src, String nmap, String dist) throws Exception {
        java.util.ArrayList<String> lines = utils.TextTabFileReader.getLineArrFromFile(src);
        double[][] coords = new double[lines.size()][2];
        for(int i=0; i<lines.size(); i++){
            String line = lines.get(i);
            String tk[] = line.split("\t");
            coords[i][0] = Double.valueOf(tk[1]);
            coords[i][1] = Double.valueOf(tk[2]);
        }
        double range[] = new double[]{1300 ,680 ,900, 300};
        java.util.HashSet<Integer> idSet = drawImageByRange(coords, dist, range);
        lines = utils.TextTabFileReader.getLineArrFromFile(nmap);
        for(int i=0; i<lines.size(); i++){
            String line = lines.get(i);
            String tk[] = line.split("\t");
            Integer id = Integer.valueOf(tk[0]);
            if(idSet.contains(id)){
                String tk2[] = tk[1].split("\\s+");
                System.out.println(line);
            }
        }
        
    }
    
    public java.util.HashSet<Integer> drawImageByRange(double coords[][], String dest, double range[]) throws Exception {
        java.util.HashSet<Integer> idSet = new java.util.HashSet<>();
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
            if(coord[0] > range[0] && coord[0] < range[0]+range[2] &&
               coord[1] > range[1] && coord[1] < range[1]+range[3]){
                g.setColor(java.awt.Color.RED);
                idSet.add(i);
            }
            else{
                g.setColor(java.awt.Color.BLACK);
            }
            g.fillOval((int)coord[0], (int)coord[1], iSize, iSize);
        }
        g.setColor(java.awt.Color.RED);
        g.drawRect((int)range[0], (int)range[1], (int)range[2], (int)range[3]);
        javax.imageio.ImageIO.write(img, "png", output);
        return idSet;
    }
    
}
