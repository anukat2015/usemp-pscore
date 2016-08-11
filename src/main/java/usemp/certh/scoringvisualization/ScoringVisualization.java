/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usemp.certh.scoringvisualization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import usemp.certh.main.Main;

/**
 *
 * @author gpetkos
 * 
 * This class just prepares and copies the files for the visualization.
 * 
 */
public class ScoringVisualization {
    public void copyVisualizationFiles(String targetDir){
        URL url = this.getClass().getClassLoader().getResource("");
        File resourcesFile = null;
        String resourcesDir=null;
        try {
            resourcesFile = new File(url.toURI());
        } catch (URISyntaxException e) {
            resourcesFile = new File(url.getPath());
        } finally {
            resourcesDir=resourcesFile.getAbsolutePath();
            if((!resourcesDir.endsWith("/"))&&(!resourcesDir.endsWith("\\")))
                resourcesDir=resourcesDir+"/";
        }
        
        try {
            FileUtils.copyDirectoryToDirectory(new File(resourcesDir+"visualization/"), new File(targetDir));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        String fileOut=targetDir+"visualization/USEMP_files/plot.js";
        String fileHead=targetDir+"visualization/USEMP_files/plot_head";
        String fileJson=targetDir+"myScores.json";
        String fileTail=targetDir+"visualization/USEMP_files/plot_tail";
        
        try{
            BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileOut), "UTF8"));
            
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileHead), "UTF8"));
            String line=null;
            while((line=br.readLine())!=null){
                bw.append(line);
                bw.newLine();
            }
            br.close();
            
            br = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileJson), "UTF8"));
            line=br.readLine();
            br.close();
            String escaped=StringEscapeUtils.escapeJavaScript(line);
            bw.append("'{\"data\":");
            bw.append(escaped);
            bw.append("}';");
            bw.newLine();
            br.close();
            
            br = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileTail), "UTF8"));
            line=null;
            while((line=br.readLine())!=null){
                bw.append(line);
                bw.newLine();
            }
            br.close();
            
            bw.close();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
        
    }
}
