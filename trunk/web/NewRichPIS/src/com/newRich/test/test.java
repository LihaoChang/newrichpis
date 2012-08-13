package com.newRich.test;

import java.util.List;

import com.newRich.backRun.vo.StockVO;
import com.newRich.dao.PersonDao;
import com.newRich.dao.SpringStockDao;
import com.newRich.model.Person;

public class test {
	public static void main(String[] args) {
		try {
			/**
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
			
			// DriverManagerDataSource dataSource = new
			// DriverManagerDataSource();
			// dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			// dataSource.setUrl("jdbc:mysql://localhost/newrich");
			// dataSource.setUsername("root");
			// dataSource.setPassword("");

			PersonDao dao = new PersonDao();
			// dao.setDataSource(dataSource);

			List<Person> list = dao.findAll();
			System.out.println("list==" + list.size());
			System.out.println("===============↓↓↓↓↓↓↓↓不會重新再取一次connetion↓↓↓↓↓↓=========================");
			
			SpringStockDao dao1 = new SpringStockDao();
			List<StockVO> list1 = dao1.findAll();
			list1 = dao1.findAll();
			System.out.println("list1==" + list1.size());
		} catch (Exception d) {
			d.printStackTrace();
		}

	}
}
