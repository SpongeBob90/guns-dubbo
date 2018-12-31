package com.stylefeng.guns.cinema.modular.cinema.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.cinema.common.persistence.dao.MoocFieldTMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wyw
 * @date 2018\12\31 0031 20:05
 */
@Component
@Service(interfaceClass = CinemaServiceAPI.class, loadbalance = "roundrobin")
public class DefaultCinemaServiceImpl implements CinemaServiceAPI {

    @Resource
    private MoocFieldTMapper moocFieldTMapper;

    /**
     * 根据CinemaQueryVO，查询影院列表
     *
     * @param cinemaQueryVO
     */
    @Override
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {
        return null;
    }

    /**
     * 根据条件获取品牌列表[除了99以外，其他数据为isActive]
     *
     * @param brandId
     */
    @Override
    public List<BrandVO> getBrands(int brandId) {
        return null;
    }

    /**
     * 获取行政区域列表
     *
     * @param areaId
     */
    @Override
    public List<AreaVO> getAreas(int areaId) {
        return null;
    }

    /**
     * 获取影厅类型列表
     *
     * @param hallType
     */
    @Override
    public List<HallTypeVO> getHallTypes(int hallType) {
        return null;
    }

    /**
     * 根据影院编号，获取影院信息
     *
     * @param cinemaId
     */
    @Override
    public CinemaInfoVO getCinemaInfoById(int cinemaId) {
        return null;
    }

    /**
     * 根据影院编号，获取所有电影的信息和对应的放映场次信息
     *
     * @param cinemaId
     */
    @Override
    public FilmInfoVO getFilmInfoByCinemaId(int cinemaId) {
        return null;
    }

    /**
     * 根据放映场次ID，获取放映信息
     *
     * @param fieldId
     */
    @Override
    public FilmFieldVO getFilmFieldInfo(int fieldId) {
        return null;
    }

    /**
     * 根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
     *
     * @param fieldId
     */
    @Override
    public FilmInfoVO getFilmInfoByFieldId(int fieldId) {
        return null;
    }
}
