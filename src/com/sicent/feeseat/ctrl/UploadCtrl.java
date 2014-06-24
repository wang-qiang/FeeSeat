/**
 * 
 */
package com.sicent.feeseat.ctrl;

import java.io.File;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sicent.feeseat.bean.CutImgFormBean;
import com.sicent.feeseat.util.ImageUtil;
import com.sicent.feeseat.util.ImageUtil.CUT_RESULT;
import com.sicent.feeseat.util.Util;

/**
 * 
 * @author wangqiang
 * 
 */
@Controller
public class UploadCtrl {

    private static final Logger log = LoggerFactory.getLogger(UploadCtrl.class);

    private static final Properties config = Util.getProperties("config.properties");

    @RequestMapping("upload")
    @ResponseBody
    public Object upload(
            @RequestParam(value = "fileToUpload", required = false) MultipartFile file,
            HttpServletResponse response) {
        Util.setHeaderType(response, "", "");
        // 最大上传限制：5M
        int maxSize = 5;
        try {
            maxSize = Integer.valueOf(config.getProperty("image_max_size", "5"));
        } catch (NumberFormatException e) {
            log.error("配置文件config.properties中，image_max_size值配置错误！");
        }

        if (file != null && file.getSize() > maxSize * 1024 * 1024) {
            return "{\"success\":false,\"msg\":\"上传图片不能超过" + maxSize + "M\"}";
        }
        String path = config.getProperty("image_upload_path");

        // 原始文件名
        String fileName = file.getOriginalFilename();

        if (Util.isEmpty(fileName)) {
            return "{\"success\":false,\"msg\":\"请选择上传图片\"}";
        }

        String ext = Util.getExtension(fileName, "jpg");

        fileName = System.currentTimeMillis() + "." + ext;

        File targetFile = new File(path, fileName);

        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        // 保存
        try {
            file.transferTo(targetFile);

            if (targetFile.isFile()) {

                String absolutePath = targetFile.getAbsolutePath();
                String savePath = Util.getFilePathOnly(absolutePath) + "_temp." + ext;

                // 压缩上传图片
                boolean compressPic = ImageUtil.compressPic(absolutePath, savePath,
                        Float.valueOf(config.getProperty("image_comp_ratio", "0.3")));

                // 如果压缩成功，则删除原文件，返回压缩文件；
                // 如果压缩失败，则直接返回源文件
                if (compressPic) {
                    targetFile.delete();
                    return "{\"success\":true,\"msg\":\"" + config.getProperty("image_domain")
                            + Util.getFileNameAndExt(savePath) + "\"}";
                } else {
                    return "{\"success\":true,\"msg\":\"" + config.getProperty("image_domain")
                            + Util.getFileNameAndExt(absolutePath) + "\"}";
                }

            }
        } catch (Exception e) {
            log.error("上传图片失败" + e.getMessage());
        }

        return "{success:false,msg:\"上传图片失败\"}";
    }

    @RequestMapping(value = "cutimg", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@Valid CutImgFormBean cutImgFormBean, BindingResult result,
            HttpServletResponse response) {
        Util.setHeaderType(response, "", "");
        if (result.hasErrors()) {
            return "{\"success\":false,\"msg\":\"" + result.getAllErrors().toString() + "\"}";
        }

        // 获取保存路径
        String path = config.getProperty("image_upload_path");

        // 得到源文件
        String source = cutImgFormBean.getSource();

        if (Util.isEmpty(source)) {
            return "{\"success\":false,\"msg\":\"源文件不存在，裁剪失败\"}";
        }

        String destFile = "aftercut_" + System.currentTimeMillis() + "."
                + Util.getExtension(source, ".jpg");

        // 设置文件路径映射
        cutImgFormBean.setSource(path + Util.getFileNameAndExt(source));
        cutImgFormBean.setDest(path + destFile);

        // 裁剪
        // 如果裁剪成功，裁剪过程会根据第二个参数（true）自动删除源文件，并将裁剪后的文件保存为源文件名
        CUT_RESULT cutResult = ImageUtil.cutImage(cutImgFormBean, true);

        String backJson = "{\"success\":false,\"msg\":\"裁剪失败\"}";

        if (cutResult == CUT_RESULT.SUCCESS) {

            backJson = "{\"success\":true,\"msg\":\"" + config.getProperty("image_domain")
                    + destFile + "\"}";
        }

        return backJson;
    }
}
