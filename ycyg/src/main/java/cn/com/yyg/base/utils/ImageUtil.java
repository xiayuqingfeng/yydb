package cn.com.yyg.base.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;


/**
 * 图片处理
 * @author lvzf
 * 2015年10月27日 上午9:36:33
 */
public class ImageUtil {
	/**
	 * 生成验证码图片，并返回验证码字符串
	 * @param responseOutputStream
	 * @return 
	 * @throws IOException
	 */
 public static String createVerify(OutputStream responseOutputStream) throws IOException{
	  int width = 73; int height = 27;
	     BufferedImage image = new BufferedImage(width, height, 
	       1);
	 
	     Graphics g = image.getGraphics();
	 
	     Random random = new Random();
	 
	     g.setColor(getRandColor(200, 250));
	     g.fillRect(0, 0, width, height);
	 
	     g.setFont(new Font("Times New Roman", 0, 24));
	 
	     g.setColor(getRandColor(160, 200));
	     for (int i = 0; i < 155; i++) {
	       int x = random.nextInt(width);
	       int y = random.nextInt(height);
	       int xl = random.nextInt(12);
	       int yl = random.nextInt(12);
	       g.drawLine(x, y, x + xl, y + yl);
	     }	 
	     //生成4位数字
	     String sRand = "";
	     for (int i = 0; i < 4; i++) {
	       String rand =randomInt(1).toUpperCase();
	       sRand = sRand + rand;
	 
	       g.setColor(
	         new Color(20 + random.nextInt(110), 20 + random
	         .nextInt(110), 20 + random.nextInt(110)));
	       g.drawString(rand, 13 * i + 6, 24);
	     } 
	 
	     g.dispose();
	 
	     ImageIO.write(image, "JPEG", responseOutputStream);
	 
	     responseOutputStream.flush();
	     responseOutputStream.close();
	     return sRand;
 }
 private static Color getRandColor(int fc, int bc) {
     Random random = new Random();
     if (fc > 255)
       fc = 255;
     if (bc > 255)
       bc = 255;
     int r = fc + random.nextInt(bc - fc);
     int g = fc + random.nextInt(bc - fc);
     int b = fc + random.nextInt(bc - fc);
     return new Color(r, g, b);
   }
/* public static void createSmallImg(String sourceImg, String targetImg, int width)
 {
   try {
     Image image = ImageIO.read(new File(sourceImg));
     int imageWidth = image.getWidth(null);
     int imageHeight = image.getHeight(null);
//     float scale = getRatio(imageWidth, imageHeight, width, height);
     if(imageWidth<width){
        return;
     }
     imageHeight =  width * imageHeight / imageWidth;;
     imageWidth = width; 
     image = image.getScaledInstance(imageWidth, imageHeight, 
       16);

     BufferedImage mBufferedImage = new BufferedImage(imageWidth, 
       imageHeight, 1);
     Graphics2D g2 = mBufferedImage.createGraphics();

     g2.drawImage(image, 0, 0, imageWidth, imageHeight, Color.white, 
       null);
     g2.dispose();

     float[] kernelData2 = { -0.125F, -0.125F, -0.125F, -0.125F, 2.0F, 
       -0.125F, -0.125F, -0.125F, -0.125F };
     Kernel kernel = new Kernel(3, 3, kernelData2);
     ConvolveOp cOp = new ConvolveOp(kernel, 1, null);
     mBufferedImage = cOp.filter(mBufferedImage, null);
     FileOutputStream out = new FileOutputStream(targetImg);

     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
     encoder.encode(mBufferedImage);
     out.close();
   }
   catch (FileNotFoundException localFileNotFoundException)
   {
   }
   catch (IOException localIOException) {
   }
 }
 public static void main(String[] args)
 {
	 ImageUtil.createSmallImg("d:/max.png", "d:/small.png", 795);
 }*/
	public static final String randomInt(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters = "0123456789".toCharArray();

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}
}
