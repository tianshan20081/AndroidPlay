package com.aoeng.play.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author Shaocheng Zhang 
 * Aug 1, 2014  2:11:35 PM 2014
 */
public class IOUtils {

	public static boolean close(Closeable io) {
		// TODO Auto-generated method stub
		if (null != io) {
			try {
				io.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LogUtils.e(e);
			}
		}
		return true ;
	}


}
