package com.my.blog.website.dao;

import com.my.blog.website.model.Vo.HistoryDataVo;

import java.util.List;
import java.util.Map;


public interface HistoryDataVoMapper {
    List<HistoryDataVo> queryHistroyData(Map<String, String> condition);

    HistoryDataVo queryHistoryDataByDeviceId(String DeviceId);

    int insertHistoryData(HistoryDataVo historyData);

    int updateHistoryData(HistoryDataVo histroyData);

    int deleteHistoryDataById(String id);

}
