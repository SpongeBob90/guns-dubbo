package com.stylefeng.guns.gateway.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.api.film.FilmAsyncServiceAPI;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.gateway.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.gateway.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.gateway.modular.film.vo.FilmRequestVO;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author : wyw 2018-12-27 09:56
 **/
@RestController
@RequestMapping("/film/")
public class FilmController {

    @Reference(interfaceClass = FilmServiceAPI.class)
    private FilmServiceAPI filmServiceAPI;

    @Reference(interfaceClass = FilmAsyncServiceAPI.class, async = true)
    private FilmAsyncServiceAPI filmAsyncServiceAPI;

    /**
     * API网关：
     *     1、功能聚合【API聚合】
     *     好处：
     *         1、六个接口，一次请求，同一时刻节省了五次HTTP请求
     *         2、同一个接口对外暴露，降低了前后端分离开发的难度和复杂度
     *     坏处：
     *         1、一次获取数据过多，容易出现问题
     */
    @GetMapping("getIndex")
    public ResponseVO getIndex() {
        FilmIndexVO filmIndexVO = new FilmIndexVO();

        // 获取banner信息
        filmIndexVO.setBanners(filmServiceAPI.getBanners());
        // 获取正在热映的电影
        filmIndexVO.setHotFilms(filmServiceAPI.getHotFilms(true, 8, 1, 1, 99, 99, 99));
        // 即将上映的电影
        filmIndexVO.setSoonFilms(filmServiceAPI.getSoonFilms(true, 8, 1, 1, 99, 99, 99));
        // 票房排行榜
        filmIndexVO.setBoxRanking(filmServiceAPI.getBoxRanking());
        // 获取受欢迎的榜单
        filmIndexVO.setExpectRanking(filmServiceAPI.getExpectRanking());
        // 获取前10
        filmIndexVO.setTop(filmServiceAPI.getTop());
        // 图片前缀
        filmIndexVO.setImgPre("http://img.meetingshop.cn/");

        return ResponseVO.success(filmIndexVO);
    }

    @GetMapping("getConditionList")
    public ResponseVO getConditionList() {
        FilmConditionVO filmConditionVO = new FilmConditionVO();
        // 类型集合
        List<CatVO> cats = filmServiceAPI.getCats();
        filmConditionVO.setCatInfo(cats);
        // 片源集合
        List<SourceVO> sources = filmServiceAPI.getSources();
        filmConditionVO.setSourceInfo(sources);
        // 年代集合
        List<YearVO> years = filmServiceAPI.getYears();
        filmConditionVO.setYearInfo(years);
        return ResponseVO.success(filmConditionVO);
    }

    @GetMapping("getFilms")
    public ResponseVO getFilms(FilmRequestVO filmRequestVO) {
        FilmVO filmVO;
        // 根据showType判断影片查询类型
        switch (filmRequestVO.getShowType()) {
            case 1:
                filmVO = filmServiceAPI.getHotFilms(false, filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(), filmRequestVO.getSortId(), filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(), filmRequestVO.getCatId());
                break;
            case 2:
                filmVO = filmServiceAPI.getSoonFilms(false, filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(), filmRequestVO.getSortId(), filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(), filmRequestVO.getCatId());
                break;
            case 3:
                filmVO = filmServiceAPI.getClassicFilms(filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(), filmRequestVO.getSourceId(), filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
            default:
                filmVO = filmServiceAPI.getHotFilms(false, filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(), filmRequestVO.getSortId(), filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(), filmRequestVO.getCatId());
                break;
        }
        return ResponseVO.success(filmVO);
    }

    @GetMapping("films/{searchParam}")
    public ResponseVO films(@PathVariable("searchParam") String searchParam, int searchType) {
        // 根据searchType判断查询类型
        FilmDetailVO filmDetail = filmServiceAPI.getFilmDetail(searchType, searchParam);

        if (filmDetail == null) {
            return ResponseVO.appFail("未查询到符合条件的影片");
        } else if ((filmDetail.getFilmId() == null) || (filmDetail.getFilmId().trim().length() == 0)) {
            return ResponseVO.appFail("影片缺少有效的filmId");
        }

        // 查询影片详细信息 --> dubbo的异步获取
        String filmId = filmDetail.getFilmId();
        // 获取影片的描述信息
        filmAsyncServiceAPI.getFilmDesc(filmId);
        Future<FilmDescVO> filmDescVOFuture = RpcContext.getContext().getFuture();
        // 获取图片信息
        filmAsyncServiceAPI.getImgs(filmId);
        Future<ImgVO> imgVOFuture = RpcContext.getContext().getFuture();
        // 获取导演信息
        filmAsyncServiceAPI.getDectInfo(filmId);
        Future<ActorVO> directorFuture = RpcContext.getContext().getFuture();
        // 获取演员信息
        filmAsyncServiceAPI.getActors(filmId);
        Future<List<ActorVO>> actorsFuture = RpcContext.getContext().getFuture();

        try {
            // 组织actor属性
            ActorRequestVO actorRequestVO = new ActorRequestVO();
            actorRequestVO.setDirector(directorFuture.get());
            actorRequestVO.setActors(actorsFuture.get());
            // 组织info对象 
            InfoRequstVO infoRequstVO = new InfoRequstVO();
            infoRequstVO.setActors(actorRequestVO);
            infoRequstVO.setBiography(filmDescVOFuture.get().getBiography());
            infoRequstVO.setImgVO(imgVOFuture.get());
            infoRequstVO.setFilmId(filmId);
            // 设置图片路径前缀
            filmDetail.setImgPre("http://img.meetingshop.cn/");
            // 组织返回值
            filmDetail.setInfo04(infoRequstVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.serviceFail("数据组装失败");
        }

        return ResponseVO.success(filmDetail);
    }

}
