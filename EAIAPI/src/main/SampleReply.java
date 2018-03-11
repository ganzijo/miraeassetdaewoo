package main;

import com.e1.eai.excp.APIException;
import com.e1.eai.service.Eai;

public class SampleReply
{
	public static void main(String[] args)
	{
		try
		{
			/** InterfaceID **/
			String interfaceID = "JAVA2way";
			
			/** API Adapter Load **/
			Eai eai= new Eai();
			
			eai.eaiInit();
			
			/** 수신메시지를 담을 변수 설정 **/
			String recvData = "";
			
			/** receive 수행 **/
			recvData = eai.eaiRecvAPSyncRead(interfaceID,10000);
			System.out.println("rcvMsg: ["+ recvData + "]");
			
			eai.eaiRecvAPSyncReturn(interfaceID, "reply Data");
			
			eai.eaiDestroy();
		}
		catch(APIException e)
		{
			System.out.println("ErrorCode : " + e.getErrorCd());
			System.out.println("   ErrMsg : " + e.getErrorMessage());
		}
	}
	
	public static void Sleep(int time){
	    try {
	    	Thread.sleep(time);
	    } catch (InterruptedException e) { }

	}
}