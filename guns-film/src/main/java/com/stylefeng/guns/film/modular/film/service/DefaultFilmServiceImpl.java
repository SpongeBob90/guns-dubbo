package com.stylefeng.guns.film.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.film.common.persistence.dao.*;
import com.stylefeng.guns.film.common.persistence.model.*;
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

    @Resource
    private MoocCatDictTMapper moocCatDictTMapper;

    @Resource
    private MoocSourceDictTMapper moocSourceDictTMapper;

    @Resource
    private MoocYearDictTMapper moocYearDictTMapper;

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
    public FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
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
            // 如果不是，则是列表页，同样需要限制内容为热映影片
            Page<MoocFilmT> page;
            // 根据sortId的不同，来组织不同的Page对象：1-按热门搜索；2-按时间搜索；3-按评价搜索
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_score");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
            }
            // 如果sourceId、yearId、catId不为99，则表示要按照对应的编号进行查询
            if (sourceId != 99) {
                wrapper.eq("film_source", sourceId);
            }
            if (yearId != 99) {
                wrapper.eq("film_date", yearId);
            }
            if (catId != 99) {
                wrapper.like("film_cats", "%#" + catId + "#%");
            }
            // 获取符合条件影片总页数
            int totalPage = moocFilmTMapper.selectCount(wrapper);
            // 获取符合条件的影片列表
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, wrapper);
            // 转换数据格式
            List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
            filmVO.setFilmInfo(filmInfos);
            filmVO.setFilmNum(filmInfos.size());
            filmVO.setTotalPage(totalPage);
            filmVO.setNowPage(nowPage);
            filmVO.setImgPre("http://img.meetingshop.cn/");
        }

        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
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
            // 如果不是，则是列表页，同样需要限制内容为即将上映影片
            Page<MoocFilmT> page;
            // 根据sortId的不同，来组织不同的Page对象：1-按热门搜索；2-按时间搜索；3-按评价搜索
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
            }
            // 如果sourceId、yearId、catId不为99，则表示要按照对应的编号进行查询
            if (sourceId != 99) {
                wrapper.eq("film_source", sourceId);
            }
            if (yearId != 99) {
                wrapper.eq("film_date", yearId);
            }
            if (catId != 99) {
                wrapper.like("film_cats", "%#" + catId + "#%");
            }
            // 获取符合条件影片总页数
            int totalPage = moocFilmTMapper.selectCount(wrapper);
            // 获取符合条件的影片列表
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, wrapper);
            // 转换数据格式
            List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
            filmVO.setFilmInfo(filmInfos);
            filmVO.setFilmNum(filmInfos.size());
            filmVO.setTotalPage(totalPage);
            filmVO.setNowPage(nowPage);
            filmVO.setImgPre("http://img.meetingshop.cn/");
        }

        return filmVO;
    }

    @Override
    public FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        // 经典影片的限制条件
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("film_status", "3");

        Page<MoocFilmT> page;
        // 根据sortId的不同，来组织不同的Page对象：1-按热门搜索；2-按时间搜索；3-按评价搜索
        switch (sortId) {
            case 1:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
            case 2:
                page = new Page<>(nowPage, nums, "film_time");
                break;
            case 3:
                page = new Page<>(nowPage, nums, "film_score");
                break;
            default:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
        }
        // 如果sourceId、yearId、catId不为99，则表示要按照对应的编号进行查询
        if (sourceId != 99) {
            wrapper.eq("film_source", sourceId);
        }
        if (yearId != 99) {
            wrapper.eq("film_date", yearId);
        }
        if (catId != 99) {
            wrapper.like("film_cats", "%#" + catId + "#%");
        }
        // 获取符合条件影片总页数
        int totalPage = moocFilmTMapper.selectCount(wrapper);
        // 获取符合条件的影片列表
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, wrapper);
        // 转换数据格式
        List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
        filmVO.setFilmInfo(filmInfos);
        filmVO.setFilmNum(filmInfos.size());
        filmVO.setTotalPage(totalPage);
        filmVO.setNowPage(nowPage);
        filmVO.setImgPre("http://img.meetingshop.cn/");

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

    @Override
    public List<CatVO> getCats() {
        List<CatVO> cats = new ArrayList<>();
        List<MoocCatDictT> moocCats = moocCatDictTMapper.selectList(null);
        for (MoocCatDictT moocCat : moocCats) {
            CatVO catVO = new CatVO();
            catVO.setCatId(moocCat.getUuid() + "");
            catVO.setCatName(moocCat.getShowName());
            cats.add(catVO);
        }
        return cats;
    }

    @Override
    public List<SourceVO> getSources() {
        List<SourceVO> sources = new ArrayList<>();
        List<MoocSourceDictT> moocSources = moocSourceDictTMapper.selectList(null);
        for (MoocSourceDictT moocSource : moocSources) {
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(moocSource.getUuid() + "");
            sourceVO.setSourceName(moocSource.getShowName());
            sources.add(sourceVO);
        }
        return sources;
    }

    @Override
    public List<YearVO> getYears() {
        List<YearVO> years = new ArrayList<>();
        List<MoocYearDictT> moocYears = moocYearDictTMapper.selectList(null);
        for (MoocYearDictT moocYearDictT : moocYears) {
            YearVO yearVO = new YearVO();
            yearVO.setYearId(moocYearDictT.getUuid() + "");
            yearVO.setYearName(moocYearDictT.getShowName());
            years.add(yearVO);
        }
        return years;
    }

    @Override
    public FilmDetailVO getFilmDetail(int searchType, String searchParam) {
        return null;
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
