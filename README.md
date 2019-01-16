<h2>Manual for OutbreakFinder</h2>
OutbreakFinder is written in Java and compiled into a standalone executable jar file, which can be executed in the Java Runtime Environment 1.8 or later version. Users can also download the source code and compile it into a preferred version. OutbreakFinder can be freely downloaded from https://github.com/skypes/Newton-method-MDS

<h2>Environmental Requirement</h2>
Jdk 1.8 or later

<h2>Quick Start</h2>
There are four examples in the examples directory, such as example_lyve-set, example_mafft, example_parsnp and example_simple. Users can easily modify these examples to suit their needs. These examples can be executed using following commands:
<pre>java -jar OutbreakFinder.jar parameters.txt</pre>

<h2>How to run</h2>
<ol>
<li><p>A Simple Example<br>
The following is a simple example to generate MDS figure by using a distance matrix file. The format of matrix file shows as following:

<pre>,0,1,2,3,4,5,6,7,8,9,10
0,0,1,6,9,9,36,38,32,33,38,45
1,1,0,4,9,7,38,34,15,36,41,38
2,6,4,0,3,7,37,38,39,20,41,34
3,9,9,3,0,6,35,20,33,38,32,35
4,9,7,7,6,0,34,38,36,37,31,36
5,36,38,37,35,34,0,3,6,8,7,9
6,38,34,38,20,38,3,0,4,6,8,9
7,32,15,39,33,36,6,4,0,6,9,9
8,33,36,20,38,37,8,6,6,0,3,4
9,38,41,41,32,31,7,8,9,3,0,2
10,45,38,34,35,36,9,9,9,4,2,0</pre>

Note: The distance matrix must be save as a csv file format.<br/>

Two steps to generate MDS figure:<br/>
Step 1. Create a distance matrix, named simple_ matrix.csv, using above distance data.<br/>
Step 2. Issue following command to generate MDS figure:<br/>
<pre>java -jar OutbreakFinder.jar matrix=path_to_matrix_file/simple_matrix.csv dist=path_to_dist</pre>

To generate MDS figure with colored data points, we just need to provide a color map file. The format of color map file is shown as following (tab-separated):<br/>
<pre>
0	#00FF00
1	#00FF00
2	#00FF00
3	#00FF00
4	#00FF00
5	#FF0000
6	#FF0000
7	#FF0000
8	#FF0000
9	#FF0000
10	#FF0000
</pre>

Save above color map as a file (named color.map), and issues following command to generate MDS figure:<br/>
<pre>java -jar OutbreakFinder.jar matrix=path_to_matrix_file/simple_matrix.csv color=path_to_color_map/color.map dist=path_to_dist</pre>

<li><p>Example for generating MDS by using multiple alignment results (Clustal Omega)<br>
Before generating MDS figure, a multiple sequence alignment file must be prepared. We can issue following command to generating MDS figure (the multiple sequence alignment file named msa.ma):<br/>
<pre>java -jar OutbreakFinder.jar mode=multiAlignment msa=path_to_msa_file/msa.ma matrix=path_to_matrix_file\simple_matrix.csv dist=path_to_dist</pre>

You just need to give a color map file to get a MDS graphic with colored data points, the command show as following:<br/>
<pre>java -jar OutbreakFinder.jar mode=multiAlignment msa=path_to_msa_file/msa.ma matrix=path_to_matrix_file\simple_matrix.csv color=path_to_color_map\color.map dist=path_to_dist</pre>

<li><p>Example for generating MDS by using SNP distance matrix from lyve-set<br/>
Before generating MDS graphic, a SNP distance matrix file must be prepared, the file name often should be out.pairwiseMatrix.tsv. We can issue following command to generating MDS plot:<br/>
<pre>java -jar OutbreakFinder.jar mode=lyve-set pairwiseMatrix=path_to_SNP_file/out.pairwiseMatrix.tsv dist=path_to_dist</pre>

<li><p>Example for generating MDS by using multiple alignment result from Parsnp<br/>
Before plotting MDS figure, a Parsnp multiple alignment file must be prepared (the file named parsnp.xmfa). We can issue following command to plot MDS figure: <br/>
  <pre>java -jar OutbreakFinder.jar mode=parsnp xmfa=path_to_parsnp_file/parsnp.xmfa dist=path_to_dist</pre>

<li><p>Affinity Propagation<br/>
All of MDS coordinate file, file extension with coods, can be cluster by affinity propagation. We can issue following command to plot MDS figure, which clustered by affinity propagation:<br/>
<pre>java -cp OutbreakFinder.jar utils.AffinityPropagation -label coods=path_to_coods dist=path_to_dist</pre>


