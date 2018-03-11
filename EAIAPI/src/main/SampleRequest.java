package main;

import com.e1.eai.excp.APIException;
import com.e1.eai.service.Eai;

public class SampleRequest
{
	public static void main(String[] args)
	{
		try
		{
			String interfaceID = "JAVA2way";

			/** 입력 파라미터중 테이터를  넘겨줍니다. **/
			String sndData = "Request Test!!";

			/** INTERVAL 시간(sec)  **/
			int intervaltime = 120000;
			
			/** 수신메시지를 담을 변수 설정 **/
			String rcvData = "";
			
			/** API Adapter Load **/
			Eai eai= new Eai();
			
			eai.eaiInit();
			
			/** request 수행 **/
			rcvData = eai.eaiSendAPSync(interfaceID, sndData, intervaltime);
			System.out.println("success Msg send. return Msg is [" + rcvData + "]");
			
			eai.eaiDestroy();
		}
		catch(APIException e)
		{
			System.out.println("ErrorCode : " + e.getErrorCd());
			System.out.println("   ErrMsg : " + e.getErrorMessage());
		}
	}
}