package com.stylefeng.guns.film.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.film.FilmAsyncServiceAPI;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.ActorVO;
import com.stylefeng.guns.api.film.vo.FilmDescVO;
import com.stylefeng.guns.api.film.vo.ImgVO;
import com.stylefeng.guns.film.common.persistence.dao.MoocActorTMapper;
import com.stylefeng.guns.film.common.persistence.dao.MoocFilmInfoTMapper;
import com.stylefeng.guns.film.common.persistence.model.MoocActorT;
import com.stylefeng.guns.film.common.persistence.model.MoocFilmInfoT;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : wyw 2018-12-27 13:30
 **/
@Component
@Service(interfaceClass = FilmAsyncServiceAPI.class, loadbalance = "roundrobin")
public class DefaultFilmAsyncServiceImpl implements FilmAsyncServiceAPI {

    @Resource
    private MoocFilmInfoTMapper moocFilmInfoTMapper;

    @Resource
    private MoocActorTMapper moocActorTMapper;

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);
        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(moocFilmInfoT.getBiography());
        filmDescVO.setFilmId(filmId);
        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);
        // 图片地址是五个以逗号为分隔的链接URL
        String filmImgStr = moocFilmInfoT.getFilmImgs();
        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO();
        if (!ArrayUtils.isEmpty(filmImgs) && (filmImgs.length == 5)) {
            imgVO.setMainImg(filmImgs[0]);
            imgVO.setImg01(filmImgs[1]);
            imgVO.setImg02(filmImgs[2]);
            imgVO.setImg03(filmImgs[3]);
            imgVO.setImg04(filmImgs[4]);
        }

        return imgVO;
    }

    @Override
    public ActorVO getDectInfo(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);
        // 获取导演信息
        Integer directId = moocFilmInfoT.getDirectorId();
        MoocActorT moocActorT = moocActorTMapper.selectById(directId);

        ActorVO actorVO = new ActorVO();
        actorVO.setImgAddress(moocActorT.getActorImg());
        actorVO.setDirectorName(moocActorT.getActorName());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        return moocActorTMapper.getActors(filmId);
    }

    private MoocFilmInfoT getFilmInfo(String filmId) {
        MoocFilmInfoT moocFilmInfoT = new MoocFilmInfoT();
        moocFilmInfoT.setFilmId(filmId);
        moocFilmInfoT = moocFilmInfoTMapper.selectOne(moocFilmInfoT);
        return moocFilmInfoT;
    }
}
