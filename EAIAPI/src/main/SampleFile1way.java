package main;

import com.e1.eai.excp.APIException;
import com.e1.eai.service.Eai;

public class SampleFile1way {
	public static void main(String[] args) {
		try {
			/** 입력 파라미터중 인터페이스 ID를 String로 넘겨줍니다. **/
			String interfaceID = "FILE1WAY";

			/** 파일명을 지정합니다. **/
			String filename = "C:\\eai\\RA\\interface\\snd\\FILE1WAY\\FILE1WAY.file";

			/** 파일 후처리 전문(ex.수신서비스) **/
			String filePostCommand = "File Send Test ...";

			/** API Adapter Load **/
			Eai eai = new Eai();
			eai.eaiInit();

			String result = eai.eaiSendFIAsync(interfaceID, filename, filePostCommand);

			if (result == "API0000") {
				System.out.println("File Send Success, result : [" + result + "]");
			}
			eai.eaiDestroy();
			
		} catch (APIException e) {
			System.out.println("ErrorCode : " + e.getErrorCd());
			System.out.println("   ErrMsg : " + e.getErrorMessage());
		}
	}
}