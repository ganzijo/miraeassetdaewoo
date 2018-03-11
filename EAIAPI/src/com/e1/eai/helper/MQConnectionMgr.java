/*
* @(#)MQConnectionPoolMgr.java   1.0 2005/07/23
 */
package com.e1.eai.helper;

import com.e1.eai.conf.APIConf;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQQueueManager;

/**
 * @author JangJooHwi
 * @version 1.0, 2005/07/23
 *
 *          This class is a Singleton that provides access to one or many
 *          connection pools defined in a Property file. A client gets access to
 *          the single instance through the static getInstance() method and can
 *          then check-out and check-in connections from a pool.
 */
public class MQConnectionMgr {
	/* connPool 사용하지 않음 */
	public static MQQueueManager openConnection(APIConf apiConf) throws Exception {
		MQQueueManager conn = null;
		String realQmgrname = null;

		realQmgrname = apiConf.getQmgr();

		MQEnvironment.hostname = apiConf.getHost();
		MQEnvironment.port = Integer.parseInt(apiConf.getPort());
		MQEnvironment.channel = apiConf.getChannel();
		MQEnvironment.CCSID = Integer.parseInt(apiConf.getCcsid());

		conn = new MQQueueManager(realQmgrname);
		return conn;
	}
}