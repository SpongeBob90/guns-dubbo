package com.stylefeng.guns.film.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.Banner;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.film.common.persistence.dao.MoocBannerTMapper;
import com.stylefeng.guns.film.common.persistence.dao.MoocFilmTMapper;
import com.stylefeng.guns.film.common.persistence.model.MoocBannerT;
import com.stylefeng.guns.film.common.persistence.model.MoocFilmT;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : wyw 2018-12-27 13:30
 **/
@Component
@Service(interfaceClass = FilmServiceAPI.class, loadbalance = "roundrobin")
public class DefaultFilmServiceImpl implements FilmServiceAPI{

    @Resource
    private MoocBannerTMapper moocBannerTMapper;

    @Resource
    private MoocFilmTMapper moocFilmTMapper;

    @Override
    public List<Banner> getBanners() {
        List<Banner> banners = new ArrayList<>();

        List<MoocBannerT> moocBannerTList = moocBannerTMapper.selectList(null);
        for (MoocBannerT moocBannerT : moocBannerTList) {
            Banner banner = new Banner();
            banner.setBannerId("" + moocBannerT.getUuid());
            banner.setBannerAddress(moocBannerT.getBannerAddress());
            banner.setBannerUrl(moocBannerT.getBannerUrl());
            banners.add(banner);
        }

        return banners;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums) {
        FilmVO filmVO = new FilmVO();
        // 热映影片的限制条件
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("film_status", "1");
        // 判断是否是首页需要的内容
        if (isLimit) {
            // 如果是，则限制条数，限制内容为热映影片
            Page<MoocFilmT> page = new Page<>(1, nums);
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, wrapper);
            // 转换数据格式
            List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
            filmVO.setFilmInfo(filmInfos);
            filmVO.setFilmNum(filmInfos.size());
        } else {
            //TODO 如果不是，则是列表页，同样需要限制内容为热映影片
        }

        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums) {
        FilmVO filmVO = new FilmVO();
        // 热映影片的限制条件
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("film_status", "2");
        // 判断是否是首页需要的内容
        if (isLimit) {
            // 如果是，则限制条数，限制内容为即将上映影片
            Page<MoocFilmT> page = new Page<>(1, nums);
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, wrapper);
            // 转换数据格式
            List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
            filmVO.setFilmInfo(filmInfos);
            filmVO.setFilmNum(filmInfos.size());
        } else {
            //TODO 如果不是，则是列表页，同样需要限制内容为即将上映影片
        }

        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        // 条件：1、正在上映 2、票房前10名
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("film_status", "1");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_box_office");

        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, wrapper);

        return getFilmInfos(moocFilmTS);
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        // 条件：1、即将上映的 2、预售前10名
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("film_status", "2");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_preSaleNum");

        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, wrapper);

        return getFilmInfos(moocFilmTS);
    }

    @Override
    public List<FilmInfo> getTop() {
        // 条件：1、正在上映 2、评分前10名
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("film_status", "1");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_score");

        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, wrapper);

        return getFilmInfos(moocFilmTS);
    }

    private List<FilmInfo> getFilmInfos(List<MoocFilmT> moocFilmTS) {
        List<FilmInfo> filmInfos = new ArrayList<>();

        for (MoocFilmT moocFilmT : moocFilmTS) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setExpectNum(moocFilmT.getFilmPresalenum());
            filmInfo.setFilmId("" + moocFilmT.getUuid());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));
            filmInfos.add(filmInfo);
        }

        return filmInfos;
    }
}
