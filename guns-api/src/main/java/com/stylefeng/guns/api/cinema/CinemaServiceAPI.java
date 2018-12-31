package com.stylefeng.guns.api.cinema;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.vo.*;

import java.util.List;

/**
 * @author wyw
 * @date 2018\12\31 0031 18:49
 */
public interface CinemaServiceAPI {

    /**
     * 根据CinemaQueryVO，查询影院列表
     */
    Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO);

    /**
     * 根据条件获取品牌列表[除了99以外，其他数据为isActive]
     */
    List<BrandVO> getBrands(int brandId);

    /**
     * 获取行政区域列表
     */
    List<AreaVO> getAreas(int areaId);

    /**
     * 获取影厅类型列表
     * */
    List<HallTypeVO> getHallTypes(int hallType);

    /**
     * 根据影院编号，获取影院信息
     * */
    CinemaInfoVO getCinemaInfoById(int cinemaId);

    /**
     * 根据影院编号，获取所有电影的信息和对应的放映场次信息
     * */
    FilmInfoVO getFilmInfoByCinemaId(int cinemaId);

    /**
     * 根据放映场次ID，获取放映信息
     * */
    FilmFieldVO getFilmFieldInfo(int fieldId);

    /**
     * 根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
     * */
    FilmInfoVO getFilmInfoByFieldId(int fieldId);

}
