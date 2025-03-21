package com.tomo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.tomo.feign.DefiLamaClient;
import com.tomo.feign.OKXClient;
import com.tomo.feign.RangoClient;
import com.tomo.model.dto.RangoIconDto;
import com.tomo.model.resp.OKXResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class DownloadIconController {
    @Autowired
    private DefiLamaClient defiLamaClient;

    @Autowired
    private RangoClient rangoClient;



    @RequestMapping(value = "/v1/icon", method = RequestMethod.GET)
    public String icon() {
        String fileContent = FileUtil.readUtf8String("/Users/bob/Documents/token2/token-platform-service/src/main/resources/dex-name.json");
        List<String> list = JSONUtil.toList(fileContent, String.class);
        List<RangoIconDto> iconDtos= rangoClient.queryIcon();

        for (RangoIconDto iconDto : iconDtos) {
            String dexName = iconDto.getId();
            if(list.contains(dexName)){
                continue;
            }
            list.add(dexName);
            // 图片的 URL
            String imageUrl = iconDto.getLogo() ;
            String imageName = iconDto.getId().replace(" ", "_");
            // 保存图片的本地文件路径
            String savePath = "/Users/bob/Downloads/rango-icon";
            String fullPath = savePath+"/"+imageName+".svg";

            try {
                // 使用 HttpUtil 下载图片
                HttpUtil.downloadFile(imageUrl, fullPath);
            } catch (Exception e) {
                log.error("下载图片失败: " + imageName);
                log.error(e.getMessage());
            }
        }

        return JSONUtil.toJsonStr(list);
    }
}
