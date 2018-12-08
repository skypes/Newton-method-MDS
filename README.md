# Manual for Multidimensional Scaling Viewer
MultiDimensional Scaling using Newton's method

**Environmental Requirement**
Jdk 1.8 or later

**Command Line Usage**

**A Simple Example**
The following is a simple example to generate MDS figure by using a distance matrix file. The format of matrix file shows as following:

,0,1,2,3,4,5,6,7,8,9,10<br/>
0,0,1,6,9,9,36,38,32,33,38,45<br/>
1,1,0,4,9,7,38,34,15,36,41,38<br/>
2,6,4,0,3,7,37,38,39,20,41,34<br/>
3,9,9,3,0,6,35,20,33,38,32,35<br/>
4,9,7,7,6,0,34,38,36,37,31,36<br/>
5,36,38,37,35,34,0,3,6,8,7,9<br/>
6,38,34,38,20,38,3,0,4,6,8,9<br/>
7,32,15,39,33,36,6,4,0,6,9,9<br/>
8,33,36,20,38,37,8,6,6,0,3,4<br/>
9,38,41,41,32,31,7,8,9,3,0,2<br/>
10,45,38,34,35,36,9,9,9,4,2,0<br/>

Note: The distance matrix must be save as a csv file format.

Two steps to generate MDS figure:
Step 1. Create a distance matrix, named simple_ matrix.csv, using above distance data.
Step 2. Issue following command to generate MDS figure:
#java -jar mds.jar matrix=path_to_matrix_file/simple_matrix.csv dist=path_to_dist

To generate MDS figure with colored data points, we just need to provide a color map file. The format of color map file is shown as following (tab-separated):

\x24\$aaaaaaaaaaaa
0\\t#00FF00<br/>
1\0x09#00FF00<br/>
2(0x09)#00FF00<br/>
3	#00FF00<br/>
4	#00FF00<br/>
5	#FF0000<br/>
6	#FF0000<br/>
7	#FF0000<br/>
8	#FF0000<br/>
9	#FF0000<br/>
10	#FF0000<br/>

Save above color map as a file (named color.map), and issues following command to generate MDS figure:

#java -jar mds.jar matrix=path_to_matrix_file/simple_matrix.csv color=path_to_color_map/color.map dist=path_to_dist

**Example for generating MDS by using multiple alignment results (Clustal Omega)**
Before generating MDS figure, a multiple sequence alignment file must be prepared. We can issue following command to generating MDS figure (the multiple sequence alignment file named msa.ma):

#java -jar mds.jar mode=multiAlignment msa=path_to_msa_file/msa.ma matrix=path_to_matrix_file\simple_matrix.csv dist=path_to_dist

You just need to give a color map file to get a MDS graphic with colored data points, the command show as following:

#java -jar mds.jar mode=multiAlignment msa=path_to_msa_file/msa.ma matrix=path_to_matrix_file\simple_matrix.csv color=path_to_color_map\color.map dist=path_to_dist

**Example for generating MDS by using SNP distance matrix from lyve-set**
Before generating MDS graphic, a SNP distance matrix file must be prepared, the file name often should be out.pairwiseMatrix.tsv. We can issue following command to generating MDS plot:

#java -jar mds.jar mode=lyve-set pairwiseMatrix=path_to_SNP_file/out.pairwiseMatrix.tsv dist=path_to_dist

**Example for generating MDS by using multiple alignment result from Parsnp**
Before plotting MDS figure, a Parsnp multiple alignment file must be prepared (the file named parsnp.xmfa). We can issue following command to plot MDS figure: 

#java -jar mds.jar mode=parsnp xmfa=path_to_parsnp_file/parsnp.xmfa dist=path_to_dist

**Affinity Propagation**
All of MDS coordinate file, file extension with coods, can be cluster by affinity propagation. We can issue following command to plot MDS figure, which clustered by affinity propagation:
#java -cp mds.jar utils.AffinityPropagation -label coods=path_to_coods dist=path_to_dist


