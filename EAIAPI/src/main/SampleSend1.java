package main;

import com.e1.eai.service.Eai;
import com.e1.eai.excp.APIException;

public class SampleSend1 {
	static final int retryCnt = 1;
	static boolean eai_status = true;
	static int eaiRetryCnt = 1;
	static int eaiStep = 1;

	public static void main(String[] args) {

		Eai eai = null;

		/** 입력 파라미터중 인터페이스 ID를 String로 넘겨줍니다. **/
		String interfaceID = "JAVA1way1";

		/** 입력 파라미터중 sndString를 String로 넘겨줍니다. **/
		String sndString = "callServiceName";

		/** 결과값 성공: API0000 **/
		String result = "";

		/*
		 * 재시도를 위한 step 구분 step 1 : new EAI() checked apiConfig.xml step 2 : MQ init
		 * step 3 : run API step 4 : MQ destory
		 */
		while (eai_status) {
			try {
				switch (eaiStep) {
				case 1:
					System.out.println("STEP " + eaiStep + "============");
					/** APIAdapter ConfigLoad **/
					eai = new Eai();
					eaiStep++;
					break;
				case 2:
					System.out.println("STEP " + eaiStep + "============");
					/** APIAdapter MQ Connect **/
					eai.eaiInit();
					eaiStep++;
					break;
				case 3:
					System.out.println("STEP " + eaiStep + "============");
					
					for (int i = 1; i <= 5; i++) {
						result = eai.eaiSendAPAsync(interfaceID, sndString);

						if (result == "API0000")
							System.out.println(i + "_Msg Snd complete.");
					}
					
					eaiStep++;
					break;
				case 4:
					/** APIAdapter MQ Disconnect **/
					eai.eaiDestroy();
					eai_status = false;
					break;
				}
			} catch (APIException ae) {
				checkException(ae);
			}
		}
	}

	public static void checkException(APIException e) {
		if (eaiRetryCnt <= retryCnt && eaiStep == 3) {
			// Connection Fail Error Retry
			if (e.getErrorCd().equals("MQE2009") || e.getErrorCd().equals("MQE2018")) {
				eaiStep = eaiStep - 1;
				System.out.println("retry " + eaiRetryCnt);
				eaiRetryCnt++;
			} else {
				eai_status = false;
			}
		} else {
			eai_status = false;
		}
	}
}