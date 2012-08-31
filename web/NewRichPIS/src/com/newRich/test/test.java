package com.newRich.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.newRich.backRun.vo.SelectVO;
import com.newRich.util.StockStrategyUtil;
import com.newRich.util.SystemUtil;

public class test {
	public static void main(String[] args) {
		try {
			List<SelectVO> strategyList = new ArrayList<SelectVO>();
			String strategy[] = StockStrategyUtil.STRATEGY_TYPE;
			for (int i = 0; i < strategy.length; i++) {
				String str = strategy[i];
				SelectVO vo = new SelectVO();
				vo.setString(str);
				vo.setValue(str);
				strategyList.add(vo);
			}
			
			System.out.println("action strategyList.size():" + strategyList.size());
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
			String date = "2012/08/21 20:37:47";
			System.out.println(date.indexOf("9"));
			//System.out.println(sdf1.format());
			//System.out.println("count:"+count);
			/**
			
			//先查詢條件符合時的第一個頁面，取得pagelist的最大值
			//pagelist規則，第二頁參數為21，第三頁為41，第四頁為61=>寫成一個方法回傳一個String[] or list<String>
			//取得每頁每一筆的股票代碼n+"</td><td height='10' align='left' class='body-table-nw' title='cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t="
			String datalist = 
				 "<td height=\"10\" align=\"right\" class=\"body-table-nw\">1</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AAC'><br>&nbsp;<b>Australia Acquisition Corp.</b><br>&nbsp;Conglomerates | Australia | 82.21M] offsetx=[100] offsety=[0] delay=[0]\"><a href=\"quote.ashx?t=AAC&amp;b=1\" class=\"tab-link\">AAC</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Australia Acquisition Corp.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Conglomerates</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Conglomerates</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Australia</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">82.21M</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">-</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AAC'><br>&nbsp;<b>Australia Acquisition Corp.</b><br>&nbsp;Conglomerates | Australia | 82.21M] offsetx=[-323] offsety=[0] delay=[0]\">10.10</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">0.00%</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">300</td></tr><tr valign=\"top\" class=\"table-light-row-cp\"  onmouseover=\"this.className='table-light-row-cp-h';\" onmouseout=\"this.className='table-light-row-cp';\" onclick=\"window.location='quote.ashx?t=AAU&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">2</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AAU'><br>&nbsp;<b>Almaden Minerals Ltd.</b><br>&nbsp;Industrial Metals & Minerals | Canada | 143.90M] offsetx=[100] offsety=[0] delay=[0]\"><a href=\"quote.ashx?t=AAU&amp;b=1\" class=\"tab-link\">AAU</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Almaden Minerals Ltd.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Basic Materials</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Industrial Metals & Minerals</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Canada</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">143.90M</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">17.36</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AAU'><br>&nbsp;<b>Almaden Minerals Ltd.</b><br>&nbsp;Industrial Metals & Minerals | Canada | 143.90M] offsetx=[-323] offsety=[0] delay=[0]\"><span style=\"color:#008800;\">2.43</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">5.65%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">191,365</td></tr><tr valign=\"top\" class=\"table-dark-row-cp\"  onmouseover=\"this.className='table-dark-row-cp-h';\" onmouseout=\"this.className='table-dark-row-cp';\" onclick=\"window.location='quote.ashx?t=ACE&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">3</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ACE'><br>&nbsp;<b>ACE Limited</b><br>&nbsp;Property & Casualty Insurance | Switzerland | 25.19B] offsetx=[100] offsety=[0] delay=[0]\"><a href=\"quote.ashx?t=ACE&amp;b=1\" class=\"tab-link\">ACE</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">ACE Limited</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Financial</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Property & Casualty Insurance</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Switzerland</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">25.19B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">12.59</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ACE'><br>&nbsp;<b>ACE Limited</b><br>&nbsp;Property & Casualty Insurance | Switzerland | 25.19B] offsetx=[-323] offsety=[0] delay=[0]\"><span style=\"color:#008800;\">74.30</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">0.23%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">1,470,441</td></tr><tr valign=\"top\" class=\"table-light-row-cp\"  onmouseover=\"this.className='table-light-row-cp-h';\" onmouseout=\"this.className='table-light-row-cp';\" onclick=\"window.location='quote.ashx?t=ACMP&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">4</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ACMP'><br>&nbsp;<b>Access Midstream Partners, L.P.</b><br>&nbsp;Oil & Gas Refining & Marketing | USA | 2.32B] offsetx=[100] offsety=[0] delay=[0]\"><a href=\"quote.ashx?t=ACMP&amp;b=1\" class=\"tab-link\">ACMP</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Access Midstream Partners, L.P.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Basic Materials</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Oil & Gas Refining & Marketing</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">2.32B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">19.90</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ACMP'><br>&nbsp;<b>Access Midstream Partners, L.P.</b><br>&nbsp;Oil & Gas Refining & Marketing | USA | 2.32B] offsetx=[-323] offsety=[0] delay=[0]\"><span style=\"color:#008800;\">29.45</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">0.65%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">92,617</td></tr><tr valign=\"top\" class=\"table-dark-row-cp\"  onmouseover=\"this.className='table-dark-row-cp-h';\" onmouseout=\"this.className='table-dark-row-cp';\" onclick=\"window.location='quote.ashx?t=ADK&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">5</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ADK'><br>&nbsp;<b>AdCare Health Systems Inc.</b><br>&nbsp;Long-Term Care Facilities | USA | 62.75M] offsetx=[100] offsety=[0] delay=[0]\"><a href=\"quote.ashx?t=ADK&amp;b=1\" class=\"tab-link\">ADK</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">AdCare Health Systems Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Healthcare</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Long-Term Care Facilities</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">62.75M</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">41.64</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ADK'><br>&nbsp;<b>AdCare Health Systems Inc.</b><br>&nbsp;Long-Term Care Facilities | USA | 62.75M] offsetx=[-323] offsety=[0] delay=[0]\"><span style=\"color:#008800;\">4.58</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">3.85%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">90,712</td></tr><tr valign=\"top\" class=\"table-light-row-cp\"  onmouseover=\"this.className='table-light-row-cp-h';\" onmouseout=\"this.className='table-light-row-cp';\" onclick=\"window.location='quote.ashx?t=ADSK&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">6</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ADSK'><br>&nbsp;<b>Autodesk, Inc.</b><br>&nbsp;Technical & System Software | USA | 8.16B] offsetx=[100] offsety=[0] delay=[0]\"><a href=\"quote.ashx?t=ADSK&amp;b=1\" class=\"tab-link\">ADSK</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Autodesk, Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Technology</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Technical & System Software</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">8.16B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">28.17</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ADSK'><br>&nbsp;<b>Autodesk, Inc.</b><br>&nbsp;Technical & System Software | USA | 8.16B] offsetx=[-323] offsety=[0] delay=[0]\"><span style=\"color:#008800;\">35.49</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">2.57%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">3,166,382</td></tr><tr valign=\"top\" class=\"table-dark-row-cp\"  onmouseover=\"this.className='table-dark-row-cp-h';\" onmouseout=\"this.className='table-dark-row-cp';\" onclick=\"window.location='quote.ashx?t=AEG&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">7</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AEG'><br>&nbsp;<b>AEGON N.V.</b><br>&nbsp;Life Insurance | Netherlands | 10.37B] offsetx=[100] offsety=[0] delay=[0]\"><a href=\"quote.ashx?t=AEG&amp;b=1\" class=\"tab-link\">AEG</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">AEGON N.V.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Financial</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Life Insurance</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Netherlands</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">10.37B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">12.14</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AEG'><br>&nbsp;<b>AEGON N.V.</b><br>&nbsp;Life Insurance | Netherlands | 10.37B] offsetx=[-323] offsety=[0] delay=[0]\"><span style=\"color:#008800;\">5.34</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">1.71%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">545,408</td></tr><tr valign=\"top\" class=\"table-light-row-cp\"  onmouseover=\"this.className='table-light-row-cp-h';\" onmouseout=\"this.className='table-light-row-cp';\" onclick=\"window.location='quote.ashx?t=AEO&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">8</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AEO'><br>&nbsp;<b>American Eagle Outfitters, Inc.</b><br>&nbsp;Apparel Stores | USA | 4.34B] offsetx=[100] offsety=[0] delay=[0]\"><a href=\"quote.ashx?t=AEO&amp;b=1\" class=\"tab-link\">AEO</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">American Eagle Outfitters, Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Services</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Apparel Stores</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">4.34B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">26.66</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AEO'><br>&nbsp;<b>American Eagle Outfitters, Inc.</b><br>&nbsp;Apparel Stores | USA | 4.34B] offsetx=[-323] offsety=[0] delay=[0]\"><span style=\"color:#008800;\">22.13</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">6.24%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">12,657,149</td></tr><tr valign=\"top\" class=\"table-dark-row-cp\"  onmouseover=\"this.className='table-dark-row-cp-h';\" onmouseout=\"this.className='table-dark-row-cp';\" onclick=\"window.location='quote.ashx?t=AIMC&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">9</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AIMC'><br>&nbsp;<b>Altra Holdings, Inc.</b><br>&nbsp;Industrial Electrical Equipment | USA | 507.28M] offsetx=[100] offsety=[0] delay=[0]\"><a href=\"quote.ashx?t=AIMC&amp;b=1\" class=\"tab-link\">AIMC</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Altra Holdings, Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Industrial Goods</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Industrial Electrical Equipment</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">507.28M</td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">12.81</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AIMC'><br>&nbsp;<b>Altra Holdings, Inc.</b><br>&nbsp;Industrial Electrical Equipment | USA | 507.28M] offsetx=[-323] offsety=[0] delay=[0]\"><span style=\"color:#008800;\">18.83</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">0.70%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">133,733</td></tr><tr valign=\"top\" class=\"table-light-row-cp\"  onmouseover=\"this.className='table-light-row-cp-h';\" onmouseout=\"this.className='table-light-row-cp';\" onclick=\"window.location='quote.ashx?t=AKAM&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">10</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AKAM'><br>&nbsp;<b>Akamai Technologies, Inc.</b><br>&nbsp;Internet Information Providers | USA | 6.73B] offsetx=[100] offsety=[0] delay=[0]\"><a href=\"quote.ashx?t=AKAM&amp;b=1\" class=\"tab-link\">AKAM</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Akamai Technologies, Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Technology</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Internet Information Providers</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">6.73B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">36.50</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AKAM'><br>&nbsp;<b>Akamai Technologies, Inc.</b><br>&nbsp;Internet Information Providers | USA | 6.73B] offsetx=[-323] offsety=[0] delay=[0]\"><span style=\"color:#008800;\">37.96</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">1.42%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">2,477,274</td></tr><tr valign=\"top\" class=\"table-dark-row-cp\"  onmouseover=\"this.className='table-dark-row-cp-h';\" onmouseout=\"this.className='table-dark-row-cp';\" onclick=\"window.location='quote.ashx?t=AKR&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">11</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AKR'><br>&nbsp;<b>Acadia Realty Trust</b><br>&nbsp;Property Management | USA | 1.12B] offsetx=[100] offsety=[-220] delay=[0]\"><a href=\"quote.ashx?t=AKR&amp;b=1\" class=\"tab-link\">AKR</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Acadia Realty Trust</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Financial</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Property Management</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">1.12B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#aa0000;\">56.70</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AKR'><br>&nbsp;<b>Acadia Realty Trust</b><br>&nbsp;Property Management | USA | 1.12B] offsetx=[-323] offsety=[-220] delay=[0]\"><span style=\"color:#008800;\">24.38</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">0.37%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">481,724</td></tr><tr valign=\"top\" class=\"table-light-row-cp\"  onmouseover=\"this.className='table-light-row-cp-h';\" onmouseout=\"this.className='table-light-row-cp';\" onclick=\"window.location='quote.ashx?t=ALJ&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">12</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ALJ'><br>&nbsp;<b>Alon USA Energy, Inc.</b><br>&nbsp;Oil & Gas Refining & Marketing | USA | 744.20M] offsetx=[100] offsety=[-220] delay=[0]\"><a href=\"quote.ashx?t=ALJ&amp;b=1\" class=\"tab-link\">ALJ</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Alon USA Energy, Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Basic Materials</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Oil & Gas Refining & Marketing</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">744.20M</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">36.56</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ALJ'><br>&nbsp;<b>Alon USA Energy, Inc.</b><br>&nbsp;Oil & Gas Refining & Marketing | USA | 744.20M] offsetx=[-323] offsety=[-220] delay=[0]\"><span style=\"color:#008800;\">13.16</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">2.73%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">311,134</td></tr><tr valign=\"top\" class=\"table-dark-row-cp\"  onmouseover=\"this.className='table-dark-row-cp-h';\" onmouseout=\"this.className='table-dark-row-cp';\" onclick=\"window.location='quote.ashx?t=ALL&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">13</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ALL'><br>&nbsp;<b>The Allstate Corporation</b><br>&nbsp;Property & Casualty Insurance | USA | 19.10B] offsetx=[100] offsety=[-220] delay=[0]\"><a href=\"quote.ashx?t=ALL&amp;b=1\" class=\"tab-link\">ALL</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">The Allstate Corporation</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Financial</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Property & Casualty Insurance</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">19.10B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">9.28</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ALL'><br>&nbsp;<b>The Allstate Corporation</b><br>&nbsp;Property & Casualty Insurance | USA | 19.10B] offsetx=[-323] offsety=[-220] delay=[0]\"><span style=\"color:#008800;\">38.34</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">0.24%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">3,007,709</td></tr><tr valign=\"top\" class=\"table-light-row-cp\"  onmouseover=\"this.className='table-light-row-cp-h';\" onmouseout=\"this.className='table-light-row-cp';\" onclick=\"window.location='quote.ashx?t=AMGN&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">14</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AMGN'><br>&nbsp;<b>Amgen Inc.</b><br>&nbsp;Biotechnology | USA | 64.64B] offsetx=[100] offsety=[-220] delay=[0]\"><a href=\"quote.ashx?t=AMGN&amp;b=1\" class=\"tab-link\">AMGN</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Amgen Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Healthcare</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Biotechnology</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">64.64B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">17.96</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AMGN'><br>&nbsp;<b>Amgen Inc.</b><br>&nbsp;Biotechnology | USA | 64.64B] offsetx=[-323] offsety=[-220] delay=[0]\"><span style=\"color:#008800;\">83.87</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">0.91%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">3,696,169</td></tr><tr valign=\"top\" class=\"table-dark-row-cp\"  onmouseover=\"this.className='table-dark-row-cp-h';\" onmouseout=\"this.className='table-dark-row-cp';\" onclick=\"window.location='quote.ashx?t=AMZN&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">15</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AMZN'><br>&nbsp;<b>Amazon.com Inc.</b><br>&nbsp;Catalog & Mail Order Houses | USA | 109.90B] offsetx=[100] offsety=[-220] delay=[0]\"><a href=\"quote.ashx?t=AMZN&amp;b=1\" class=\"tab-link\">AMZN</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Amazon.com Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Services</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Catalog & Mail Order Houses</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">109.90B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#aa0000;\">296.46</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=AMZN'><br>&nbsp;<b>Amazon.com Inc.</b><br>&nbsp;Catalog & Mail Order Houses | USA | 109.90B] offsetx=[-323] offsety=[-220] delay=[0]\"><span style=\"color:#008800;\">243.10</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">1.52%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">2,471,801</td></tr><tr valign=\"top\" class=\"table-light-row-cp\"  onmouseover=\"this.className='table-light-row-cp-h';\" onmouseout=\"this.className='table-light-row-cp';\" onclick=\"window.location='quote.ashx?t=ARCT&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">16</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ARCT'><br>&nbsp;<b>American Realty Capital Trust, Inc.</b><br>&nbsp;REIT - Office | USA | 1.81B] offsetx=[100] offsety=[-220] delay=[0]\"><a href=\"quote.ashx?t=ARCT&amp;b=1\" class=\"tab-link\">ARCT</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">American Realty Capital Trust, Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Financial</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">REIT - Office</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">1.81B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">-</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ARCT'><br>&nbsp;<b>American Realty Capital Trust, Inc.</b><br>&nbsp;REIT - Office | USA | 1.81B] offsetx=[-323] offsety=[-220] delay=[0]\"><span style=\"color:#008800;\">11.40</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">0.35%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">2,251,739</td></tr><tr valign=\"top\" class=\"table-dark-row-cp\"  onmouseover=\"this.className='table-dark-row-cp-h';\" onmouseout=\"this.className='table-dark-row-cp';\" onclick=\"window.location='quote.ashx?t=ARKR&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">17</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ARKR'><br>&nbsp;<b>Ark Restaurants Corp.</b><br>&nbsp;Restaurants | USA | 51.35M] offsetx=[100] offsety=[-220] delay=[0]\"><a href=\"quote.ashx?t=ARKR&amp;b=1\" class=\"tab-link\">ARKR</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Ark Restaurants Corp.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Services</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Restaurants</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">51.35M</td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">10.86</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ARKR'><br>&nbsp;<b>Ark Restaurants Corp.</b><br>&nbsp;Restaurants | USA | 51.35M] offsetx=[-323] offsety=[-220] delay=[0]\"><span style=\"color:#008800;\">15.85</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">0.13%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">4,096</td></tr><tr valign=\"top\" class=\"table-light-row-cp\"  onmouseover=\"this.className='table-light-row-cp-h';\" onmouseout=\"this.className='table-light-row-cp';\" onclick=\"window.location='quote.ashx?t=ARLP&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">18</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ARLP'><br>&nbsp;<b>Alliance Resource Partners LP</b><br>&nbsp;Industrial Metals & Minerals | USA | 2.47B] offsetx=[100] offsety=[-220] delay=[0]\"><a href=\"quote.ashx?t=ARLP&amp;b=1\" class=\"tab-link\">ARLP</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Alliance Resource Partners LP</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Basic Materials</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Industrial Metals & Minerals</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">2.47B</td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">12.12</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ARLP'><br>&nbsp;<b>Alliance Resource Partners LP</b><br>&nbsp;Industrial Metals & Minerals | USA | 2.47B] offsetx=[-323] offsety=[-220] delay=[0]\"><span style=\"color:#008800;\">67.01</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">2.07%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">58,725</td></tr><tr valign=\"top\" class=\"table-dark-row-cp\"  onmouseover=\"this.className='table-dark-row-cp-h';\" onmouseout=\"this.className='table-dark-row-cp';\" onclick=\"window.location='quote.ashx?t=ARRY&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">19</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ARRY'><br>&nbsp;<b>Array BioPharma, Inc.</b><br>&nbsp;Biotechnology | USA | 501.86M] offsetx=[100] offsety=[-220] delay=[0]\"><a href=\"quote.ashx?t=ARRY&amp;b=1\" class=\"tab-link\">ARRY</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Array BioPharma, Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Healthcare</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Biotechnology</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">501.86M</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">-</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ARRY'><br>&nbsp;<b>Array BioPharma, Inc.</b><br>&nbsp;Biotechnology | USA | 501.86M] offsetx=[-323] offsety=[-220] delay=[0]\"><span style=\"color:#aa0000;\">5.63</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#aa0000;\">-1.05%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">511,328</td></tr><tr valign=\"top\" class=\"table-light-row-cp\"  onmouseover=\"this.className='table-light-row-cp-h';\" onmouseout=\"this.className='table-light-row-cp';\" onclick=\"window.location='quote.ashx?t=ASBB&ty=c&ta=1&p=d&b=1';return false;\">"
				+"<td height=\"10\" align=\"right\" class=\"body-table-nw\">20</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ASBB'><br>&nbsp;<b>ASB Bancorp, Inc.</b><br>&nbsp;Regional - Mid-Atlantic Banks | USA | 82.53M] offsetx=[100] offsety=[-220] delay=[0]\"><a href=\"quote.ashx?t=ASBB&amp;b=1\" class=\"tab-link\">ASBB</a></td><td height=\"10\" align=\"left\" class=\"body-table-nw\">ASB Bancorp, Inc.</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Financial</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">Regional - Mid-Atlantic Banks</td><td height=\"10\" align=\"left\" class=\"body-table-nw\">USA</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">82.53M</td><td height=\"10\" align=\"right\" class=\"body-table-nw\">-</td><td height=\"10\" align=\"right\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=ASBB'><br>&nbsp;<b>ASB Bancorp, Inc.</b><br>&nbsp;Regional - Mid-Atlantic Banks | USA | 82.53M] offsetx=[-323] offsety=[-220] delay=[0]\"><span style=\"color:#008800;\">14.79</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\"><span style=\"color:#008800;\">1.30%</span></td><td height=\"10\" align=\"right\" class=\"body-table-nw\">3,700</td></tr></table>"
				+"<b>1</b> <a href=\"screener.ashx?v=111&f=ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3&r=21\" class=\"screener-pages\">2</a> <a href=\"screener.ashx?v=111&f=ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3&r=41\" class=\"screener-pages\">3</a> <a href=\"screener.ashx?v=111&f=ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3&r=61\" class=\"screener-pages\">4</a> <a href=\"screener.ashx?v=111&f=ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3&r=81\" class=\"screener-pages\">5</a> <a href=\"screener.ashx?v=111&f=ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3&r=101\" class=\"screener-pages\">6</a> ... <a href=\"screener.ashx?v=111&f=ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3&r=181\" class=\"screener-pages\">10</a> <a href=\"screener.ashx?v=111&f=ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3&r=201\" class=\"screener-pages\">11</a> <a href=\"screener.ashx?v=111&f=ta_highlow20d_nh,ta_highlow50d_nh,ta_sma20_pa,ta_sma200_pa,ta_sma50_pa&ft=3&r=21\" class=\"tab-link\"><b>next</b></a>";
			
			
			//判斷是否有分頁
			datalist.indexOf("<b>next</b>");
			//取得分頁最大值
			int lastPageHeader = datalist.lastIndexOf("class=\"screener-pages\">");
			System.out.println("lastPageHeader:"+lastPageHeader);
			String maxPage = datalist.substring(lastPageHeader+"class=\"screener-pages\">".length(), datalist.lastIndexOf("</a> <a"));
			System.out.println("maxPage:"+maxPage);
			
			//取出所有的股票代號
			String startString = "</td><td height=\"10\" align=\"left\" class=\"body-table-nw\" title=\"cssbody=[tabchrtbdy] cssheader=[tabchrthdr] body=[<img src='chart.ashx?s=m&ty=c&p=d&t=";
			String endString = "'><br>&nbsp;<b>";
			String recordEndString = ";return false;\">";
			int start = 1, pageOfRecords = 20;
			
			String stock = "";
//			System.out.println("datalist:"+datalist);
			for (int i = start; i < pageOfRecords; i++) {
				if (datalist.indexOf(String.valueOf(i) + startString) > 0) {
					stock = "";
					int starta = datalist.indexOf(String.valueOf(i) + startString) + startString.length();
					stock = datalist.substring(datalist.indexOf(String.valueOf(i) + startString) + startString.length() + String.valueOf(i).length(), datalist.indexOf(endString));
					System.out.println("stock:" + stock);
					datalist = datalist.substring(datalist.indexOf(recordEndString) + recordEndString.length(), datalist.length());
					//System.out.println("datalist:" + datalist);
				}
			}
			 -----------------------------------------------------------------------
			 Set<Class<?>> set = SystemUtil.getClasses(SystemUtil.QUARTZ_PACKAGE);
			Iterator<Class<?>> it = set.iterator();
			String reClassName = "", lastName = "";
			int count = 0;
			while (it.hasNext()) {
				SelectVO select = new SelectVO();
				Class<?> classs = (Class<?>) it.next();
				String className = classs.getName();
				if(null != className)
					className = className.replace(SystemUtil.QUARTZ_PACKAGE+".", "");
				System.out.println("lastName:"+lastName+"  className:"+className);
				System.out.println("className.indexOf('$'):"+className.indexOf('$'));
				if(className.indexOf('$') == -1 && !className.equals(lastName)){
					count++;
					reClassName = className;
					System.out.println("  className:"+className);
				}
				lastName = className;
			}
			 ===========================================================
			protected static final AreaCountryVO AreaCountryVO = null;
			private static Log logger = LogFactory.getLog(CustomerBO.class);
		    private static EspaBO boInstance = new EspaBO();
		    private Log log = null;
		    private PlatformTransactionManager transactionManager = null;
			//會用到的DAO
			private SerialNumberInfoDAO SerialNumberInfoDAO = null;
			private PurchaseOrder PurchaseOrderDAO = null;
			private PurchaseOrderDetail PurchaseOrderDetailDAO = null;
			private PurchaseOrderTemp PurchaseOrderTempDAO = null;
			private AreaCountry AreaCountryDAO = null;
			private SalesArea SalesAreaDAO = null;
			private AreaContinent AreaContinentDAO = null;
			private List<SalesAreaWithNameVO> salesAreaList;

			private EspaBO() {
				super();
				this.log = LogFactory.getLog(this.getClass());
				transactionManager = new DataSourceTransactionManager(getDataSource());
			}

		    public static EspaBO getInstance() {
		        return boInstance;
		    }

		    protected String getDsName() {
		        return DATA_SOURCE_NB;
		    	//return DATA_SOURCE_ERROR;
		    }

		    protected Log getLog() {
		        return log;
		    }
		    
			//traceaction 開始
		    synchronized public void order(final CustomerDetailVO customerDetailVO, final EspaCartVO espaCartVO,
		    		final PurchaseOrderVO purchaseOrderVO, final ArrayList<PurchaseOrderDetailVO> detailList, 
		    		final String afert2Day, final String sessionId) throws Exception {
		        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
		            public void doInTransactionWithoutResult(TransactionStatus status) {
		            	//先取得各DAO層的連線
		            	SerialNumberInfoDAO = SerialNumberInfoDAO.getInstance();
		            	PurchaseOrderDAO = PurchaseOrderImpl.getInstance();
		            	PurchaseOrderDetailDAO = PurchaseOrderDetailImpl.getInstance();
		            	PurchaseOrderTempDAO = PurchaseOrderTempImpl.getInstance();
		            	AreaCountryDAO = AreaCountryImpl.getInstance();
		            	SalesAreaDAO = SalesAreaImpl.getInstance();
		            	AreaContinentDAO = AreaContinentImpl.getInstance();
		            	// cid 用取號程式處理
						// 取得DB中的流水號+1
		            	int nextSn, yyyyMM = 0;
						String espaOrderId = "", nowYearMonth = "", yearMonth = ""
							, snPrefix = "";
						//取得系統的西元年
						nowYearMonth = DateUtil.getNowYearMonth();
						
						//...加強要抓取年月，然後分別比較一下
						try {
							List<PurchaseOrderVO> list = PurchaseOrderDAO.findAll(Definition.ORDER_PREFIX_ES);
							if(list.size() > 0){
								yyyyMM = PurchaseOrderDAO.getLastOrderYYYYMM(Definition.ORDER_PREFIX_ES);	
							}
							//System.out.println("yyyyMM :"+ yyyyMM);
							if(yyyyMM > 0){
								yearMonth = String.valueOf(yyyyMM);
								//如果跨月，就將流水號歸1
								//System.out.println("Integer.valueOf(nowYearMonth) :"+ (Integer.valueOf(nowYearMonth) > yyyyMM));
								if(Integer.valueOf(nowYearMonth) > yyyyMM){
									yearMonth = nowYearMonth;
									SerialNumberInfoDAO.update(Definition.TABLE_PURCHASE_ORDER_SPA, 1);
								}
							}else{
								yearMonth = nowYearMonth;
								SerialNumberInfoDAO.update(Definition.TABLE_PURCHASE_ORDER_SPA, 1);
							}
							
						} catch (Exception e1) {
							e1.printStackTrace();
							throw new RuntimeException("getLastOrderYYYYMM() Error!", e1);
						}
						// 以定義好的流水號碼規格(snInfo)來產生下一個流水號碼(sid)
						SerialNumberInfo snInfoVO = SerialNumberInfoDAO.findByTableName(Definition.TABLE_PURCHASE_ORDER_SPA);
						nextSn = snInfoVO.getCurrentSn() + 1;
						snPrefix = snInfoVO.getSnPrefix() + yearMonth;
						espaOrderId = KeyGenerator.keyGenerate(snPrefix, snInfoVO.getCurrentSn(), 
								snInfoVO.getSnSize(), '0');
						
						logger.info("esap order :" + espaOrderId);// 取號完成
						espaCartVO.setEspaOrderId(espaOrderId);
						//塞入OID
						purchaseOrderVO.setOid(espaOrderId);
						purchaseOrderVO.setCid(customerDetailVO.getCid());
						//新增資料
						try {
							PurchaseOrderDAO.insert(purchaseOrderVO);
							PurchaseOrderTempVO vo = new PurchaseOrderTempVO();
							vo.setOoid(espaOrderId);
							vo.setSessionId(sessionId);
							vo.setOrderType(Definition.ORDER_PREFIX_ES);
							//將espaOrderId塞到temp的db
							PurchaseOrderTempDAO.insert(vo);
							
							for (int i = 0; i < detailList.size(); i++) {
								PurchaseOrderDetailVO podVO = detailList.get(i);
								podVO.setOid(espaOrderId);
								PurchaseOrderDetailDAO.insert(podVO);
							}
							
							// 都新增沒問題後，才將取號的table存入新的號碼
							SerialNumberInfoDAO.update(Definition.TABLE_PURCHASE_ORDER_SPA, nextSn);
						} catch (Exception e1) {
							throw new RuntimeException("DAO Error!", e1);
						}

		            	//寄送確認信給客戶
		            	//Send mail to Customer
						String MailSubject = "";
						String MailBody = "";
						String MailTo = customerDetailVO.getLoginId();
						String MailCc = "";
						//String MailBcc = "";
						String MailBcc = Definition.MAIL_BCC + ";" + Definition.SALES_MAIL_ADDRESS;
						String MailFrom = Definition.MAIL_FORM;
						String MailHost = Definition.MAIL_HOST;
						String MailAttPath = "";
						String filename[] = null;
						String mfname[] = null;
						boolean sendfile = false;
						String Url = Definition.WEB_URL;
						
						//myOwenId的處理，如果有多行的話
						String myOwnId[] = StringUtil.handleMultiLine(espaCartVO.getMyOwnId());
						String ownIdDec = "";
						if(myOwnId.length > 0){
					    	 for(int i = 0; i < myOwnId.length;i++){
					    		 ownIdDec += myOwnId[i] + "<br/>\n";
					    	 }
					     }
						
						//Build Mail Body
						MailSubject = "Shuttle eSPA Order No." + espaOrderId;
						//內容為html
						String mailContent =
						  "<div id='espa'>Dear "+customerDetailVO.getFirstName()+" "+ customerDetailVO.getLastName() +":<br />\n"
					     +" <br />\n"
					     +" Your request has been submitted, and we will contact with you within 48 hours." + "<br />\n"
					     +" <br />\n"
					     +" </div>\n"
					     +" <hr />\n"
					     +" <div id='orderDetail'><span class='ordernumber'>Order sheet No. "+ espaOrderId +"</span>\n"
					     +" <table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>\n"
					     +"   <tr>\n"
					     +"     <td rowspan='4' class='title'><img src='"+Url+ espaCartVO.getImageFullUrl() +"' class='espaImg' /></td><td colspan='2' class='title'>Item</td>\n"
					     +"   </tr>\n"
					     +"   <tr>\n"
					     +"     <td colspan='2' class='content'>Screen Size : "+ espaCartVO.getLcdModelName() +"<br />\n"
					     + espaCartVO.getMbModelSpecPositionPlatformName()+ " : " +espaCartVO.getMbModelName()+ " (" +espaCartVO.getMbModelSpecTypePlatformName()+ ", " +espaCartVO.getMbModelSpecTypeChipsetName()+ ", " +espaCartVO.getMbModelSpecTypeSpindleName()+ ", " 
					     + espaCartVO.getMbModelSpecTypeGraphicName()+ ")</td>\n"
					     +"   </tr>\n"
					     +"   <tr>\n"
					     +"     <td class='title'>Forecast of selected item</td>\n"
					     +"     <td class='title'>Average sales volume (by month)</td>\n"
					     +"   </tr>\n"
					     +"   <tr>\n"
				         +"     <td class='content'>"+ espaCartVO.getForecast() +"</td>\n"
				         +"     <td class='content'>"+ espaCartVO.getMonthlyQty() +"</td>\n"
				         +"   </tr>\n"
					     +"   <tr>\n"
					     +"     <td colspan='4' class='title'>Customer's Message</td>\n"
					     +"   </tr>\n"
					     +"   <tr>\n"
					     +"     <td colspan='4' class='content'>"+ownIdDec+"</td>\n"
					     +"   </tr>\n"
					     +" </table>\n"
					     +" </div>\n"
					     +" <hr />";
						
						MailBody = StringUtil.getOrderMailBodyHtml(mailContent);
						
						//SendMail mail = new SendMail();
						SendMail.Send(MailTo, MailCc, MailBcc, MailFrom, MailHost,
								MailSubject, MailBody, MailAttPath, filename,
								mfname, sendfile);
						
						
						//會員等級判斷
						String MemberGrade ="";
						if (customerDetailVO.getGoldenMember()=="Y"){
							MemberGrade = Definition.NORMAL_GRADE;
						}else{
							MemberGrade = Definition.GOLDEN_GRADE;
						}
						
						//會員居住國家名稱轉換
						AreaCountryVO aco = new AreaCountryVO();
						AreaContinentVO ato = new AreaContinentVO();
						PurchaseOrderVO pod = new PurchaseOrderVO();
						SalesAreaVO sal = new SalesAreaVO();
						
										
						//會員銷售據點處理
						String area = "";
						try {
							aco = AreaCountryDAO.findByCountryId(customerDetailVO.getCountryId());
							List<PurchaseOrderVO> podList = PurchaseOrderDAO.findByOid(espaOrderId);
							if(podList.size() > 0){
								pod = podList.get(0);
							}
							List<SalesAreaVO> salList = SalesAreaDAO.findByCid(customerDetailVO.getCid());
							if(salList.size() > 0){
								sal = salList.get(0);
							}
							salesAreaList = SalesAreaDAO.findByCidWithName(customerDetailVO.getCid());
							for (int i = 0; i < salesAreaList.size(); i++) {
								SalesAreaWithNameVO vo = salesAreaList.get(i);
								if(!"".equals(StringUtil.eliminateNull(vo.getCountryId())) &&
								  StringUtil.eliminateNull(vo.getCountryId()).lastIndexOf("00") < 0){
									area += vo.getContinentName() + "-" + vo.getCountryName() + "<br/>"; 
								}else{
									area += vo.getContinentName() + "-ALL"  + "<br/>";
								}
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							throw new RuntimeException("DAO Error!", e);
						}
						
						//訂單時間
						String creatDate = "";
						if(!"".equals(StringUtil.eliminateNull(pod.getCreateDate()))){
							creatDate = DateUtil.formatTimestamp(pod.getCreateDate());
						}
						String MemberCountry = aco.getCountryName();
										
						
						
						//寄送訂購信給sales
						String MailToSales = Definition.SALES_MAIL_ADDRESS;
						String MailBccToSales = Definition.MAIL_BCC;
						String MailBodySales = 
						   "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>Shuttle | Notebook Website</title><style type=\"text/css\"> "
						 + "<!-- body{font:0.8em Verdana, Arial, Helvetica, sans-serif;margin:0;padding:0;}	#container{width:800px;margin:0;text-align:left;}	#mainContent {padding: 0;}	#pageContentLoginView{padding: 50px;text-align:center;} "
						 + " #espa{text-align:left;padding-left:20px;padding-right:20px;}	#orderDetail{text-align:left;margin-left:20px;margin-bottom:10px;margin-right:20px;margin-top:10px;} "
						 + " #orderDetail .table{width:660px;margin-top:5px;margin-bottom:5px;border-top-width:2px;border-right-width:1px;border-bottom-width:1px;border-left-width:2px;border-color:#999999;border-style:solid;} "
						 + " #orderDetail .title{text-align:center;font-size:0.9em;font-weight:bold;padding:5px;border-right-width:1px;border-bottom-width:1px;border-right-style:solid;border-bottom-style: solid;border-right-color: #999999;border-bottom-color: #999999;} "
						 + " #orderDetail .customer_title{font-size:0.9em;padding:5px;border-right-width:1px;border-bottom-width:1px;border-right-style:solid;border-bottom-style: solid;border-right-color: #999999;border-bottom-color: #999999;} "
						 + " #orderDetail .content{font-size:0.8em;color:#000000;padding:10px;border-right-width:1px;border-bottom-width:1px;border-right-style:solid;border-bottom-style:solid;border-right-color:#999999;border-bottom-color:#999999;} "
						 + " --></style></head><body><div id=\"container\"><div id=\"mainContent\"><div id=\"pageContentLoginView\"> "
						 + " <div id='espa'>Dear "+StringUtil.eliminateNull(customerDetailVO.getFirstName())+" "+ StringUtil.eliminateNull(customerDetailVO.getLastName()) +":<br />\n"
						 + " <br />\n"
					     + " Your request has been submitted, and we will contact with you within 48 hours." + "<br />\n"
					     + " <br />\n"
					     + " </div>\n"
					     + " <hr />\n"
					     + " <div id='orderDetail'><span class='ordernumber'>Order sheet No. "+ espaOrderId + " (" + creatDate + ")</span>\n"
					     + " <table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>\n"
					     + "   <tr>\n"
					     + "     <td rowspan='4' class='title'><img src='"+Url+ espaCartVO.getImageFullUrl() +"' class='espaImg' /></td><td colspan='2' class='title'>Item</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td colspan='2' class='content'>Screen Size : "+ espaCartVO.getLcdModelName() +"<br />\n"
					     +   espaCartVO.getMbModelSpecPositionPlatformName()+ " : " +espaCartVO.getMbModelName()+ " (" +espaCartVO.getMbModelSpecTypePlatformName()+ ", " +espaCartVO.getMbModelSpecTypeChipsetName()+ ", " +espaCartVO.getMbModelSpecTypeSpindleName()+ ", " 
					     +   espaCartVO.getMbModelSpecTypeGraphicName()+ ")</td>\n"
					     + "   </tr>\n"
					     +"    <tr>\n"
					     +"      <td class='title'>Forecast of selected item</td>\n"
					     +"      <td class='title'>Average sales volume (by month)</td>\n"
					     +"    </tr>\n"
					     +"    <tr>\n"
				         +"      <td class='content'>"+ espaCartVO.getForecast() +"</td>\n"
				         +"      <td class='content'>"+ espaCartVO.getMonthlyQty() +"</td>\n"
				         +"    </tr>\n"
					     + "   <tr>\n"
					     + "     <td colspan='4' class='title'>Customer's Message</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td colspan='4' class='content'>"+ownIdDec+"</td>\n"
					     + "   </tr>\n"
					     + " </table>\n"
					     + " </div>\n"
					     + " <hr />"
					     + " <div id=\"orderDetail\">\n"
					     + " <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"table\">\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Company Name</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(customerDetailVO.getCompany()) +"</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Grade</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(MemberGrade) +"</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Business E-mail</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(customerDetailVO.getLoginId()) +"</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Name</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(customerDetailVO.getTitle()) + " " + StringUtil.eliminateNull(customerDetailVO.getFirstName()) +" "+ StringUtil.eliminateNull(customerDetailVO.getLastName()) +"</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Job Title</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(customerDetailVO.getJobTitle()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Address</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(customerDetailVO.getAddress()) + ", " + StringUtil.eliminateNull(customerDetailVO.getCity()) + ", " + StringUtil.eliminateNull(customerDetailVO.getState()) + ", " + StringUtil.eliminateNull(customerDetailVO.getZip()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Country</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(MemberCountry) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Contact Phone Number</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getTelCountryCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getTelAreaCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getTelNumber()) + "-" + StringUtil.eliminateNull(customerDetailVO.getTelExt()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Mobile Number</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getMobileCountryCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getMobileAreaCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getMobileNumber()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Fax Number</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getFaxCountryCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getFaxAreaCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getFaxNumber()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">URL</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getWebSite()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">MSN</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getMsn()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Skype</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getSkype()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Sales Area</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(area) + "</td>\n"
					     + "   </tr>\n"
					     + "</table>\n"
					     + "</div></div></div></div></body></html>";
						
						SendMail.Send(MailToSales, MailCc, MailBccToSales, MailFrom, MailHost,
								MailSubject, MailBodySales, MailAttPath, filename,
								mfname, sendfile);
						
		            }
		        });
		    }
		    
		    
		    synchronized public void myOwnIdOrder(final CustomerDetailVO customerDetailVO, final EspaCartVO espaCartVO,
		    		final PurchaseOrderVO purchaseOrderVO, final ArrayList<PurchaseOrderDetailVO> detailList, 
		    		final String afert2Day, final String sessionId) throws Exception {
		        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
		            public void doInTransactionWithoutResult(TransactionStatus status) {
		            	//先取得各DAO層的連線
		            	SerialNumberInfoDAO = SerialNumberInfoDAO.getInstance();
		            	PurchaseOrderDAO = PurchaseOrderImpl.getInstance();
		            	PurchaseOrderDetailDAO = PurchaseOrderDetailImpl.getInstance();
		            	PurchaseOrderTempDAO = PurchaseOrderTempImpl.getInstance();
		            	AreaCountryDAO = AreaCountryImpl.getInstance();
		            	SalesAreaDAO = SalesAreaImpl.getInstance();
		            	AreaContinentDAO = AreaContinentImpl.getInstance();
		            	// cid 用取號程式處理
						// 取得DB中的流水號+1
		            	int nextSn, yyyyMM = 0;
						String espaOrderId = "", nowYearMonth = "", yearMonth = ""
							, snPrefix = "";
						//取得系統的西元年
						nowYearMonth = DateUtil.getNowYearMonth();
						
						//...加強要抓取年月，然後分別比較一下
						try {
							yyyyMM = PurchaseOrderDAO.getLastOrderYYYYMM();
							//System.out.println("yyyyMM :"+ yyyyMM);
							if(yyyyMM > 0){
								yearMonth = String.valueOf(yyyyMM);
								//如果跨月，就將流水號歸1
								if(Integer.valueOf(nowYearMonth) > yyyyMM){
									yearMonth = nowYearMonth;
									SerialNumberInfoDAO.update(Definition.TABLE_PURCHASE_ORDER_SPA, 1);
								}
							}else{
								yearMonth = nowYearMonth;
								SerialNumberInfoDAO.update(Definition.TABLE_PURCHASE_ORDER_SPA, 1);
							}
							
						} catch (Exception e1) {
							e1.printStackTrace();
							throw new RuntimeException("getLastOrderYYYYMM() Error!", e1);
						}
						// 以定義好的流水號碼規格(snInfo)來產生下一個流水號碼(sid)
						SerialNumberInfo snInfoVO = SerialNumberInfoDAO.findByTableName(Definition.TABLE_PURCHASE_ORDER_SPA);
						nextSn = snInfoVO.getCurrentSn() + 1;
						snPrefix = snInfoVO.getSnPrefix() + yearMonth;
						espaOrderId = KeyGenerator.keyGenerate(snPrefix, snInfoVO.getCurrentSn(), 
								snInfoVO.getSnSize(), '0');
						
						logger.info("esap order :" + espaOrderId);// 取號完成
						espaCartVO.setEspaOrderId(espaOrderId);
						//塞入OID
						purchaseOrderVO.setOid(espaOrderId);
						purchaseOrderVO.setCid(customerDetailVO.getCid());
						//新增資料
						try {
							PurchaseOrderDAO.insert(purchaseOrderVO);
							PurchaseOrderTempVO vo = new PurchaseOrderTempVO();
							vo.setOoid(espaOrderId);
							vo.setSessionId(sessionId);
							vo.setOrderType(Definition.ORDER_PREFIX_ES);
							//將espaOrderId塞到temp的db
							PurchaseOrderTempDAO.insert(vo);
							
							for (int i = 0; i < detailList.size(); i++) {
								PurchaseOrderDetailVO podVO = detailList.get(i);
								podVO.setOid(espaOrderId);
								PurchaseOrderDetailDAO.insert(podVO);
							}
							
							// 都新增沒問題後，才將取號的table存入新的號碼
							SerialNumberInfoDAO.update(Definition.TABLE_PURCHASE_ORDER_SPA, nextSn);
						} catch (Exception e1) {
							throw new RuntimeException("DAO Error!", e1);
						}

		            	//寄送確認信給客戶
		            	//Send mail to Customer
						String MailSubject = "";
						String MailBody = "";
						String MailTo = customerDetailVO.getLoginId();
						String MailCc = "";
						String MailBcc = Definition.MAIL_BCC + ";" + Definition.SALES_MAIL_ADDRESS;
						String MailFrom = Definition.MAIL_FORM;
						String MailHost = Definition.MAIL_HOST;
						String MailAttPath = "";
						String filename[] = null;
						String mfname[] = null;
						boolean sendfile = false;
						String Url = Definition.WEB_URL;
						
						//myOwenId的處理，如果有多行的話
						String myOwnId[] = StringUtil.handleMultiLine(espaCartVO.getMyOwnId());
						String ownIdDec = "";
						if(myOwnId.length > 0){
					    	 for(int i = 0; i < myOwnId.length;i++){
					    		 ownIdDec += myOwnId[i] + "<br/>\n";
					    	 }
					     }
						//Build Mail Body
						MailSubject = "Shuttle eSPA Order No." + espaOrderId;
						String mailContent =
							  "<div id='espa'>Dear "+customerDetailVO.getFirstName()+" "+ customerDetailVO.getLastName() +":<br />\n"
						     +" <br />\n"
						     +" Your request has been submitted, and we will contact with you within 48 hours.<br />\n"
						     +" <br />\n"
						     +"</div>\n"
						     +" <hr />\n"
						     +" <div id='orderDetail'><span class='ordernumber'>Order sheet No. "+ espaOrderId +"</span>\n"
						     +"   <table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>\n"
						     +"    <tr>\n"
						     +"      <td colspan='2' class='title'>Item</td>\n"
						     +"    </tr>\n"
						     +"    <tr>\n"
						     +"     <td colspan='2' class='content'>Screen Size : "+espaCartVO.getLcdModelName()+"<br />\n"
						     + espaCartVO.getMbModelSpecPositionPlatformName()+ " : " +espaCartVO.getMbModelName()+ " (" +espaCartVO.getMbModelSpecTypePlatformName()+ ", " +espaCartVO.getMbModelSpecTypeChipsetName()+ ", " +espaCartVO.getMbModelSpecTypeSpindleName()+ ", " 
						     + espaCartVO.getMbModelSpecTypeGraphicName()+ ")</td>\n"
						     +"    </tr>\n"
						     +"    <tr>\n"
						     +"      <td class='title'>Forecast of selected item</td>\n"
						     +"      <td class='title'>Average sales volume (by month)</td>\n"
						     +"    </tr>\n"
						     +"    <tr>\n"
					         +"      <td class='content'>"+ espaCartVO.getForecast() +"</td>\n"
					         +"      <td class='content'>"+ espaCartVO.getMonthlyQty() +"</td>\n"
					         +"    </tr>\n"
						     +"    <tr>\n"
						     +"      <td colspan='2' class='title'>My Own ID – Customer’s Message</td>\n"
						     +"    </tr>\n"
						     +"    <tr>\n"
						     +"      <td colspan='2' class='content'>"+ownIdDec+"</td>\n"
						     //+"      <td class='content'>"+ownIdDec+"</td>\n"
						     //+"      <td class='content'>"+espaCartVO.getMyOwnId()+"</td>\n"
						     +"    </tr>\n"
						     +"   </table>\n"
						     +" </div><hr />\n";
						
						MailBody = StringUtil.getOrderMailBodyHtml(mailContent);
						
						//SendMail mail = new SendMail();
						SendMail.Send(MailTo, MailCc, MailBcc, MailFrom, MailHost,
								MailSubject, MailBody, MailAttPath, filename,
								mfname, sendfile);
						
						
						//會員等級判斷
						String MemberGrade ="";
						if (customerDetailVO.getGoldenMember()=="Y"){
							MemberGrade = Definition.NORMAL_GRADE;
						}else{
							MemberGrade = Definition.GOLDEN_GRADE;
						}
						
						//會員居住國家名稱轉換
						AreaCountryVO aco = new AreaCountryVO();
						AreaContinentVO ato = new AreaContinentVO();
						PurchaseOrderVO pod = new PurchaseOrderVO();
						SalesAreaVO sal = new SalesAreaVO();
						
										
						//會員銷售據點處理
						String area = "";
						try {
							aco = AreaCountryDAO.findByCountryId(customerDetailVO.getCountryId());
							List<PurchaseOrderVO> podList = PurchaseOrderDAO.findByOid(espaOrderId);
							if(podList.size() > 0){
								pod = podList.get(0);
							}
							List<SalesAreaVO> salList = SalesAreaDAO.findByCid(customerDetailVO.getCid());
							if(salList.size() > 0){
								sal = salList.get(0);
							}
							salesAreaList = SalesAreaDAO.findByCidWithName(customerDetailVO.getCid());
							for (int i = 0; i < salesAreaList.size(); i++) {
								SalesAreaWithNameVO vo = salesAreaList.get(i);
								if(!"".equals(StringUtil.eliminateNull(vo.getCountryId())) &&
								  StringUtil.eliminateNull(vo.getCountryId()).lastIndexOf("00") < 0){
									area += vo.getContinentName() + "-" + vo.getCountryName() + "<br/>"; 
								}else{
									area += vo.getContinentName() + "-ALL"  + "<br/>";
								}
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							throw new RuntimeException("DAO Error!", e);
						}
						
						//訂單時間
						String creatDate = "";
						if(!"".equals(StringUtil.eliminateNull(pod.getCreateDate()))){
							creatDate = DateUtil.formatTimestamp(pod.getCreateDate());
						}
						String MemberCountry = aco.getCountryName();
						
						//寄送訂購信給sales
						String MailToSales = Definition.SALES_MAIL_ADDRESS;
						String MailBccToSales = Definition.MAIL_BCC;
						String MailBodySales = 
						   "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>Shuttle | Notebook Website</title><style type=\"text/css\"> "
						 + "<!-- body{font:0.8em Verdana, Arial, Helvetica, sans-serif;margin:0;padding:0;}	#container{width:800px;margin:0;text-align:left;}	#mainContent {padding: 0;}	#pageContentLoginView{padding: 50px;text-align:center;} "
						 + " #espa{text-align:left;padding-left:20px;padding-right:20px;}	#orderDetail{text-align:left;margin-left:20px;margin-bottom:10px;margin-right:20px;margin-top:10px;} "
						 + " #orderDetail .table{width:660px;margin-top:5px;margin-bottom:5px;border-top-width:2px;border-right-width:1px;border-bottom-width:1px;border-left-width:2px;border-color:#999999;border-style:solid;} "
						 + " #orderDetail .title{text-align:center;font-size:0.9em;font-weight:bold;padding:5px;border-right-width:1px;border-bottom-width:1px;border-right-style:solid;border-bottom-style: solid;border-right-color: #999999;border-bottom-color: #999999;} "
						 + " #orderDetail .customer_title{font-size:0.9em;padding:5px;border-right-width:1px;border-bottom-width:1px;border-right-style:solid;border-bottom-style: solid;border-right-color: #999999;border-bottom-color: #999999;} "
						 + " #orderDetail .content{font-size:0.8em;color:#000000;padding:10px;border-right-width:1px;border-bottom-width:1px;border-right-style:solid;border-bottom-style:solid;border-right-color:#999999;border-bottom-color:#999999;} "
						 + " --></style></head><body><div id=\"container\"><div id=\"mainContent\"><div id=\"pageContentLoginView\"> "
						 + " <div id='espa'>Dear "+StringUtil.eliminateNull(customerDetailVO.getFirstName())+" "+ StringUtil.eliminateNull(customerDetailVO.getLastName()) +":<br />\n"
						 + " <br />\n"
					     + " Your request has been submitted, and we will contact with you within 48 hours." + "<br />\n"
					     + " <br />\n"
					     + " </div>\n"
					     + " <hr />\n"
					     + " <div id='orderDetail'><span class='ordernumber'>Order sheet No. "+ espaOrderId + " (" + creatDate + ")</span>\n"
					     + " <table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>\n"
					     + "   <tr>\n"
					     + "     <td colspan='2' class='title'>Item</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td colspan='2' class='content'>Screen Size : "+ espaCartVO.getLcdModelName() +"<br />\n"
					     +   espaCartVO.getMbModelSpecPositionPlatformName()+ " : " +espaCartVO.getMbModelName()+ " (" +espaCartVO.getMbModelSpecTypePlatformName()+ ", " +espaCartVO.getMbModelSpecTypeChipsetName()+ ", " +espaCartVO.getMbModelSpecTypeSpindleName()+ ", " 
					     +   espaCartVO.getMbModelSpecTypeGraphicName()+ ")</td>\n"
					     + "   </tr>\n"
					     +"    <tr>\n"
					     +"      <td class='title'>Forecast of selected item</td>\n"
					     +"      <td class='title'>Average sales volume (by month)</td>\n"
					     +"    </tr>\n"
					     +"    <tr>\n"
				         +"      <td class='content'>"+ espaCartVO.getForecast() +"</td>\n"
				         +"      <td class='content'>"+ espaCartVO.getMonthlyQty() +"</td>\n"
				         +"    </tr>\n"
					     + "   <tr>\n"
					     + "     <td colspan='2' class='title'>Customer's Message</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td colspan='2' class='content'>"+ownIdDec+"</td>\n"
					     + "   </tr>\n"
					     + " </table>\n"
					     + " </div>\n"
					     + " <hr />"
					     + " <div id=\"orderDetail\">\n"
					     + " <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"table\">\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Company Name</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(customerDetailVO.getCompany()) +"</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Grade</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(MemberGrade) +"</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Business E-mail</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(customerDetailVO.getLoginId()) +"</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Name</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(customerDetailVO.getTitle()) + " " + StringUtil.eliminateNull(customerDetailVO.getFirstName()) +" "+ StringUtil.eliminateNull(customerDetailVO.getLastName()) +"</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Job Title</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(customerDetailVO.getJobTitle()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Address</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNull(customerDetailVO.getAddress()) + ", " + StringUtil.eliminateNull(customerDetailVO.getCity()) + ", " + StringUtil.eliminateNull(customerDetailVO.getState()) + ", " + StringUtil.eliminateNull(customerDetailVO.getZip()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Country</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(MemberCountry) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Contact Phone Number</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getTelCountryCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getTelAreaCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getTelNumber()) + "-" + StringUtil.eliminateNull(customerDetailVO.getTelExt()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Mobile Number</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getMobileCountryCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getMobileAreaCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getMobileNumber()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Fax Number</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getFaxCountryCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getFaxAreaCode()) + "-" + StringUtil.eliminateNull(customerDetailVO.getFaxNumber()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">URL</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getWebSite()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">MSN</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getMsn()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Skype</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(customerDetailVO.getSkype()) + "</td>\n"
					     + "   </tr>\n"
					     + "   <tr>\n"
					     + "     <td class=\"customer_title\">Sales Area</td>\n"
					     + "     <td class=\"content\">" + StringUtil.eliminateNullToSpace(area) + "</td>\n"
					     + "   </tr>\n"
					     + "</table>\n"
					     + "</div></div></div></div></body></html>";
						
						SendMail.Send(MailToSales, MailCc, MailBccToSales, MailFrom, MailHost,
								MailSubject, MailBodySales, MailAttPath, filename,
								mfname, sendfile);
		            }
		        });
		    }
			*/
			
			/*
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql://localhost/newrich");
			dataSource.setUsername("root");
			dataSource.setPassword("");

			PersonDao dao = new PersonDao();
			// dao.setDataSource(dataSource);

			List<Person> list = dao.findAll();
			System.out.println("list==" + list.size());
			System.out.println("===============↓↓↓↓↓↓↓↓不會重新再取一次connetion↓↓↓↓↓↓=========================");
			
			SpringStockDao dao1 = new SpringStockDao();
			List<StockVO> list1 = dao1.findAll();
			list1 = dao1.findAll();
			System.out.println("list1==" + list1.size());
			*/
		} catch (Exception d) {
			d.printStackTrace();
		}

	}
}
