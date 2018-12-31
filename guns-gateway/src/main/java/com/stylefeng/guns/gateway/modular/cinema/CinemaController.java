package com.stylefeng.guns.gateway.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.CinemaQueryVO;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

/**
 * @author wyw
 * @date 2018\12\31 0031 18:46
 */
@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    @Reference(interfaceClass = CinemaServiceAPI.class)
    private CinemaServiceAPI cinemaServiceAPI;

    @GetMapping("getCinemas")
    public ResponseVO getCinemas(CinemaQueryVO cinemaQueryVO) {
        // 按照五个条件进行筛选

        // 判断是否有满足条件的影院

        // 如果出现异常，应该如何处理

        return null;
    }

    @GetMapping("getCondition")
    public ResponseVO getCondition(CinemaQueryVO cinemaQueryVO) {
        return null;
    }

    @RequestMapping(value = "getFields", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseVO getFields(Integer cinemaId) {
        return null;
    }

    @PostMapping("getFieldInfo")
    public ResponseVO getFieldInfo(Integer cinemaId, Integer fieldId) {
        return null;
    }

}
