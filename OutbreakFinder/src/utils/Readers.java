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
public class Readers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
         new Readers().exampleForFastqIndex();
    }
    
    void exampleForFastqIndex() throws Exception {
//        FastqReader fqr = new FastqReader("C:\\tmp_for_molecular_typing\\data\\ngs\\SRR4424149_1.fastq");
        java.util.HashMap<String, String> nameSeqMap = new java.util.HashMap<>();
        FastqReaderWithIndex fqr = new FastqReaderWithIndex("C:\\tmp_for_molecular_typing\\data\\ngs\\SRR4424149_1.fastq");
        Triple<String, String, String> tp;
        int c=0;
        while((tp=fqr.getTuple())!=null){
            nameSeqMap.put(tp.first, tp.second);
            c++;
        }
        java.util.HashMap<String, long[]> namePosMap = fqr.namePosMap;
        fqr.close();
        System.out.println(c);
        FastqRandonReader fqr2 = new FastqRandonReader("C:\\tmp_for_molecular_typing\\data\\ngs\\SRR4424149_1.fastq");
        for(String sid:nameSeqMap.keySet()){
            long[] pos = namePosMap.get(sid);
            if(pos == null){
                System.out.println(sid);
                break;
            }
            tp = fqr2.getTuple(pos[0], (int)pos[1]);
            if(!tp.first.equals(sid)){
                System.out.println(sid+", "+tp.first);
                break;
            }
            if(!tp.second.equals(nameSeqMap.get(sid))){
                System.out.println(sid+", "+tp.second.length());
                break;
            }
            if(tp.second.length() != tp.third.length()){
                System.out.println(sid+"---><, "+tp.second.length());
                break;
            }
        }
        fqr.close();
    }
    
    public FastqReader createFastqReader(String fileName) throws Exception {
        return new FastqReader(fileName);
    }
    
    public FastaReader createFastaReader(String fileName) throws Exception{
        return new FastaReader(fileName);
    }
    
    //////////////////////////////////////////////////
    // FastqRandonReader
    //////////////////////////////////////////////////
    public class FastqRandonReader {
        
        java.io.RandomAccessFile in;
        
        public FastqRandonReader(String shortReadsFileNamePath) throws Exception {
            in = new java.io.RandomAccessFile(shortReadsFileNamePath, "r");
        }
        
        public synchronized Triple<String, String, String> getTuple(long pos, int len) throws Exception {
            byte b[] = new byte[len];
            in.seek(pos);
            in.read(b);
            String str = new String(b);
            int atPtr = str.indexOf("@");
            int begin = str.indexOf("\n", atPtr);
            int line2end = str.indexOf("\n", begin+1);
            int line3end = str.indexOf("\n", line2end+1);
            while( atPtr<0 || begin<0 || line2end<0 || line3end<0 ){
                return null;
            }
            String id = str.substring(atPtr+1, begin);
            String seq = str.substring(begin+1, line2end);
            String qstr = str.substring(line3end+1);
            return new Triple<>(id, seq, qstr);
        }
        
        public void close() throws Exception {
            in.close();
        }
        
    }
    
    //////////////////////////////////////////////////
    // FastqReaderWithIndex
    //////////////////////////////////////////////////
    public class FastqReaderWithIndex extends FastqReader {
        
        long base=0;
        public java.util.HashMap<String, long[]> namePosMap = new java.util.HashMap<>();
        
        public FastqReaderWithIndex(String shortReadsFileNamePath) throws Exception {
            super(shortReadsFileNamePath);
        }
        
        @Override
        public synchronized Triple<String, String, String> getTuple() throws Exception {
            atPtr = str.indexOf("@", end);
            begin = str.indexOf("\n", atPtr);
            line2end = str.indexOf("\n", begin+1);
            line3end = str.indexOf("\n", line2end+1);
            int tmpEnd = str.indexOf("\n", line3end+1);
            while( atPtr<0 || begin<0 || line2end<0 || line3end<0 || tmpEnd<0){
                if(end>0){
                    str.delete(0, end);
                    base+=end;
                    end = 0;
                }
                nextBuff();
                atPtr = str.indexOf("@");
                begin = str.indexOf("\n", atPtr);
                line2end = str.indexOf("\n", begin+1);
                line3end = str.indexOf("\n", line2end+1);
                tmpEnd = str.indexOf("\n", line3end+1);
                if(readLen<0){
                    if(atPtr>=0 && begin>0 && line2end>0 && line3end>0){
                        if(tmpEnd<0){
                            tmpEnd = str.length();
                        }
                        end = tmpEnd;
                        String id = str.substring(atPtr+1, begin);
                        String seq = str.substring(begin+1, line2end);
                        String qstr = str.substring(line3end+1, end);
                        namePosMap.put(id, new long[]{base+atPtr, end-atPtr});
                        return new Triple<>(id, seq, qstr);
                    }
                    return null;
                }
            }
            end = tmpEnd;
            String id = str.substring(atPtr+1, begin);
            String seq = str.substring(begin+1, line2end);
            String qstr = str.substring(line3end+1, end);
            namePosMap.put(id, new long[]{base+atPtr, end-atPtr});
            return new Triple<>(id, seq, qstr);
        }
        
    }
    
    //////////////////////////////////////////////////
    // FastqReader
    //////////////////////////////////////////////////
    public class FastqReader{
        
        int buferSize = 40960;
        java.io.InputStream in;
//        java.io.BufferedOutputStream readSidSeqPosFile;
        
        byte[] buf = new byte[buferSize];
        int readLen;
        StringBuilder str = new StringBuilder();
//        long pos=0;
        int atPtr;
        int begin;
        int line2end;
        int line3end;
        int end=0;// = 0;
//        int id=0;
        
        public FastqReader(String shortReadsFileNamePath) throws Exception {
            in = java.nio.file.Files.newInputStream(java.nio.file.Paths.get(shortReadsFileNamePath), java.nio.file.StandardOpenOption.READ);
        }
        
        void nextBuff() throws Exception {
            readLen = in.read(buf);
            if(readLen>=0){
//                str += new String(buf, 0, readLen);
                str.append(new String(buf, 0, readLen));
            }
        }
        
        public synchronized Triple<String, String, String> getTuple() throws Exception {
            atPtr = str.indexOf("@", end);
            begin = str.indexOf("\n", atPtr);
            line2end = str.indexOf("\n", begin+1);
            line3end = str.indexOf("\n", line2end+1);
            int tmpEnd = str.indexOf("\n", line3end+1);
            while( atPtr<0 || begin<0 || line2end<0 || line3end<0 || tmpEnd<0){
                if(end>0){
                    str.delete(0, end);
                    end = 0;
                }
                nextBuff();
                atPtr = str.indexOf("@");
                begin = str.indexOf("\n", atPtr);
                line2end = str.indexOf("\n", begin+1);
                line3end = str.indexOf("\n", line2end+1);
                tmpEnd = str.indexOf("\n", line3end+1);
                if(readLen<0){
                    if(atPtr>=0 && begin>0 && line2end>0 && line3end>0){
                        if(tmpEnd<0){
                            tmpEnd = str.length();
                        }
                        end = tmpEnd;
                        String id = str.substring(atPtr+1, begin);
                        String seq = str.substring(begin+1, line2end);
                        String qstr = str.substring(line3end+1, end);
                        return new Triple<>(id, seq, qstr);
                    }
                    return null;
                }
            }
            end = tmpEnd;
            String id = str.substring(atPtr+1, begin);
            String seq = str.substring(begin+1, line2end);
            String qstr = str.substring(line3end+1, end);
            return new Triple<>(id, seq, qstr);
        }
        
        public void close() throws Exception {
            in.close();
        }
    }
    
    public class Triple<K, V, W>{
        public K first;
        public V second;
        public W third;
        
        public Triple(K k, V v, W w){
            first = k;
            second = v;
            third = w;
        }
    }
    
    //////////////////////////////////////////////////
    // FastaReader
    //////////////////////////////////////////////////
    public class FastaReader{
        
        int buferSize = 40960;
        java.io.InputStream in;
        
        byte[] buf = new byte[buferSize];
        int readLen;
        StringBuilder str = new StringBuilder();
        int atPtr;
        int begin;
        int end=0;// = 0;
        
        public FastaReader(String shortReadsFileNamePath) throws Exception {
            in = java.nio.file.Files.newInputStream(java.nio.file.Paths.get(shortReadsFileNamePath), java.nio.file.StandardOpenOption.READ);
        }
        
        void nextBuff() throws Exception {
            readLen = in.read(buf);
            if(readLen>=0){
                str.append(new String(buf, 0, readLen));
            }
        }
        
        public synchronized Tuple<String, String> getTuple() throws Exception {
            atPtr = str.indexOf(">", end);
            begin = str.indexOf("\n", atPtr+1);
            int tmpEnd = str.indexOf(">", begin+1);
            while( atPtr<0 || begin<0 || tmpEnd<0){
                if(end>0){
                    str.delete(0, end);
                    end = 0;
                }
                nextBuff();
                atPtr = str.indexOf(">", end);
                begin = str.indexOf("\n", atPtr+1);
                tmpEnd = str.indexOf(">", begin+1);
                if(readLen<0){
                    if(atPtr>=0 && begin>=0 ){
                        if(tmpEnd<0){
                            tmpEnd = str.length();
                        }
                        end = tmpEnd;
                        String id = str.substring(atPtr+1, begin);
                        String seq = str.substring(begin+1, tmpEnd).replaceAll("\\s+", "");
                        return new Tuple<>(id, seq);
                    }
                    return null;
                }
            }
            end = tmpEnd;
            String id = str.substring(atPtr+1, begin);
            String seq = str.substring(begin+1, end-1).replaceAll("\\s+", "");
            return new Tuple<>(id, seq);
        }
        
        public void close() throws Exception {
            in.close();
        }
    }
    
    public class Tuple<K, V>{
        public K first;
        public V second;
        
        public Tuple(K k, V v){
            first = k;
            second = v;
        }
    }
    
}
