package main;

import com.e1.eai.excp.APIException;

public class SampleSend2 {
	static final int retryCnt = 1;
	static boolean eai_status = true;
	static int eaiRetryCnt = 1;
	static int eaiStep = 1;

	public static void main(String[] args) {

		/** 입력 파라미터중 인터페이스 ID를 String로 넘겨줍니다. **/
		String interfaceID = "JAVA1way1";

		/** 입력 파라미터중 sndString를 String로 넘겨줍니다. **/
		String sndString = "callServiceName";

		/** 결과값 성공: API0000 **/
		String result = "";

		try {
			/** APIAdapter ConfigLoad **/
			package_EAI eai = new package_EAI();

			/** APIAdapter MQ Connect **/
			eai.init();
		
			result = eai.Send(interfaceID, sndString);
			System.out.println("결과 : " + result);
			
			/** APIAdapter MQ Disconnect **/
			eai.destroy();
		} catch (APIException ae) {
			ae.printErrorMessage();
		}
	}
}