package com.bfd.smartqa.etl.tool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.bfd.smartqa.etl.GlobalHolder;
import com.bfd.smartqa.etl.biz.SuperTagFetcher;
import com.bfd.smartqa.etl.biz.TagFetcher;
import com.bfd.smartqa.etl.dao.po.SuperTag;
import com.bfd.smartqa.etl.dao.po.Tag;
import com.bfd.smartqa.etl.exception.TagNotFoundException;

public class SuperTagLoader {
	
	private TagFetcher tFetcher;
	private SuperTagFetcher stFetcher;
	
	private int superIdCnt = 1;
	
	public SuperTagLoader() {
		tFetcher = new TagFetcher();
		stFetcher = new SuperTagFetcher();
	}
	
	public void free() {
		tFetcher.free();
		stFetcher.free();
	}

	public List<List<String>> readFile() {
		String name = "F:/kaige.txt";
		try {
			FileReader fr = new FileReader(name);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String[] cols;
			
			while ((line = br.readLine()) != null) {
				cols = line.split(",");
//				for (String i : cols) {
////					System.out.println(i);
//					Tag tag = new Tag();
//					tag.setTag(i);
//					try {
//						tFetcher.getTagByName(i);
//					} catch (TagNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						tFetcher.addTag(tag);
//					}
//					
//				}
				for (String i : cols) {
					try {
						System.out.println(i);
						Tag tag = tFetcher.getTagByName(i);
						int tagId = tag.getID();
						SuperTag superTag = new SuperTag();
						superTag.setSuperID(superIdCnt);
						superTag.setTagID(tagId);
						stFetcher.addSuperTag(superTag);
					} catch (TagNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				// next superid
				this.superIdCnt++;
				System.out.println("----cut-------");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		GlobalHolder.init();
		SuperTagLoader demo = new SuperTagLoader();
		demo.readFile();
		demo.free();
	}
}
