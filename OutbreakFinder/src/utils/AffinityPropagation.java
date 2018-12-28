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
public class AffinityPropagation {

    int N = 0;
    int iter = 230;
    double lambda = 0.9;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        if(args.length==0){
            // a simple case
            args = new String[]{"coods=E:\\tmp_for_mds\\result\\cluster16S\\16Src.png.coods",
                                "dist=E:\\tmp_for_mds\\result\\cluster16S",
//                                "-label",
                                "prefix=ap"
                               };
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
        String coods = para.get("coods");
        if(coods == null){
            System.out.println("Parameter coods is not found!!");
            return;
        }
        if(para.containsKey("-label")){
            new AffinityPropagation().plotMSDbyAPlebel(coods, dist, prefix);
        }
        else{
            new AffinityPropagation().plotMSDbyAP(coods, dist, prefix);
        }
    }
    
    public void plotMSDbyAPlebel(String coods, String dist, String prefix) throws Exception {
        String coods2 = dist+"/"+prefix+".coods";
        String figure = dist+"/"+prefix+".png";
        runAffinityPropagation(coods, dist, prefix);
        AdvancedMDS advancedMDS = new AdvancedMDS();
        advancedMDS.iSize = 20;
        advancedMDS.plotMDSbyCoordsFile(coods2, figure, true);

    }
    
    public void plotMSDbyAP(String coods, String dist, String prefix) throws Exception {
        String coods2 = dist+"/"+prefix+".coods";
        String figure = dist+"/"+prefix+".png";
        runAffinityPropagation(coods, dist, prefix);
        AdvancedMDS advancedMDS = new AdvancedMDS();
        advancedMDS.iSize = 20;
        advancedMDS.plotMDSbyCoordsFile(coods2, figure, false);

    }
    
    public void runAffinityPropagation(String coods, String dist, String prefix) throws Exception {
        String coods2 = dist+"/"+prefix+".coods";
//        String figure = dist+"/"+prefix+".png";
        java.util.ArrayList<String> lines = utils.TextTabFileReader.getLineArrFromFile(coods);
        java.util.ArrayList<double[]> dataPoint = new java.util.ArrayList<>();
        for(String line : lines){
            String tk[] = line.split("\t");
            dataPoint.add(new double[]{Double.valueOf(tk[1]), Double.valueOf(tk[2])});
        }
        mainProcess(dataPoint, coods2);
//        new AdvancedMDS().plotMDSbyCoordsFile(coods2, figure);
    }
    
    public void runAffinityPropagation(double[][] coords, String coodsdist) throws Exception {
//        java.util.ArrayList<String> lines = utils.TextTabFileReader.getLineArrFromFile(coodsrc);
        java.util.ArrayList<double[]> dataPoint = new java.util.ArrayList<>();
        for(double[] d : coords){
            dataPoint.add(d);
        }
        mainProcess(dataPoint, coodsdist);
    }
    
    public void example_2() throws Exception {
        String fn = "E:\\tmp_for_mds\\data\\ToyProblemData.txt";
        String dist = "E:\\tmp_for_mds\\result\\example_for_simple\\AP_example.coods";
        java.util.ArrayList<double[]> dataPoint = loadData(fn);
        mainProcess(dataPoint, dist);
    }
    
    public void mainProcess(java.util.ArrayList<double[]> dataPoint, String dist) throws Exception {
//        java.util.ArrayList<double[]> dataPoint = loadData(fn);
        double[][] S = initAP(dataPoint);
//        double[][]  S = readData(fn);
        double[][] A = new double[N][N];
        double[][] R = new double[N][N];
        //////////////////////////////////////
	for(int m=0; m<iter; m++) {
//            System.out.println(m);
            for(int i=0; i<N; i++) {
                for(int k=0; k<N; k++) {
                    double max = -1e100;
                    for(int kk=0; kk<k; kk++) {
                        if(S[i][kk]+A[i][kk]>max)
                            max = S[i][kk]+A[i][kk];
                    }
                    for(int kk=k+1; kk<N; kk++) {
                        if(S[i][kk]+A[i][kk]>max)
                            max = S[i][kk]+A[i][kk];
                    }
                    R[i][k] = (1-lambda)*(S[i][k] - max) + lambda*R[i][k];
                }
            }
            for(int i=0; i<N; i++) {
                for(int k=0; k<N; k++) {
                    if(i==k) {
                        double sum = 0.0;
                        for(int ii=0; ii<i; ii++) {
                            sum += Math.max(0.0, R[ii][k]);
                        }
                        for(int ii=i+1; ii<N; ii++) {
                            sum += Math.max(0.0, R[ii][k]);
                        }
                        A[i][k] = (1-lambda)*sum + lambda*A[i][k];
                    } else {
                        double sum = 0.0;
                        int maxik = Math.max(i, k);
                        int minik = Math.min(i, k);
                        for(int ii=0; ii<minik; ii++) {
                            sum += Math.max(0.0, R[ii][k]);
                        }
                        for(int ii=minik+1; ii<maxik; ii++) {
                            sum += Math.max(0.0, R[ii][k]);
                        }
                        for(int ii=maxik+1; ii<N; ii++) {
                            sum += Math.max(0.0, R[ii][k]);
                        }
                        A[i][k] = (1-lambda)*Math.min(0.0, R[k][k]+sum) + lambda*A[i][k];
                    }
                }
            }
        }
        
//	double E[N][N] = {0};
        double[][] E = new double[N][N];

//	vector<int> center;
        java.util.ArrayList<Integer> center = new java.util.ArrayList<>();
        for(int i=0; i<N; i++) {
            E[i][i] = R[i][i] + A[i][i];
            if(E[i][i]>0) {
                center.add(i);
            }
        }
//        int idx[N] = {0};
        int idx[] = new int[N];

	for(int i=0; i<N; i++) {
            int idxForI = 0;
            double maxSim = -1e100;
            for(int j=0; j<center.size(); j++) {
                int c = center.get(j);
                if (S[i][c]>maxSim) {
                    maxSim = S[i][c];
                    idxForI = c;
                }
            }
            idx[i] = idxForI;
        }
//        java.util.HashMap<Integer, String> colorMap = new java.util.HashMap<>();
        java.util.HashMap<Integer, Integer> idcMap = new java.util.HashMap<>();
        java.util.HashSet<Integer> idSet = new java.util.HashSet<>();
        for(int i=0; i<N; i++) {
            idcMap.put(i, idx[i]);
            idSet.add(idx[i]);
//            System.out.println(idx[i]+1);
	}
        java.io.FileWriter out = new java.io.FileWriter(dist);
        java.util.HashMap<Integer, String> colorMap = getColorMap(idSet);
        for(int i=0; i<N; i++){
            double d[] = dataPoint.get(i);
            if(idSet.contains(i)){
                out.write(i+"\t"+d[0]+"\t"+d[1]+"\t"+colorMap.get(i)+"\n");
            }
            else{
                out.write(i+"\t"+d[0]+"\t"+d[1]+"\t"+colorMap.get(idcMap.get(i))+"\n");
            }
//            System.out.println(i+"\t"+d[0]+"\t"+d[1]+"\t"+colorMap.get(idcMap.get(i)));
        }
        out.close();
//        System.out.println(colorMap);
        /////////////////////////////////////////
    }
    
    public double[][] initAP(java.util.ArrayList<double[]> dataPoint){
        N = dataPoint.size();
        double[][] S = new double[N][N];
        java.util.ArrayList<Double> tmpS = new java.util.ArrayList<>();
        for(int i=0; i<N; i++){
            double di[] = dataPoint.get(i);
            for(int j=0; j<N; j++){
                double dj[] = dataPoint.get(j);
                S[i][j] = -((di[0]-dj[0])*(di[0]-dj[0])+(di[1]-dj[1])*(di[1]-dj[1]));
                S[j][i] = S[i][j];
                tmpS.add(S[i][j]); 
            }
        }
        java.util.Collections.sort(tmpS);
	double median = 0;
        
	int size = N*(N-1)/2;
	if(size%2==0) 
		median = (tmpS.get(size/2)+tmpS.get(size/2-1))/2;
	else 
		median = tmpS.get(size/2);
	for(int i=0; i<N; i++) S[i][i] = median;
        return S;
    }
    
    public java.util.ArrayList<double[]> loadData(String fn) throws Exception {
        java.util.ArrayList<String>  lines = utils.TextTabFileReader.getLineArrFromFile(fn);
        N = lines.size();
        double[][] S = new double[N][N];
        java.util.ArrayList<double[]> dataPoint = new java.util.ArrayList<>();
        for(int i=0; i<N; i++){
            String tk[] = lines.get(i).split("\t");
            dataPoint.add(new double[]{Double.valueOf(tk[0]), Double.valueOf(tk[1])});
        }
        return dataPoint;
    }
    
    public double[][] readData(String fn) throws Exception {
        java.util.ArrayList<String>  lines = utils.TextTabFileReader.getLineArrFromFile(fn);
        N = lines.size();
        double[][] S = new double[N][N];
        double dataPoint[][] = new double[N][2];
        for(int i=0; i<N; i++){
            String tk[] = lines.get(i).split("\t");
//            double d1[] = new double[]{Double.valueOf(tk1[0]), Double.valueOf(tk1[1])};
            dataPoint[i][0] = Double.valueOf(tk[0]);
            dataPoint[i][1] = Double.valueOf(tk[1]);
        }
        java.util.ArrayList<Double> tmpS = new java.util.ArrayList<>();
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                S[i][j] = -((dataPoint[i][0]-dataPoint[j][0])*(dataPoint[i][0]-dataPoint[j][0])+(dataPoint[i][1]-dataPoint[j][1])*(dataPoint[i][1]-dataPoint[j][1]));
                S[j][i] = S[i][j];
                tmpS.add(S[i][j]); 
            }
        }
        java.util.Collections.sort(tmpS);
	double median = 0;
        
	int size = N*(N-1)/2;
	if(size%2==0) 
		median = (tmpS.get(size/2)+tmpS.get(size/2-1))/2;
	else 
		median = tmpS.get(size/2);
	for(int i=0; i<N; i++) S[i][i] = median;
        return S;
    }
    
    public java.util.HashMap<Integer, String> getColorMap(java.util.HashSet<Integer> idSet) throws Exception {
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
        java.util.HashMap<Integer, String> colorMap = new java.util.HashMap<>();
        int maxCode = sortColorMap.size();
        int stepCode = maxCode/(idSet.size());
        int base = 0;
        
        for(Integer S:idSet){
            colorMap.put(S, sortColorMap.get(base));
            base+=stepCode;
        }
        return colorMap;
    }
    
}
