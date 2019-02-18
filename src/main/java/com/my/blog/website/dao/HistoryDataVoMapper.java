package com.my.blog.website.dao;

import com.my.blog.website.model.Vo.HistoryDataVo;

import java.util.List;


public interface HistoryDataVoMapper {
    List<HistoryDataVo> queryHistroyData();

    HistoryDataVo queryHistoryDataByDeviceId(String DeviceId);

    int insertHistoryData(HistoryDataVo historyData);

    int updateHistoryData(HistoryDataVo histroyData);

    int deleteHistoryDataById(String id);

}
