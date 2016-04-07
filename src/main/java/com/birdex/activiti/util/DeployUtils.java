package com.birdex.activiti.util;

import java.io.File;

public class DeployUtils {
	
	public static String[] list(){
		String basePath=DeployUtils.class.getResource("/").getPath();
		basePath=basePath.substring(1,basePath.length());
		return new File(basePath+File.separator+"diagrams").list();
	}

}
