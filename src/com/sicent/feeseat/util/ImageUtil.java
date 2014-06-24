/**
 * 
 */
package com.sicent.feeseat.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sicent.feeseat.bean.CutImgFormBean;

/**
 * 
 * @author wangqiang
 * 
 */
public abstract class ImageUtil {

    private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 
     * @author wangqiang EMPTY_SOURCE:<br>
     *         EMPTY_DEST: 目标文件为空<br>
     *         SOURCE_NONEXIST: 源文件不存在<br>
     *         ERROR: 未知错误导致失败<br>
     *         SUCCESS: 成功<br>
     */
    public enum CUT_RESULT {
        EMPTY_SOURCE, EMPTY_DEST, SOURCE_NONEXIST, ERROR, SUCCESS
    };

    /**
     * 图片剪切
     * 
     * @param cutImageBean
     * @param isDelSource
     *            是否删除原文件
     * @return <code>CUT_RESULT</code>对象
     */
    public static CUT_RESULT cutImage(CutImgFormBean cutImgFormBean, boolean isDelSource) {
        try {
            String sourcePath = cutImgFormBean.getSource();
            String dest = cutImgFormBean.getDest();

            if (Util.isEmpty(sourcePath)) {
                return CUT_RESULT.EMPTY_SOURCE;// "{success:false,msg:\"源文件不能为空\"}";
            }

            if (Util.isEmpty(dest)) {
                return CUT_RESULT.EMPTY_DEST;// "{success:false,msg:\"目标文件不能为空\"}";
            }

            File source = new File(sourcePath);

            if (!source.isFile()) {
                // 源文件不是文件
                return CUT_RESULT.SOURCE_NONEXIST;// "{success:false,msg:\"源文件不存在[" + sourcePath +
                                                  // "]\"}";
            }

            File fileDest = new File(dest);

            // 创建文件
            if (!fileDest.getParentFile().exists()) {
                fileDest.getParentFile().mkdirs();
            }

            // 获取后缀名
            String ext = Util.getExtension(dest, "jpg").toLowerCase();

            BufferedImage bi = (BufferedImage) ImageIO.read(source);

            int height = Math.min(cutImgFormBean.getHeight(), bi.getHeight());
            int width = Math.min(cutImgFormBean.getWidth(), bi.getWidth());

            if (height <= 0) {
                height = bi.getHeight();
            }
            if (width <= 0) {
                width = bi.getWidth();
            }

            int top = Math.min(Math.max(0, cutImgFormBean.getTop()), bi.getHeight() - height);
            int left = Math.min(Math.max(0, cutImgFormBean.getLeft()), bi.getWidth() - width);

            BufferedImage bi_cropper = bi.getSubimage(left, top, width, height);

            boolean writeOk = ImageIO.write(bi_cropper, ext, fileDest);

            if (writeOk) {
                // cutResult = "{success:true,msg:\"\"}";
                if (isDelSource) {
                    source.delete();
                }
                return CUT_RESULT.SUCCESS;
            } else {
                // cutResult = "{success:false,msg:\"压缩失败\"}";
                return CUT_RESULT.ERROR;
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return CUT_RESULT.ERROR;
    }

    /**
     * 图片压缩
     * 
     * @param srcFilePath
     *            源文件路径
     * @param descFilePath
     *            图片压缩后保存路径
     * @param quality
     *            图片压缩质量（0~1范围）
     * @return
     */
    public static boolean compressPic(String srcFilePath, String descFilePath, float quality) {
        File file = null;
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;

        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);

        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality(quality);
        imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

        ColorModel colorModel = ColorModel.getRGBdefault();

        // 指定压缩时使用的色彩模式
        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
                colorModel.createCompatibleSampleModel(16, 16)));

        try {
            if (Util.isEmpty(srcFilePath)) {
                return false;
            } else {
                file = new File(srcFilePath);
                src = ImageIO.read(file);
                out = new FileOutputStream(descFilePath);

                imgWrier.reset();

                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何 OutputStream构造
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));

                // 调用write方法，就可以向输入流写图片
                imgWrier.write(null, new IIOImage(src, null, null), imgWriteParams);

                out.flush();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return true;
    }

    public static void main(String[] args) {

        CutImgFormBean cutImageBean = new CutImgFormBean();
        cutImageBean.setSource("C:\\Users\\wangqiang\\Desktop\\test_img_rar\\IMG_0969.JPG");
        cutImageBean.setDest("C:\\Users\\wangqiang\\Desktop\\test_img_rar\\IMG_0969_cut_tmp.JPG");
        cutImageBean.setHeight(2848);
        cutImageBean.setWidth(4272);
        cutImageBean.setTop(0);
        cutImageBean.setLeft(0);

        System.out.println(cutImage(cutImageBean, false));

        long start = System.currentTimeMillis();
        if (compressPic("C:\\Users\\wangqiang\\Desktop\\test_img_rar\\real.jpg",
                "C:\\Users\\wangqiang\\Desktop\\test_img_rar\\real-temp.jpg", .3F)) {
            log.info("压缩成功！");
        } else {
            log.info("压缩失败！");
        }
        log.info("耗时" + (System.currentTimeMillis() - start));
    }

}
