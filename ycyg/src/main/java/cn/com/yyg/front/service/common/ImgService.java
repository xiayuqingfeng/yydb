package cn.com.yyg.front.service.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 图片服务
 * 
 * @author nibili 2015年1月11日上午11:12:08
 * 
 */
@Service
public class ImgService {

	/** 文件存放路径 */
	@Value("${file.rootpath}"+"upload")
	private String filePath;

	/**
	 * 读取图片
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 * @auth nibili 2015年1月11日 上午11:13:24
	 */
	public byte[] get(String filename) throws IOException {
		String path = getFilePath() + filename;
		return FileUtils.readFileToByteArray(FileUtils.getFile(path));
	}

	/**
	 * 获取存放图片的文件目录
	 * 
	 * @return
	 * @auth nibili 2015年1月30日 上午11:32:08
	 */
	public String getFilePath() {
		// 配置的为空，则目录位置创建到项目路径的兄弟路径里
		if (StringUtils.isBlank(filePath) == true) {
			String classLoaderPath = ImgService.class.getResource("/").getPath();
			File file = new File(classLoaderPath);
			file = file.getParentFile().getParentFile().getParentFile();
			filePath = file.getAbsolutePath() + "/";
		}
		return filePath;

	}

	public static void main(String[] args) {
		ImgService imgService = new ImgService();
		System.out.println(imgService.getFilePath());
	}

}
