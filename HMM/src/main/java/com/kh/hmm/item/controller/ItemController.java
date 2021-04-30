package com.kh.hmm.item.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.hmm.board.controller.BoardController;
import com.kh.hmm.item.model.service.ItemService;
import com.kh.hmm.item.model.vo.Item;
import com.kh.hmm.item.model.vo.Purchaseditem;
import com.kh.hmm.member.model.service.MemberService;
import com.kh.hmm.member.model.vo.Member;

@Controller
public class ItemController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private ItemService itemService;
	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "itemLists.do", method = RequestMethod.GET)
	public String selectItemList(HttpSession session, Model model) {
		logger.info("selectItemList() call...");
		int membercode = 0;
		ArrayList<Item> pList = null;
		ArrayList<Item> list = itemService.selectItemList();
		if (session.getAttribute("member") != null) {
			membercode = ((Member) session.getAttribute("member")).getMembercode();
			pList = itemService.selectPurchasedItemList(membercode);
		}
		if (list != null) {
			model.addAttribute("list", list);
		}

		if (pList != null)
			model.addAttribute("pList", pList);
		else
			model.addAttribute("pList", null);

		// 매점으로
		return "../../cashshop";
	}

	@RequestMapping(value = "itemOne.do", method = RequestMethod.GET)
	public String selectItemOne(Model model, int itemcode) {
		logger.info("selectItemOne(" + itemcode + ") call...");

		Item item = itemService.selectItemOne(itemcode);

		if (item != null) {
			model.addAttribute("item", item);
		}

		// 아이템 상세보기로
		return "../../index";
	}

	@ResponseBody
	@RequestMapping(value = "itemPurchasedLists.do", method = RequestMethod.GET)
	public void selectPurchasedItemList(HttpSession session, Model model) {
		logger.info("selectPurchasedItemList call...");
		int membercode = ((Member) session.getAttribute("member")).getMembercode();
		System.out.println("membercode : " + membercode);
		ArrayList<Item> list = itemService.selectPurchasedItemList(membercode);

		if (list != null)
			model.addAttribute("pList", list);
	}

	@RequestMapping(value = "itemPurchase.do", method = RequestMethod.POST)
	public String itemPurchase(HttpSession session, int itemcode) {
		logger.info("itemPurchase(" + itemcode + ") call...");

		String returnGo = "";
		Member member = (Member) session.getAttribute("member");
		Item item = itemService.selectItemOne(itemcode);

		if (member == null)
			returnGo = "";// 로그인 페이지로 넘어가라
		else {
			if (item.getPrice() > member.getDdaru())
				returnGo = "";// 따루 부족이니 따루 구매 페이지 또는 따루 모으는 방법 페이지
			else {
				if (itemcode <= 34 && itemcode >= 32) {
					int medalCharge = 0;
					System.out.println("아이템코드 : " + itemcode);
					switch (itemcode) {
					case 32:
						medalCharge = memberService.buyMedal(member.getMembercode(), 1);
						break;
					case 33:
						medalCharge = memberService.buyMedal(member.getMembercode(), 10);
						break;
					case 34:
						medalCharge = memberService.buyMedal(member.getMembercode(), 50);
						break;
					}

				}
				else if(itemcode == 44)
				{
					memberService.buyLevelItem(member.getMembercode());
				}
				Purchaseditem pitem = new Purchaseditem();
				pitem.setMembercode(member.getMembercode());
				pitem.setItemcode(itemcode);
				int flag = itemService.selectIsPurchaseItem(pitem);
				if (flag == 1) {
					session.setAttribute("purchased", 1);
					return "redirect:/itemLists.do";
				} else {
					itemService.insertOne(pitem);
					member.setDdaru(member.getDdaru() - item.getPrice());
					memberService.updateDDARU(member);
				}
				returnGo = "";// 구매 완료 페이지, 인벤토리로 넘어가라
			}
		}

		// 인벤토리로
		return "redirect:/itemLists.do";
	}

	@RequestMapping(value = "itemDelete.do", method = RequestMethod.POST)
	public String itemDelete(HttpSession session, int itemcode) {
		logger.info("itemDelete(" + itemcode + ") call...");

		String returnGo = "";
		int membercode = ((Member) session.getAttribute("member")).getMembercode();

		Purchaseditem pitem = new Purchaseditem();
		pitem.setMembercode(membercode);
		pitem.setItemcode(itemcode);

		itemService.deleteOne(pitem);

		return "../../index";// 인벤토리로
	}
}
