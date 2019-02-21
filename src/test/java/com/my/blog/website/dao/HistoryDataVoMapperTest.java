package com.my.blog.website.dao;

import com.my.blog.website.model.Vo.HistoryDataVo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryDataVoMapperTest {

    @Autowired
    private  HistoryDataVoMapper historyDataVoMapper;

    @Test
    @Ignore
    public void queryHistroyData() {
        Map<String, String> condition = new HashMap<>();
        condition.put("startTime", "2019-02-01");
        condition.put("endTime", "2019-02-22");
        List<HistoryDataVo> historyDataVos = historyDataVoMapper.queryHistroyData(condition);
        System.out.println(historyDataVos.size());
        HistoryDataVo historyDataVo = historyDataVos.get(0);
        System.out.println(historyDataVo.getId() + " " + historyDataVo.getVehicleType());
    }
    @Test
    @Ignore
    public void queryHistoryDataByDeviceId() {
        HistoryDataVo historyDataVo = historyDataVoMapper.queryHistoryDataByDeviceId("444");
        assertEquals("校门口", historyDataVo.getLocation());
    }

    @Test
    @Ignore
    public void insertHistoryData() {
        HistoryDataVo historyDataVo = new HistoryDataVo();
        historyDataVo.setId("555");
        historyDataVo.setDate("2019-02-18");
        historyDataVo.setLocation("榕树下");
        historyDataVo.setVehicleType("货车");
        int effectedNum = historyDataVoMapper.insertHistoryData(historyDataVo);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void updateHistoryData() {
        HistoryDataVo historyDataVo = new HistoryDataVo();
        historyDataVo.setId("666");
        historyDataVo.setDate("2019-02-11");
        int effectedNum = historyDataVoMapper.updateHistoryData(historyDataVo);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void deleteHistoryDataById() {
        int effectedNum = historyDataVoMapper.deleteHistoryDataById("555");
        assertEquals(1, effectedNum);
    }
}