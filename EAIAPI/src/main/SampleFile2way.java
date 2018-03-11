package main;

import com.e1.eai.excp.APIException;
import com.e1.eai.service.Eai;

public class SampleFile2way
{
	public static void main(String[] args)
	{
		try
		{
			/** 입력 파라미터중 인터페이스 ID를 String로 넘겨줍니다. **/
			String interfaceID = "FILE2WAY";

			/** 파일명을 지정합니다. **/
			String filename = "C:\\eai\\RA\\interface\\snd\\FILE2WAY\\FILE2WAY.file";
			
			/** 파일 후처리 전문(ex.수신서비스) **/
			String filePostCommand = "File Send Test ...";
			
			/** INTERVAL 시간(sec) **/
			int interval = 10000;
			
			/** 결과값 성공: API0000 **/
			String result = "";
			
			/** API Adapter Load **/
			Eai eai= new Eai();
			
			eai.eaiInit();
			
			/** sendFileSync 수행 **/
			result = eai.eaiSendFISync(interfaceID, filename, filePostCommand, interval);
			
			if(result.equals("API0000"))
				System.out.println("File Send Success");
			
			eai.eaiDestroy();
		}
		catch(APIException e)
		{
			System.out.println("ErrorCode : " + e.getErrorCd());
			System.out.println("   ErrMsg : " + e.getErrorMessage());
			System.out.println(e.printStackTraceMessage());
		}
	}
}
