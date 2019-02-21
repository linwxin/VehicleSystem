package com.my.blog.website.controller.admin;

import com.github.pagehelper.PageInfo;
import com.my.blog.website.controller.BaseController;
import com.my.blog.website.dao.HistoryDataVoMapper;
import com.my.blog.website.model.Bo.RestResponseBo;
import com.my.blog.website.model.Vo.CommentVo;
import com.my.blog.website.model.Vo.CommentVoExample;
import com.my.blog.website.model.Vo.HistoryDataVo;
import com.my.blog.website.model.Vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 13 on 2017/2/26.
 */
@Controller
@RequestMapping("admin/comments")
public class CommentController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private HistoryDataVoMapper historyDataVoMapper;

    @GetMapping(value = "")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "15") int limit, HttpServletRequest request) {
        String start = (String) request.getParameter("start");
        String end = (String) request.getParameter("end");
        Map<String, String> condition = new HashMap<>();
        condition.put("startTime", start);
        condition.put("endTime", end);
        List<HistoryDataVo> historyDataVos = historyDataVoMapper.queryHistroyData(condition);
        request.setAttribute("historyData", historyDataVos);
        return "admin/comment_list";
    }


}
