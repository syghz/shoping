package com.netease.course.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.course.model.Json;
import com.netease.course.model.Trx;
import com.netease.course.service.ContentService;
import com.netease.course.service.TrxService;

/**
 * content的controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/api")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private TrxService trxService;
	
	/**
	 * 删除产品
	 * @param id
	 * @param map
	 * @param hs
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json apiDelete(@RequestParam("id") int id, ModelMap map, HttpSession hs) {
		if (id > 0) {
			contentService.deleteContentById(id);
			return new Json(200, "删除成功！", true);
		} else {
			return new Json(220, "删除失败！", false);
		}
	}
	/**
	 * 购买商品
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	@ResponseBody
	public Json apiBuy(@RequestParam("id") int id, ModelMap map) {
		int contentId = (Integer) map.get("id");
		int number = (Integer) map.get("number");
		Trx trx =new Trx();
		trx.setContentid(contentId);
		trx.setPersonid(id);
		trx.setPrice(number);
		trx.setTime(new Date().getTime());
		int result = trxService.insertTrx(trx);
		if (result > 0) {
			return new Json(200, "购买成功！", true);
		} else {
			return new Json(220, "购买失败！", false);
		}
	}
	
	
	/**
	 * 上传产品图片
	 * @param id
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Json apiUpload(@RequestParam("id") int id, @RequestParam("url") String url) {
		if (id > 0 && url.trim().length() > 0) {
			return new Json(200, url, true);
		} else {
			return new Json(220, "上传失败！", false);
		}
	}
}
