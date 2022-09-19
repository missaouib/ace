package com.controller;

import com.controller.common.CommonController;
import com.service.GalleryService;
import com.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * @Classname: GalleryController
 * @Date: 7/7/2021 2:44 下午
 * @Author: garlam
 * @Description:
 */

@Controller
@RequestMapping("/ace")
public class GalleryController extends CommonController {
    private static Logger log = LogManager.getLogger(GalleryController.class.getName());

    private GalleryService galleryService;

    @Autowired
    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @RequestMapping(value = "/gallery.html", method = RequestMethod.GET)
    public ModelAndView gallery() throws IOException {
        ModelAndView modelAndView = super.page("ace/tool-pages/gallery");

        String src = "C:\\ideaPorject\\ace\\src\\main\\resources\\static\\files\\img\\";
        String output = "C:\\ideaPorject\\ace\\src\\main\\resources\\static\\files\\img\\temp\\";
        galleryService.compressImages(src, output);

        return modelAndView;
    }


}

