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
import org.springframework.web.bind.annotation.PathVariable;
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

    private static final Properties config = Util.getProperties("config/config.properties");

    /**
     * 文件上传控制类
     * 
     * @param type
     *            上传文件类型：文件（file）、图片（image）<br>
     *            格式限制请在config.properties中配置
     * @param file
     *            上传文件
     * @param response
     *            <code>HttpServletResponse</code>对象
     * @return
     */
    @RequestMapping("upload/{type}")
    @ResponseBody
    public Object upload(@PathVariable String type,
            @RequestParam(value = "fileToUpload", required = false) MultipartFile file,
            HttpServletResponse response) {

        // 需增加上传权限判断，只能拥有网吧账号和管理员的用户上传

        Util.setHeaderType(response, "", "");
        boolean isImage = false;
        String fileExtLimit = "";

        String path = "";

        if ("file".equalsIgnoreCase(type)) {
            fileExtLimit = config.getProperty("file_upload_type", "zip,rar");
            path = config.getProperty("file_upload_path");
        } else {
            isImage = true;
            fileExtLimit = config.getProperty("image_upload_type", "jpg,jpeg,png,bmp");
            path = config.getProperty("image_upload_path");
        }

        // 原始文件名
        String fileName = file.getOriginalFilename();

        if (Util.isEmpty(fileName)) {
            return "{\"success\":false,\"msg\":\"请选择上传文件\"}";
        }

        String ext = Util.getExtension(fileName, "jpg");
        if (!fileExtLimit.contains(ext)) {
            return "{\"success\":false,\"msg\":\"请上传[" + fileExtLimit + "]格式文件\"}";
        }

        // 最大上传限制：5M
        int maxSize = 5;
        try {
            maxSize = Integer.valueOf(config.getProperty("max_upload_size", "5"));
        } catch (NumberFormatException e) {
            log.error("配置文件config.properties中，image_max_size值配置错误！");
        }

        if (file != null && file.getSize() > maxSize * 1024 * 1024) {
            return "{\"success\":false,\"msg\":\"上传文件不能超过" + maxSize + "M\"}";
        }

        fileName = System.currentTimeMillis() + "." + ext;

        File targetFile = new File(path, fileName);

        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String errMsg = "";
        // 保存
        try {
            file.transferTo(targetFile);
            String absolutePath = targetFile.getAbsolutePath();

            if (targetFile.isFile() && isImage) {
                String savePath = Util.getFilePathOnly(absolutePath) + "_temp." + ext;

                // 压缩上传图片
                boolean compressPic = ImageUtil.compressPic(absolutePath, savePath,
                        Float.valueOf(config.getProperty("image_comp_ratio", "0.2")));

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

            } else {
                return "{\"success\":true,\"msg\":\"" + config.getProperty("file_domain")
                        + Util.getFileNameAndExt(absolutePath) + "\"}";
            }

        } catch (Exception e) {
            log.error("上传失败" + e.getMessage());
            errMsg = e.getMessage();
        }

        return "{success:false,msg:\"上传失败：" + errMsg + "\"}";
    }

    /**
     * 图片裁剪接口
     * 
     * @param cutImgFormBean
     *            图片裁剪类，详细请参见<code>cutImgFormBean</code>
     * @param result
     *            绑定前端传递的对象验证结果
     * @param response
     *            <code>HttpServletResponse</code>对象
     * @return json成功or失败
     */
    @Deprecated
    @SuppressWarnings("unused")
    @RequestMapping(value = "cutimg", method = RequestMethod.POST)
    @ResponseBody
    public Object cutImg(@Valid CutImgFormBean cutImgFormBean, BindingResult result,
            HttpServletResponse response) {

        if (true) {
            return "{\"success\":false,\"msg\":\"裁剪接口已关闭\"}";
        }

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
