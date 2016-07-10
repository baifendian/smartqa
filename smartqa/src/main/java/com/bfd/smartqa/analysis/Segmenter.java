package com.bfd.smartqa.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.bfd.smartqa.GlobalHolder;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.WordDictionary;

public class Segmenter {
	
	private JiebaSegmenter segmenter = null;
	private HashSet<String> stopwords = null;
	
	public Segmenter(Path configPath,Path stopworddict)
	{
		WordDictionary.getInstance().init(configPath);
		this.segmenter = new JiebaSegmenter();
		
		this.stopwords = new HashSet<String>();
		this.loadStopWords(stopworddict);
		
	}
	
	public List<String> process(String subject)
	{
		List<String> result = new ArrayList<String>();
		List<SegToken> segTokens = segmenter.process(subject, SegMode.SEARCH);
		for(SegToken item : segTokens)
		{
			if(this.stopwords.contains(item.word))
			{
				continue;
			}
			//System.out.println(item.word);
			result.add(item.word);
		}
		return result;
	}
	
	private void loadStopWords(Path stopworddict) 
	{
		BufferedReader br;
		try {
			br = Files.newBufferedReader(stopworddict, StandardCharsets.UTF_8);
			while (br.ready()) {
	            String line = br.readLine();
	            this.stopwords.add(line);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			GlobalHolder.getLogger().error(e.getMessage());
		}
        
	}

	
	
	
	

}
