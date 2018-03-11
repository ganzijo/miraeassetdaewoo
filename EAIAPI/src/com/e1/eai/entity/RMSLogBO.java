/**
 * 0. Project	: KT Rainbow Adapter 프로젝트
 * 1. FileName	: RMSTracking.java
 * 2. Package	: com.enterprise1.common.tracking
 * 3. Commnet	: RMG Log Data BO
 * 4. 작성자		: 김현수
 * 5. 작성일		: 2015. 2. 10. 오전 10:54:00
 * 6. 변경이력	:
 *	  이름						:일자			:	변경내용
 *	------------------------------------------------------
 *	hskim@enterprise1.co.kr		:2015. 2. 10.	:	신규 개발
 * 	sakim@enterprise1.co.kr		:2015. 9. 16.	: 	주석 정리
 *
 */
package com.e1.eai.entity;

import com.e1.eai.conf.constant.ConfigConst;

/**
 * RMG Log Data BO
 */
public class RMSLogBO {
	
	/**
	 * RMS Log Delimiter 
	 */ 
	private static String RMS_DELIMITER = ConfigConst.SEP_RMS;
	
	/**
	 * 1. 수행 일자 (Hop 생성 일자)
	 */
	private String PT_DT;
	
	/**
	 * 2. Global Unique ID (전문 작성시각+브로커 명+플로우 명+랜덤 문자열+쓰레드 번호)
	 */
	private String GUID;
	
	/**
	 * 3. 인터페이스 ID (또는 서비스 ID - 고유한 값)
	 */
	private String IFID;
	
	/**
	 * 4. Hop Count (인터페이스 처리 순서)
	 */
	private String HOP_CNT;
	
	/**
	 * 5. Custom 수행 시간 01 (송신 - DB Polling Time / 수신 - Queue Polling Time)
	 */
	private String PROC_TM_CUST_01;
	
	/**
	 * 6. Custom 수행 시간 02 (송신 - Queue Put Time / 수신 - DB Put Before Time)
	 */
	private String PROC_TM_CUST_02;
	
	/**
	 * 7. Custom 수행 시간 03 (송신 - Interface Table Update Time / 수신 - DB Put After Time)
	 */
	private String PROC_TM_CUST_03;
	
	/**
	 * 8. Custom 수행 시간 04 (미사용)
	 */
	private String PROC_TM_CUST_04;
	
	/**
	 * 9. Custom 수행 시간 05 (미사용)
	 */
	private String PROC_TM_CUST_05;
	
	/**
	 * 10. 수행 시간 (메시지 처리 시간)
	 */
	private String PROC_TM;
	
	/**
	 * 11. 서비스 타입 (대상 웹 서비스 명)
	 */
	private String TYPE;
	
	/**
	 * 12. 송신(요청) 시스템 명
	 */
	private String SND_SYS_NM;
	
	/**
	 * 13. 수신(응답) 시스템 명
	 */
	private String RCV_SYS_NM;
	
	/**
	 * 14. 송신(요청) IP
	 */
	private String SND_IP;
	
	/**
	 * 15. 수신(응답) IP
	 */
	private String RCV_IP;
	
	/**
	 * 16. 리턴 코드 - 메시지 처리 성공 유무 (0 : 성공 / 2 : 실패)
	 */	
	private String RTN_CD;
	
	/**
	 * 17. 메세지 처리 실패시 에러 코드
	 */
	private String ERR_CD;
	
	/**
	 * 18. 에러 메시지
	 */
	private String ERR_MSG;
	
	/**
	 * 19. 에러 상세 메세지 (상세 에러 내용)
	 */
	private String ERR_DETAIL;
	
	/**
	 * 20. 메시지 전문 길이
	 */
	private String DATA_LEN;
	
	/**
	 * 21. 큐 매니져 명
	 */
	private String QMGR_NM;

	/**
	 * 22. 브로커 명
	 */
	private String BK_NM;

	/**
	 * 23. 실행 그룹 명
	 */
	private String EG_NM;

	/**
	 * 24. 플로우 명
	 */
	private String MF_NM;

	/**
	 * 25. Queue 명
	 */
	private String Q_NM;

	/**
	 * 26. 전문 데이터 내용
	 */
	private String MSG;

	/**
	 * 27. RM_LOG DB 삽입 시간 (트래커에서 Insert)
	 */
	private String INSERT_DT;

	/**
	 * 28. 처리 구분 Flag (RM_LOG 데이터 가공 유무 구분 Flag)
	 */
	private String FLAG;

	/**
	 * 29. 
	 */
	private String RCOL_1;

	/**
	 * 30. 
	 */
	private String RCOL_2;
	

	/**
	 * 31. 
	 */
	private String RCOL_3;
	
	/**
	 * 32. 
	 */
	private String RCOL_4;
	
	/**
	 * 33. 
	 */
	private String RCOL_5;
	
	/**
	 * 34. 
	 */
	private String RCOL_6;
	
	/**
	 * 35. 
	 */
	private String RCOL_7;
	
	/**
	 * 36. 
	 */
	private String RCOL_8;
	
	/**
	 * 37. 
	 */
	private String RCOL_9;
	
	/**
	 * 38. 
	 */
	private String RCOL_10;
	
	/**
	 * 39. 
	 */
	private String RCOL_11;
	
	/**
	 * 40. 
	 */
	private String RCOL_12;
	
	/**
	 * 41. 
	 */
	private String RCOL_13;

	/**
	 * Bring the value of 'RMS Log PT_DT'
	 * 
	 * @return the pT_DT
	 */
	public String getPT_DT() {
		if(PT_DT==null){
			//return GUID.substring(0, 14); // 15.06.05 다시 14자리로 
			return GUID.substring(0, 8);  // 15.05.28 KT 설치된 RMS에 맞춰 PT_DT 8자리로 자름
		}
		return PT_DT;
	}

	/**
	 * Bring the value of 'RMS Log GUID'
	 * 
	 * @return the gUID
	 */
	public String getGUID() {
		if(GUID==null){
			return "";
		}
		return GUID;
	}

	/**
	 * Bring the value of 'RMS Log IFID'
	 * 
	 * @return the iFID
	 */
	public String getIFID() {
		if(IFID==null){
			return "";
		}
		return IFID;
	}

	/**
	 * Bring the value of 'RMS Log HOP_CNT'
	 * 
	 * @return the hOP_CNT
	 */
	public String getHOP_CNT() {
		if(HOP_CNT==null){
			return "";
		}
		return HOP_CNT;
	}

	/**
	 * Bring the value of 'RMS Log PROC_TM_CUST_01'
	 * 
	 * @return the pROC_TM_CUST_01
	 */
	public String getPROC_TM_CUST_01() {
		//System.out.println("PROC_TM_CUST_01" + PROC_TM_CUST_01);
		if(PROC_TM_CUST_01==null){
			return "";
		}
		return PROC_TM_CUST_01;
	}

	/**
	 * Bring the value of 'RMS Log PROC_TM_CUST_02'
	 * 
	 * @return the pROC_TM_CUST_02
	 */
	public String getPROC_TM_CUST_02() {
		//System.out.println("PROC_TM_CUST_02" + PROC_TM_CUST_02);
		if(PROC_TM_CUST_02==null){
			return "";
		}
		return PROC_TM_CUST_02;
	}

	/**
	 * Bring the value of 'RMS Log PROC_TM_CUST_03'
	 * 
	 * @return the pROC_TM_CUST_03
	 */
	public String getPROC_TM_CUST_03() {
		//System.out.println("PROC_TM_CUST_03" + PROC_TM_CUST_03);
		if(PROC_TM_CUST_03==null){
			return "";
		}
		return PROC_TM_CUST_03;
	}

	/**
	 * Bring the value of 'RMS Log PROC_TM_CUST_04'
	 * 
	 * @return the pROC_TM_CUST_04
	 */
	public String getPROC_TM_CUST_04() {
		//System.out.println("PROC_TM_CUST_04" + PROC_TM_CUST_04);
		if(PROC_TM_CUST_04==null){
			return "";
		}
		return PROC_TM_CUST_04;
	}

	/**
	 * Bring the value of 'RMS Log PROC_TM_CUST_05'
	 * 
	 * @return the pROC_TM_CUST_05
	 */
	public String getPROC_TM_CUST_05() {
		if(PROC_TM_CUST_05==null){
			return "";
		}
		return PROC_TM_CUST_05;
	}

	/**
	 * Bring the value of 'RMS Log PROC_TM'
	 * 
	 * @return the pROC_TM
	 */
	public String getPROC_TM() {
		if(PROC_TM==null){
			return "";
		}
		return PROC_TM;
	}

	/**
	 * Bring the value of 'RMS Log TYPE'
	 * 
	 * @return the tYPE
	 */
	public String getTYPE() {
		if(TYPE==null){
			return "";
		}
		return TYPE;
	}

	/**
	 * Bring the value of 'RMS Log SND_SYS_NM'
	 * 
	 * @return the sND_SYS_NM
	 */
	public String getSND_SYS_NM() {
		if(SND_SYS_NM==null){
			return "";
		}
		return SND_SYS_NM;
	}

	/**
	 * Bring the value of 'RMS Log RCV_SYS_NM'
	 * 
	 * @return the rCV_SYS_NM
	 */
	public String getRCV_SYS_NM() {
		if(RCV_SYS_NM==null){
			return "";
		}
		return RCV_SYS_NM;
	}

	/**
	 * Bring the value of 'RMS Log SND_IP'
	 * 
	 * @return the sND_IP
	 */
	public String getSND_IP() {
		if(SND_IP==null){
			return "";
		}
		return SND_IP;
	}

	/**
	 * Bring the value of 'RMS Log RCV_IP'
	 * 
	 * @return the rCV_IP
	 */
	public String getRCV_IP() {
		if(RCV_IP==null){
			return "";
		}
		return RCV_IP;
	}

	/**
	 * Bring the value of 'RMS Log RTN_CD'
	 * 
	 * @return the rTN_CD
	 */
	public String getRTN_CD() {
		if(RTN_CD==null){
			return "";
		}
		return RTN_CD;
	}

	/**
	 * Bring the value of 'RMS Log ERR_CD'
	 * 
	 * @return the eRR_CD
	 */
	public String getERR_CD() {
		if(ERR_CD==null){
			return "";
		}
		return ERR_CD;
	}

	/**
	 * Bring the value of 'RMS Log ERR_MSG'
	 * 
	 * @return the eRR_MSG
	 */
	public String getERR_MSG() {
		if(ERR_MSG==null){
			return "";
		}
		return ERR_MSG;
	}

	/**
	 * Bring the value of 'RMS Log ERR_DETAIL'
	 * 
	 * @return the eRR_DETAIL
	 */
	public String getERR_DETAIL() {
		if(ERR_DETAIL==null){
			return "";
		}
		return ERR_DETAIL;
	}

	/**
	 * Bring the value of 'RMS Log DATA_LEN'
	 * 
	 * @return the dATA_LEN
	 */
	public String getDATA_LEN() {
		if(DATA_LEN==null){
			if(MSG!=null){
				MSG.length();
			}else{
				return "";
			}
		}
		return DATA_LEN;
	}

	/**
	 * Bring the value of 'RMS Log QMGR_NM'
	 * 
	 * @return the qMGR_NM
	 */
	public String getQMGR_NM() {
		if(QMGR_NM==null){
			return "";
		}
		return QMGR_NM;
	}

	/**
	 * Bring the value of 'RMS Log BK_NM'
	 * 
	 * @return the bK_NM
	 */
	public String getBK_NM() {
		if(BK_NM==null){
			return "";
		}
		return BK_NM;
	}

	/**
	 * Bring the value of 'RMS Log EG_NM'
	 * 
	 * @return the eG_NM
	 */
	public String getEG_NM() {
		if(EG_NM==null){
			return "";
		}
		return EG_NM;
	}

	/**
	 * Bring the value of 'RMS Log MF_NM'
	 * 
	 * @return the mF_NM
	 */
	public String getMF_NM() {
		if(MF_NM==null){
			return "";
		}
		return MF_NM;
	}

	/**
	 * Bring the value of 'RMS Log Q_NM'
	 * 
	 * @return the q_NM
	 */
	public String getQ_NM() {
		if(Q_NM==null){
			return "";
		}
		return Q_NM;
	}

	/**
	 * Bring the value of 'RMS Log MSG'
	 * 
	 * @return the mSG
	 */
	public String getMSG() {
		if(MSG==null){
			return "";
		}
		return MSG;
	}

	/**
	 * Bring the value of 'RMS Log INSERT_DT'
	 * 
	 * @return the iNSERT_DT
	 */
	public String getINSERT_DT() {
		if(INSERT_DT==null){
			return "";
		}
		return INSERT_DT;
	}

	/**
	 * Bring the value of 'RMS Log FLAG'
	 * 
	 * @return the fLAG
	 */
	public String getFLAG() {
		if(FLAG==null){
			return "";
		}
		return FLAG;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_1'
	 * 
	 * @return the rCOL_1
	 */
	public String getRCOL_1() {
		if(RCOL_1==null){
			return "";
		}
		return RCOL_1;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_2'
	 * 
	 * @return the rCOL_2
	 */
	public String getRCOL_2() {
		if(RCOL_2==null){
			return "";
		}
		return RCOL_2;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_3'
	 * 
	 * @return the rCOL_3
	 */
	public String getRCOL_3() {
		if(RCOL_3==null){
			return "";
		}
		return RCOL_3;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_4'
	 * 
	 * @return the rCOL_4
	 */
	public String getRCOL_4() {
		if(RCOL_4==null){
			return "";
		}
		return RCOL_4;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_5'
	 * 
	 * @return the rCOL_5
	 */
	public String getRCOL_5() {
		if(RCOL_5==null){
			return "";
		}
		return RCOL_5;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_6'
	 * 
	 * @return the rCOL_6
	 */
	public String getRCOL_6() {
		if(RCOL_6==null){
			return "";
		}
		return RCOL_6;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_7'
	 * 
	 * @return the rCOL_7
	 */
	public String getRCOL_7() {
		if(RCOL_7==null){
			return "";
		}
		return RCOL_7;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_8'
	 * 
	 * @return the rCOL_8
	 */
	public String getRCOL_8() {
		if(RCOL_8==null){
			return "";
		}
		return RCOL_8;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_9'
	 * 
	 * @return the rCOL_9
	 */
	public String getRCOL_9() {
		if(RCOL_9==null){
			return "";
		}
		return RCOL_9;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_10'
	 * 
	 * @return the rCOL_10
	 */
	public String getRCOL_10() {
		if(RCOL_10==null){
			return "";
		}
		return RCOL_10;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_11'
	 * 
	 * @return the rCOL_11
	 */
	public String getRCOL_11() {
		if(RCOL_11==null){
			return "";
		}
		return RCOL_11;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_12'
	 * 
	 * @return the rCOL_12
	 */
	public String getRCOL_12() {
		if(RCOL_12==null){
			return "";
		}
		return RCOL_12;
	}

	/**
	 * Bring the value of 'RMS Log RCOL_13'
	 * 
	 * @return the rCOL_13
	 */
	public String getRCOL_13() {
		if(RCOL_13==null){
			return "";
		}
		return RCOL_13;
	}

	/**
	 * Setting the value of 'RMS Log PT_DT'
	 * 
	 * @param pT_DT the pT_DT to set
	 */
	public void setPT_DT(String pT_DT) {
		PT_DT = pT_DT;
	}

	/**
	 * Setting the value of 'RMS Log GUID'
	 * 
	 * @param gUID the gUID to set
	 */
	public void setGUID(String gUID) {
		GUID = gUID;
	}

	/**
	 * Setting the value of 'RMS Log IFID'
	 * 
	 * @param iFID the iFID to set
	 */
	public void setIFID(String iFID) {
		IFID = iFID;
	}

	/**
	 * Setting the value of 'RMS Log HOP_CNT'
	 * 
	 * @param hOP_CNT the hOP_CNT to set
	 */
	public void setHOP_CNT(String hOP_CNT) {
		HOP_CNT = hOP_CNT;
	}

	/**
	 * Setting the value of 'RMS Log PROC_TM_CUST_01'
	 * 
	 * @param pROC_TM_CUST_01 the pROC_TM_CUST_01 to set
	 */
	public void setPROC_TM_CUST_01(String pROC_TM_CUST_01) {
		PROC_TM_CUST_01 = pROC_TM_CUST_01;
	}

	/**
	 * Setting the value of 'RMS Log PROC_TM_CUST_02'
	 * 
	 * @param pROC_TM_CUST_02 the pROC_TM_CUST_02 to set
	 */
	public void setPROC_TM_CUST_02(String pROC_TM_CUST_02) {
		PROC_TM_CUST_02 = pROC_TM_CUST_02;
	}

	/**
	 * Setting the value of 'RMS Log PROC_TM_CUST_03'
	 * 
	 * @param pROC_TM_CUST_03 the pROC_TM_CUST_03 to set
	 */
	public void setPROC_TM_CUST_03(String pROC_TM_CUST_03) {
		PROC_TM_CUST_03 = pROC_TM_CUST_03;
	}

	/**
	 * Setting the value of 'RMS Log PROC_TM_CUST_04'
	 * 
	 * @param pROC_TM_CUST_04 the pROC_TM_CUST_04 to set
	 */
	public void setPROC_TM_CUST_04(String pROC_TM_CUST_04) {
		PROC_TM_CUST_04 = pROC_TM_CUST_04;
	}

	/**
	 * Setting the value of 'RMS Log PROC_TM_CUST_05'
	 * 
	 * @param pROC_TM_CUST_05 the pROC_TM_CUST_05 to set
	 */
	public void setPROC_TM_CUST_05(String pROC_TM_CUST_05) {
		PROC_TM_CUST_05 = pROC_TM_CUST_05;
	}

	/**
	 * Setting the value of 'RMS Log PROC_TM'
	 * 
	 * @param pROC_TM the pROC_TM to set
	 */
	public void setPROC_TM(String pROC_TM) {
		PROC_TM = pROC_TM;
	}

	/**
	 * Setting the value of 'RMS Log TYPE'
	 * 
	 * @param tYPE the tYPE to set
	 */
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	/**
	 * Setting the value of 'RMS Log SND_SYS_NM'
	 * 
	 * @param sND_SYS_NM the sND_SYS_NM to set
	 */
	public void setSND_SYS_NM(String sND_SYS_NM) {
		SND_SYS_NM = sND_SYS_NM;
	}

	/**
	 * Setting the value of 'RMS Log RCV_SYS_NM'
	 * 
	 * @param rCV_SYS_NM the rCV_SYS_NM to set
	 */
	public void setRCV_SYS_NM(String rCV_SYS_NM) {
		RCV_SYS_NM = rCV_SYS_NM;
	}

	/**
	 * Setting the value of 'RMS Log SND_IP'
	 * 
	 * @param sND_IP the sND_IP to set
	 */
	public void setSND_IP(String sND_IP) {
		SND_IP = sND_IP;
	}

	/**
	 * Setting the value of 'RMS Log RCV_IP'
	 * 
	 * @param rCV_IP the rCV_IP to set
	 */
	public void setRCV_IP(String rCV_IP) {
		RCV_IP = rCV_IP;
	}

	/**
	 * Setting the value of 'RMS Log RTN_CD'
	 * 
	 * @param rTN_CD the rTN_CD to set
	 */
	public void setRTN_CD(String rTN_CD) {
		RTN_CD = rTN_CD;
	}

	/**
	 * Setting the value of 'RMS Log ERR_CD'
	 * 
	 * @param eRR_CD the eRR_CD to set
	 */
	public void setERR_CD(String eRR_CD) {
		ERR_CD = eRR_CD;
	}

	/**
	 * Setting the value of 'RMS Log'
	 * 
	 * @param eRR_MSG the eRR_MSG to set
	 */
	public void setERR_MSG(String eRR_MSG) {
		ERR_MSG = eRR_MSG;
	}

	/**
	 * Setting the value of 'RMS Log'
	 * 
	 * @param eRR_DETAIL the eRR_DETAIL to set
	 */
	public void setERR_DETAIL(String eRR_DETAIL) {
		ERR_DETAIL = eRR_DETAIL;
	}

	/**
	 * Setting the value of 'RMS Log '
	 * 
	 * @param dATA_LEN the dATA_LEN to set
	 */
	public void setDATA_LEN(String dATA_LEN) {
		DATA_LEN = dATA_LEN;
	}

	/**
	 * Setting the value of 'RMS Log QMGR_NM'
	 * 
	 * @param qMGR_NM the qMGR_NM to set
	 */
	public void setQMGR_NM(String qMGR_NM) {
		QMGR_NM = qMGR_NM;
	}

	/**
	 * Setting the value of 'RMS Log BK_NM'
	 * 
	 * @param bK_NM the bK_NM to set
	 */
	public void setBK_NM(String bK_NM) {
		BK_NM = bK_NM;
	}

	/**
	 * Setting the value of 'RMS Log EG_NM'
	 * 
	 * @param eG_NM the eG_NM to set
	 */
	public void setEG_NM(String eG_NM) {
		EG_NM = eG_NM;
	}

	/**
	 * Setting the value of 'RMS Log'
	 * 
	 * @param mF_NM the mF_NM to set
	 */
	public void setMF_NM(String mF_NM) {
		MF_NM = mF_NM;
	}

	/**
	 * Setting the value of 'RMS Log'
	 * 
	 * @param q_NM the q_NM to set
	 */
	public void setQ_NM(String q_NM) {
		Q_NM = q_NM;
	}

	/**
	 * Setting the value of 'RMS Log'
	 * 
	 * @param mSG the mSG to set
	 */
	public void setMSG(String mSG) {
		MSG = mSG;
	}

	/**
	 * Setting the value of 'RMS Log INSERT_DT'
	 * 
	 * @param iNSERT_DT the iNSERT_DT to set
	 */
	public void setINSERT_DT(String iNSERT_DT) {
		INSERT_DT = iNSERT_DT;
	}

	/**
	 * Setting the value of 'RMS Log FLAG'
	 * 
	 * @param fLAG the fLAG to set
	 */
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_1'
	 * 
	 * @param rCOL_1 the rCOL_1 to set
	 */
	public void setRCOL_1(String rCOL_1) {
		RCOL_1 = rCOL_1;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_2'
	 * 
	 * @param rCOL_2 the rCOL_2 to set
	 */
	public void setRCOL_2(String rCOL_2) {
		RCOL_2 = rCOL_2;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_3'
	 * 
	 * @param rCOL_3 the rCOL_3 to set
	 */
	public void setRCOL_3(String rCOL_3) {
		RCOL_3 = rCOL_3;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_4'
	 * 
	 * @param rCOL_4 the rCOL_4 to set
	 */
	public void setRCOL_4(String rCOL_4) {
		RCOL_4 = rCOL_4;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_5'
	 * 
	 * @param rCOL_5 the rCOL_5 to set
	 */
	public void setRCOL_5(String rCOL_5) {
		RCOL_5 = rCOL_5;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_6'
	 * 
	 * @param rCOL_6 the rCOL_6 to set
	 */
	public void setRCOL_6(String rCOL_6) {
		RCOL_6 = rCOL_6;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_7'
	 * 
	 * @param rCOL_7 the rCOL_7 to set
	 */
	public void setRCOL_7(String rCOL_7) {
		RCOL_7 = rCOL_7;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_8'
	 * 
	 * @param rCOL_8 the rCOL_8 to set
	 */
	public void setRCOL_8(String rCOL_8) {
		RCOL_8 = rCOL_8;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_9'
	 * 
	 * @param rCOL_9 the rCOL_9 to set
	 */
	public void setRCOL_9(String rCOL_9) {
		RCOL_9 = rCOL_9;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_10'
	 * 
	 * @param rCOL_10 the rCOL_10 to set
	 */
	public void setRCOL_10(String rCOL_10) {
		RCOL_10 = rCOL_10;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_11'
	 * 
	 * @param rCOL_11 the rCOL_11 to set
	 */
	public void setRCOL_11(String rCOL_11) {
		RCOL_11 = rCOL_11;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_12'
	 * 
	 * @param rCOL_12 the rCOL_12 to set
	 */
	public void setRCOL_12(String rCOL_12) {
		RCOL_12 = rCOL_12;
	}

	/**
	 * Setting the value of 'RMS Log RCOL_13'
	 * 
	 * @param rCOL_13 the rCOL_13 to set
	 */
	public void setRCOL_13(String rCOL_13) {
		RCOL_13 = rCOL_13;
	}
	
	/**
	 * Setting the value of 'RAHeader(GUID, HOP_CNT, IFID, DATA_LEN)'
	 * 
	 * @param raHeaderbo
	 */
	public void setRAHeader(InterfaceBO ifConf, APIMsgBO apiMsg){
		/*  1 */ setPT_DT(ifConf.getPT_DT());
		/*  2 */ setGUID(ifConf.getMsgId());
		/*  3 */ setIFID(ifConf.getInterfaceId());
		/*  4 */ setHOP_CNT(apiMsg.getHopCount());
		/*  5 */ setPROC_TM_CUST_01(ifConf.getTriggerTime());
		/*  6 */ setPROC_TM_CUST_02(ifConf.getMsgMakeTime());
		/*  7 */ setPROC_TM_CUST_03(ifConf.getMsgPutTime());
		/*  8 */ setPROC_TM_CUST_04(ifConf.getMsgFinTime());
		/*  9 */ //setPROC_TM_CUST_05(""); - not used
		/* 10 */ setPROC_TM(ifConf.getMsgMakeTime());
		/* 11 */ setTYPE(ConfigConst.ADT_TYPE);
		/* 12 */ //setSND_SYS_NM("");
		/* 13 */ //setRCV_SYS_NM("");
		/* 14 */ //setSND_IP("");
		/* 15 */ //setRCV_IP("");
		/* 16 */ setRTN_CD(ConfigConst.RMS_SUCCESS);
		/* 17 */ //setERR_CD("");
		/* 18 */ //setERR_MSG("");
		/* 19 */ //setERR_DETAIL("");
		/* 20 */ setDATA_LEN(ifConf.getRecordLengt());
		/* 21 */ setQMGR_NM(ifConf.getApiConf().getQmgr());
		/* 22 */ //setBK_NM("");
		/* 23 */ //setEG_NM("");
		/* 24 */ //setMF_NM("");
		/* 25 */ setQ_NM(ifConf.getPutQ());
		/* 26 */
		if        (ifConf.getApiConf().getTraceLevel().equals("01")) {
			setMSG(apiMsg.getAPIHeader());
		} else if (ifConf.getApiConf().getTraceLevel().equals("02")) {
			setMSG(apiMsg.getAPIFullMsg().substring(0, 1000));
		} else if (ifConf.getApiConf().getTraceLevel().equals("03")) {
			setMSG(apiMsg.getAPIFullMsg());
		} else { // TraceLevel() == "00"
			setMSG("");
		}
		/* 27 */ //setINSERT_DT("");
		/* 28 */ setFLAG("");
		/* 29 */ setRCOL_1("1");
		/* 30 */ setRCOL_2("1");
		/* 31 */ setRCOL_3(ConfigConst.ADT_TYPE);
		/* 32 */ //setRCOL_4("");
	}
	
	/**
	 * RMS LOG Message
	 */
	public String getRMSLog(){
		String rmsMsg =  getPT_DT()+RMS_DELIMITER+
				getGUID()+RMS_DELIMITER+
				getIFID()+RMS_DELIMITER+
				getHOP_CNT()+RMS_DELIMITER+
				getPROC_TM_CUST_01()+RMS_DELIMITER+
				getPROC_TM_CUST_02()+RMS_DELIMITER+
				getPROC_TM_CUST_03()+RMS_DELIMITER+
				getPROC_TM_CUST_04()+RMS_DELIMITER+
				getPROC_TM_CUST_05()+RMS_DELIMITER+
				getPROC_TM()+RMS_DELIMITER+
				getTYPE()+RMS_DELIMITER+
				getSND_SYS_NM()+RMS_DELIMITER+
				getRCV_SYS_NM()+RMS_DELIMITER+
				getSND_IP()+RMS_DELIMITER+
				getRCV_IP()+RMS_DELIMITER+
				getRTN_CD()+RMS_DELIMITER+
				getERR_CD()+RMS_DELIMITER+
				getERR_MSG()+RMS_DELIMITER+
				getERR_DETAIL()+RMS_DELIMITER+
				getDATA_LEN()+RMS_DELIMITER+
				getQMGR_NM()+RMS_DELIMITER+
				getBK_NM()+RMS_DELIMITER+
				getEG_NM()+RMS_DELIMITER+
				getMF_NM()+RMS_DELIMITER+
				getQ_NM()+RMS_DELIMITER+
				getMSG()+RMS_DELIMITER+
				getINSERT_DT()+RMS_DELIMITER+
				getFLAG()+RMS_DELIMITER+
				getRCOL_1()+RMS_DELIMITER+
				getRCOL_2()+RMS_DELIMITER+
				getRCOL_3()+RMS_DELIMITER+
				getRCOL_4()+RMS_DELIMITER+
				getRCOL_5()+RMS_DELIMITER+
				getRCOL_6()+RMS_DELIMITER+
				getRCOL_7()+RMS_DELIMITER+
				getRCOL_8()+RMS_DELIMITER+
				getRCOL_9()+RMS_DELIMITER+
				getRCOL_10()+RMS_DELIMITER+
				getRCOL_11()+RMS_DELIMITER+
				getRCOL_12()+RMS_DELIMITER+
				getRCOL_13();
		
		//System.out.println("rmsMsg = " + rmsMsg);
		return rmsMsg;
	}
	
	/**
	 * RMS LOG Message
	 */
	public String printRmsLog(){
		
		return "PT_DT = " + 			getPT_DT()				+"\n"+ 
				"GUID = " + 			getGUID()				+"\n"+
				"IFID = " + 			getIFID()				+"\n"+
				"HOP_CNT = " + 			getHOP_CNT()			+"\n"+
				"PROC_TM_CUST_01 = " + 	getPROC_TM_CUST_01()	+"\n"+
				"PROC_TM_CUST_02 = " + 	getPROC_TM_CUST_02()	+"\n"+
				"PROC_TM_CUST_03 = " + 	getPROC_TM_CUST_03()	+"\n"+
				"PROC_TM_CUST_04 = " + 	getPROC_TM_CUST_04()	+"\n"+
				"PROC_TM_CUST_05 = " + 	getPROC_TM_CUST_05()	+"\n"+
				"PROC_TM = " + 			getPROC_TM()			+"\n"+
				"TYPE = " + 			getTYPE()				+"\n"+
				"SND_SYS_NM = " + 		getSND_SYS_NM()			+"\n"+
				"RCV_SYS_NM = " + 		getRCV_SYS_NM()			+"\n"+
				"SND_IP = " + 			getSND_IP()				+"\n"+
				"RCV_IP = " + 			getRCV_IP()				+"\n"+
				"RTN_CD = " + 			getRTN_CD()				+"\n"+
				"ERR_CD = " + 			getERR_CD()				+"\n"+
				"ERR_MSG = " + 			getERR_MSG()			+"\n"+
				"ERR_DETAIL = " + 		getERR_DETAIL()			+"\n"+
				"DATA_LEN = " + 		getDATA_LEN()			+"\n"+
				"QMGR_NM = " + 			getQMGR_NM()			+"\n"+
				"BK_NM = " + 			getBK_NM()				+"\n"+
				"EG_NM = " + 			getEG_NM()				+"\n"+
				"MF_NM = " + 			getMF_NM()				+"\n"+
				"Q_NM = " + 			getQ_NM()				+"\n"+
				"MSG = " + 				getMSG()				+"\n"+
				"INSERT_DT = " + 		getINSERT_DT()			+"\n"+
				"FLAG = " + 			getFLAG()				+"\n"+
				"RCOL_1 = " + 			getRCOL_1()				+"\n"+
				"RCOL_2 = " + 			getRCOL_2()				+"\n"+
				"RCOL_3 = " + 			getRCOL_3()				+"\n"+
				"RCOL_4 = " + 			getRCOL_4()				+"\n"+
				"RCOL_5 = " + 			getRCOL_5()				+"\n"+
				"RCOL_6 = " + 			getRCOL_6()				+"\n"+
				"RCOL_7 = " + 			getRCOL_7()				+"\n"+
				"RCOL_8 = " + 			getRCOL_8()				+"\n"+
				"RCOL_9 = " + 			getRCOL_9()				+"\n"+
				"RCOL_10 = " + 			getRCOL_10()			+"\n"+
				"RCOL_11 = " + 			getRCOL_11()			+"\n"+
				"RCOL_12 = " + 			getRCOL_12()			+"\n"+
				"RCOL_13 = " + 			getRCOL_13()
				;
	}
	
	public String lengthInspection(){
		StringBuilder lengthErrValueSB = new StringBuilder();
		
		if(PT_DT.length() != 14){
			lengthErrValueSB.append("PT_DT("+PT_DT+")\r\n");
		}
		if(GUID.length() != 45){
			lengthErrValueSB.append("\t\t\t\tGUID("+GUID+")\r\n");
		}
		if(IFID.length() != 24){
			lengthErrValueSB.append("\t\t\t\tIFID("+IFID+")\r\n");
		}
		if(HOP_CNT.length() != 2){
			lengthErrValueSB.append("\t\t\t\tHOP_CNT("+HOP_CNT+")\r\n");
		}
		if(PROC_TM_CUST_01.length() != 17){
			lengthErrValueSB.append("\t\t\t\tPROC_TM_CUST_01("+PROC_TM_CUST_01+")\r\n");
		}
		if(PROC_TM_CUST_02.length() != 17){
			lengthErrValueSB.append("\t\t\t\tPROC_TM_CUST_02("+PROC_TM_CUST_02+")\r\n");
		}
		if(PROC_TM.length() != 17){
			lengthErrValueSB.append("\t\t\t\tPROC_TM("+PROC_TM+")\r\n");
		}
		if(TYPE.length() != 2){
			lengthErrValueSB.append("\t\t\t\tTYPE("+TYPE+")\r\n");
		}
		if(RTN_CD.length() != 1){
			lengthErrValueSB.append("\t\t\t\tRTN_CD("+RTN_CD+")\r\n");
		}
		
		return lengthErrValueSB.toString();
	}

}
