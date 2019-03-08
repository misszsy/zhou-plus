package com.zhou.plus.admin.controller.upload;

import com.google.common.collect.Maps;
import com.zhou.plus.framework.config.Global;
import com.zhou.plus.framework.resp.R;
import com.zhou.plus.framework.ueditor.ActionEnter;
import com.zhou.plus.framework.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 统一上传文件接口
 */
@RestController
@RequestMapping(value = "/upload")
@Api(value = "UnifiedUploadController",description = "文件上传相关api")
public class UnifiedUploadController {

    /**
     * 上传文件
     * @return
     */
    @PostMapping(value = "action")
    @ApiOperation(value = "单个文件上传",notes = "根据file文件上传",httpMethod = "POST")
    @ApiImplicitParam(name = "file", value = "上传的文件名称",required = true, dataType = "MultipartFile")
    public R uploadFile(MultipartFile file){
        if(file==null && file.getSize()<=0){
            return R.fail("文件不能为空");
        }
        String filePath=FileUtils.uploadFile(file, Global.IMG_BASE_PATH);
        return R.ok(filePath);
    }

    /**
     * 百度编辑器上传文件
     * @return
     */
    @RequestMapping(value = "editorUpload")
    @ApiOperation(value = "编辑器文件上传",notes = "百度编辑器文件上传",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "upfile", value = "上传的文件名称",required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "action", value = "上传的文件类型",required = true, dataType = "String")
    })
    public Map<String,Object>  editorUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "upfile",required = false) MultipartFile file, String action) {
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            if ("config".equals(action)) {    //如果是初始化
                String exec = new ActionEnter(request, rootPath).exec();
                PrintWriter writer = response.getWriter();
                writer.write(exec);
                writer.flush();
                writer.close();
            } else if ("uploadimage".equals(action) || "uploadfile".equals(action)) {    //如果是上传图片、和其他文件
                String filePath = FileUtils.uploadFile(file, Global.IMG_BASE_EDITOR);
                Map<String, Object> map = Maps.newHashMap();
                map.put("state", "SUCCESS");
                map.put("original", file.getOriginalFilename());//原来的文件名
                map.put("title", file.getOriginalFilename());//随意，代表的是鼠标经过图片时显示的文字
                map.put("url",filePath);
                return map;
            }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
    }

}
