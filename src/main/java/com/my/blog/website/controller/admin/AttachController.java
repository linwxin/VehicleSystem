package com.my.blog.website.controller.admin;

import com.github.pagehelper.PageInfo;
import com.my.blog.website.constant.WebConst;
import com.my.blog.website.controller.BaseController;
import com.my.blog.website.dto.LogActions;
import com.my.blog.website.dto.Types;
import com.my.blog.website.model.Bo.RestResponseBo;
import com.my.blog.website.model.Vo.AttachVo;
import com.my.blog.website.model.Vo.UserVo;
import com.my.blog.website.service.IAttachService;
import com.my.blog.website.service.ILogService;
import com.my.blog.website.utils.Commons;
import com.my.blog.website.utils.TaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 附件管理
 * <p>
 * Created by 13 on 2017/2/21.
 */
@Controller
@RequestMapping("admin/attach")
public class AttachController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachController.class);

    public static final String CLASSPATH = TaleUtils.getUploadFilePath();

    @Resource
    private IAttachService attachService;

    @Resource
    private ILogService logService;

    /**
     * 附件页面
     *
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "")
    public String index(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "12") int limit) {
        PageInfo<AttachVo> attachPaginator = attachService.getAttachs(page, limit);
        request.setAttribute("attachs", attachPaginator);
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType(), Commons.site_url()));
        request.setAttribute("max_file_size", WebConst.MAX_FILE_SIZE / 1024);
        return "admin/attach";
    }

    /**
     * 上传文件接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public RestResponseBo upload(HttpServletRequest request, @RequestParam("file") MultipartFile[] multipartFiles) throws IOException {
        UserVo users = this.user(request);
        Integer uid = users.getUid();
        List<String> errorFiles = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                String fname = multipartFile.getOriginalFilename();
                if (multipartFile.getSize() <= WebConst.MAX_FILE_SIZE) {
                    String fkey = TaleUtils.getFileKey(fname);
                    String ftype = TaleUtils.isImage(multipartFile.getInputStream()) ? Types.IMAGE.getType() : Types.FILE.getType();
                    File file = new File(CLASSPATH + fkey);
                    Date dNow = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String fdate = ft.format(dNow);

                    try {
                        FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    attachService.save(fname, fkey, ftype, uid, fdate);
                } else {
                    errorFiles.add(fname);
                }
            }
        } catch (Exception e) {
            return RestResponseBo.fail();
        }
        return RestResponseBo.ok(errorFiles);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public RestResponseBo delete(@RequestParam Integer id, HttpServletRequest request) {
        try {
            AttachVo attach = attachService.selectById(id);
            if (null == attach) {
                return RestResponseBo.fail("不存在该附件");
            }
            attachService.deleteById(id);
            new File(CLASSPATH + attach.getFkey()).delete();
            logService.insertLog(LogActions.DEL_ARTICLE.getAction(), attach.getFkey(), request.getRemoteAddr(), this.getUid(request));
        } catch (Exception e) {
            String msg = "附件删除失败";
            LOGGER.error(msg, e);
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }

    @RequestMapping(value = "analysis")
    @ResponseBody
    public RestResponseBo analysis(@RequestParam Integer id,HttpServletRequest request) {
        try {
            AttachVo attach = attachService.selectById(id);
            if (null == attach) {
                return RestResponseBo.fail("不存在该附件");
            }
            String filePath = CLASSPATH + attach.getFkey();
            String host = "127.0.0.1";// 要连接的服务器IP
            int port = 8384;// 要连接的服务器端口
            Socket socket = new Socket(host, port);// 与服务器建立连接
            OutputStream outputStream = socket.getOutputStream();// 获取输入流
            socket.getOutputStream().write(filePath.getBytes("UTF-8"));
            socket.shutdownOutput();

//            InputStream inputStream = socket.getInputStream();
//            byte[] bytes = new byte[1024];
//            int len;
//            StringBuilder result = new StringBuilder();
//            while ((len = inputStream.read(bytes)) != -1) {
//                result.append(new String(bytes, 0, len, "UTF-8"));
//            }
//
//            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            String msg = "附件分析失败";
            LOGGER.error(msg, e);
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }

    @RequestMapping
    @ResponseBody
    public RestResponseBo exportData(@RequestParam Integer id, HttpServletRequest request) {


        return RestResponseBo.ok();
    }

}
