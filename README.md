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
Step 2. Issue following command to generate MDS figure:<br/>
#java -jar mds.jar matrix=path_to_matrix_file/simple_matrix.csv dist=path_to_dist

To generate MDS figure with colored data points, we just need to provide a color map file. The format of color map file is shown as following (tab-separated):<br/>

0	#00FF00<br/>
1	#00FF00<br/>
2	#00FF00<br/>
3	#00FF00<br/>
4	#00FF00<br/>
5	#FF0000<br/>
6	#FF0000<br/>
7	#FF0000<br/>
8	#FF0000<br/>
9	#FF0000<br/>
10	#FF0000<br/>

Save above color map as a file (named color.map), and issues following command to generate MDS figure:<br/>
#java -jar mds.jar matrix=path_to_matrix_file/simple_matrix.csv color=path_to_color_map/color.map dist=path_to_dist

**Example for generating MDS by using multiple alignment results (Clustal Omega)**
Before generating MDS figure, a multiple sequence alignment file must be prepared. We can issue following command to generating MDS figure (the multiple sequence alignment file named msa.ma):<br/>
#java -jar mds.jar mode=multiAlignment msa=path_to_msa_file/msa.ma matrix=path_to_matrix_file\simple_matrix.csv dist=path_to_dist

You just need to give a color map file to get a MDS graphic with colored data points, the command show as following:<br/>
#java -jar mds.jar mode=multiAlignment msa=path_to_msa_file/msa.ma matrix=path_to_matrix_file\simple_matrix.csv color=path_to_color_map\color.map dist=path_to_dist

**Example for generating MDS by using SNP distance matrix from lyve-set**
Before generating MDS graphic, a SNP distance matrix file must be prepared, the file name often should be out.pairwiseMatrix.tsv. We can issue following command to generating MDS plot:<br/>
#java -jar mds.jar mode=lyve-set pairwiseMatrix=path_to_SNP_file/out.pairwiseMatrix.tsv dist=path_to_dist

**Example for generating MDS by using multiple alignment result from Parsnp**
Before plotting MDS figure, a Parsnp multiple alignment file must be prepared (the file named parsnp.xmfa). We can issue following command to plot MDS figure: <br/>
#java -jar mds.jar mode=parsnp xmfa=path_to_parsnp_file/parsnp.xmfa dist=path_to_dist

**Affinity Propagation**
All of MDS coordinate file, file extension with coods, can be cluster by affinity propagation. We can issue following command to plot MDS figure, which clustered by affinity propagation:<br/>
#java -cp mds.jar utils.AffinityPropagation -label coods=path_to_coods dist=path_to_dist















<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>README</title>


<style type="text/css">
body {
  font-family: Helvetica, arial, sans-serif;
  font-size: 14px;
  line-height: 1.6;
  padding-top: 10px;
  padding-bottom: 10px;
  background-color: white;
  padding: 30px; }

body > *:first-child {
  margin-top: 0 !important; }
body > *:last-child {
  margin-bottom: 0 !important; }

a {
  color: #4183C4; }
a.absent {
  color: #cc0000; }
a.anchor {
  display: block;
  padding-left: 30px;
  margin-left: -30px;
  cursor: pointer;
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0; }

h1, h2, h3, h4, h5, h6 {
  margin: 20px 0 10px;
  padding: 0;
  font-weight: bold;
  -webkit-font-smoothing: antialiased;
  cursor: text;
  position: relative; }

h1:hover a.anchor, h2:hover a.anchor, h3:hover a.anchor, h4:hover a.anchor, h5:hover a.anchor, h6:hover a.anchor {
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA09pVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoMTMuMCAyMDEyMDMwNS5tLjQxNSAyMDEyLzAzLzA1OjIxOjAwOjAwKSAgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OUM2NjlDQjI4ODBGMTFFMTg1ODlEODNERDJBRjUwQTQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OUM2NjlDQjM4ODBGMTFFMTg1ODlEODNERDJBRjUwQTQiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo5QzY2OUNCMDg4MEYxMUUxODU4OUQ4M0REMkFGNTBBNCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5QzY2OUNCMTg4MEYxMUUxODU4OUQ4M0REMkFGNTBBNCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PsQhXeAAAABfSURBVHjaYvz//z8DJYCRUgMYQAbAMBQIAvEqkBQWXI6sHqwHiwG70TTBxGaiWwjCTGgOUgJiF1J8wMRAIUA34B4Q76HUBelAfJYSA0CuMIEaRP8wGIkGMA54bgQIMACAmkXJi0hKJQAAAABJRU5ErkJggg==) no-repeat 10px center;
  text-decoration: none; }

h1 tt, h1 code {
  font-size: inherit; }

h2 tt, h2 code {
  font-size: inherit; }

h3 tt, h3 code {
  font-size: inherit; }

h4 tt, h4 code {
  font-size: inherit; }

h5 tt, h5 code {
  font-size: inherit; }

h6 tt, h6 code {
  font-size: inherit; }

h1 {
  font-size: 28px;
  color: black; }

h2 {
  font-size: 24px;
  border-bottom: 1px solid #cccccc;
  color: black; }

h3 {
  font-size: 18px; }

h4 {
  font-size: 16px; }

h5 {
  font-size: 14px; }

h6 {
  color: #777777;
  font-size: 14px; }

p, blockquote, ul, ol, dl, li, table, pre {
  margin: 15px 0; }

hr {
  background: transparent url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAYAAAAECAYAAACtBE5DAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBNYWNpbnRvc2giIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OENDRjNBN0E2NTZBMTFFMEI3QjRBODM4NzJDMjlGNDgiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OENDRjNBN0I2NTZBMTFFMEI3QjRBODM4NzJDMjlGNDgiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4Q0NGM0E3ODY1NkExMUUwQjdCNEE4Mzg3MkMyOUY0OCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4Q0NGM0E3OTY1NkExMUUwQjdCNEE4Mzg3MkMyOUY0OCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PqqezsUAAAAfSURBVHjaYmRABcYwBiM2QSA4y4hNEKYDQxAEAAIMAHNGAzhkPOlYAAAAAElFTkSuQmCC) repeat-x 0 0;
  border: 0 none;
  color: #cccccc;
  height: 4px;
  padding: 0;
}

body > h2:first-child {
  margin-top: 0;
  padding-top: 0; }
body > h1:first-child {
  margin-top: 0;
  padding-top: 0; }
  body > h1:first-child + h2 {
    margin-top: 0;
    padding-top: 0; }
body > h3:first-child, body > h4:first-child, body > h5:first-child, body > h6:first-child {
  margin-top: 0;
  padding-top: 0; }

a:first-child h1, a:first-child h2, a:first-child h3, a:first-child h4, a:first-child h5, a:first-child h6 {
  margin-top: 0;
  padding-top: 0; }

h1 p, h2 p, h3 p, h4 p, h5 p, h6 p {
  margin-top: 0; }

li p.first {
  display: inline-block; }
li {
  margin: 0; }
ul, ol {
  padding-left: 30px; }

ul :first-child, ol :first-child {
  margin-top: 0; }

dl {
  padding: 0; }
  dl dt {
    font-size: 14px;
    font-weight: bold;
    font-style: italic;
    padding: 0;
    margin: 15px 0 5px; }
    dl dt:first-child {
      padding: 0; }
    dl dt > :first-child {
      margin-top: 0; }
    dl dt > :last-child {
      margin-bottom: 0; }
  dl dd {
    margin: 0 0 15px;
    padding: 0 15px; }
    dl dd > :first-child {
      margin-top: 0; }
    dl dd > :last-child {
      margin-bottom: 0; }

blockquote {
  border-left: 4px solid #dddddd;
  padding: 0 15px;
  color: #777777; }
  blockquote > :first-child {
    margin-top: 0; }
  blockquote > :last-child {
    margin-bottom: 0; }

table {
  padding: 0;border-collapse: collapse; }
  table tr {
    border-top: 1px solid #cccccc;
    background-color: white;
    margin: 0;
    padding: 0; }
    table tr:nth-child(2n) {
      background-color: #f8f8f8; }
    table tr th {
      font-weight: bold;
      border: 1px solid #cccccc;
      margin: 0;
      padding: 6px 13px; }
    table tr td {
      border: 1px solid #cccccc;
      margin: 0;
      padding: 6px 13px; }
    table tr th :first-child, table tr td :first-child {
      margin-top: 0; }
    table tr th :last-child, table tr td :last-child {
      margin-bottom: 0; }

img {
  max-width: 100%; }

span.frame {
  display: block;
  overflow: hidden; }
  span.frame > span {
    border: 1px solid #dddddd;
    display: block;
    float: left;
    overflow: hidden;
    margin: 13px 0 0;
    padding: 7px;
    width: auto; }
  span.frame span img {
    display: block;
    float: left; }
  span.frame span span {
    clear: both;
    color: #333333;
    display: block;
    padding: 5px 0 0; }
span.align-center {
  display: block;
  overflow: hidden;
  clear: both; }
  span.align-center > span {
    display: block;
    overflow: hidden;
    margin: 13px auto 0;
    text-align: center; }
  span.align-center span img {
    margin: 0 auto;
    text-align: center; }
span.align-right {
  display: block;
  overflow: hidden;
  clear: both; }
  span.align-right > span {
    display: block;
    overflow: hidden;
    margin: 13px 0 0;
    text-align: right; }
  span.align-right span img {
    margin: 0;
    text-align: right; }
span.float-left {
  display: block;
  margin-right: 13px;
  overflow: hidden;
  float: left; }
  span.float-left span {
    margin: 13px 0 0; }
span.float-right {
  display: block;
  margin-left: 13px;
  overflow: hidden;
  float: right; }
  span.float-right > span {
    display: block;
    overflow: hidden;
    margin: 13px auto 0;
    text-align: right; }

code, tt {
  margin: 0 2px;
  padding: 0 5px;
  white-space: nowrap;
  border: 1px solid #eaeaea;
  background-color: #f8f8f8;
  border-radius: 3px; }

pre code {
  margin: 0;
  padding: 0;
  white-space: pre;
  border: none;
  background: transparent; }

.highlight pre {
  background-color: #f8f8f8;
  border: 1px solid #cccccc;
  font-size: 13px;
  line-height: 19px;
  overflow: auto;
  padding: 6px 10px;
  border-radius: 3px; }

pre {
  background-color: #f8f8f8;
  border: 1px solid #cccccc;
  font-size: 13px;
  line-height: 19px;
  overflow: auto;
  padding: 6px 10px;
  border-radius: 3px; }
  pre code, pre tt {
    background-color: transparent;
    border: none; }

sup {
    font-size: 0.83em;
    vertical-align: super;
    line-height: 0;
}

kbd {
  display: inline-block;
  padding: 3px 5px;
  font-size: 11px;
  line-height: 10px;
  color: #555;
  vertical-align: middle;
  background-color: #fcfcfc;
  border: solid 1px #ccc;
  border-bottom-color: #bbb;
  border-radius: 3px;
  box-shadow: inset 0 -1px 0 #bbb
}

* {
	-webkit-print-color-adjust: exact;
}
@media screen and (min-width: 914px) {
    body {
        width: 854px;
        margin:0 auto;
    }
}
@media print {
	table, pre {
		page-break-inside: avoid;
	}
	pre {
		word-wrap: break-word;
	}
}
</style>


</head>

<body>

<h2 id="toc_0">travelTree-wgMLST download</h2>

<ol>
<li>Open Virtualization Format file: <a href="http://140.117.103.220/%7EtravelTree/travelTree-wgMLST.ova.zip">travelTree-wgMLST.ova.zip</a> (6 GB)</li>
<li>The source file: <a href="http://140.117.103.220/%7EtravelTree/travelTree-wgMLST.tgz">travelTree-wgMLST.tgz</a> (8 MB)</li>
<li>README file: <a href="http://140.117.103.220/%7EtravelTree/README.md">README.md</a> (5.2 K)</li>
<li>Results of <em>L. monocytogenes</em>: <a href="http://140.117.103.220/%7EtravelTree/L.monocytogenes.zip">L.monocytogenes.zip</a> (371 MB)</li>
<li>Results of <em>E. coli</em>: <a href="http://140.117.103.220/%7EtravelTree/E.coli.zip">E.coli.zip</a> (226 MB)</li>
</ol>

<h2 id="toc_1">How to run</h2>

<ol>
<li><p>run with VirtualBox or VMware<br>
Use VirtualBox (<a href="https://www.virtualbox.org" target="_blank">https://www.virtualbox.org</a>) or VMware (<a href="http://www.vmware.com" target="_blank">http://www.vmware.com</a>) to import &quot;travelTree-wgMLST.ova&quot;.<br>
The root password is &#39;rootwgmlst&#39;, the normal user (wgmlst) password is &#39;wgmlst&#39;.</p>

<div><pre><code class="language-none">$ unzip travelTree-wgMLST.ova.zip</code></pre></div>

<p>or</p></li>
<li><p>install travelTree-wgMLST (travelTree-wgMLST.tgz)<br>
To install all related tools, and use Perl and Python scripts to run.</p>

<h4 id="toc_2">Installation</h4>

<div><pre><code class="language-none">Use CentOS 7.4.1708 as OS to show how to install.

$ tar -vzxf travelTree-wgMLST.tgz
$ cd travelTree-wgMLST

* set PATH
    setenv PATH /YOUR_DIR/travelTree-wgMLST:/YOUR_DIR/travelTree-wgMLST/bin:$PATH</code></pre></div>

<h4 id="toc_3">Software required</h4>

<div><pre><code class="language-none">Following is a list of the softwares that you need:

* Perl v5.16.3 (https://www.perl.org)
* Python 2.7.5 (https://www.python.org)
* Prokka v1.13 (https://github.com/tseemann/prokka)
* Roary v3.12.0 (https://github.com/sanger-pathogens/Roary)
* R v3.5.0 (https://www.r-project.org)
* Fastx-Toolkit v0.0.14 (http://hannonlab.cshl.edu/fastx_toolkit/download.html)
* PHYLIP v3.696 (http://evolution.genetics.washington.edu/phylip.html)
* pip (https://pypi.org/project/pip/)
    $ wget https://bootstrap.pypa.io/get-pip.py
    $ python get-pip.py
* ETE3-Toolkit (http://etetoolkit.org)
    $ sudo pip install ete3
* scikit-learn (http://scikit-learn.org/stable/)
    $ sudo pip install -U scikit-learn
* pdftk
    $ sudo yum localinstall https://www.linuxglobal.com/static/blog/pdftk-2.02-1.el7.x86_64.rpm
* xvfb-run
    $ sudo yum install xorg-x11-server-Xvfb</code></pre></div></li>
</ol>

<h2 id="toc_4">Command details</h2>

<h3 id="toc_5">Run sample_run.sh</h3>

<div><pre><code class="language-none">$ sample_run.sh
    Usage:
        sample_run.sh input_contigs_dir output_dir number_of_threads
    Arguments:
        Input contig directory [String]
        Output directory [String]
        Number of threads [Integer]
    Example: 
        Perform sample run by using 12 threads
        sample_run.sh ./sample_run/contigFiles ./sample_run 12</code></pre></div>

<h3 id="toc_6">Run 1.Contig-Annotator.pl</h3>

<div><pre><code class="language-none">$ 1.Contig-Annotator.pl
    Usage:
        1.Contig-Annotator.pl -i input_dir -o output_dir [-p number_of_threads]
    Arguments:
        -i  Input contig directory [String]
        -o  Output annotated contig directory [String]
        -p  Number of threads [Integer]  Optional
          default = 1
    Example: 
        Generate annotated contig files using 12 threads
        1.Contig-Annotator.pl -i ./contigFiles -o ./1.contigAnn -p 12</code></pre></div>

<h3 id="toc_7">Run 2.PGAdb-Builder.pl</h3>

<div><pre><code class="language-none">$ 2.PGAdb-Builder.pl 
    Usage:
        2.PGAdb-Builder.pl -i input_dir -o output_dir [-m min_identity] [-p number_of_threads]
    Arguments:
        -i  Input cintig annotated directory [String]
        -o  Output PGAdb directory [String]
        -m  Minimum percentage identity for blastp [Integer]  Optional
          default = 95
        -p  Number of threads [Integer]  Optional
          default = 1
    Example: 
        Generate PGAdb using 12 threads
        2.PGAdb-Builder.pl -i ./1.contigAnn -o ./2.PGAdb -p 12</code></pre></div>

<h3 id="toc_8">Run 3.wgMLST-Profiler.pl</h3>

<div><pre><code class="language-none">$ 3.wgMLST-Profiler.pl 
    Usage:
    3.wgMLST-Profiler.pl -i input_dir -d profileDB_dir -o output_dir [-m align_identity] [-n align_coverage] [-p number_of_threads] [-s scheme_file]
    Arguments:
        -i  Input contig directory [String]
        -d  Input PGAdb directory [String]
        -o  Output wgProfiles directory [String]
        -m  Percentage of aligned identity for blastn [Integer]  Optional
          default = 90
        -n  Aligned coverage for blastn [Real]  Optional
          default = 0.9
        -p  Number of threads [Integer]  Optional
          default = 1
        -s  Selected scheme file [String]  Optional
    Example:
        Generate whole genome profiles using 12 threads
        3.wgMLST-Profiler.pl -i ./contigFiles -d ./2.PGAdb -o ./3.wgProfiles -p 12</code></pre></div>

<h3 id="toc_9">Run 4.Dendro-Plotter.pl</h3>

<div><pre><code class="language-none">$ 4.Dendro-Plotter.pl 
    Usage:
        4.Dendro-Plotter.pl -i input_dir -o output_dir [-s scheme_file]
    Arguments:
        -i  Input wgProfiles directory [String]
        -o  Output dendrogram directory [String]
        -s  Selected scheme file [String]  Optional
          default = &#39;input_dir/scheme/core.scheme&#39;
    Example:
        Dendro-Plotter with core scheme
        4.Dendro-Plotter.pl -i ./3.wgProfiles -o ./4.DendroPlot</code></pre></div>

<h3 id="toc_10">Run 5.Loci-Extractor.pl</h3>

<div><pre><code class="language-none">$ 5.Loci-Extractor.pl
    Usage:
        5.Loci-Extractor.pl -i input_dir -o output_dir -s scheme_file [-t top_threshold]
    Arguments:
        -i  Input dendrogram directory [String]
        -o  Output extracted loci directory [String]
        -s  Selected scheme file [String]  Optional
          default = &#39;input_dir/core.scheme&#39;
        -t  Top threshold [Integer]  Optional
          default = 5
    Example: 
        Loci-Extractor
        5.Loci-Extractor.pl -i ./4.DendroPlot -o ./5.ExtractedLoci </code></pre></div>

<div><pre><code class="language-none">Date: 2018/11/01  
Author: Yen-Yi Liu (current788@nhri.org.tw),
        Ji-Wei Lin (jwlin@imst.nsysu.edu.tw),
        Chih-Chieh Chen (chieh@imst.nsysu.edu.tw)</code></pre></div>




</body>

</html>



