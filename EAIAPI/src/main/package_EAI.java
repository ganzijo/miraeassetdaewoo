package main;

import com.e1.eai.excp.APIException;
import com.e1.eai.service.Eai;

public class package_EAI {
	public Eai eai = null;

	boolean eaiStatus = true;
	int eaiRetryCnt = 1;
	int eaiStep = 2;

	package_EAI() throws APIException {
		this.eai = new Eai();
	}

	public void init() throws APIException {
		eai.eaiInit();
	}

	public void destroy() throws APIException {
		eai.eaiDestroy();
	}

	public String Send(String interfaceID, String data) throws APIException {
		String result = "";
		eaiRetryCnt = 1;

		while (eaiStatus) {
			try {
				switch (eaiStep) {
				case 1:
					init();
					eaiStep++;
				case 2:
					result = eai.eaiSendAPAsync(interfaceID, data);
					eaiStatus = false;
				}
			} catch (APIException ae) {
				checkException(ae);
			}
		}
		return result;
	}

	public void checkException(APIException e) throws APIException {
		if (eaiRetryCnt <= 2 && eaiStep == 2) {
			// Connection Fail Error Retry
			if (e.getErrorCd().equals("MQE2019") || e.getErrorCd().equals("MQE2085")) {
				eaiStep = 2;
				System.out.println("retry " + eaiRetryCnt);
				eaiRetryCnt++;
			} else if (e.getErrorCd().equals("API0004")) {
				// 중복 Init 오류 시 
				destroy();
				eaiStep = 1;
			} else if (e.getErrorCd().equals("MQE2009") || e.getErrorCd().equals("MQE2018")) {
				// MQ Connection 오류 시
				eaiStep = 1;
			} else {
				eaiStatus = false;
			}
		} else {
			eaiStatus = false;
		}
	}
}