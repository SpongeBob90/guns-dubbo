package com.stylefeng.guns.gateway.modular.film.vo;

import com.stylefeng.guns.api.film.vo.Banner;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;
import lombok.Data;

import java.util.List;

/**
 * @author : wyw 2018-12-27 10:09
 **/
@Data
public class FilmIndexVO {

    private List<Banner> banners;

    private FilmVO hotFilms;

    private FilmVO soonFilms;

    private List<FilmInfo> boxRanking;

    private List<FilmInfo> expectRanking;

    private List<FilmInfo> top;

}
